/**
 * 功能:公共Controller
 */
package dswork.common.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.common.DsCommonFactory;

@Scope("prototype")
@Controller
@RequestMapping("/common/share")
public class CommonShareController extends BaseController
{
	@RequestMapping("/getJsonDict")
	public void getJsonDict()
	{
		String name = req.getString("name", "0");
		String parentAlias = req.getString("value", null);//null时全部节点数据，""时根节点数据
		print(DsCommonFactory.getDictJson(name, parentAlias));
	}

	@RequestMapping("/getJsonOrg")
	public void getJsonOrg()
	{
		long pid = req.getLong("pid");
		int status = req.getInt("status", -1);
		print(DsCommonFactory.getOrgJson(pid, (status > -1 && status < 3)?status:null));
	}
}