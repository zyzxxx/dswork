package dswork.cas.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import dswork.cas.model.IFunc;
import dswork.cas.model.IOrg;
import dswork.cas.model.IPost;
import dswork.cas.model.ISystem;
import dswork.cas.model.IUser;


@WebService(name = "DsworkCasWebService", targetNamespace = "http://service.cas.dswork/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DsworkCasWebService
{
	/**
	 * 获取用户有权限访问的子系统
	 * @param userAccount 用户帐号
	 * @return ISystem[]
	 */
	@WebMethod
	public ISystem[] getSystemByUser(@WebParam(name = "userAccount") String userAccount);

	/**
	 * 获取子系统信息
	 * @param systemAlias 系统标识
	 * @return ISystem
	 */
	@WebMethod
	public ISystem getSystem(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword);

	/**
	 * 获取系统的功能结构
	 * @param systemAlias 系统标识
	 * @return IFunc[]
	 */
	@WebMethod
	public IFunc[] getFunctionBySystem(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword);

	/**
	 * 获取用户权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param userAccount 用户帐号
	 * @return IFunc[]
	 */
	@WebMethod
	public IFunc[] getFunctionByUser(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword, @WebParam(name = "userAccount") String userAccount);

	/**
	 * 获取岗位权限范围内的系统功能结构
	 * @param systemAlias 系统标识
	 * @param postId 岗位ID
	 * @return IFunc[]
	 */
	@WebMethod
	public IFunc[] getFunctionByPost(@WebParam(name = "systemAlias") String systemAlias, @WebParam(name = "systemPassword") String systemPassword, @WebParam(name = "postId") String postId);
	
	
	
	

	/**
	 * @note 获取组织机构
	 * @param orgId 组织机构ID
	 * @return IOrg
	 */
	@WebMethod
	public IOrg getOrg(@WebParam(name = "orgId") String orgId);

	/**
	 * @note 获取下级组织机构
	 * @param orgPid 组织机构ID，为0则取顶级
	 * @return IOrg[]
	 */
	@WebMethod
	public IOrg[] getOrgByOrgParent(@WebParam(name = "orgPid") String orgPid);

	/**
	 * @note 获取用户所属机构（部门）
	 * @param userAccount 用户帐号
	 * @return IOrg[]
	 */
	@WebMethod
	public IOrg[] getOrgChildByAccount(@WebParam(name = "userAccount") String userAccount);

	/**
	 * @note 获取用户所属机关（单位）
	 * @param userAccount 用户帐号
	 * @return IOrg[]
	 */
	@WebMethod
	public IOrg[] getOrgParentByAccount(@WebParam(name = "userAccount") String userAccount);

	/**
	 * @note 获取组织机构下的岗位
	 * @param orgId 组织机构ID
	 * @return IPost[]
	 */
	@WebMethod
	public IPost[] getPostByOrg(@WebParam(name = "orgId") String orgId);

	/**
	 * @note 获取指定用户的基本信息
	 * @param userAccount 用户帐号
	 * @return IUser
	 */
	@WebMethod
	public IUser getUser(@WebParam(name = "userAccount") String userAccount);

	/**
	 * @note 获取岗位下的所有用户
	 * @param postId 岗位ID
	 * @return IUser[]
	 */
	@WebMethod
	public IUser[] getUserByPost(@WebParam(name = "postId") String postId);

	/**
	 * @note 获取指定用户拥有的岗位
	 * @param userAccount 用户帐号
	 * @return IPost[]
	 */
	@WebMethod
	public IPost[] getPostByUser(@WebParam(name = "userAccount") String userAccount);
}
