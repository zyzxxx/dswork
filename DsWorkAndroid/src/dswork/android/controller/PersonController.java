package dswork.android.controller;

import java.util.List;
import java.util.Map;
import android.content.Context;
import dswork.android.model.Person;
import dswork.android.service.PersonService;

public class PersonController
{
	private Context ctx;
	private PersonService service;//注入service
	
	public PersonController(Context ctx) {
		super();
		this.ctx = ctx;
		this.service = new PersonService(ctx);
	}

	public String add(Person po) {
		service.add(po);
		return "1";
	}

	public String deleteBatch(Long[] ids) {
		service.deleteBatch("person", ids);
		return "1";
	}

	public String upd(Person po) {
		service.update(po);
		return "1";
	}

	public List<Person> get(Map m) {
		return service.query(m);
	}

	public Person getById(Long id) {
		return service.getById(id);
	}
	
	public List<Person> queryPage(Map m, int offset, int maxResult){
		return service.queryPage(m, offset, maxResult);
	}
}
