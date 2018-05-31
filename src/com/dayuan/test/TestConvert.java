package com.dayuan.test;

public class TestConvert {
  public static void main(String[] args) {
	System.out.println(Integer.toBinaryString(-5));
	System.out.println(Integer.toBinaryString(Integer.parseInt("FBB4",16)));
	//二进制转十进制
	System.out.println(Integer.valueOf("-101011001", 2));
	//十进制转二进制
	System.out.println(Integer.toBinaryString(345));
}
}
