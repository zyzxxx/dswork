package dswork.core.datasource;

import org.springframework.transaction.TransactionDefinition;

/**
 * 重载DataSourceTransactionManager事务管理
 * @author skey
 */
public class DataSourceTransactionManager extends org.springframework.jdbc.datasource.DataSourceTransactionManager
{
	private static final long serialVersionUID = 1L;

	/**
	 * 根据Spring配置的事务设置，为当前线程选择主库还是从库数据源
	 */
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition)
	{
		if(definition.isReadOnly())
		{
			System.out.println("doBegin slave");
			DataSourceHolder.setSlave();
		}
		else
		{
			System.out.println("doBegin master");
			DataSourceHolder.setMaster();
		}
		super.doBegin(transaction, definition);
	}
	
	/**
	 * 移除本地线程事务状态信息
	 */
	@Override
	protected void doCleanupAfterCompletion(Object arg0)
	{
		super.doCleanupAfterCompletion(arg0);
		DataSourceHolder.removeDataSource();
	}
}
