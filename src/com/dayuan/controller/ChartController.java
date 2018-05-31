package com.dayuan.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
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

import com.dayuan.bean.ExportRepairRecorder;
import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.util.Tools;
@Controller
@RequestMapping("/barChartService")
public class ChartController extends BaseController {
	private String startTime;
	private String endTime;
	
		public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

		/**
		 * 出货记录统计分析
		 * @param request
		 * @param session
		 * @param startTime
		 * @param endTime
		 * @return
		 * @throws IOException
		 */
		  @ResponseBody
		  @RequestMapping(value = "/statiscalShip",produces="application/json;charset=UTF-8" )
		 public String  genBarChart(HttpServletRequest request,HttpSession session,String startTime,String endTime,String staticsType) throws IOException{
				if(StringUtils.isNotBlank(staticsType)){
					getStatisTime(staticsType);
				}else{
					this.startTime=startTime;
					this.endTime=endTime;
				}
			 List<ExportShippingrecords> list=shipmentsService.statisticsShip(this.startTime, this.endTime);
			 Map<String, Object> map=new HashMap<>();
			 int sumTotal=0;
				for (ExportShippingrecords shippingrecords : list) {
					 sumTotal+=shippingrecords.getQuantity();
				}
			 
			 map.put("dataresource", list.size()>0 ? list : "");
			 map.put("sumTotal", sumTotal);
			 map.put("startTime", this.startTime);
			 map.put("endTime", this.endTime);
			 JSONObject json=new JSONObject(map);
			 return json.toString();
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
			 * 返修统计
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
				if (StringUtils.isNotBlank(staticsType)) {
					getStatisTime(staticsType);
				} else {
					this.startTime = startTime;
					this.endTime = endTime;
				}
				List<ExportRepairRecorder> list = repairService.statisticsPieShip(this.startTime, this.endTime);
				Map<String, Object> map = new HashMap<>();
				int sumTotal = 0;
				for (ExportRepairRecorder repairRecorder : list) {
					sumTotal += repairRecorder.getQuantity();
				}
				map.put("dataresource", list.size() > 0 ? list : "");
				map.put("sumTotal", sumTotal);
				 map.put("startTime", this.startTime);
				 map.put("endTime", this.endTime);
				JSONObject json = new JSONObject(map);
				return json.toString();
			 }
			 
			  @SuppressWarnings("unused")
			private void getStatisTime(String staticsType){
				    Date d=new Date();
					Calendar cal=Calendar.getInstance();
					if(StringUtils.isNotBlank(staticsType)){
						this.endTime=Tools.getDateString(d);
						if(staticsType.equals("week")){//近一周
							d.setDate(d.getDate()-7);
						}else if(staticsType.equals("oneMonth")){//近一个月
							d.setMonth(d.getMonth()-1);
						}else if(staticsType.equals("threeMonth")){//近三个月
							d.setMonth(d.getMonth()-3);
						}else if(staticsType.equals("oneYear")){//近一年
							d.setYear(d.getYear()-1);
						}
						this.startTime=Tools.getDateString(d);
					}
			  }
			  /**
				 * 生成柏拉图，柱状图和折线图
				 * @param request
				 * @param session
				 * @param startTime
				 * @param endTime
				 * @param time
				 * @return
				 * @throws IOException
				 */
				  @ResponseBody
				  @RequestMapping(value = "/genBarPercentChart",produces="application/json;charset=UTF-8" )//,produces="application/json;charset=UTF-8"    @ResponseBody  
				 public String  genBarPercentChart(HttpServletRequest request,HttpSession session,String startTime,String endTime,String staticsType) throws IOException{
					  Map<String, Object> map = new HashMap<>();
						int sumTotalRepair = 0;
						int sumTotalShip = 0;
//						this.startTime="2016-11-20";
//						this.endTime="2016-12-20";
						if(StringUtils.isNotBlank(staticsType)){
							getStatisTime(staticsType);
						}else{
							this.startTime=startTime;
							this.endTime=endTime;
						}
						//1.查询出货数量
					 List<ExportShippingrecords> list=shipmentsService.statisticsShip(this.startTime, this.endTime);
					   //2.查询维修数量
					 List<ExportRepairRecorder> list2 = repairService.statisticsShip(this.startTime, this.endTime,"true");
					 List<ExportRepairRecorder> reparirList=new ArrayList<>();
					 //3.计算出返修率
						List<Double>  percentList=new ArrayList<>();
						double percent=0.0D;
						String str="";
								int count=0;
								String shipSapNo;
								String repairSapNo;
								for (ExportShippingrecords shippingrecords : list) {
										 sumTotalShip+=shippingrecords.getQuantity();
										 shipSapNo=shippingrecords.getSapNo();
										for (ExportRepairRecorder repairRecorder : list2) {
											repairSapNo=repairRecorder.getSapNo();
											 if(shipSapNo.equals(repairSapNo)){//repairRecorder.getSapNo().equals(shippingrecords.getSapNo())
												 sumTotalRepair += repairRecorder.getQuantity();//合计维修数量
												 count+=1;
												 str=Tools.percent(repairRecorder.getQuantity(), shippingrecords.getQuantity());
												 percent=Double.valueOf(str);
												 percent=Math.round(percent*100);
												 percentList.add(percent);
												 percent=0.0D;
												 reparirList.add(repairRecorder);
												 shipSapNo="";
												// repairSapNo="";
											}
											
										}
										if(count==0){//没有维修仪器时设置为0
											 percentList.add(0.0D);
											 reparirList.add(null);
										}else{
											count=0;
										}
									 }
					 //计算总的返修率
//					 	str=Tools.percent(sumTotal2,sumTotal);
//						 percent=(double)sumTotal2/(double)sumTotal;//*100;
						 str=Tools.percent(sumTotalRepair, sumTotalShip);
						 percent=Double.valueOf(str);
						 percent=Math.round(percent*100);
						map.put("repairRecorder", reparirList.size() > 0 ? reparirList : "");
						map.put("shippingrecords", list.size() > 0 ? list : "");
						map.put("percentList", percentList.size()>0 ? percentList : "");
						map.put("sumTotalRepair", sumTotalRepair);
						map.put("sumTotalShip", sumTotalShip);
						map.put("totalPercent", percent);
						 map.put("startTime", this.startTime);
						 map.put("endTime", this.endTime);
						JSONObject json = new JSONObject(map);
						return json.toString();
				  }
//			  /**
//				 * 生成柏拉图，柱状图和折线图
//				 * @param request
//				 * @param session
//				 * @param startTime
//				 * @param endTime
//				 * @param time
//				 * @return
//				 * @throws IOException
//				 */
//				  @ResponseBody
//				  @RequestMapping(value = "/genBarPercentChart",produces="application/json;charset=UTF-8" )//,produces="application/json;charset=UTF-8"    @ResponseBody  
//				 public String  genBarPercentChart(HttpServletRequest request,HttpSession session,String startTime,String endTime,String staticsType) throws IOException{
//					  Map<String, Object> map = new HashMap<>();
//						int sumTotalRepair = 0;
//						int sumTotalShip = 0;
////						this.startTime="2016-11-20";
////						this.endTime="2016-12-20";
//						if(StringUtils.isNotBlank(staticsType)){
//							getStatisTime(staticsType);
//						}else{
//							this.startTime=startTime;
//							this.endTime=endTime;
//						}
//						//1.查询出货数量
//					 List<ExportShippingrecords> list=shipmentsService.statisticsShip(this.startTime, this.endTime);
//					   //2.查询维修数量
//					 List<ExportRepairRecorder> list2 = repairService.statisticsShip(this.startTime, this.endTime,"true");
//					 List<ExportRepairRecorder> reparirList=new ArrayList<>();
//					 //3.计算出返修率
//						List<Double>  percentList=new ArrayList<>();
//						double percent=0.0D;
//						String str="";
//								int count=0;
//								String shipSapNo;
//								String repairSapNo;
//								for (ExportShippingrecords shippingrecords : list) {
//										 sumTotalShip+=shippingrecords.getQuantity();
//										 shipSapNo=shippingrecords.getSapNo();
//										for (ExportRepairRecorder repairRecorder : list2) {
//											repairSapNo=repairRecorder.getSapNo();
//											 if(shipSapNo.equals(repairSapNo)){//repairRecorder.getSapNo().equals(shippingrecords.getSapNo())
//												 sumTotalRepair += repairRecorder.getQuantity();//合计维修数量
//												 count+=1;
//												 str=Tools.percent(repairRecorder.getQuantity(), shippingrecords.getQuantity());
//												 percent=Double.valueOf(str);
//												 percent=Math.round(percent*100);
//												 percentList.add(percent);
//												 percent=0.0D;
//												 reparirList.add(repairRecorder);
//												 shipSapNo="";
//												 repairSapNo="";
//											}
//											
//										}
//										if(count==0){//没有维修仪器时设置为0
//											 percentList.add(0.0D);
//											 reparirList.add(null);
//										}else{
//											count=0;
//										}
//									 }
//					 //计算总的返修率
////					 	str=Tools.percent(sumTotal2,sumTotal);
////						 percent=(double)sumTotal2/(double)sumTotal;//*100;
//						 str=Tools.percent(sumTotalRepair, sumTotalShip);
//						 percent=Double.valueOf(str);
//						 percent=Math.round(percent*100);
//						map.put("repairRecorder", reparirList.size() > 0 ? reparirList : "");
//						map.put("shippingrecords", list.size() > 0 ? list : "");
//						map.put("percentList", percentList.size()>0 ? percentList : "");
//						map.put("sumTotalRepair", sumTotalRepair);
//						map.put("sumTotalShip", sumTotalShip);
//						map.put("totalPercent", percent);
//						 map.put("startTime", this.startTime);
//						 map.put("endTime", this.endTime);
//						JSONObject json = new JSONObject(map);
//						return json.toString();
//				  }
}