package codebuilder.freemarker;

import java.util.List;

import codebuilder.freemarker.TableModel.Column;

public abstract class TableDao
{
	public abstract void initConnect(String url);
	public abstract List<String> queryPk(String tableName);
	public abstract String queryTableComment(String tableName);
	public abstract List<Column> queryTableColumn(String tableName);
	public abstract TableModel queryTable(String tableName);
	public abstract List<TableModel> queryAllTables(String dbName);
	public abstract void close();
}
