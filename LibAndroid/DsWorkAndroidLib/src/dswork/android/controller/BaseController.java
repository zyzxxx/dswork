package dswork.android.controller;

import java.util.List;
import java.util.Map;

public interface BaseController<T> 
{
	public String add(T po);
	public String deleteBatch(String ids);
	public String upd(T po, String ids);
	public List<T> get(Map m);
	public T getById(Long id);
}
