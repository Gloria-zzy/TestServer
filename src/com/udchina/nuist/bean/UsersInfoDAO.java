package com.udchina.nuist.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * UsersInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.udchina.nuist.bean.UsersInfo
 * @author MyEclipse Persistence Tools
 */
public class UsersInfoDAO extends BaseHibernateDAO
{
	private static final Logger log = LoggerFactory
			.getLogger(UsersInfoDAO.class);
	// property constants
	public static final String SCHOOL = "school";
	public static final String AREA = "area";
	public static final String BUILDING = "building";
	public static final String ROOM = "room";

	public void save(UsersInfo transientInstance)
	{
		log.debug("saving UsersInfo instance");
		try
		{
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re)
		{
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UsersInfo persistentInstance)
	{
		log.debug("deleting UsersInfo instance");
		try
		{
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re)
		{
			log.error("delete failed", re);
			throw re;
		}
	}

	public UsersInfo findById(java.lang.String id)
	{
		log.debug("getting UsersInfo instance with id: " + id);
		try
		{
			UsersInfo instance = (UsersInfo) getSession()
					.get("com.udchina.nuist.bean.UsersInfo", id);
			return instance;
		} catch (RuntimeException re)
		{
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UsersInfo instance)
	{
		log.debug("finding UsersInfo instance by example");
		try
		{
			List results = getSession()
					.createCriteria("com.udchina.nuist.bean.UsersInfo")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re)
		{
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value)
	{
		log.debug("finding UsersInfo instance with property: " + propertyName
				+ ", value: " + value);
		try
		{
			String queryString = "from UsersInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re)
		{
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySchool(Object school)
	{
		return findByProperty(SCHOOL, school);
	}

	public List findByArea(Object area)
	{
		return findByProperty(AREA, area);
	}

	public List findByBuilding(Object building)
	{
		return findByProperty(BUILDING, building);
	}

	public List findByRoom(Object room)
	{
		return findByProperty(ROOM, room);
	}

	public List findAll()
	{
		log.debug("finding all UsersInfo instances");
		try
		{
			String queryString = "from UsersInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re)
		{
			log.error("find all failed", re);
			throw re;
		}
	}

	public UsersInfo merge(UsersInfo detachedInstance)
	{
		log.debug("merging UsersInfo instance");
		try
		{
			UsersInfo result = (UsersInfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re)
		{
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UsersInfo instance)
	{
		log.debug("attaching dirty UsersInfo instance");
		try
		{
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re)
		{
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UsersInfo instance)
	{
		log.debug("attaching clean UsersInfo instance");
		try
		{
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re)
		{
			log.error("attach failed", re);
			throw re;
		}
	}
}