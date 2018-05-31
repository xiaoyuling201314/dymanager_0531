package com.dayuan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTools {
	
	public static String addSecond(String dateStr,long interval) throws Exception{
		Date date = DateTools.formatStrToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		long time = date.getTime();
		time = time + interval*1000;
		date  = new Date(time);
		String str = formatDateToStr(date, "yyyy-MM-dd HH:mm:ss");
		return str;
	}
	
	public static Date addHour(Date date,int hour){
		date = new Date(date.getTime()+hour*3600000);
		return date;
	}
	
	
	/**
	 * 将格式化的字符串转为日期类型
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public static Date formatStrToDate(String dateStr,String formatStr) throws Exception{
		if(dateStr==null||formatStr==null||dateStr.equals("")||formatStr.equals("")){
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw new Exception("格式不匹配.");
		}
	}
	/**
	 * 将日期格式化为字符串
	 * @param date	日期
	 * @param formatStr	格式化字符串
	 * @return
	 */
	public static String formatDateToStr(Date date,String formatStr){
		if(date==null||formatStr==null||formatStr.equals("")){
			return null;
		}
		return new SimpleDateFormat(formatStr).format(date);
	}
	
	public static int getYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	public static int getMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH)+1;
	}
	public static int getDay(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	public static int getHour(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	public static int getMinute(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MINUTE);
	}
	public static int getSecond(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.SECOND);
	}
	public static int getMilliSecond(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MILLISECOND);
	}

}
