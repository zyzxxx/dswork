package common.auto;

@SuppressWarnings("serial")
public class AutoServlet extends javax.servlet.http.HttpServlet
{
	static
	{
		common.lucene.LuceneUtil.load();
	}
}
