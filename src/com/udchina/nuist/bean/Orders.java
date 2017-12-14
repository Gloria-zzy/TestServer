package com.udchina.nuist.bean;

/**
 * Orders entity. @author MyEclipse Persistence Tools
 */

public class Orders implements java.io.Serializable
{

	// Fields

	private String orderNum;
	private String phone;
	private String time;
	private String location;
	private String note;
	private Integer status;
	private String date;
	private String takenum;

	// Constructors

	/** default constructor */
	public Orders()
	{
	}

	/** minimal constructor */
	public Orders(String orderNum, String phone, String time, String location,
			Integer status, String date, String takenum)
	{
		this.orderNum = orderNum;
		this.phone = phone;
		this.time = time;
		this.location = location;
		this.status = status;
		this.date = date;
		this.takenum = takenum;
	}

	/** full constructor */
	public Orders(String orderNum, String phone, String time, String location,
			String note, Integer status, String date, String takenum)
	{
		this.orderNum = orderNum;
		this.phone = phone;
		this.time = time;
		this.location = location;
		this.note = note;
		this.status = status;
		this.date = date;
		this.takenum = takenum;
	}

	// Property accessors

	public String getOrderNum()
	{
		return this.orderNum;
	}

	public void setOrderNum(String orderNum)
	{
		this.orderNum = orderNum;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getTime()
	{
		return this.time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getLocation()
	{
		return this.location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getNote()
	{
		return this.note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public Integer getStatus()
	{
		return this.status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getDate()
	{
		return this.date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getTakenum()
	{
		return this.takenum;
	}

	public void setTakenum(String takenum)
	{
		this.takenum = takenum;
	}

}