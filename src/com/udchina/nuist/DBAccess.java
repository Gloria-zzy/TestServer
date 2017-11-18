package com.udchina.nuist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccess
{
	// 连接数据库
	static final String jdbc_dirver = "com.mysql.jdbc.Driver";
	static final String db_url = "jdbc:mysql://101.132.190.102:3306/phone_code";

	// 数据库帐号
	static final String user = "root";
	static final String pass = "charles";
	
	public void saveAddress(){
		
	}
	
	public boolean userExist(String phone){
		System.out.println("phone or token: " + phone);
		String _phone = "'" + phone + "'";
		ResultSet rs = null;
		String sql = "select * from regist_info where phone = " + _phone;
		
		Connection conn = null;
		Statement stmt = null;
		try
		{
			// register jdbc driver
			Class.forName(jdbc_dirver);
			
			// open a connection
			System.out.println("Connection to database...");
			conn = DriverManager.getConnection(db_url, user, pass);
			
			// execute a query
			System.out.println("Creating statement");
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				rs.close();
				conn.close();
				stmt.close();
				return true;
			} else {
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
			System.out.println("Connection to database...");
			conn = DriverManager.getConnection(db_url, user, pass);

			// execute a query
			System.out.println("Creating statement");
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
			System.out.println("Connection to database...");
			conn = DriverManager.getConnection(db_url, user, pass);

			// execute a query
			System.out.println("Creating statement");
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
