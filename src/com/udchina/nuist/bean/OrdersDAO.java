package com.udchina.nuist.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Orders entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.udchina.nuist.bean.Orders
 * @author MyEclipse Persistence Tools
 */
public class OrdersDAO extends BaseHibernateDAO
{
	private static final Logger log = LoggerFactory.getLogger(OrdersDAO.class);
	// property constants
	public static final String PHONE = "phone";
	public static final String TIME = "time";
	public static final String LOCATION = "location";
	public static final String NOTE = "note";
	public static final String STATUS = "status";
	public static final String DATE = "date";
	public static final String TAKENUM = "takenum";

	public void save(Orders transientInstance)
	{
		log.debug("saving Orders instance");
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

	public void delete(Orders persistentInstance)
	{
		log.debug("deleting Orders instance");
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

	public Orders findById(java.lang.String id)
	{
		log.debug("getting Orders instance with id: " + id);
		try
		{
			Orders instance = (Orders) getSession()
					.get("com.udchina.nuist.bean.Orders", id);
			return instance;
		} catch (RuntimeException re)
		{
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Orders instance)
	{
		log.debug("finding Orders instance by example");
		try
		{
			List results = getSession()
					.createCriteria("com.udchina.nuist.bean.Orders")
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
		log.debug("finding Orders instance with property: " + propertyName
				+ ", value: " + value);
		try
		{
			String queryString = "from Orders as model where model."
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

	public List findByPhone(Object phone)
	{
		return findByProperty(PHONE, phone);
	}

	public List findByTime(Object time)
	{
		return findByProperty(TIME, time);
	}

	public List findByLocation(Object location)
	{
		return findByProperty(LOCATION, location);
	}

	public List findByNote(Object note)
	{
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status)
	{
		return findByProperty(STATUS, status);
	}

	public List findByDate(Object date)
	{
		return findByProperty(DATE, date);
	}

	public List findByTakenum(Object takenum)
	{
		return findByProperty(TAKENUM, takenum);
	}

	public List findAll()
	{
		log.debug("finding all Orders instances");
		try
		{
			String queryString = "from Orders";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re)
		{
			log.error("find all failed", re);
			throw re;
		}
	}

	public Orders merge(Orders detachedInstance)
	{
		log.debug("merging Orders instance");
		try
		{
			Orders result = (Orders) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re)
		{
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Orders instance)
	{
		log.debug("attaching dirty Orders instance");
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

	public void attachClean(Orders instance)
	{
		log.debug("attaching clean Orders instance");
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