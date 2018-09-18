package com.clip.payments.ex.view;

public class ViewTestConstants {
	
	//Constants values for testing the view
	public final static String VALID_ADD_INPUT = "66666 add { \"amount\": 1.23, \"description\": \"Joes Tacos\", \"date\":\"2018-12-30\", \"user_id\": 345 }";
	public final static String VALID_OPERATION_INPUT = "66666 123e4567-e89b-12d3-a456-556642440000";
	public final static String INCOMPLETE_INPUT = "{ \"amount\": 1.23, \"description\": \"Joes Tacos\", \"date\":\"2018-12-30\", \"user_id\": 345 }";
	public final static String INCORRECT_INPUT = "incorrect input";

}
