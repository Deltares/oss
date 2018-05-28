package com.worth.deltares.subversion.model;


public class PermissionConstants {

	public static final String PERMISSION_READ 	= "r";
	
	public static final String PERMISSION_WRITE = "w";
	
	public static final String PERMISSION_NONE 	= "-";
	
	public static final String[] PERMISSIONS = {
		PERMISSION_READ, PERMISSION_WRITE, PERMISSION_NONE
	};
	
	public static final String TYPE_USER = "u";
	
	public static final String TYPE_ROLE = "r";
	
	public static final String[] TYPES = {
		TYPE_USER, TYPE_ROLE
	};
}