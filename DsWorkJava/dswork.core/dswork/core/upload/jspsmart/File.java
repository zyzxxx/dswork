package dswork.core.upload.jspsmart;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import java.io.InputStream;

public class File
{
	private SmartUpload m_parent;
	private int m_startData;
	private int m_endData;
	private int m_size;
	private String m_fieldname;
	private String m_filename;
	private String m_fileExt;
	private String m_filePathName;
	private String m_contentType;
	private String m_contentDisp;
	private String m_typeMime;
	private String m_subTypeMime;
	// private String m_contentString;
	private boolean m_isMissing;
	public static final int SAVEAS_AUTO = 0;
	public static final int SAVEAS_VIRTUAL = 1;
	public static final int SAVEAS_PHYSICAL = 2;

	public void close()
	{
		m_parent = null;
	}

	File()
	{
		m_startData = 0;
		m_endData = 0;
		m_size = 0;
		m_fieldname = "";
		m_filename = "";
		m_fileExt = "";
		m_filePathName = "";
		m_contentType = "";
		m_contentDisp = "";
		m_typeMime = "";
		m_subTypeMime = "";
		// m_contentString = "";
		m_isMissing = true;
	}

	public void saveAs(String s) throws IOException, SmartUploadException
	{
		saveAs(s, 0);
	}

	public void saveAs(String s, int i) throws IOException, SmartUploadException
	{
		String s1 = m_parent.getPhysicalPath(s, i);
		if(s1 == null)
		{
			throw new IllegalArgumentException("There is no specified destination file (1140).");
		}
		FileOutputStream fileoutputstream = null;
		try
		{
			java.io.File file = new java.io.File(s1);
			fileoutputstream = new FileOutputStream(file);
			fileoutputstream.write(m_parent.m_binArray, m_startData, m_size);
		}
		catch(IOException ioexception)
		{
			throw new SmartUploadException("File can't be saved (1120).");
		}
		finally
		{
			if(fileoutputstream != null)
			{
				fileoutputstream.close();
			}
		}
	}

	public InputStream getInputSteram()
	{
		return new java.io.ByteArrayInputStream(m_parent.m_binArray, m_startData, m_size);
	}

	public void fileToField(ResultSet resultset, String s) throws ServletException, IOException, SmartUploadException, SQLException
	{
		long l = 0L;
		int i = 0x10000;
		int j = 0;
		int k = m_startData;
		if(resultset == null)
		{
			throw new IllegalArgumentException("The RecordSet cannot be null (1145).");
		}
		if(s == null)
		{
			throw new IllegalArgumentException("The columnName cannot be null (1150).");
		}
		if(s.length() == 0)
		{
			throw new IllegalArgumentException("The columnName cannot be empty (1155).");
		}
		l = BigInteger.valueOf(m_size).divide(BigInteger.valueOf(i)).longValue();
		j = BigInteger.valueOf(m_size).mod(BigInteger.valueOf(i)).intValue();
		try
		{
			for(int i1 = 1; (long) i1 < l; i1++)
			{
				resultset.updateBinaryStream(s, new ByteArrayInputStream(m_parent.m_binArray, k, i), i);
				k = k != 0 ? k : 1;
				k = i1 * i + m_startData;
			}
			if(j > 0)
			{
				resultset.updateBinaryStream(s, new ByteArrayInputStream(m_parent.m_binArray, k, j), j);
			}
		}
		catch(SQLException sqlexception)
		{
			byte abyte0[] = new byte[m_size];
			System.arraycopy(m_parent.m_binArray, m_startData, abyte0, 0, m_size);
			resultset.updateBytes(s, abyte0);
		}
		catch(Exception exception)
		{
			throw new SmartUploadException("Unable to save file in the DataBase (1130).");
		}
	}

	public boolean isMissing()
	{
		return m_isMissing;
	}

	public String getFieldName()
	{
		return m_fieldname;
	}

	public String getFileName()
	{
		return m_filename;
	}

	public String getFilePathName()
	{
		return m_filePathName;
	}

	public String getFileExt()
	{
		return m_fileExt;
	}

	public String getContentType()
	{
		return m_contentType;
	}

	public String getContentDisp()
	{
		return m_contentDisp;
	}

	public String getContentString()
	{
		return new String(m_parent.m_binArray, m_startData, m_size);
	}

	public String getTypeMIME() throws IOException
	{
		return m_typeMime;
	}

	public String getSubTypeMIME()
	{
		return m_subTypeMime;
	}

	public int getSize()
	{
		return m_size;
	}

	protected int getStartData()
	{
		return m_startData;
	}

	protected int getEndData()
	{
		return m_endData;
	}

	protected void setParent(SmartUpload smartupload)
	{
		m_parent = smartupload;
	}

	protected void setStartData(int i)
	{
		m_startData = i;
	}

	protected void setEndData(int i)
	{
		m_endData = i;
	}

	protected void setSize(int i)
	{
		m_size = i;
	}

	protected void setIsMissing(boolean flag)
	{
		m_isMissing = flag;
	}

	protected void setFieldName(String s)
	{
		m_fieldname = s;
	}

	protected void setFileName(String s)
	{
		m_filename = s;
	}

	protected void setFilePathName(String s)
	{
		m_filePathName = s;
	}

	protected void setFileExt(String s)
	{
		m_fileExt = s;
	}

	protected void setContentType(String s)
	{
		m_contentType = s;
	}

	protected void setContentDisp(String s)
	{
		m_contentDisp = s;
	}

	protected void setTypeMIME(String s)
	{
		m_typeMime = s;
	}

	protected void setSubTypeMIME(String s)
	{
		m_subTypeMime = s;
	}

	public byte getBinaryData(int i)
	{
		if(m_startData + i > m_endData)
		{
			throw new ArrayIndexOutOfBoundsException("Index Out of range (1115).");
		}
		if(m_startData + i <= m_endData)
		{
			return m_parent.m_binArray[m_startData + i];
		}
		else
		{
			return 0;
		}
	}
}
