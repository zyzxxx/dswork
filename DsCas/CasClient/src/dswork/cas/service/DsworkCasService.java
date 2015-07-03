package dswork.cas.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import dswork.cas.model.IFunc;
import dswork.cas.model.IOrg;
import dswork.cas.model.ISystem;
import dswork.cas.model.IUser;

@WebService(name = "DsworkCasService", targetNamespace = "http://service.cas.dswork/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DsworkCasService
{
	//////////////////////////////////////////////////////////////////////////////
	// 权限相关的方法
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * 获取子系统信息
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @return ISystem
	 */
	public ISystem getSystem(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword);

	/**
	 * 获取用户有权限访问的子系统
	 * @param userAccount 用户帐号
	 * @return ISystem[]
	 */
	public ISystem[] getSystemByUser(@WebParam(name = "userAccount") String userAccount);
	
	/**
	 * 获取系统的功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @return IFunc[]
	 */
	public IFunc[] getFunctionBySystem(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword);
	
	/**
	 * 获取用户权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @param userAccount 用户帐号
	 * @return IFunc[]
	 */
	public IFunc[] getFunctionByUser(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword, @WebParam(name = "userAccount") String userAccount);
	
	/**
	 * 获取岗位权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param systemPassword 系统访问密码
	 * @param postId 岗位ID
	 * @return IFunc[]
	 */
	public IFunc[] getFunctionByPost(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword, @WebParam(name = "postId") String postId);

	//////////////////////////////////////////////////////////////////////////////
	// 用户相关的方法
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * @note 获取组织机构
	 * @param orgId 组织机构ID
	 * @return IOrg
	 */
	public IOrg getOrg(@WebParam(name = "orgId") String orgId);
	
	/**
	 * @note 获取下级组织机构(status:2单位,1部门,0岗位)
	 * @param orgPid 组织机构ID，为0则取顶级
	 * @return IOrg[]
	 */
	public IOrg[] queryOrgByOrgParent(@WebParam(name = "orgPid") String orgPid);

	/**
	 * @note 获取组织机构下的岗位
	 * @param orgId 组织机构ID
	 * @return IOrg[]
	 */
	public IOrg[] queryPostByOrg(@WebParam(name = "orgId") String orgId);
	
	/**
	 * @note 获取指定用户的基本信息
	 * @param userAccount 用户帐号
	 * @return IUser
	 */
	public IUser getUser(@WebParam(name = "userAccount") String userAccount);
	
	/**
	 * @note 获取岗位下的所有用户
	 * @param postId 岗位ID
	 * @return IUser[]
	 */
	public IUser[] queryUserByPost(@WebParam(name = "postId") String postId);
	
	/**
	 * @note 获取指定用户拥有的岗位
	 * @param userAccount 用户帐号
	 * @return IOrg[]
	 */
	public IOrg[] queryPostByUser(@WebParam(name = "userAccount") String userAccount);
}
