package com.udchina.nuist;

public class User
{
	private String room;
	private String area;
	private String building;
	private String school;
	private String phone;
	
	public User(String phone, String school, String area, String building, String room)
	{
		this.phone = phone;
		this.school = school;
		this.area = area;
		this.building = building;
		this.room = room;
	}
	
	public String getArea()
	{
		return area;
	}
	public String getBuilding()
	{
		return building;
	}
	public String getPhone()
	{
		return phone;
	}
	public String getRoom()
	{
		return room;
	}
	public String getSchool()
	{
		return school;
	}
}
