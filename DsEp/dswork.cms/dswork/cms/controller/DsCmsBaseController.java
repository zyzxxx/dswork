package dswork.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

import common.authown.AuthOwn;
import common.authown.AuthOwnUtil;
import dswork.mvc.BaseController;

public class DsCmsBaseController extends BaseController
{
	private String id;
	private String own;
	private String account;
	private String name;

	@Override
	@ModelAttribute
	public void BaseInitialization(HttpServletRequest request, HttpServletResponse response)
	{
		super.BaseInitialization(request, response);
		AuthOwn m = AuthOwnUtil.getUser(request);
		id = m.getId();
		own = m.getOwn();
		account = m.getAccount();
		name = m.getName();
	}

	protected boolean checkOwn(String own)
	{
		return own.equals(this.own);
	}

	protected String getId()
	{
		return id;
	}

	protected String getAccount()
	{
		return account;
	}

	protected String getName()
	{
		return name;
	}

	protected String getOwn()
	{
		return own;
	}
}
