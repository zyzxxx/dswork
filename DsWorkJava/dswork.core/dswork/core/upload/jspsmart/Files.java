package dswork.core.upload.jspsmart;

import java.io.IOException;
import java.util.*;

public class Files
{
//	private SmartUpload m_parent;
	private Hashtable<Integer, File> m_files;
	private int m_counter;

	public void close()
	{
		if(m_files != null)
		{
			m_files.clear();
			m_files = null;
		}
	}

	Files()
	{
		m_files = new Hashtable<Integer, File>();
		m_counter = 0;
	}

	protected void addFile(File file)
	{
		if(file == null)
		{
			throw new IllegalArgumentException("newFile cannot be null.");
		}
		else
		{
			m_files.put(new Integer(m_counter), file);
			m_counter++;
			return;
		}
	}

	public File getFile(int i)
	{
		if(i < 0)
		{
			throw new IllegalArgumentException("File's index cannot be a negative value (1210).");
		}
		File file = m_files.get(new Integer(i));
		if(file == null)
		{
			throw new IllegalArgumentException("Files' name is invalid or does not exist (1205).");
		}
		else
		{
			return file;
		}
	}

	public int getCount()
	{
		return m_counter;
	}

	public long getSize() throws IOException
	{
		long l = 0L;
		for(int i = 0; i < m_counter; i++)
		{
			l += getFile(i).getSize();
		}
		return l;
	}

	public Collection<File> getCollection()
	{
		return m_files.values();
	}

	public Enumeration<File> getEnumeration()
	{
		return m_files.elements();
	}
}
