/**
 * CMSDao
 */
package common.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.MyBatisDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.TimeUtil;

@Repository
@SuppressWarnings("all")
public class DsCmsPreviewDao extends DsCmsDao
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsPreviewDao.class;
	}
}
