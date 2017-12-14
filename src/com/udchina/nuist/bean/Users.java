package com.udchina.nuist.bean;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable
{

	// Fields

	private String phoneMd5;
	private String code;

	// Constructors

	/** default constructor */
	public Users()
	{
	}

	/** minimal constructor */
	public Users(String phoneMd5)
	{
		this.phoneMd5 = phoneMd5;
	}

	/** full constructor */
	public Users(String phoneMd5, String code)
	{
		this.phoneMd5 = phoneMd5;
		this.code = code;
	}

	// Property accessors

	public String getPhoneMd5()
	{
		return this.phoneMd5;
	}

	public void setPhoneMd5(String phoneMd5)
	{
		this.phoneMd5 = phoneMd5;
	}

	public String getCode()
	{
		return this.code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

}