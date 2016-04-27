package dswork.core.upload.jspsmart;

import java.util.Enumeration;
import java.util.Hashtable;

@SuppressWarnings("all")
public class Request
{
	private Hashtable<String, Hashtable<Integer, String>> m_parameters;
	private int m_counter;

	public void close()
	{
		if(m_parameters != null)
		{
			m_parameters.clear();
			m_parameters = null;
		}
	}

	Request()
	{
		m_parameters = new Hashtable<String, Hashtable<Integer, String>>();
		m_counter = 0;
	}

	protected void putParameter(String s, String s1)
	{
		if(s == null)
		{
			throw new IllegalArgumentException("The name of an element cannot be null.");
		}
		if(m_parameters.containsKey(s))
		{
			Hashtable<Integer, String> hashtable = m_parameters.get(s);
			hashtable.put(new Integer(hashtable.size()), s1);
		}
		else
		{
			Hashtable<Integer, String> hashtable1 = new Hashtable<Integer, String>();
			hashtable1.put(new Integer(0), s1);
			m_parameters.put(s, hashtable1);
			m_counter++;
		}
	}

	public String getParameter(String s)
	{
		if(s == null)
		{
			throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
		}
		Hashtable<Integer, String> hashtable = m_parameters.get(s);
		if(hashtable == null)
		{
			return null;
		}
		else
		{
			return hashtable.get(new Integer(0));
		}
	}

	public Enumeration<String> getParameterNames()
	{
		return m_parameters.keys();
	}

	public String[] getParameterValues(String s)
	{
		if(s == null)
		{
			throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
		}
		Hashtable<Integer, String> hashtable = m_parameters.get(s);
		if(hashtable == null)
		{
			return null;
		}
		String as[] = new String[hashtable.size()];
		for(int i = 0; i < hashtable.size(); i++)
		{
			as[i] = hashtable.get(new Integer(i));
		}
		return as;
	}
}
