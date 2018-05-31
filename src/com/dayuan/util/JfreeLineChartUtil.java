package com.dayuan.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class JfreeLineChartUtil extends ApplicationFrame{

	 public static void print(String path,int width,int height,CategoryDataset dataset,GradientPaint[] gradientPaintArray,String chartTitle,String xTitle,String yTitle) throws IOException{  
	        FileOutputStream fos = new  FileOutputStream(path);  
	          
	        ChartUtilities.writeChartAsJPEG(fos,//输出到那个流，   
	                                        1, //图片质量，0~1   
	                                        createChart(dataset,gradientPaintArray,chartTitle,xTitle,yTitle), //图表对象   
	                                        width,//宽   
	                                        height,//高   
	                                        null//ChartRenderingInfo信息   
	                                        );  
	        fos.flush();
	}
	 
	 private static JFreeChart createChart(CategoryDataset dataset,GradientPaint[] gradientPaintArray,String chartTitle,String xTitle,String yTitle) {
		 JFreeChart chart = ChartFactory.createLineChart(chartTitle, // chart title
			     xTitle, // domain axis label
			     yTitle, // range axis label
			     dataset, // data
			     PlotOrientation.VERTICAL, // orientation
			     true, // include legend
			     true, // tooltips
			     false // urls
			     );
			   //图例字体
			   chart.getLegend().setItemFont(new Font("黑体",Font.PLAIN,14));
			   //统计图标题字体
			   chart.getTitle().setFont(new Font("黑体",Font.PLAIN,14));
			   CategoryPlot plot = chart.getCategoryPlot();
			  
			   // customise the range axis...
			   NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			   //y轴标题字体
			   rangeAxis.setLabelFont(new Font("黑体",Font.PLAIN,14));
			   rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			   rangeAxis.setAutoRangeIncludesZero(true);
			   rangeAxis.setUpperMargin(0.20);
			   //x轴标题字体
			   plot.getDomainAxis().setLabelFont(new Font("黑体",Font.PLAIN,14));
			   plot.getRenderer().setBaseItemLabelsVisible(true);  
			   //显示折点和数据
			   LineAndShapeRenderer render = (LineAndShapeRenderer)plot.getRenderer();
			   render.setBaseItemLabelFont(new Font("黑体",Font.PLAIN,10));
			   render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			   render.setBaseItemLabelsVisible(true);
			   
			   CategoryAxis domainAxis = plot.getDomainAxis();
			   
			   domainAxis.setTickLabelFont(new Font("黑体",Font.PLAIN,10));
			   domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/6));
			   return chart;
 }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4972160440996090678L;

	public JfreeLineChartUtil(String s) {
		   super(s);
		   setContentPane(createDemoLine());
		}

		public static void main(String[] args) {
//			JfreeLineChartUtil fjc = new JfreeLineChartUtil("折线图");
//		   fjc.pack();
//		   RefineryUtilities.centerFrameOnScreen(fjc);
//		   fjc.setVisible(true);
			GradientPaint[] gradientPaintArray = new GradientPaint[]{ 
					new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, new Color(0, 0, 0)),
				    new GradientPaint(0.0f, 0.0f, Color.green,0.0f, 0.0f, new Color(0, 0, 0))
			};
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				Random random = new Random();
				for (int i = 0; i < 7; i++) {
					dataset.addValue(random.nextInt(50), "totalnumbercalling", i+1+"月");
					dataset.addValue(random.nextInt(50), "totalnumbercalled",  i+1+"月");
					
				}
			try {
				JfreeLineChartUtil.print("d:\\test.jpg", 1000, 400, dataset, gradientPaintArray, "Traffic Load", "Start Time", "Traffic Load");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 生成显示图表的面板
		public static JPanel createDemoLine() {
		   JFreeChart jfreechart = createChart(createDataset());
		   return new ChartPanel(jfreechart);
		}

		// 生成图表主对象JFreeChart
		public static JFreeChart createChart(DefaultCategoryDataset linedataset) {
		   //定义图表对象
		   JFreeChart chart = ChartFactory.createLineChart("折线图", // chart title
		     "时间", // domain axis label
		     "销售额(百万)", // range axis label
		     linedataset, // data
		     PlotOrientation.VERTICAL, // orientation
		     true, // include legend
		     true, // tooltips
		     false // urls
		     );
		   //图例字体
		   chart.getLegend().setItemFont(new Font("黑体",Font.PLAIN,14));
		   //统计图标题字体
		   chart.getTitle().setFont(new Font("黑体",Font.PLAIN,14));
		   CategoryPlot plot = (CategoryPlot) chart.getPlot();
		  
		   // customise the range axis...
		   NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		   //y轴标题字体
		   rangeAxis.setLabelFont(new Font("黑体",Font.PLAIN,14));
		   rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		   rangeAxis.setAutoRangeIncludesZero(true);
		   rangeAxis.setUpperMargin(0.20);
		   //y轴标题倾斜度
//		   rangeAxis.setLabelAngle(Math.PI / 2.0);
		   //x轴标题字体
		   plot.getDomainAxis().setLabelFont(new Font("黑体",Font.PLAIN,14));
		   plot.getRenderer().setBaseItemLabelsVisible(true);
		   CategoryAxis domainAxis = plot.getDomainAxis();
		   LineAndShapeRenderer render = (LineAndShapeRenderer)plot.getRenderer();
		   render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		   render.setBaseItemLabelsVisible(true);
		   domainAxis.setTickLabelFont(new Font("黑体",Font.PLAIN,10));
		   domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(Math.PI/6));
		   return chart;
		}

		//生成数据
		public static DefaultCategoryDataset createDataset() {
		   DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
		   //   各曲线名称
		   String series1 = "冰箱";
		   String series2 = "彩电";
		   String series3 = "洗衣机";

		   //    横轴名称(列名称)
		   String type1 = "1月";
		   String type2 = "2月";
		   String type3 = "3月";

		   linedataset.addValue(1.2, series1, type1);
		   linedataset.addValue(2.2, series1, type2);
		   linedataset.addValue(3.9, series1, type3);

		   linedataset.addValue(2.9, series2, type1);
		   linedataset.addValue(3.3, series2, type2);
		   linedataset.addValue(2.9, series2, type3);

		   linedataset.addValue(3.0, series3, type1);
		   linedataset.addValue(4.4, series3, type2);
		   linedataset.addValue(3.9, series3, type3);

		   return linedataset;
		}

}
