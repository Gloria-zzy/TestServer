package com.udchina.nuist;

public class Order
{
	private String orderNum;
	private String phone;
	private String time;
	private String location;
	private String note;
	private String date;
	private int status;
	
	public Order(String orderNum, String phone, String time, String location, String note, int status)
	{
		this.orderNum = orderNum;
		this.phone = phone;
		this.time = time;
		this.location = location;
		this.note = note;
		this.status = status;
	}
	
	public Order(String orderNum, String phone, String time, String location, String note, String date, int status)
	{
		this.orderNum = orderNum;
		this.phone = phone;
		this.time = time;
		this.location = location;
		this.note = note;
		this.date = date;
		this.status = status;
	}

	public String getLocation()
	{
		return location;
	}

	public String getNote()
	{
		return note;
	}

	public String getOrderNum()
	{
		return orderNum;
	}

	public String getPhone()
	{
		return phone;
	}

	public int getStatus()
	{
		return status;
	}

	public String getTime()
	{
		return time;
	}
	
	public String getDate()
	{
		return date;
	}
	
}
