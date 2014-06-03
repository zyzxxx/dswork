/**
 * 用户Service
 */
package dswork.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonUserDao;
import dswork.common.model.DsCommonUser;
import dswork.core.db.BaseService;
import dswork.core.db.EntityDao;
import dswork.core.util.EncryptUtil;

@Service
@SuppressWarnings("all")
public class DsCommonUserService extends BaseService<DsCommonUser, java.lang.Long>
{
	@Autowired
	private DsCommonUserDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}

	/**
	 * 修改密码
	 * @param id 用户对象ID
	 * @param password 加密后的密码
	 */
	public void updatePassword(long id, String password)
	{
		password = EncryptUtil.encryptMd5(password);
		dao.updatePassword(id, password);
	}

	/**
	 * 修改状态
	 * @param id 用户对象ID
	 * @param status 状态
	 */
	public void updateStatus(long id, int status)
	{
		dao.updateStatus(id, status);
	}

	/**
	 * 修改CA证书
	 * @param id 用户对象ID
	 * @param cakey ca证书
	 */
	public void updateCAKey(long id, String cakey)
	{
		dao.updateCAKey(id, cakey);
	}

	/**
	 * 判断账号是否存在
	 * @param account 账号
	 * @return boolean 存在true，不存在false
	 */
	public boolean isExistByAccount(String account)
	{
		return dao.isExistByAccount(account);
	}

	/**
	 * 根据账号获取用户对象
	 * @param account 账号
	 * @return DsCommonUser
	 */
	public DsCommonUser getByAccount(String account)
	{
		return dao.getByAccount(account);
	}
}
