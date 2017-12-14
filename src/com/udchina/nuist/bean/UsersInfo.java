package com.udchina.nuist.bean;

/**
 * UsersInfo entity. @author MyEclipse Persistence Tools
 */

public class UsersInfo implements java.io.Serializable
{

	// Fields

	private String phone;
	private String school;
	private String area;
	private String building;
	private String room;

	// Constructors

	/** default constructor */
	public UsersInfo()
	{
	}

	/** minimal constructor */
	public UsersInfo(String phone, String school, String building, String room)
	{
		this.phone = phone;
		this.school = school;
		this.building = building;
		this.room = room;
	}

	/** full constructor */
	public UsersInfo(String phone, String school, String area, String building,
			String room)
	{
		this.phone = phone;
		this.school = school;
		this.area = area;
		this.building = building;
		this.room = room;
	}

	// Property accessors

	public String getPhone()
	{
		return this.phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getSchool()
	{
		return this.school;
	}

	public void setSchool(String school)
	{
		this.school = school;
	}

	public String getArea()
	{
		return this.area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public String getBuilding()
	{
		return this.building;
	}

	public void setBuilding(String building)
	{
		this.building = building;
	}

	public String getRoom()
	{
		return this.room;
	}

	public void setRoom(String room)
	{
		this.room = room;
	}

}