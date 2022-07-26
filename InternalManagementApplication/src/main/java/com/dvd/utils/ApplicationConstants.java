package com.dvd.utils;

/**
* Defines the constants used within the application.
*
* @author David Gheorghe
*/
public class ApplicationConstants {
	public static final String DEFAULT_PAGE_NUMBER = "0";
	public static final String DEFAULT_PAGE_SIZE = "5";
	public static final String DEFAULT_SORT_BY = "id";
	public static final String DEFAULT_SORT_DIRECTION = "asc";
	
	public static final String JWT_AUTHORIZATION_PREFIX = "Bearer ";
	/** Current VAT (Value-Added Tax) */
	public static final Double VAT = 19.0;
	
	/** API's URIs */
	public static final String API_ROOT= "/api/";
	//TODO remove api from auth roots
	public static final String AUTH_ROOT = API_ROOT + "auth/";
	public static final String AUTH_LOGIN = AUTH_ROOT + "login";
	public static final String AUTH_REFRESH_TOKEN = AUTH_ROOT + "refresh-token";	
	public static final String ADMIN_API_ROOT= "admin" + API_ROOT;
	public static final String ADMIN_API_USER_ROOT = ADMIN_API_ROOT + "users";
	public static final String ADMIN_API_ROLE_ROOT = ADMIN_API_ROOT + "roles";	
	public static final String API_CHANGE_PASSWORD = API_ROOT + "me/change-password";	
	public static final String API_CUSTOMER_ROOT = API_ROOT + "customers";
	public static final String API_COLOR_ROOT = API_ROOT + "colors";
	public static final String API_PRODUCT_CATEGORY_ROOT = API_ROOT + "product-categories";	
	public static final String API_PRODUCT_ROOT = API_ROOT + "products";	
	public static final String API_ORDER_ROOT = API_ROOT + "orders";	
	public static final String API_TODO_ROOT = API_ROOT + "todos";
	
	public static final String CLIENT_SIDE_URL = "http://localhost:8081/";
}
