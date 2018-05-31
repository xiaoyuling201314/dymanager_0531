package com.dayuan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.xerces.impl.dv.util.Base64;
import org.dom4j.Document;
import org.dom4j.io.XMLWriter;

import com.ibm.icu.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
public final class Tools {
	/**
	 * 加密字符串
	 * @param sStr         --要加密的字符串
	 * @param securityType --MD5或SHA
	 * @return String
	 */
	public static String getSecurityString(String sStr, String securityType) {
		String returnStr = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(securityType);
			md.update(sStr.getBytes());
			returnStr = new String(Base64.encode(md.digest()));
		}catch(Exception e) {}
		
		if(returnStr.equals("")) {
			returnStr = sStr;
		}
		return returnStr.trim();
	}
	
	public static boolean isInt(String number){
		try{
			Integer.parseInt(number);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	/**
	 * 根据日志的操作内容给日志分类
	 */
	public static int getLogType(int opType){
		int logType=0;
		if((opType>=1&&opType<=8)||(opType>=13&&opType<=16)||(opType>=31&&opType<=40)||(opType>=46 && opType<=57)){
			logType=1;
		}else if((opType>=18&&opType<=24)||(opType>=43&&opType<=45)){
			logType=2;
		}
		return logType;
	}
	
	/**
	 * 返回系统时间字符串
	 * @return String
	 */
	public static String getDateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //采用默认的格式
		return sdf.format(new Date());
		
	}
	
	/**
	 * 精确到秒
	 * @param DateString
	 * @return
	 */
	public static Date getDate (String DateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //采用默认的格式
		try{
		return sdf.parse(DateString);
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 返回系统日期字符串
	 * @param date         --日期
	 * @return String
	 */
	public static String getDateString1(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   //采用年月日
		return sdf.format(date);
	}
	
	/**
	 * 精确到微秒
	 * @param DateString
	 * @return
	 */
	public static Date getDate1 (String DateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");   //采用默认的格式
		try{
		return sdf.parse(DateString);
		}
		catch(Exception e){
			return null;
		}
	}
	/**
	 * 返回系统日期时间字符串
	 * @param datetimeType --时间格式(如yyyyMMddHHmmss)
	 * @return String
	 */
	public static String getDateTimeString(String datetimeType) {
		SimpleDateFormat sdf = new SimpleDateFormat(datetimeType);
		return sdf.format(new Date());
	}
	
	/**
	 * 返回系统时间字符串
	 * @param dateTime         --日期时间
	 * @return String
	 */
	public static String getDateTimeString(Date dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //采用默认的格式
		return sdf.format(dateTime);
	}
	/**
	 * 返回系统时间字符串精确到微秒
	 * @param dateTime         --日期时间
	 * @return String
	 */
	public static String getDateTimeString1(Date dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");   //采用默认的格式
		return sdf.format(dateTime);
	}
	/**
	 * 返回系统日期字符串
	 * @param date         --日期
	 * @return String
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //采用默认的格式
		return sdf.format(date);
	}
	
	/**
	 * 返回系统时间字符串
	 * @param date         --时间
	 * @param datetimeType --时间格式(如yyyyMMddHHmmss)
	 * @return String
	 */
	public static String getDateTimeString(Date date, String datetimeType) {
		SimpleDateFormat sdf = new SimpleDateFormat(datetimeType);
		return sdf.format(date);
	}
	 /**
	  * 一个日期加上小时后变成新的时间
	  * 
	  * @param date 日期
	  * @param minute 小时
	  * @return 返回相加后的日期
	  */
	 public static java.util.Date addDate(Date date, long minute) {
	  java.util.Calendar c = java.util.Calendar.getInstance();
	  c.setTimeInMillis(getMillis(date) + minute * 60 * 1000);
	  return c.getTime();
	 }
	 /**
	  * 一个日期减去分钟后变成新的时间
	  * 
	  * @param date 日期
	  * @param hour 分种
	  * @return 返回相加后的日期
	  */
	 public static java.util.Date subDate(Date date, long minute) {
	  java.util.Calendar c = java.util.Calendar.getInstance();
	  c.setTimeInMillis(getMillis(date)-minute * 60 * 1000);
	  return c.getTime();
	 }
	 
	 /**
	  * 一个日期减去分钟后变成新的时间
	  * 
	  * @param date 日期
	  * @param hour 分种
	  * @return 返回相加后的日期
	  */
	 public static java.util.Date subDate1(Date date, int second) {
	  java.util.Calendar c = java.util.Calendar.getInstance();
	  c.setTimeInMillis(getMillis(date)-second * 1000);
	  return c.getTime();
	 }
	 
	 /**
	  * 返回毫秒
	  * 
	  * @param date  日期
	  * @return 返回毫秒
	  */
	 public static long getMillis(Date date) {
	  java.util.Calendar c = java.util.Calendar.getInstance();
	  c.setTime(date);
	  return c.getTimeInMillis();
	 }
	 /**
	  * 两个日期之间相差几秒
	  * @param beginTime
	  * @param endTime
	  * @return
	  */
	 public static int subDate(String beginTime,String endTime){
		 Date begin=getDate(beginTime);
		 Date end=getDate(endTime);
		 return (int)(getMillis(end)-getMillis(begin)/1000);
	 }
	/**
	 * 将时间格式的字符串转换成时间类型
	 * @param datetimeString --时间格式的字符串
	 * @return
	 */
	public static Date parseStringToDate(String datetimeString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //采用默认的格式
		Date dt = null;
		try {
			dt = sdf.parse(datetimeString);
		}catch(Exception e) {}
		return dt;
	}
	
	/**
	 * 将时间格式的字符串转换成时间类型
	 * @param datetimeString --时间格式的字符串
	 * @param datetimeType   --时间格式
	 * @return
	 */
	public static Date parseStringToDate(String datetimeString, String datetimeType) {
		SimpleDateFormat sdf = new SimpleDateFormat(datetimeType);
		Date dt = null;
		try {
			dt = sdf.parse(datetimeString);
		}catch(Exception e) {}
		return dt;
	}
	
	/**   
	 * 获得某一日期的前一天   
	 * @param date   
	 * @return Date   
	 */    
	public static Date getPreviousDate(Date date){    
	Calendar calendar=Calendar.getInstance();    
	calendar.setTime(date);    
	int day=calendar.get(Calendar.DATE);    
	calendar.set(Calendar.DATE,day-1);    
	return new Date(calendar.getTime().getTime());    
	} 
	
	/**
	 * 根据输入数据获取是星期的那一天
	 * @param i
	 * @return
	 */
	public static String[] getDateString(Integer i){
		
		String []ss=new String[7];
		for(int j=0;j<ss.length;j++){
			ss[j]="0";
		}
		byte []bs=new byte[4];
		bs=int2bytes(Integer.parseInt(i.toString()));
		byte b=bs[3];//获取字节
		if(((int)(b&0x01))!=0){
			ss[0]="1";
		}
		if(((int)(b&0x02))!=0){
			ss[1]="1";
		}
		if(((int)(b&0x04))!=0){
			ss[2]="1";
		}
		if(((int)(b&0x08))!=0){
			ss[3]="1";
		}
		if(((int)(b&0x10))!=0){
			ss[4]="1";
		}
		if(((int)(b&0x20))!=0){
			ss[5]="1";
		}
		if(((int)(b&0x40))!=0){
		    ss[6]="1";
		}
		return ss;
	}
    
	/**
	 * 当然Int转换成byte数据
	 * @param num
	 * @return
	 */
	public static byte[] int2bytes(int num)
	{
	      byte[] b=new byte[4];
	      for(int i=0;i<4;i++){
	            b[i]=(byte)(num>>>(24-i*8));
	       }
	      return b;
	}
	/**
	 * 将一个byte转换成int
	 * @param b
	 * @return
	 */
	public static int ChangeByte(byte b){
		int res=0;
		int mask=0xff;
        int temp=0;
    	res<<=8;
        temp=b&mask;
        res|=temp;
        return res;
	}
	
	/**
	 * 将byte数组转换成long
	 * @param b
	 * @return
	 */
	public static  long ChangeByte(byte[] b){
		long mask=0xff;
        long temp=0;
       long res=0;
       for(int i=0;i<b.length;i++){
           res<<=8;
           temp=b[i]&mask;
           res|=temp;
       }
      return res;
	}
	
	/**
	 * 将byte数组转换成long
	 * @param b
	 * @return
	 */
	public static  long ChangeByte1(byte[] b){
		long mask=0xff;
        long temp=0;
       long res=0;
       for(int i=b.length-1;i>=0;i--){
           res<<=8;
           temp=b[i]&mask;
           res|=temp;
       }
      return res;
	}
	/**
	 * 将byte数组转换成long
	 * @param b
	 * @return
	 */
	public static  int ChangeByte2(byte[] b){
		int mask=0xff;
        int temp=0;
       int res=0;
       for(int i=b.length-1;i>=0;i--){
           res<<=8;
           temp=b[i]&mask;
           res|=temp;
       }
      return res;
	}
	/**
	 * 生成UUID
	 * @return String
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	
	/**
	 * 向页面输出文本内容
	 * @param str
	 * @param reponse
	 */
	public static void printWriter(String str, javax.servlet.http.HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		java.io.PrintWriter out;
		try {
			out = response.getWriter();
			out.print(str);
		}catch(java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输入图片
	 * @param filePath--数据来源
	 * @param response
	 */
	public static void showPhoto(String filePath, javax.servlet.http.HttpServletResponse response) {
        
        println("开始输出要下载的文件...");

		//打开指定文件的流信息
        /**FileInputStream fs = null;
        File  dlfile=null;
        try {
        	dlfile=new File(filePath); 
             fs = new FileInputStream(dlfile);
        }catch(FileNotFoundException e) {
        	e.printStackTrace();
        	return;
        }
        //设置响应头和保存文件名 
        //response.setContentType("text/tiff"); 显示传真
        response.setContentType("image/jpg");
        response.setHeader("Content-Length",String.valueOf(dlfile.length())); 
        //写出流信息
        int b = 0;
        try {
            PrintWriter out = response.getWriter();
            while((b=fs.read())!=-1) {
        	    out.write(b);
            }
            fs.close();
            out.close();
            println("文件下载完毕.");
        }catch(Exception e) {
        	e.printStackTrace();
        	println("下载文件失败!");
        }**/
        try{ 
        	FileInputStream hFile = new FileInputStream(filePath); // 以byte流的方式打开文件 d:\1.gif 
        	int i=hFile.available(); //得到文件大小 
        	byte data[]=new byte[i]; 
        	hFile.read(data); //读数据 
        	hFile.close(); 
        	response.setContentType("image/*"); //设置返回的文件类型 
        	OutputStream toClient=response.getOutputStream(); //得到向客户端输出二进制数据的对象 
        	toClient.write(data); //输出数据 
        	toClient.close(); 
        	} 
        	catch(IOException e) //错误处理 
        	{ 
        	//PrintWriter toClient = (PrintWriter)response.getWriter(); //得到向客户端输出文本的对象 
        	//response.setContentType("text/html;charset=gb2312"); 
        	//toClient.write("无法打开图片!"); 
        	//toClient.close(); 
        		e.printStackTrace();
        	} 
	}
	
	/**
	 * 取得随机数 字符串
	 * @param num --指定字符个数
	 * @return String
	 */
	public static String getRandomNum(int num) {
		String rStr = "";
		Random rdm = new Random();
		rStr = String.valueOf(Math.abs(rdm.nextInt()));
		if(num > rStr.length())return rStr;
		return rStr.substring(0, num);
	}
	
	
	/**
	 * 根据对象实例返回这个实例的所有get方法返回的数据类型、字段名、返回值
	 * @param instance
	 * @return Collection
	 */
	public static Collection<Object> getMethodInfo(Object instance) {
		Collection<Object> methodInfo = new ArrayList<Object>();
		Object[] fv = null;
		Method[] methods = instance.getClass().getDeclaredMethods();
		for(int i=0;i<methods.length;i++) {
			String methodName = methods[i].getName();
			if(methodName.startsWith("get")) {
    			Object[] args = null;
    			Object returnValue = null;
				try {
					returnValue = methods[i].invoke(instance, args);
					if(returnValue != null && !returnValue.equals("")) {
						//获取实际字段名
    					String fieldName = methodName.substring(3,methodName.length());
    					String oneWord = fieldName.substring(0,1).toLowerCase();
    					String endAllWord = fieldName.substring(1, fieldName.length());
    					fieldName = oneWord + endAllWord;
    					
						fv = new Object[3];
						fv[0] = methods[i].getReturnType();   //保存返回类型
						fv[1] = fieldName;   //保存字段名
						fv[2] = returnValue;   //保存返回值
						methodInfo.add(fv);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return methodInfo;
	}
	
	/**
	 * 判断文件是否存在
	 * @param filePath --文件完整物理路径
	 * @return boolean
	 */
	public static boolean isFileExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	/**
	 * 删除指定路径的文件
	 * @param filePath --文件完整物理路径
	 * @return boolean
	 */
	public static boolean delFile(String filePath) {
		boolean mark = false;
		if(isFileExists(filePath)) {
			File dFile = new File(filePath);
			mark = dFile.delete();
		}
		return mark;
	}
	
	/**
	 * 下载文件
	 * @param filePath --文件完整路径
	 * @param response
	 */
	public static void downloadXLSFile(
			String filePath, 
			javax.servlet.http.HttpServletResponse response) {
		
		println("开始输出要下载的文件...");
		
        String fileName = "";
        try {
        	if(filePath.lastIndexOf("/") > 0) {
        		fileName = new String(filePath.substring(filePath.lastIndexOf("/")+1, filePath.length()).getBytes("GB2312"), "ISO8859_1");
        	}else if(filePath.lastIndexOf("\\") > 0) {
        		fileName = new String(filePath.substring(filePath.lastIndexOf("\\")+1, filePath.length()).getBytes("GB2312"), "ISO8859_1");
        	}
        	
        }catch(Exception e) {}
		//打开指定文件的流信息
        FileInputStream fs = null;
        try {
             fs = new FileInputStream(new File(filePath));
        }catch(FileNotFoundException e) {
        	e.printStackTrace();
        	return;
        }
        //设置响应头和保存文件名 
        response.setContentType("application/xls;charset=utf-8"); 
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        //写出流信息
        int b = 0;
        try {
            PrintWriter out = response.getWriter();
            while((b=fs.read())!=-1) {
        	    out.write(b);
            }
            fs.close();
            out.close();
            println("文件下载完毕.");
        }catch(Exception e) {
        	e.printStackTrace();
        	println("下载文件失败!");
        }
	}
	
	/**
	 * 下载文件
	 * @param filePath --文件完整路径
	 * @param response
	 */
	public static void downloadFile(
			String filePath, 
			javax.servlet.http.HttpServletResponse response) {
		
		println("开始输出要下载的文件...");
		
        String fileName = "";
        try {
        	if(filePath.lastIndexOf("/") > 0) {
        		fileName = new String(filePath.substring(filePath.lastIndexOf("/")+1, filePath.length()).getBytes("GB2312"), "ISO8859_1");
        	}else if(filePath.lastIndexOf("\\") > 0) {
        		fileName = new String(filePath.substring(filePath.lastIndexOf("\\")+1, filePath.length()).getBytes("GB2312"), "ISO8859_1");
        	}
        	
        }catch(Exception e) {}
		//打开指定文件的流信息
        FileInputStream fs = null;
        try {
             fs = new FileInputStream(new File(filePath));
        }catch(FileNotFoundException e) {
        	e.printStackTrace();
        	return;
        }
        //设置响应头和保存文件名 
        response.setContentType("APPLICATION/OCTET-STREAM"); 
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        //写出流信息
        int b = 0;
        try {
            PrintWriter out = response.getWriter();
            while((b=fs.read())!=-1) {
        	    out.write(b);
            }
            fs.close();
            out.close();
            println("文件下载完毕.");
        }catch(Exception e) {
        	e.printStackTrace();
        	println("下载文件失败!");
        }
	}
	
	/**
	 * 输出多个WAV文件流
	 * @param filePaths --多个WAV文件完整路径集合
	 * @param response
	 */
	public static void downloadMultiWavIn(
			String[] filePaths, 
			javax.servlet.http.HttpServletResponse response) {
        //设置响应头和保存文件名
        response.setContentType("APPLICATION/OCTET-STREAM"); 
        response.setHeader("Content-Disposition", "attachment; filename=\"MultiWavIn.wav\"");

        //合并多个WAV------------
		int totalSize = 0;
		int buffLen = 0;
		int realIndex = -1;
		byte[] buff = new byte[245760];   //240KB
		BufferedInputStream bi = null;
		try {
			for(int i=0;i<filePaths.length;i++) {
				if(!isFileExists(filePaths[i])) {
					continue;
				}
				realIndex ++;
				bi = new BufferedInputStream(new FileInputStream(new File(filePaths[i])));
				if(realIndex == 0) {   //读取第一个文件
					while((buffLen=bi.read(buff))!=-1) {
						totalSize += buffLen;
					}
				}else {
					while((buffLen=bi.read(buff))!=-1) {
						totalSize += buffLen;
					}
					totalSize -= 44;
				}
				bi.close();
			}
			//println("计算即将合并后的WAV文件大小==>" + totalSize);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		realIndex = -1;
		
		int RIFF_SIZE = totalSize - 8;
		int DATA_SIZE = totalSize - 44;
		byte[] riffSizeBytes = revers(intToBytes(RIFF_SIZE));
		byte[] dataSizeBytes = revers(intToBytes(DATA_SIZE));
		try {
			int b = 0;
			InputStream is = null;
			PrintWriter out = response.getWriter();
			for(int i=0;i<filePaths.length;i++) {
				if(!isFileExists(filePaths[i])) {
					continue;
				}
				realIndex ++;
				bi = new BufferedInputStream(new FileInputStream(new File(filePaths[i])));
				if(realIndex == 0) {   //读取第一个文件
					if((buffLen=bi.read(buff))!=-1) {
						buff[4] = riffSizeBytes[0];
						buff[5] = riffSizeBytes[1];
						buff[6] = riffSizeBytes[2];
						buff[7] = riffSizeBytes[3];
						
						buff[40] = dataSizeBytes[0];
						buff[41] = dataSizeBytes[1];
						buff[42] = dataSizeBytes[2];
						buff[43] = dataSizeBytes[3];
						
						is = new ByteArrayInputStream(buff);
						while((b=is.read())!=-1 && buffLen>0) {
							out.write(b);
							buffLen--;
						}
					}
					while((buffLen=bi.read(buff))!=-1) {
						is = new ByteArrayInputStream(buff);
						while((b=is.read())!=-1 && buffLen>0) {
							out.write(b);
							buffLen--;
						}
					}
				}else {
					if((buffLen=bi.read(buff))!=-1) {
						byte[] firstBytes = new byte[buffLen-44];
						for(int index=44;index<buffLen;index++) {
							firstBytes[index-44] = buff[index];
						}
						is = new ByteArrayInputStream(firstBytes);
						while((b=is.read())!=-1) {
							out.write(b);
						}
					}
					while((buffLen=bi.read(buff))!=-1) {
						is = new ByteArrayInputStream(buff);
						while((b=is.read())!=-1 && buffLen>0) {
							out.write(b);
							buffLen--;
						}
					}
				}
			}
			is.close();
			bi.close();
			out.flush();
			out.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 * 下载播放文件
	 * @param filePath --文件完整路径
	 * @param response
	 */
	public static void downloadPlayFile(
			String filePath, 
			javax.servlet.http.HttpServletResponse response) {
		
		println("开始输出要下载的文件...");
		
		//打开指定文件的流信息
        FileInputStream fs = null;
        try {
        	String []FileName=filePath.split(",");
            //设置响应头和保存文件名 
            response.setContentType("APPLICATION/OCTET-STREAM"); 
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath + "\"");
            PrintWriter out = response.getWriter();
            //写出流信息
            int b = 0;
        	for(int i=0;i<FileName.length;i++){
                fs = new FileInputStream(new File(FileName[i]));
                while((b=fs.read())!=-1) {
            	    out.write(b);
                }
        	}
             fs.close();
             out.close();
             println("文件下载完毕.");
        }catch(Exception e) {
        	e.printStackTrace();
        	println("下载文件失败!");
        }
	}
	
	/**
	 * 输出XML文档
	 * @param doc          --XML文档对象
	 * @param fullFileName --输出文件全路径
	 * @return boolean
	 */
	public static boolean writeXMLDocument(Document doc, String fullFileName) {
		boolean mark = true;
		try {
			XMLWriter writer = new XMLWriter(new FileOutputStream(fullFileName));
			writer.write(doc);
			writer.close();
		}catch(IOException e) {
			mark = false;
		}
		return mark;
	}
	
	
	 public static void Zip(String targetFile,String inputFileName)throws Exception {
		  println("output zip file name->"+targetFile);
		  Zip(targetFile,new File(inputFileName));
	}
		 
	public static void Zip(String zipFileName,File inputFile)throws Exception {
//		  ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipFileName));
//		  Zip(out,inputFile,"");
//		  println("zip done ");
//		  out.close();
	}
	
	/**
     * 新建目录(file.mkdirs()可创建多级目录)
     * @param folderPath 目录
     * 
     */
    public static int createFolder(String folderPath) {
        String txt = folderPath;
        int state=0;
        try {
            File myFilePath = new File(txt);
            txt = folderPath;
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
            return state;
        }
        catch (Exception e) {
        	state=-1;
            e.printStackTrace();
        }
        return state;
    }
    /**
     * 删除目录
     * @param folderPath 目录
     * @return 返回目录创建后的路径
     */
    public static void deleteFolder(String folderPath) {
        String txt = folderPath;
        try {
            File myFilePath = new File(txt);
            txt = folderPath;
            if (myFilePath.exists()) {
                myFilePath.delete();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//删除文件夹
//param folderPath 文件夹完整绝对路径
   public static void delFolder(String folderPath) {
       try {
          delAllFile(folderPath); //删除完里面所有内容
          String filePath = folderPath;
          filePath = filePath.toString();
          java.io.File myFilePath = new java.io.File(filePath);
          myFilePath.delete(); //删除空文件夹
       } catch (Exception e) {
         e.printStackTrace(); 
       }
  }

//  删除指定文件夹下所有文件
//  param path 文件夹完整绝对路径
     public static boolean delAllFile(String path) {
         boolean flag = false;
         File file = new File(path);
         if (!file.exists()) {
           return flag;
         }
         if (!file.isDirectory()) {
           return flag;
         }
         String[] tempList = file.list();
         File temp = null;
         for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
               temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
               temp.delete();
            }
            if (temp.isDirectory()) {
               delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
               delFolder(path + "/" + tempList[i]);//再删除空文件夹
               flag = true;
            }
         }
         return flag;
       }
     
     /**
      * 获取字符串的前几个字符
      * @param str
      * @param length
      * @return
      */
     public static String getString(String str){
    	 if(str.length()>30){
        	 return str.substring(0, 30); 
    	 }else{
    		 return str;
    	 }
     }
     
     //--------------------------------
     
 	 public static byte[] intToBytes(int num){   
		 byte[]  bytes=new byte[4];   
		 bytes[0]=(byte)(num>>24);   
		 bytes[1]=(byte)((num>>16)& 0x000000FF);   
		 bytes[2]=(byte)((num>>8)& 0x000000FF);   
		 bytes[3]=(byte)(num & 0x000000FF);   
		 return bytes;   
	 } 
	 public static byte[] revers(byte[] tmp){   
		 byte[] reversed=new byte[tmp.length];   
		 for(int i=0;i<tmp.length;i++){   
		     reversed[i]=tmp[tmp.length-i-1];                    
		 }   
		 return reversed;   
	 }
     
     public static void println(String arg) {
    	 //System.out.println(arg);
     }
     
     /**
      * 计算一个时间的后N天日期
      * @param date要计算的时间
      * @param addday 要添加的天数
      * @author xyl
      */
     public static Date getAfterDate(Date date,int addday){    
    		Calendar calendar=Calendar.getInstance();    
    		calendar.setTime(date);    
    		int day=calendar.get(Calendar.DATE);    
    		calendar.set(Calendar.DATE,day+addday);    
    		return new Date(calendar.getTime().getTime());    
    		} 
     //
     public static void fileChanelCpoy(File source,File target) {
    	 FileInputStream fiStream=null;
    	 FileOutputStream foStream=null;
    	 FileChannel in=null;
    	 FileChannel out=null;
    	 try {
			fiStream=new FileInputStream(source);
			foStream=new FileOutputStream(target);
			in=fiStream.getChannel();
			out=foStream.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fiStream.close();
				in.close();
				foStream.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    	 
     }
     
     public static String getFormatDateString(String dateStr){
    	Date date=Tools.getDate(dateStr);
    	return Tools.getDateString(date);
     }
     public   static  String percent( double  p1,  double  p2){
  	   double  p3  = (double) p1  /  (double)p2;
  	   DecimalFormat df = new DecimalFormat("0.00");//格式化小数 
  	   //str= df.format(p3);//返回的是String类型 
  	   if(p2==0){
  		   return "0";
  	   }
  	   return  df.format(p3);//返回的是String类型 ;
    }
}