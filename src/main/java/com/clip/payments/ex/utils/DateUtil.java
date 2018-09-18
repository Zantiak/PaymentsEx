package com.clip.payments.ex.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
    public static Date stringToDate(String operationDate) throws ParseException {
    	return SDF.parse(operationDate);
    }
    
    public static String dateToString(Date date) throws ParseException {
    	return SDF.format(date);
    }    

}
