package dswork.android.controller;

import java.util.List;
import java.util.Map;

import android.content.Context;
import dswork.android.lib.controller.BaseController;
import dswork.android.model.Person;
import dswork.android.service.PersonService;

public class PersonController implements BaseController<Person> 
{
	private Context ctx;
	private PersonService service;//注入service
	
	public PersonController(Context ctx) {
		super();
		this.ctx = ctx;
		this.service = new PersonService(ctx);
	}

	@Override
	public String add(Person po) {
		service.add(po);
		return "1";
	}

	@Override
	public String deleteBatch(Long[] ids) {
		service.deleteBatch("person", ids);
		return "1";
	}

	@Override
	public String upd(Person po,  Long id) {
		service.update(po, id);
		return "1";
	}

	@Override
	public List<Person> get(Map m) {
		return service.query(m);
	}

	@Override
	public Person getById(Long id) {
		return service.getById(id);
	}
	
	public List<Person> queryPage(Map m, int offset, int maxResult){
		return service.queryPage(m, offset, maxResult);
	}
}
