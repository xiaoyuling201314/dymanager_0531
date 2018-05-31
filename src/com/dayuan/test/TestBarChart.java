package com.dayuan.test;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

public class TestBarChart {
  public static void main(String[] args) throws IOException {
//	  //创建饼图
//	DefaultPieDataset dpd=new DefaultPieDataset();
//	dpd.setValue("管理人员", 25);
//	dpd.setValue("市场人员", 25);
//	dpd.setValue("开发人员", 45);
//	dpd.setValue("其他人员", 5);
//	JFreeChart chart=ChartFactory.createPieChart("公司人员组织架构图", dpd, true,true, false);
//	//参数1：标题 参数2：数据集 参数3：是否显示Legend 参数4：是否显示提示 参数5：是否存在url
//	ChartFrame chartFrame=new ChartFrame("数据图", chart);
//	//chart要放在java父容器中，ChartFrame继承自java的Jframe类；该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
//	chartFrame.pack();//以合适的大小展现图形
//	chartFrame.setVisible(true);//图形是否可见
	  JFreeChart chart=ChartFactory.createPieChart("某公司人员组织数据图",getDataset(),true,true,false);
      chart.setTitle(new TextTitle("某公司组织结构图",new Font("宋体",Font.BOLD+Font.ITALIC,20)));
      
       LegendTitle legend=chart.getLegend(0);//设置Legend
       legend.setItemFont(new Font("宋体",Font.BOLD,14));
       PiePlot plot=(PiePlot) chart.getPlot();//设置Plot
       plot.setLabelFont(new Font("隶书",Font.BOLD,16));
       
      OutputStream os = new FileOutputStream("E:\\Pie.jpeg");//图片是文件格式的，故要用到FileOutputStream用来输出。
      ChartUtilities.writeChartAsJPEG(os, chart, 1000, 800);
      //使用一个面向application的工具类，将chart转换成JPEG格式的图片。第3个参数是宽度，第4个参数是高度。
      
      os.close();//关闭输出流
  }

  private static DefaultPieDataset getDataset()
  {
      DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
      dpd.setValue("管理人员", 25);  //输入数据
      dpd.setValue("市场人员", 25);
      dpd.setValue("开发人员", 45);
      dpd.setValue("其他人员", 10);
      return dpd;
  }
}
