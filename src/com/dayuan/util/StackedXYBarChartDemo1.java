package com.dayuan.util;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class StackedXYBarChartDemo1
  extends ApplicationFrame
{
  public StackedXYBarChartDemo1(String paramString)
  {
    super(paramString);
    JPanel localJPanel = createDemoPanel();
    localJPanel.setPreferredSize(new Dimension(500, 270));
    setContentPane(localJPanel);
  }
  
  private static TableXYDataset createDataset()
  {
    DefaultTableXYDataset localDefaultTableXYDataset = new DefaultTableXYDataset();
    XYSeries localXYSeries1 = new XYSeries("Series 1", true, false);
    localXYSeries1.add(1.0D, 5.0D);
    localXYSeries1.add(2.0D, 15.5D);
    localXYSeries1.add(3.0D, 9.5D);
    localXYSeries1.add(4.0D, 7.5D);
    localDefaultTableXYDataset.addSeries(localXYSeries1);
    XYSeries localXYSeries2 = new XYSeries("Series 2", true, false);
    localXYSeries2.add(1.0D, 5.0D);
    localXYSeries2.add(2.0D, 15.5D);
    localXYSeries2.add(3.0D, 9.5D);
    localXYSeries2.add(4.0D, 3.5D);
    localDefaultTableXYDataset.addSeries(localXYSeries2);
    return localDefaultTableXYDataset;
  }
  
  private static JFreeChart createChart(TableXYDataset paramTableXYDataset)
  {
    NumberAxis localNumberAxis1 = new NumberAxis("X");
    localNumberAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    NumberAxis localNumberAxis2 = new NumberAxis("Y");
    StackedXYBarRenderer localStackedXYBarRenderer = new StackedXYBarRenderer(0.1D);
    localStackedXYBarRenderer.setDrawBarOutline(false);
    //设置柱子上显示值
    localStackedXYBarRenderer.setBaseItemLabelsVisible(true); 
    localStackedXYBarRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
    localStackedXYBarRenderer.setBaseItemLabelPaint(Color.BLACK);//设置数值颜色，默认黑色 
    XYPlot localXYPlot = new XYPlot(paramTableXYDataset, localNumberAxis1, localNumberAxis2, localStackedXYBarRenderer);
    JFreeChart localJFreeChart = new JFreeChart("Stacked XY Bar Chart Demo 1", localXYPlot);
    ChartUtilities.applyCurrentTheme(localJFreeChart);
    return localJFreeChart;
  }
  
  public static JPanel createDemoPanel()
  {
    JFreeChart localJFreeChart = createChart(createDataset());
    return new ChartPanel(localJFreeChart);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    StackedXYBarChartDemo1 localStackedXYBarChartDemo1 = new StackedXYBarChartDemo1("Stacked XY Bar Chart Demo 1");
    localStackedXYBarChartDemo1.pack();
    RefineryUtilities.centerFrameOnScreen(localStackedXYBarChartDemo1);
    localStackedXYBarChartDemo1.setVisible(true);
  }
}
