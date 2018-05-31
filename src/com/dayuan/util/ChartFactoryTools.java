package com.dayuan.util;

import org.jfree.data.category.DefaultCategoryDataset;

public class ChartFactoryTools {
   
	/**
	 * 创建柱状图
	 * @return
	 */
	public static String createCategoryDatasetDemo(){
		
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		dataset.addValue(32.0D, "上海", "JAVA");//值，Y，X，
		dataset.addValue(60.0D, "北京", "JAVA");//值，Y，X，
		dataset.addValue(100.0D, "广州", "JAVA");//值，Y，X，
		
		return "";
	}
	
}
