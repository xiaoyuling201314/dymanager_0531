package com.dayuan.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.xerces.impl.dv.util.Base64;

import com.dayuan.util.Tools;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;

public class TestMD5 {
  public static void main(String[] args) throws NoSuchAlgorithmException {
//	MessageDigest md=MessageDigest.getInstance("MD5");
//	md.update("123456".getBytes());
//	String str=new String(Base64.encode(md.digest()));
//	System.out.println("加密后的密码："+str);
	  
//	 System.out.println(System.currentTimeMillis()+"\n"+new
//			 Random().nextInt(1000));
//	  System.out.println(Tools.getUUID());
	  
//	  System.out.println(Tools.getDateTimeString(new Date()));
//	  String str="";
//	  System.out.println(str!=""?"123":"456");
	  
//	  Calendar c=Calendar.getInstance();
//	  Date d=new Date();
//	  c.set(Calendar.YEAR, d.getYear());
//	  System.out.println(c.getActualMaximum(Calendar.DAY_OF_YEAR));
	  System.out.println(percent(2,10));
}
  
  public   static  String percent( double  p1,  double  p2){
	   String str;
	   double  p3  = (double) p1  /  (double)p2;
	   DecimalFormat df = new DecimalFormat("0.00");//格式化小数 
	   str= df.format(p3);//返回的是String类型 
	 return  str;
  }
}
