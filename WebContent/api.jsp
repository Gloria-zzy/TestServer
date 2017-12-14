<%@ page language="java" import="com.udchina.nuist.Sms"
	import="com.udchina.nuist.DBAccess" import="com.udchina.nuist.User"
	import="com.udchina.nuist.Order" import="java.util.ArrayList"
	import="com.udchina.nuist.utils.DBConn"
	import="com.udchina.nuist.bean.Orders"
	import="com.udchina.nuist.tools.MD5Tool"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	out.clear();
	String action = request.getParameter("action");

	if (action != null)
	{
		
		if (action.equals("send_pass"))	// send pass to client
		{

			String phone = request.getParameter("item_phone");
			DBAccess dba = new DBAccess();

			try
			{
				Sms sms = new Sms(phone);
				dba.addCode(MD5Tool.md5(phone), sms.getCode());
				out.print("{\"status\":1}");
			} catch (Exception e)
			{
				out.print("{\"status\":0}");
			}

		} else if (action.equals("login"))	// client login model
		{
			// if user exist, just judge the code
			// if user doesn't exist, first register,  
			String phone_md5 = request.getParameter("phone_md5");
			String code_rec = request.getParameter("key_code");
			String phone = request.getParameter("item_phone");

			DBAccess dba = new DBAccess();
			String code = dba.getCode(phone_md5);

			if (dba.userExist(phone))
			{
				if (code_rec.equals(code))
				{
					out.print("{\"status\":1,\"token\":" + phone + "}");
				} else
				{
					out.print("{\"status\":0,\"token\":\"\"}");
				}
			} else
			{
				
				if (code_rec.equals(code))
				{
					dba.register(phone);
					out.print("{\"status\":2,\"token\":" + phone + "}");
				} else
				{
					out.print("{\"status\":0,\"token\":\"\"}");
				}
			}

		} else if (action.equals("upload_token"))	// client upload token, Server judge whether it's valid
		{
			String token = request.getParameter("token");
			DBAccess dba = new DBAccess();
			if (dba.userExist(token))
			{
				out.print("{\"status\":1}");
			} else
			{
				out.print("{\"status\":0}");
			}
		} else if (action.equals("upload_address"))	// client upload address, Server save it to table users_info
		{
			String phone = new String(request.getParameter("item_phone")
					.getBytes("iso-8859-1"), "utf-8");
			String school = new String(
					request.getParameter("address_school")
							.getBytes("iso-8859-1"),
					"utf-8");
			String area = new String(
					request.getParameter("address_area")
							.getBytes("iso-8859-1"),
					"utf-8");
			String building = new String(
					request.getParameter("address_building")
							.getBytes("iso-8859-1"),
					"utf-8");
			String room = new String(
					request.getParameter("address_room")
							.getBytes("iso-8859-1"),
					"utf-8");

			DBAccess dba = new DBAccess();

			dba.test(school);
			dba.test(school);
			try
			{
				if (dba.saveAddress(phone, school, area, building,
						room))
				{
					out.print("{\"status\":1,\"message\":" + school
							+ "}");
				} else
				{
					out.print("{\"status\":0}");
					out.print(
							"{\"status\":0,\"token\":\"failed to save\"}");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				out.print("{\"status\":0}");
				out.print("{\"status\":0}");
			}

		} else if (action.equals("download_address"))	// sendback the address that match the phone number sent by client
		{
			String phone = request.getParameter("item_phone");
			DBAccess dba = new DBAccess();
			User user = dba.getAddress(phone);
			out.print("{\"status\":1,\"address_school\":"
					+ user.getSchool() + ",\"address_area\":"
					+ user.getArea() + ",\"address_building\":"
					+ user.getBuilding() + ",\"address_room\":"
					+ user.getRoom() + "}");
		} else if (action.equals("upload_order"))	// client upload order, Server save it to table 
		//!!!!!!!!!!!!!!!!!there is a problem!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		{
			String phone = new String(request.getParameter("item_phone")
					.getBytes("iso-8859-1"), "utf-8");
			String location = new String(
					request.getParameter("order_location")
							.getBytes("iso-8859-1"),
					"utf-8");
			String time = new String(request.getParameter("order_point")
					.getBytes("iso-8859-1"), "utf-8");
			String note = new String(request.getParameter("order_note")
					.getBytes("iso-8859-1"), "utf-8");
			String date = new String(request.getParameter("order_date")
					.getBytes("iso-8859-1"), "utf-8");
			String takenum = new String(request.getParameter("order_takenum")
					.getBytes("iso-8859-1"), "utf-8");
			
			DBAccess dba = new DBAccess();

			if (location.equals("默认地址"))
			{
				User user = dba.getAddress(phone);
				location = user.getArea() + user.getBuilding()
						+ user.getRoom();
			}

			try
			{
				String orderNum = dba.newOrder(phone, location, time,
						note, date, takenum);
				/* out.print("{\"status\":1}"); */
				out.print("{\"status\":1,\"orderNum\":"+ orderNum +"}");
				
			} catch (Exception e)
			{
				out.print("{\"status\":0}");
				e.printStackTrace();
			}
		} else if (action.equals("download_orders"))	// sendback the orders match the phone number sent by client 
		{
			String phone = new String(request.getParameter("item_phone")
					.getBytes("iso-8859-1"), "utf-8");

			DBAccess dba = new DBAccess();
			DBConn dbc = new DBConn();
			
			try
			{
				ArrayList<Orders> orderList = dbc.getOrders(phone);

				String resultSet = "{\"status\":1,\"orders\":[ ";
				for (Orders o : orderList)
				{
					resultSet += "{\"order_number\":" + o.getOrderNum()
							+ ",\"item_phone\":" + o.getPhone()
							+ ",\"order_point\":" + o.getTime()
							+ ",\"order_location\":" + o.getLocation()
							+ ",\"order_note\":" + o.getNote()
							+ ",\"order_date\":" + o.getDate()
							+ ",\"order_status\":" + o.getStatus()
							+ ",\"order_takenum\":" + o.getTakenum()
							+ "},";
				}
				resultSet = resultSet.substring(0,
						resultSet.length() - 1);
				resultSet += "]}";

				out.print(resultSet);

			} catch (Exception e)
			{
				out.print("{\"status\":0}");
				e.printStackTrace();
			}
		} else if (action.equals("complete_order")) 
		{
			String orderNum = request.getParameter("order_number");
			
			DBConn dbc = new DBConn();
			
			dbc.completeOrder(orderNum);
			
			out.print("{\"status\":1}");
		} else if (action.equals("download_waiting_orders"))
		{
			DBConn dbc = new DBConn();
			
			try
			{
				ArrayList<Orders> orderList = dbc.getWaitingOrders();

				String resultSet = "{\"status\":1,\"orders\":[ ";
				for (Orders o : orderList)
				{
					resultSet += "{\"order_number\":" + o.getOrderNum()
							+ ",\"item_phone\":" + o.getPhone()
							+ ",\"order_point\":" + o.getTime()
							+ ",\"order_location\":" + o.getLocation()
							+ ",\"order_note\":" + o.getNote()
							+ ",\"order_date\":" + o.getDate()
							+ ",\"order_status\":" + o.getStatus()
							+ ",\"order_takenum\":" + o.getTakenum()
							+ "},";
				}
				resultSet = resultSet.substring(0,
						resultSet.length() - 1);
				resultSet += "]}";

				out.print(resultSet);

			} catch (Exception e)
			{
				out.print("{\"status\":0}");
				e.printStackTrace();
			}
		}
	} else
	{
		out.print("请指定action");
	}
%>