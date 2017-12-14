package com.udchina.nuist.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.udchina.nuist.bean.Orders;
import com.udchina.nuist.bean.UsersInfo;

public class DBConn
{
	// 连接数据库
	static final String jdbc_dirver = "com.mysql.jdbc.Driver";
	static final String db_url = "jdbc:mysql://localhost/phone_code";

	// 数据库帐号
	static final String user = "root";
	static final String pass = "charles";

	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	private Query query;

	public DBConn()
	{
		config = new Configuration().configure();
		sessionFactory = config.buildSessionFactory();
	}

	private Session getSession()
	{
		Session session = sessionFactory.openSession();
		return session;
	}
	
	public ArrayList<Orders> getWaitingOrders()
	{
		ArrayList<Orders> orderList = new ArrayList<Orders>();
		session = getSession();
		Transaction ts = session.beginTransaction();
		setQuery(session.createQuery("from Orders where status=2"));

		@SuppressWarnings("unchecked")
		List<Orders> list = getQuery().list();
		orderList = (ArrayList<Orders>) list;

		ts.commit();
		session.close();
		
		for (Orders o : orderList)
		{
			System.out.println(o.getDate());
		}

		return orderList;
	}
	
	public boolean updateOrder(String phone, String location, String time,
			String note, String date, String takeNum)
	{
		
		return false;
	}

	public ArrayList<Orders> getOrders(String phone)
	{
		ArrayList<Orders> orderList = new ArrayList<Orders>();
		session = getSession();
		Transaction ts = session.beginTransaction();
		setQuery(session.createQuery("from Orders where phone=:phoneNum"));
		getQuery().setParameter("phoneNum", phone);

		@SuppressWarnings("unchecked")
		List<Orders> list = getQuery().list();
		orderList = (ArrayList<Orders>) list;

		ts.commit();
		session.close();
		
		for (Orders o : orderList)
		{
			System.out.println(o.getDate());
		}

		return orderList;
	}

	// create order number
	public String newOrderNum()
	{
		String orderNum;
		orderNum = (int) (Math.random() * 10000) + ""
				+ (int) (Math.random() * 10000) + ""
				+ (int) (Math.random() * 10000);
		return orderNum;
	}

	public boolean completeOrder(String orderNum)
	{
		session = getSession();
		Transaction ts = session.beginTransaction();
		setQuery(session.createQuery(
				"update Orders set status = 0 where orderNum=:orderNum"));
		getQuery().setParameter("orderNum", orderNum);
		getQuery().executeUpdate();
		ts.commit();
		session.close();

		return true;
	}

	// return order number
	public String newOrder(String phone, String location, String time,
			String note)
	{
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			Class.forName(jdbc_dirver);
			conn = DriverManager.getConnection(db_url, user, pass);

			String sql = "select * from orders where orderNum=?";
			String orderNum = newOrderNum();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNum);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rs.close();
				orderNum = newOrderNum();
				pstmt.setString(1, orderNum);
				rs = pstmt.executeQuery();
			}

			rs.close();

			sql = "insert into orders values(?, ?, ?, ?, ?, 1)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNum);
			pstmt.setString(2, phone);
			pstmt.setString(3, time);
			pstmt.setString(4, location);
			pstmt.setString(5, note);

			pstmt.execute();

			rs.close();
			conn.close();

			return orderNum;
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public UsersInfo getAddress(String phone)
	{
		String _phone = "'" + phone + "'";

		String sql = "select school, area, building, room from users_info where phone = "
				+ _phone;

		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;

		try
		{
			Class.forName(jdbc_dirver);
			conn = DriverManager.getConnection(db_url, user, pass);
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			UsersInfo user = null;

			while (rs.next())
			{
				String school = rs.getString("school");
				String area = rs.getString("area");
				String building = rs.getString("building");
				String room = rs.getString("room");
				user = new UsersInfo(phone, school, area, building, room);
			}

			rs.close();
			stmt.close();
			conn.close();

			return user;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public boolean saveAddress(String phone, String school, String area,
			String building, String room)
	{
		String _phone = "'" + phone + "'";
		String _school = "'" + school + "'";
		String _area = "'" + area + "'";
		String _building = "'" + building + "'";
		String _room = "'" + room + "'";

		System.out.println(_school);

		String sql = "update users_info set school = " + _school + ", area = "
				+ _area + ", building = " + _building + ", room = " + _room
				+ " where phone = " + _phone;

		return DoSQL_ins(sql);
	}

	public boolean userExist(String phone)
	{
		String _phone = "'" + phone + "'";
		ResultSet rs = null;
		String sql = "select * from users_info where phone = " + _phone;

		Connection conn = null;
		Statement stmt = null;
		try
		{
			// register jdbc driver
			Class.forName(jdbc_dirver);

			// open a connection
			conn = DriverManager.getConnection(db_url, user, pass);

			// execute a query
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				rs.close();
				conn.close();
				stmt.close();
				return true;
			} else
			{
				rs.close();
				conn.close();
				stmt.close();
				return false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}

	// register
	public boolean register(String phone)
	{
		String _phone = "'" + phone + "'";
		String sql = "insert into users_info values(" + _phone
				+ ",'','','','')";

		return DoSQL_ins(sql);
	}

	// add to regist_info
	public boolean regist(String username, String password, String phone,
			String email, String address)
	{
		String _username = "'" + username + "'";
		String _password = "'" + password + "'";
		String _phone = "'" + phone + "'";
		String _email = "'" + email + "'";
		String _address = "'" + address + "'";

		String sql = "insert into regist_info values(" + _username + ","
				+ _password + "," + _phone + "," + _email + "," + _address
				+ ")";

		return DoSQL_ins(sql);

	}

	// add to users
	public boolean addCode(String phone_md5, String code)
	{
		String _phone_md5 = "'" + phone_md5 + "'";
		String _code = "'" + code + "'";
		String sql = "insert into users values(" + _phone_md5 + "," + _code
				+ ")";

		if (DoSQL("select phone_md5, code from users where phone_md5 = "
				+ _phone_md5, "select") != null)
		{
			sql = "update users set code = " + _code + " where phone_md5 = "
					+ _phone_md5;
		}

		return DoSQL_ins(sql);
	}

	public String getCode(String phone_md5)
	{
		String _phone_md5 = "'" + phone_md5 + "'";
		String sql = "select phone_md5, code from users where phone_md5 = "
				+ _phone_md5;
		PC pc = DoSQL(sql, "select");
		System.out.println(
				"phone_md5: " + pc.getPhoneMD5() + "\ncode: " + pc.getCode());
		return pc.getCode();
	}

	// return boolean, execute insert or update
	public boolean DoSQL_ins(String sql)
	{
		boolean rs = false;
		Connection conn = null;
		Statement stmt = null;
		try
		{
			// register jdbc driver
			Class.forName(jdbc_dirver);

			// open a connection
			conn = DriverManager.getConnection(db_url, user, pass);

			// execute a query
			stmt = conn.createStatement();

			rs = stmt.execute(sql);

			stmt.close();
			conn.close();

			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	// execute phone or code orperations
	private PC DoSQL(String sql, String notice)
	{
		PC pc = null;

		Connection conn = null;
		Statement stmt = null;
		//
		try
		{
			// register jdbc driver
			Class.forName(jdbc_dirver);

			// open a connection
			conn = DriverManager.getConnection(db_url, user, pass);

			// execute a query
			stmt = conn.createStatement();
			ResultSet rs;
			boolean rs_update;

			if (notice.equals("select"))
			{
				rs = stmt.executeQuery(sql);
			} else if (notice.equals("insert"))
			{
				rs_update = stmt.execute(sql);
				rs = null;
			} else
			{
				rs = stmt.executeQuery(sql);
			}

			// Extract data from result set
			while (rs != null && rs.next())
			{
				// Retrieve by column name
				String phone = rs.getString("phone_md5");
				String code = rs.getString("code");

				pc = new PC(phone, code);
			}

			// clean-up environment
			if (rs != null)
			{
				rs.close();
			}
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e)
		{
			// handle errors for class.forname
			e.printStackTrace();
		} catch (SQLException se)
		{
			// handle errors for JDBC
			se.printStackTrace();
		} finally
		{
			// finally block used to close resources
			try
			{
				if (stmt != null)
				{
					stmt.close();
				}
			} catch (SQLException se2)
			{

			} // nothing we can do
			try
			{
				if (conn != null)
				{
					conn.close();
				}
			} catch (SQLException se)
			{
				se.printStackTrace();
			} // end finally try
		} // end try
		if (pc == null)
		{
			System.out.println("PC failed..............................");
		} else
		{
			System.out.println("PC succeeded..............................");
		}
		return pc;
	}

	public void test(String action)
	{
		System.out.println(": " + action);
	}

	public Query getQuery()
	{
		return query;
	}

	public void setQuery(Query query)
	{
		this.query = query;
	}

	class PC
	{
		private String phone;
		private String phone_md5;
		private String code;

		public PC()
		{
		}

		public PC(String p, String c)
		{
			phone_md5 = p;
			code = c;
		}

		public void setPhone_md5(String phone_md5)
		{
			this.phone_md5 = phone_md5;
		}

		public void setCode(String code)
		{
			this.code = code;
		}

		public String getCode()
		{
			return code;
		}

		public String getPhoneMD5()
		{
			return phone_md5;
		}

		public String getPhone()
		{
			return phone;
		}

		public void setPhone(String phone)
		{
			this.phone = phone;
		}
	}

}
