package com.dayuan.test;

import com.dayuan.util.ZipCompressorByAnt;

public class TestZip {
 public static void main(String[] args) {
	ZipCompressorByAnt zip=new ZipCompressorByAnt("E:\\apache-tomcat-8.5.5\\work\\Catalina\\localhost\\dymanager.zip");
	zip.compressExe("E:\\apache-tomcat-8.5.5\\work\\Catalina\\localhost\\dymanager");
	System.out.println("压缩成功");
}
}
