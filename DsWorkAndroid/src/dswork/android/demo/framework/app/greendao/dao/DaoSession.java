package dswork.android.demo.framework.app.greendao.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import dswork.android.demo.framework.app.greendao.dao.Person;

import dswork.android.demo.framework.app.greendao.dao.PersonDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig personDaoConfig;

    private final PersonDao personDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        personDaoConfig = daoConfigMap.get(PersonDao.class).clone();
        personDaoConfig.initIdentityScope(type);

        personDao = new PersonDao(personDaoConfig, this);

        registerDao(Person.class, personDao);
    }
    
    public void clear() {
        personDaoConfig.getIdentityScope().clear();
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

}
