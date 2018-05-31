package com.dayuan.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
public class JfreeChartUtil {
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

        JFreeChart chart = ChartFactory.createBarChart(
        	chartTitle,       // chart title
        	xTitle,               // domain axis label
        	yTitle,                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
      );
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 12));
        chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 14));

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        NumberAxis numberAxis  = (NumberAxis )categoryPlot.getRangeAxis();
        numberAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,12));
        numberAxis.setLabelFont(new Font("宋体",Font.PLAIN,12));
        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.getDomainAxis().setTickLabelFont(new Font("黑体",Font.BOLD,9));
  
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
        
        renderer.setBaseItemLabelFont(new Font("黑体",Font.BOLD,10));  
        renderer.setBaseItemLabelsVisible(true);  
        // set up gradient paints for series...
        //----------------------------------------
//        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
//                0.0f, 0.0f, new Color(0, 0, 64));
//        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
//                0.0f, 0.0f, new Color(0, 64, 0));
//        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
//                0.0f, 0.0f, new Color(64, 0, 0));
//        GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, Color.orange,
//                0.0f, 0.0f, new Color(0, 0, 0));
//        GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, Color.cyan,
//                0.0f, 0.0f, new Color(0, 0, 0));
//        renderer.setSeriesPaint(0, gp0);
//        renderer.setSeriesPaint(1, gp1);
//        renderer.setSeriesPaint(2, gp2);
//        renderer.setSeriesPaint(3, gp3);
//        renderer.setSeriesPaint(4, gp4);
        
        for (int i = 0; i < gradientPaintArray.length; i++) {
        	renderer.setSeriesPaint(i, gradientPaintArray[i]);
        	
		}

        //----------------------------------------
        CategoryAxis domainAxis = plot.getDomainAxis();
        
        domainAxis.setLabelFont(new Font("黑体",Font.PLAIN,14));
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 9.0));
        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;

    }

}
