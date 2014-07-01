package dswork.android.lib.controller;

import java.util.List;
import java.util.Map;

public interface BaseController<T> 
{
	public String add(T po);
	public String deleteBatch(Long[] ids);
	public String upd(T po);
	public List<T> get(Map m);
	public T getById(Long id);
}
