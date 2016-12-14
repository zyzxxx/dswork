package dswork.html.nodes;

import static dswork.html.nodes.Entities.EscapeMode.characters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dswork.html.parser.HtmlReader;
import dswork.html.parser.StringUtil;

/**
 * 只放了几个常用的转码，可自行扩展
 * http://www.w3.org/TR/html5/named-character-references.html#named-character-references
 */
public class Entities
{
	private static final int empty = -1;
	private static final String emptyName = "";

	public enum EscapeMode
	{
		characters("entities.properties");
		private int[] codeVals;
		private String[] nameVals;

		EscapeMode(String file)
		{
			load(this, file);
		}

		int codepointForName(final String name)
		{
			int index = Arrays.binarySearch(nameVals, name);
			return index >= 0 ? codeVals[index] : empty;
		}

		String nameForCodepoint(final int codepoint)
		{
			final int index = Arrays.binarySearch(codeVals, codepoint);
			if(index >= 0)
			{
				//return nameVals[index];
				// the results are ordered so lower case versions of same codepoint come after uppercase, and we prefer
				// to emit lower
				// (and binary search for same item with multi results is undefined
				return (index < nameVals.length - 1 && codeVals[index + 1] == codepoint) ? nameVals[index + 1] : nameVals[index];
			}
			return emptyName;
		}
	}
	
	private Entities()
	{
	}

	public static boolean isNamedEntity(final String name)
	{
		return characters.codepointForName(name) != empty;
	}

	public static String getByName(String name)
	{
		int codepoint = characters.codepointForName(name);
		if(codepoint != empty)
			return new String(new int[]{codepoint}, 0, 1);
		return emptyName;
	}

	public static int codepointsForName(final String name, final int[] codepoints)
	{
		int codepoint = characters.codepointForName(name);
		if(codepoint != empty)
		{
			codepoints[0] = codepoint;
			return 1;
		}
		return 0;
	}

	static void escape(Appendable accum, String string, Document out, boolean inAttribute, boolean normaliseWhite, boolean stripLeadingWhite) throws IOException
	{
		boolean lastWasWhite = false;
		boolean reachedNonWhite = false;
		final CharsetEncoder encoder = out.encoder();
		final CoreCharset coreCharset = CoreCharset.byName(encoder.charset().name());
		final int length = string.length();
		int codePoint;
		for(int offset = 0; offset < length; offset += Character.charCount(codePoint))
		{
			codePoint = string.codePointAt(offset);
			if(normaliseWhite)
			{
				if(StringUtil.isWhitespace(codePoint))
				{
					if((stripLeadingWhite && !reachedNonWhite) || lastWasWhite)
						continue;
					accum.append(' ');
					lastWasWhite = true;
					continue;
				}
				else
				{
					lastWasWhite = false;
					reachedNonWhite = true;
				}
			}
			if(codePoint < Character.MIN_SUPPLEMENTARY_CODE_POINT)
			{
				final char c = (char) codePoint;
				switch(c)
				{
					case '&':
						accum.append("&amp;");
						break;
					case 0xA0:
						accum.append("&nbsp;");
						break;
					case '<':
						accum.append("&lt;");
						break;
					case '>':
						if(!inAttribute)
							accum.append("&gt;");
						else
							accum.append(c);
						break;
					case '"':
						if(inAttribute)
							accum.append("&quot;");
						else
							accum.append(c);
						break;
					default:
						if(canEncode(coreCharset, c, encoder))
							accum.append(c);
						else
							appendEncoded(accum, codePoint);
				}
			}
			else
			{
				final String c = new String(Character.toChars(codePoint));
				if(encoder.canEncode(c)) // uses fallback encoder for simplicity
					accum.append(c);
				else
					appendEncoded(accum, codePoint);
			}
		}
	}

	private static void appendEncoded(Appendable accum, int codePoint) throws IOException
	{
		final String name = characters.nameForCodepoint(codePoint);
		if(name != emptyName)
			accum.append('&').append(name).append(';');
	}

	private static boolean canEncode(final CoreCharset charset, final char c, final CharsetEncoder fallback)
	{
		switch(charset)
		{
			case ascii:
				return c < 0x80;
			case utf:
				return true;
			default:
				return fallback.canEncode(c);
		}
	}

	private enum CoreCharset
	{
		ascii, utf, fallback;
		private static CoreCharset byName(String name)
		{
			if(name.equals("US-ASCII"))
				return ascii;
			if(name.startsWith("UTF-"))
				return utf;
			return fallback;
		}
	}
	public static ByteBuffer readToByteBuffer(InputStream inStream, int maxSize) throws IOException
	{
		final int bufferSize = 60000;
		final boolean capped = maxSize > 0;
		byte[] buffer = new byte[capped && maxSize < bufferSize ? maxSize : bufferSize];
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(capped ? maxSize : bufferSize);
		int read;
		int remaining = maxSize;
		while(!Thread.interrupted())
		{
			read = inStream.read(buffer);
			if(read == -1)
				break;
			if(capped)
			{
				if(read > remaining)
				{
					outStream.write(buffer, 0, remaining);
					break;
				}
				remaining -= read;
			}
			outStream.write(buffer, 0, read);
		}
		return ByteBuffer.wrap(outStream.toByteArray());
	}

	private static void load(EscapeMode e, String file)
	{
		List<String> nameVals = new ArrayList<String>();
		List<Integer> codeVals = new ArrayList<Integer>();
		InputStream stream = Entities.class.getResourceAsStream(file);
		try
		{
			ByteBuffer bytes = Entities.readToByteBuffer(stream, 0);
			String contents = Charset.forName("ascii").decode(bytes).toString();
			HtmlReader reader = new HtmlReader(contents);
			while(!reader.isEmpty())
			{
				final String name = reader.consumeTo('=');
				reader.advance();
				final int cp1 = Integer.parseInt(reader.consumeTo('\n'), 36);
				reader.advance();
				nameVals.add(name);
				codeVals.add(cp1);
			}
		}
		catch(IOException err)
		{
			throw new IllegalStateException("Error reading resource " + file);
		}
		e.nameVals = new String[nameVals.size()];
		e.codeVals = new int[nameVals.size()];
		for(int j = 0; j < nameVals.size(); j++)
		{
			e.nameVals[j] = nameVals.get(j);
			e.codeVals[j] = codeVals.get(j);
		}
	}
}
