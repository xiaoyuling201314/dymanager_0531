package com.dayuan.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLabelLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Year;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;
import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.ExportRepairRecorder;
import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.util.Tools;

//@Controller
//@RequestMapping("/barChartService")
public class BarChartController extends BaseController {
/**
 * 出货记录统计分析
 * @param request
 * @param session
 * @param startTime
 * @param endTime
 * @return
 * @throws IOException
 */
@RequestMapping("/genBarChart")
 public ModelAndView  genBarChart(HttpServletRequest request,HttpSession session,String startTime,String endTime) throws IOException{
	 ModelAndView result=new ModelAndView("statistical_analysis_ship");
//	 DefaultCategoryDataset dataset=new DefaultCategoryDataset();
//	 List<ExportShippingrecords> list=shipmentsService.statisticsShip("2016-11-20","2016-12-20");//startTime, endTime
//	 Integer count=0;
//	 for (ExportShippingrecords shippingrecords : list) {
//		 dataset.addValue(shippingrecords.getQuantity(), "ship", shippingrecords.getSapNo());
//		 count+=shippingrecords.getQuantity();
//	 }
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(390, "中国", "河南");
			dataset.addValue(220, "中国", "河北");
			dataset.addValue(510, "中国", "辽宁");
			dataset.addValue(580, "中国", "山东");
			dataset.addValue(320, "中国", "山西");
			dataset.addValue(410, "中国", "陕西");
	ChartFactory.setChartTheme(setFont());
JFreeChart chart=ChartFactory.createBarChart("苹果产量统计", "省份", "产量", dataset,PlotOrientation.VERTICAL,true,false,false);
	CategoryPlot plot=(CategoryPlot) chart.getPlot();
	plot.setBackgroundPaint(Color.WHITE);
	plot.setDomainGridlinePaint(Color.pink);
	plot.setRangeGridlinePaint(Color.pink);
	BarRenderer render=new BarRenderer();
	render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	render.setBaseItemLabelsVisible(true);
	render.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3,TextAnchor.BASELINE_RIGHT));
	render.setItemLabelAnchorOffset(20D);
	plot.setRenderer(render);
	String filename=ServletUtilities.saveChartAsPNG(chart, 700, 500, session);
	 request.setAttribute("filename", filename);
	 return result;
 }

	// 处理时间的方法
	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}
	/**
	 * 生成饼图
	 * @param request
	 * @param session
	 * @param startTime
	 * @param endTime
	 * @param time
	 * @return
	 * @throws IOException
	 */
	  @ResponseBody
	  @RequestMapping(value = "/genBarPieChart",produces="application/json;charset=UTF-8" )//,produces="application/json;charset=UTF-8"    @ResponseBody  
	 public String  genBarPieChart(HttpServletRequest request,HttpSession session,String startTime,String endTime,String staticsType) throws IOException{
		//ModelAndView result=new ModelAndView("statistical_analysis");
		ChartFactory.setChartTheme(setFont());//设置字体格式，解决中文乱码问题
		DefaultPieDataset dataset=new DefaultPieDataset();
//		dataset.setValue("IE", 120);//内容 数量
		Date d=new Date();
		Calendar cal=Calendar.getInstance();
		if(StringUtils.isNotBlank(staticsType)){
			if(staticsType.equals("week")){//近一周
				endTime=Tools.getDateString(d);
				d.setDate(d.getDate()-7);
				startTime=Tools.getDateString(d);
			}else if(staticsType.equals("oneMonth")){//近一个月
				endTime=Tools.getDateString(d);
				d.setMonth(d.getMonth()-1);
				startTime=Tools.getDateString(d);
			}else if(staticsType.equals("threeMonth")){//近三个月
				endTime=Tools.getDateString(d);
				d.setMonth(d.getMonth()-3);
				startTime=Tools.getDateString(d);
			}else if(staticsType.equals("oneYear")){//近一年
				endTime=Tools.getDateString(d);
				d.setYear(d.getYear()-1);
				startTime=Tools.getDateString(d);
			}
		}
		List<ExportRepairRecorder> list=repairService.statisticsPieShip(startTime, endTime);
		Integer sumTotal=0;
		if(list!=null && list.size()>0){
			for (ExportRepairRecorder recorder : list) {
				dataset.setValue(recorder.getSapNo(), recorder.getQuantity()); 
				sumTotal+=recorder.getQuantity();
			}
		}
		JFreeChart chart=ChartFactory.createPieChart("", dataset, true,true,false);//new JFreeChart("用户使用得浏览器类型",JFreeChart.DEFAULT_TITLE_FONT,plot,true);
		chart.getLegend().setPosition(RectangleEdge.RIGHT);//设置图例显示在右侧
		//chart.getLegend().setVisible(false);
		chart.setBackgroundPaint(new Color(245,245,245));//设置总的背景颜色
		chart.getLegend().setBackgroundPaint(null);//设置图例
		PiePlot plot=(PiePlot) chart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
		plot.setNoDataMessage("无数据显示");
		//设置label无边框
		plot.setLabelBackgroundPaint(null);//#f5f5f5
		plot.setLabelOutlinePaint(null);
		plot.setLabelShadowPaint(null);
		plot.setOutlinePaint(new Color(245,245,245));//new Color(245,245,245)
		plot.setBackgroundPaint(new Color(245,245,245));//new Color(245,245,245)
		String filename=ServletUtilities.saveChartAsPNG(chart, 500, 500, session);
		// request.setAttribute("filename", filename);
		// request.setAttribute("sumTotal", sumTotal);
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("filename", filename);
		 map.put("sumTotal", sumTotal);
		 JSONObject json=new JSONObject(map);
		 return json.toString();
	 }
	 
	  private CategoryDataset createDataset()
	  {
		  DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		    localDefaultCategoryDataset.addValue(20.3D, "Product 1 (US)", "");
		    localDefaultCategoryDataset.addValue(27.2D, "Product 1 (US)", "");
		    localDefaultCategoryDataset.addValue(19.7D, "Product 1 (US)", "");
		    localDefaultCategoryDataset.addValue(19.4D, "Product 1 (Europe)", "");
		    localDefaultCategoryDataset.addValue(10.9D, "Product 1 (Europe)", "");
		    localDefaultCategoryDataset.addValue(18.4D, "Product 1 (Europe)", "");
		    localDefaultCategoryDataset.addValue(16.5D, "Product 1 (Asia)", "");
		    localDefaultCategoryDataset.addValue(23.3D, "Product 2 (US)", "");
		    localDefaultCategoryDataset.addValue(16.2D, "Product 2 (US)", "");
		    localDefaultCategoryDataset.addValue(28.7D, "Product 2 (US)", "");
		    localDefaultCategoryDataset.addValue(11.9D, "Product 3 (US)", "");
		    localDefaultCategoryDataset.addValue(31.0D, "Product 3 (US)", "");
		    localDefaultCategoryDataset.addValue(22.7D, "Product 3 (US)", "");
		    return localDefaultCategoryDataset;
	  }
	 private JFreeChart createChart(CategoryDataset paramCategoryDataset)
	  { JFreeChart localJFreeChart = ChartFactory.createStackedBarChart("Stacked Bar Chart Demo 4", "Category", "Value", paramCategoryDataset, PlotOrientation.VERTICAL, true, true, false);
	    GroupedStackedBarRenderer localGroupedStackedBarRenderer = new GroupedStackedBarRenderer();
	    KeyToGroupMap localKeyToGroupMap = new KeyToGroupMap("G1");
	    localKeyToGroupMap.mapKeyToGroup("Product 1 (US)", "G1");
	    localKeyToGroupMap.mapKeyToGroup("Product 1 (Europe)", "G1");
	    localKeyToGroupMap.mapKeyToGroup("Product 1 (Asia)", "G1");
	    localKeyToGroupMap.mapKeyToGroup("Product 1 (Middle East)", "G1");
	    localKeyToGroupMap.mapKeyToGroup("Product 2 (US)", "G2");
	    localKeyToGroupMap.mapKeyToGroup("Product 2 (Europe)", "G2");
	    localKeyToGroupMap.mapKeyToGroup("Product 2 (Asia)", "G2");
	    localKeyToGroupMap.mapKeyToGroup("Product 2 (Middle East)", "G2");
	    localKeyToGroupMap.mapKeyToGroup("Product 3 (US)", "G3");
	    localKeyToGroupMap.mapKeyToGroup("Product 3 (Europe)", "G3");
	    localKeyToGroupMap.mapKeyToGroup("Product 3 (Asia)", "G3");
	    localKeyToGroupMap.mapKeyToGroup("Product 3 (Middle East)", "G3");
	    localGroupedStackedBarRenderer.setSeriesToGroupMap(localKeyToGroupMap);
	    localGroupedStackedBarRenderer.setItemMargin(0.1D);
	    localGroupedStackedBarRenderer.setDrawBarOutline(false);
	    SubCategoryAxis localSubCategoryAxis = new SubCategoryAxis("Product / Month");
	    localSubCategoryAxis.setCategoryMargin(0.05D);
	    localSubCategoryAxis.addSubCategory("Product 1");
	    localSubCategoryAxis.addSubCategory("Product 2");
	    localSubCategoryAxis.addSubCategory("Product 3");
	    CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
	    localCategoryPlot.setDomainAxis(localSubCategoryAxis);
	    localCategoryPlot.setRenderer(localGroupedStackedBarRenderer);
	    localCategoryPlot.setFixedLegendItems(createLegendItems());
	    ChartUtilities.applyCurrentTheme(localJFreeChart);
	    localSubCategoryAxis.setSubLabelFont(new Font("Tahoma", 2, 10));
	    GradientPaint localGradientPaint1 = new GradientPaint(0.0F, 0.0F, new Color(34, 34, 255), 0.0F, 0.0F, new Color(136, 136, 255));
	    localGroupedStackedBarRenderer.setSeriesPaint(0, localGradientPaint1);
	    localGroupedStackedBarRenderer.setSeriesPaint(4, localGradientPaint1);
	    localGroupedStackedBarRenderer.setSeriesPaint(8, localGradientPaint1);
	    GradientPaint localGradientPaint2 = new GradientPaint(0.0F, 0.0F, new Color(34, 255, 34), 0.0F, 0.0F, new Color(136, 255, 136));
	    localGroupedStackedBarRenderer.setSeriesPaint(1, localGradientPaint2);
	    localGroupedStackedBarRenderer.setSeriesPaint(5, localGradientPaint2);
	    localGroupedStackedBarRenderer.setSeriesPaint(9, localGradientPaint2);
	    GradientPaint localGradientPaint3 = new GradientPaint(0.0F, 0.0F, new Color(255, 34, 34), 0.0F, 0.0F, new Color(255, 136, 136));
	    localGroupedStackedBarRenderer.setSeriesPaint(2, localGradientPaint3);
	    localGroupedStackedBarRenderer.setSeriesPaint(6, localGradientPaint3);
	    localGroupedStackedBarRenderer.setSeriesPaint(10, localGradientPaint3);
	    GradientPaint localGradientPaint4 = new GradientPaint(0.0F, 0.0F, new Color(255, 255, 34), 0.0F, 0.0F, new Color(255, 255, 136));
	    localGroupedStackedBarRenderer.setSeriesPaint(3, localGradientPaint4);
	    localGroupedStackedBarRenderer.setSeriesPaint(7, localGradientPaint4);
	    localGroupedStackedBarRenderer.setSeriesPaint(11, localGradientPaint4);
	    localGroupedStackedBarRenderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));
	    return localJFreeChart;
	    }
	 private LegendItemCollection createLegendItems()
	  {
		 LegendItemCollection localLegendItemCollection = new LegendItemCollection();
		    LegendItem localLegendItem1 = new LegendItem("US", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(34, 34, 255));
		    LegendItem localLegendItem2 = new LegendItem("Europe", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(34, 255, 34));
		    LegendItem localLegendItem3 = new LegendItem("Asia", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 34, 34));
		    LegendItem localLegendItem4 = new LegendItem("Middle East", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 255, 34));
		    localLegendItemCollection.add(localLegendItem1);
		    localLegendItemCollection.add(localLegendItem2);
		    localLegendItemCollection.add(localLegendItem3);
		    localLegendItemCollection.add(localLegendItem4);
	    return localLegendItemCollection;
	  }
//	 
//	 @RequestMapping("/genBarChart")
//	 public ModelAndView  genBarChart2(HttpServletRequest request,HttpSession session,String startTime,String endTime) throws IOException{
//		 ModelAndView result=new ModelAndView("statistical_analysis_ship");
//		 ChartFactory.setChartTheme(setFont());
//		JFreeChart chart=createChart(createDataset());
//		String filename=ServletUtilities.saveChartAsJPEG(chart, 700, 500, session);
//		request.setAttribute("filename", filename);
//		return result;
//	 }
}
