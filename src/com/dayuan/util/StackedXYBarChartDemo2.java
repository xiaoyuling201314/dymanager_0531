package com.dayuan.util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Year;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
public class StackedXYBarChartDemo2 extends ApplicationFrame
{
  public StackedXYBarChartDemo2(String paramString)
  {
    super(paramString);
    JPanel localJPanel = createDemoPanel();
    localJPanel.setPreferredSize(new Dimension(500, 270));
    setContentPane(localJPanel);
  }
  
  private static TableXYDataset createDataset()
  {
    TimeTableXYDataset localTimeTableXYDataset = new TimeTableXYDataset();
    localTimeTableXYDataset.add(new Year(1983), 0.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1984), 2.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1985), 1.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1986), 1.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1987), 2.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1988), 2.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1989), 1.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1990), 5.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1991), 5.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1992), 2.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1993), 4.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1994), 3.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1995), 2.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1996), 1.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1997), 2.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1998), 1.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1999), 4.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(2000), 6.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(2001), 5.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(2002), 4.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(2003), 2.0D, "Albatrosses");
    localTimeTableXYDataset.add(new Year(1983), 21.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1984), 24.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1985), 32.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1986), 20.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1987), 28.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1988), 17.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1989), 31.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1990), 32.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1991), 29.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1992), 31.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1993), 25.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1994), 44.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1995), 35.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1996), 40.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1997), 32.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1998), 32.0D, "Aces");
    localTimeTableXYDataset.add(new Year(1999), 30.0D, "Aces");
    localTimeTableXYDataset.add(new Year(2000), 29.0D, "Aces");
    localTimeTableXYDataset.add(new Year(2001), 28.0D, "Aces");
    localTimeTableXYDataset.add(new Year(2002), 0.0D, "Aces");
    localTimeTableXYDataset.add(new Year(2003), 32.0D, "Aces");
    return localTimeTableXYDataset;
  }
  
  private static JFreeChart createChart(TableXYDataset paramTableXYDataset)
  {
    DateAxis localDateAxis = new DateAxis("Date");
    localDateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
    localDateAxis.setLowerMargin(0.01D);
    localDateAxis.setUpperMargin(0.01D);
    NumberAxis localNumberAxis = new NumberAxis("Count");
    localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    localNumberAxis.setUpperMargin(0.1D);
    StackedXYBarRenderer localStackedXYBarRenderer = new StackedXYBarRenderer(0.15D);
    localStackedXYBarRenderer.setDrawBarOutline(false);
    localStackedXYBarRenderer.setBaseItemLabelsVisible(true);
    localStackedXYBarRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
    localStackedXYBarRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER));
    localStackedXYBarRenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0} : {1} = {2}", new SimpleDateFormat("yyyy"), new DecimalFormat("0")));
    XYPlot localXYPlot = new XYPlot(paramTableXYDataset, localDateAxis, localNumberAxis, localStackedXYBarRenderer);
    JFreeChart localJFreeChart = new JFreeChart("Holes-In-One / Double Eagles", localXYPlot);
   // localJFreeChart.removeLegend();
    localJFreeChart.getLegend().setVisible(true);
    localJFreeChart.addSubtitle(new TextTitle("PGA Tour, 1983 to 2003"));
    TextTitle localTextTitle = new TextTitle("http://www.golfdigest.com/majors/masters/index.ssf?/majors/masters/gw20040402albatross.html", new Font("Dialog", 0, 8));
    localJFreeChart.addSubtitle(localTextTitle);
    localJFreeChart.setTextAntiAlias(RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
    LegendTitle localLegendTitle = new LegendTitle(localXYPlot);
    localLegendTitle.setBackgroundPaint(Color.white);
    localLegendTitle.setFrame(new BlockBorder());
    localLegendTitle.setPosition(RectangleEdge.BOTTOM);
    localJFreeChart.addSubtitle(localLegendTitle);
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
    StackedXYBarChartDemo2 localStackedXYBarChartDemo2 = new StackedXYBarChartDemo2("JFreeChart: Stacked XY Bar Chart Demo 2");
    localStackedXYBarChartDemo2.pack();
    RefineryUtilities.centerFrameOnScreen(localStackedXYBarChartDemo2);
    localStackedXYBarChartDemo2.setVisible(true);
  }
}
