package dswork.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.*;

class MyRequestUpload
{
	private long totalMaxFileSize = 0L;
	private long maxFileSize = 0L;
	
	private int m_totalBytes = 0;
	private int m_currentIndex = 0;
	private int m_startData = 0;
	private int m_endData = 0;
	private String m_boundary = "";
	private String m_deniedFilesList = null;
	private String m_allowedFilesList = null;
	private byte[] m_binArray;

	private HttpServletRequest request = null;
	private Map<String, ArrayList<MyFile>> formFiles;
	private Map<String, ArrayList<String>> formParams;

	public Map<String, ArrayList<MyFile>> getFiles()
	{
		return formFiles;
	}

	public Map<String, ArrayList<String>> getParams()
	{
		return formParams;
	}
	
	public MyRequestUpload(HttpServletRequest httpservletrequest)
	{
		request = httpservletrequest;
		formFiles = new LinkedHashMap<String, ArrayList<MyFile>>();
		formParams = new LinkedHashMap<String, ArrayList<String>>();
	}

	public void upload() throws Exception
	{
		int i = 0;
		long l = 0L;
		boolean flag1 = false;
		String s4 = "";
		String s5 = "";
		String s6 = "";
		String s7 = "";
		String s8 = "";
		m_totalBytes = request.getContentLength();
		m_binArray = new byte[m_totalBytes];
		int j;
		for(; i < m_totalBytes; i += j)
		{
			try
			{
				request.getInputStream();
				j = request.getInputStream().read(m_binArray, i, m_totalBytes - i);
			}
			catch(Exception exception)
			{
				throw new Exception("MyRequestException:Unable to upload.");
			}
		}
		for(; !flag1 && m_currentIndex < m_totalBytes; m_currentIndex++)
		{
			if(m_binArray[m_currentIndex] == 13)
			{
				flag1 = true;
			}
			else
			{
				m_boundary = m_boundary + (char) m_binArray[m_currentIndex];
			}
		}
		if(m_currentIndex == 1)
		{
			return;
		}
		for(m_currentIndex++; m_currentIndex < m_totalBytes; m_currentIndex = m_currentIndex + 2)
		{
			String s1 = getDataHeader();
			m_currentIndex = m_currentIndex + 2;
			boolean flag3 = s1.indexOf("filename") > 0;
			String s3 = getDataFieldValue(s1, "name");
			if(flag3)
			{
				s6 = getDataFieldValue(s1, "filename");
				s7 = getContentType(s1);
				s8 = getContentDisp(s1);
				
				s4 = getFileName(s6);
				s5 = getFileExt(s4);
			}
			getDataSection();
			int size = (m_endData - m_startData) + 1;// 单个文件大小
			if(flag3 && s4.length() > 0)
			{
				String _ext = "," + s5 + ",";
				if(m_deniedFilesList != null)
				{
					if(m_deniedFilesList.indexOf(_ext) != -1)
					{
						throw new Exception("MyRequestException:The extension of the file is denied to be uploaded (1015).");
					}
				}
				if(m_allowedFilesList != null)
				{
					if(m_allowedFilesList.indexOf(_ext) == -1)
					{
						throw new Exception("MyRequestException:The extension of the file is not allowed to be uploaded (1010).");
					}
				}
				
				if(maxFileSize > 0L && (long)(size) > maxFileSize)
				{
					throw new Exception("MyRequestException:Size exceeded for this file : " + s4 + " (1105).");
				}
				l += (m_endData - m_startData) + 1;
				if(totalMaxFileSize > 0L && l > totalMaxFileSize)
				{
					throw new Exception("MyRequestException:Total File Size exceeded (1110).");
				}
			}
			if(flag3)
			{
				if(size > 0 || s4.length() > 0)
				{
					if(s7.indexOf("application/x-macbinary") > 0)
					{
						m_startData = m_startData + 128;
					}
					if(s5 != null && s4 != null && s5.length() > 0 && s4.length() > 0)
					{
						s4 = s4.substring(0, s4.length() - s5.length()) + s5;// 转化后缀名为小写
					}
					MyFile file = new MyFile();
					file.setFieldName(s3);
					file.setFileName(s4);
					file.setFileExt(s5);
					file.setContentType(s7);
					file.setContentDisp(s8);
					file.setFileData(new byte[size]);
					System.arraycopy(m_binArray, m_startData, file.getFileData(), 0, size);
					if(formFiles.get(s3) == null)
					{
						formFiles.put(s3, new ArrayList<MyFile>());
					}
					formFiles.get(s3).add(file);
				}
			}
			else
			{
				if(formParams.get(s3) == null)
				{
					formParams.put(s3, new ArrayList<String>());
				}
				formParams.get(s3).add(new String(m_binArray, m_startData, (m_endData - m_startData) + 1, "UTF-8"));
			}
			if((char) m_binArray[m_currentIndex + 1] == '-')
			{
				break;
			}
		}
		m_binArray = null;
	}

	private String getDataFieldValue(String s, String s1)
	{
		String s2 = "";
		String s3 = "";
		int i = 0;
		s2 = s1 + "=" + '"';
		i = s.indexOf(s2);
		if(i > 0)
		{
			int j = i + s2.length();
			int k = j;
			s2 = "\"";
			int l = s.indexOf(s2, j);
			if(k > 0 && l > 0)
			{
				s3 = s.substring(k, l);
			}
		}
		return s3;
	}
	private String getFileExt(String s)
	{
		if(s == null)
		{
			return null;
		}
		int i = s.lastIndexOf(46);// 46="."
		return i != -1 ? s.substring(i+1).toLowerCase() : "";
	}
	private String getContentType(String s)
	{
		String s1 = "";
		String s2 = "";
		int i = 0;
		s1 = "Content-Type:";
		i = s.indexOf(s1) + s1.length();
		if(i != -1)
		{
			int j = s.length();
			s2 = s.substring(i, j);
		}
		return s2;
	}
	private String getContentDisp(String s)
	{
		String s1 = "";
		int i = 0;
		int j = 0;
		i = s.indexOf(":") + 1;
		j = s.indexOf(";");
		s1 = s.substring(i, j);
		return s1;
	}
	private void getDataSection()
	{
		int i = m_currentIndex;
		int j = 0;
		int k = m_boundary.length();
		m_startData = m_currentIndex;
		m_endData = 0;
		while(i < m_totalBytes)
		{
			if(m_binArray[i] == (byte) m_boundary.charAt(j))
			{
				if(j == k - 1)
				{
					m_endData = ((i - k) + 1) - 3;
					break;
				}
				i++;
				j++;
			}
			else
			{
				i++;
				j = 0;
			}
		}
		m_currentIndex = m_endData + k + 3;
	}
	private String getDataHeader()
	{
		int i = m_currentIndex;
		int j = 0;
		for(boolean flag1 = false; !flag1;)
		{
			if(m_binArray[m_currentIndex] == 13 && m_binArray[m_currentIndex + 2] == 13)
			{
				flag1 = true;
				j = m_currentIndex - 1;
				m_currentIndex = m_currentIndex + 2;
			}
			else
			{
				m_currentIndex++;
			}
		}
		String s = "";
		try
		{
			s = new String(m_binArray, i, (j - i) + 1, "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
	private String getFileName(String s)
	{
		int i = 0;
		i = s.lastIndexOf(47);
		if(i != -1)
		{
			return s.substring(i + 1, s.length());
		}
		i = s.lastIndexOf(92);
		if(i != -1)
		{
			return s.substring(i + 1, s.length());
		}
		else
		{
			return s;
		}
	}

	public void setDeniedFilesList(String s)
	{
		if(s != null)
		{
			m_deniedFilesList = "," + s.replaceAll(" ", "") + ",";
		}
		else
		{
			m_deniedFilesList = null;
		}
	}
	public void setAllowedFilesList(String s)
	{
		if(s != null)
		{
			m_allowedFilesList = "," + s.replaceAll(" ", "") + ",";
		}
		else
		{
			m_allowedFilesList = null;
		}
	}
	public void setTotalMaxFileSize(long l)
	{
		totalMaxFileSize = l;
	}
	public void setMaxFileSize(long l)
	{
		maxFileSize = l;
	}
}
