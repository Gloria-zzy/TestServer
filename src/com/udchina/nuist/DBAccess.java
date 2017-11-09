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
	static final String db_url = "jdbc:mysql://localhost/phone_code";

	// 数据库帐号
	static final String user = "root";
	static final String pass = "charles";

	// 添加记录
	public boolean addCode(String phone_md5, String code)
	{
		String _phone_md5 = "'" + phone_md5 + "'";
		String _code = "'" + code + "'";
		String sql = "insert into users values(" + _phone_md5 + "," + _code
				+ ")";

		if (DoSQL("select phone_md5, code from users where phone_md5 = "
				+ _phone_md5, "select") != null)
		{
			sql = "update users set code = " + _code + " where phone_md5 = " + _phone_md5;
		}

		try
		{
			DoSQL(sql, "insert");
		} catch (Exception e)
		{
			return false;
		}

		return true;
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

		public String getCode()
		{
			return code;
		}

		public String getPhoneMD5()
		{
			return phone_md5;
		}
	}
}