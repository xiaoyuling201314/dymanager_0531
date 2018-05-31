package com.dayuan.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.ExportRepairRecorder;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.RepairRecordDTO;
import com.dayuan.dto.ShippingrecordsDTO;
import com.dayuan.util.Excel;
import com.dayuan.util.FileBean;
import com.dayuan.util.ItextTools;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;
import com.lowagie.text.DocumentException;
@Controller
@RequestMapping("/repairService")
public class RepairRecordController extends BaseController{
  private Logger logger=Logger.getLogger(RepairRecordController.class);
	// 处理时间的方法
	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}
	/**
	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/repairList")
	public ModelAndView repairList(HttpServletRequest request,String repairKeys,Integer state,PaginationData pagData, Integer curPage){
		PaginationData pData=new PaginationData();
		ModelAndView result = new ModelAndView("repairRecordList");
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		if(state==null){
			state=0;
		}
		List<RepairRecordDTO> listData=repairService.queryList(repairKeys,state,curPage, pData.getPageSize());
		pData.setRecordCount(repairService.queryRecordCount(repairKeys,state),pData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pData.getPageSize());
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		pData.setItemsData(listData);
		request.setAttribute("pData", pData);
		request.setAttribute("repairKeys", repairKeys);
		request.setAttribute("state", state);
		request.setAttribute("picturePath",dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
		result.addObject("nav", "repair");  //导航高亮
		return result;
	}
	/**
	 * 上传故障图片，处理方法图片
	 * @param request
	 * @param response
	 * @param myFile
	 * @return
	 */
	  @ResponseBody  
	  @RequestMapping(value = "/uploadPicture",produces="application/json;charset=UTF-8")  
	  public String uploadPicture(HttpServletRequest request,HttpServletResponse response, @RequestParam("myFile") MultipartFile[] myFile) {//String repairOrderNumber
		 List<FileBean> fileRealPath =new ArrayList<FileBean>();
		 FileBean fBean=null;
		 //创建一个通用的多部分解析器 
		Map<String, Object> map=new HashMap<String, Object>();
		 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断 request 是否有文件上传,即多部分请求  
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 转换成多部分request
			Iterator<String> iter = multiRequest.getFileNames();// 取得request中的所有文件名
			//String realPath=dyfileUtils.ROOTPATH+inDto.getSapNo()+dyfileUtils.INSTRU_PICTURE_PATH;
			String realpath =dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());// 取得上传文件
				if (file != null) {
					fBean = new FileBean();
					String myFileName = file.getOriginalFilename();// 取得当前上传文件的文件名称
					String suffix = (myFileName.substring(myFileName.lastIndexOf("."))).toLowerCase();
					// 判断文件是否存在
					if (!"".equals(myFileName.trim())) {
						// 重命名：格式为：yyyyMMddHHmmss_随机数+后缀名（.jpg）
						myFileName=Tools.getDateTimeString("yyyyMMddHHmmss")+"_"+new Random().nextInt(100)+suffix;
						try {
							dyfileUtils.uploadImageFile(file, realpath, myFileName);//上传文件
							realpath += myFileName;
							fBean.setUrl(realpath);//设置图片绝对路径
							fBean.setFileName(myFileName);//设置图片名称
							fileRealPath.add(fBean); // 把图片路径保存到列表
						} catch (IOException e) {
							map.put("result", "error");
							logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
						}

					}
				}

			}

		}
		
		if (fileRealPath.size() > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "error");
		}
		JSONArray json = new JSONArray(fileRealPath);
		json.put(map);
		return json.toString();
	}
//	  /**
//		 * 新增维修记录
//		 * 1.校验维修单号是否可用
//		 * 2.
//		 * @param request
//		 * @param response
//		 * @param myFile
//		 * @return
//	 * @throws JSONException 
//		 */
//		  @ResponseBody  
//		  @RequestMapping(value = "/addRepaid",produces="application/json;charset=UTF-8")  
//		  public String addRepaid(HttpServletRequest request,HttpServletResponse response, String  repairStr) throws JSONException {
//			  Map<String, Object> map=new HashMap<String, Object>();
//			  JSONObject json=new JSONObject(repairStr);
//			  /**
//			   * public RepairRecordDTO(String repairOrderNumber,ShippingrecordsDTO shipmentNo,
//			String receivedDate,String planCompleteDate, String repairMan,
//			String faultDescription,String faultPicture,Integer state, String processingMethod, String processingPicture,
//			String actualCompleteDate,  String updateTime,String comments) {special
//			   */
//			  ShippingrecordsDTO shipDto=null;
//			  RepairRecordDTO recordDTO=null;
//			  int state=1;
//			  int count=0;
//			  String actualCompleteDate=json.getString("actualCompleteDate");
//			  String planCompleteDate= json.getString("planCompleteDate");
//			  Date d=new Date();
//			  Date planDate=Tools.getDate(planCompleteDate);
//			  if(StringUtils.isBlank(actualCompleteDate) && planDate.before(d)){//实际完成日期为空，并且当前日期大于计划日期 ：未完成
//				  state=3;
//			  }else if(StringUtils.isNotBlank(actualCompleteDate)){//实际完成日期不为空 ：已完成
//				  state=2;
//			  }
//			  if(StringUtils.isNotBlank(json.getString("addType"))){//没有出货记录的维修记录写入方式
//				  recordDTO=new RepairRecordDTO(json.getString("repairOrderNumber"),json.getString("sapNo"),json.getString("receivedDate"),
//						  planCompleteDate,json.getString("repairMan").equals("")?null:json.getString("repairMan"),json.getString("faultDescription"),json.getString("faultPicture").equals("")?null:json.getString("faultPicture"),
//						state,json.getString("processingMethod").equals("")?null:json.getString("processingMethod"),json.getString("processingPicture").equals("")?null:json.getString("processingPicture"),
//						actualCompleteDate.equals("")?null:actualCompleteDate,Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"),json.getString("comments").equals("")?null:json.getString("comments")
//								,json.getString("instrumentFuselage").equals("")?null:json.getString("instrumentFuselage"));
//				  recordDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
//				  count=repairService.addBySelective(recordDTO);
//			  }else{
//				  shipDto=new ShippingrecordsDTO();
//				  shipDto.setId(Integer.parseInt(json.getString("shipmentNo")));
//				  recordDTO=new RepairRecordDTO(json.getString("repairOrderNumber"),json.getString("sapNo"),json.getString("receivedDate"),
//						  planCompleteDate,json.getString("repairMan").equals("")?null:json.getString("repairMan"),json.getString("faultDescription"),json.getString("faultPicture").equals("")?null:json.getString("faultPicture"),
//						state,json.getString("processingMethod").equals("")?null:json.getString("processingMethod"),json.getString("processingPicture").equals("")?null:json.getString("processingPicture"),
//						actualCompleteDate.equals("")?null:actualCompleteDate,Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"),json.getString("comments").equals("")?null:json.getString("comments")
//								,json.getString("instrumentFuselage").equals("")?null:json.getString("instrumentFuselage"));
//				  recordDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
//				  recordDTO.setShipmentNo(shipDto);
//				  count=repairService.add(recordDTO);
//			  }
//			 
//			  if (count > 0) {
//					map.put("result", "success");
//				} else {
//					map.put("result", "error");
//				}
//			  json=new JSONObject(map);
//			  return json.toString();
//		  }
	  /**
		 * 新增维修记录
		 * 1.校验维修单号是否可用
		 * 2.
		 * @param request
		 * @param response
		 * @param myFile
		 * @return
		 * @throws JSONException 
		 */
		  @ResponseBody  
		  @RequestMapping(value = "/addRepaid",produces="application/json;charset=UTF-8")  
		  public String addRepaid(HttpServletRequest request,HttpServletResponse response, String  repairStr) throws JSONException {
			  Map<String, Object> map=new HashMap<String, Object>();
			  JSONObject json=new JSONObject(repairStr);
			  RepairRecordDTO recordDTO=null;
			  int state=1;
			  int count=0;
			  String actualCompleteDate=json.getString("actualCompleteDate");
			  String planCompleteDate= json.getString("planCompleteDate");
			  Date d=new Date();
			  Date planDate=Tools.getDate(planCompleteDate);
			  if(StringUtils.isBlank(actualCompleteDate) && planDate.before(d)){//实际完成日期为空，并且当前日期大于计划日期 ：未完成
				  state=3;
			  }else if(StringUtils.isNotBlank(actualCompleteDate)){//实际完成日期不为空 ：已完成
				  state=2;
			  }
				  recordDTO=new RepairRecordDTO(json.getString("repairOrderNumber"),json.getString("sapNo"),json.getString("receivedDate"),
						  planCompleteDate,json.getString("repairMan").equals("")?null:json.getString("repairMan"),json.getString("faultDescription"),json.getString("faultPicture").equals("")?null:json.getString("faultPicture"),
						state,json.getString("processingMethod").equals("")?null:json.getString("processingMethod"),json.getString("processingPicture").equals("")?null:json.getString("processingPicture"),
						actualCompleteDate.equals("")?null:actualCompleteDate,Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"),json.getString("comments").equals("")?null:json.getString("comments")
								,json.getString("instrumentFuselage").equals("")?null:json.getString("instrumentFuselage"));
				  recordDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
				  count=repairService.addBySelective(recordDTO);
			  if (count > 0) {
					map.put("result", "success");
				} else {
					map.put("result", "error");
				}
			  json=new JSONObject(map);
			  return json.toString();
		  }
		  /**
			 * 导入出货记录
			 * @param request
			 * @param cirDto
			 * @param operationType 导入方式 special标识历史维修导入
			 * @param myFile
			 * @return
			 */
			 @ResponseBody  
			 @RequestMapping(value = "/importRepairs",produces="application/json;charset=UTF-8")
			public String importRepairs(HttpServletRequest request,
					@RequestParam(value = "myFile")MultipartFile[] myFile,String operationType){
				String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH);
				List<RepairRecordDTO> repairList=null;
				String newFileName=myFile[0].getOriginalFilename().substring(0,myFile[0].getOriginalFilename().lastIndexOf("."));
				if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename()) ){
					try {
						dyfileUtils.uploadFile(myFile, realpath,newFileName);
//						File file=new File(realpath+myFile[0].getOriginalFilename());
//						InputStream input=new FileInputStream(file);
						String filePath=realpath+myFile[0].getOriginalFilename();
						List<String[]> list=Excel.readExcelContent(filePath, 2);
						repairList=new ArrayList<RepairRecordDTO>();
						RepairRecordDTO reDto=null;
						String updateTime="";
						if(list!=null){
							for (String[] model : list) {// 序号   维修单号	产品名称	机身号	接收日期	计划完成日期	故障原因	维修人	实际完成日期	处理方法	信息备注
								if(StringUtils.isNotBlank(model[1])){
										 int state=1;
										  String actualCompleteDate=model[8].equals("") ? null : model[8];
										  String planCompleteDate= model[5];
										  Date d=new Date();
										  Date planDate=Tools.getDate(planCompleteDate);
										  if(StringUtils.isBlank(actualCompleteDate) && planDate.before(d)){//实际完成日期为空，并且当前日期大于计划日期 ：未完成
											  state=3;
										  }else if(StringUtils.isNotBlank(actualCompleteDate)){//实际完成日期不为空 ：已完成
											  state=2;
										  }
										  updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
										  reDto=new RepairRecordDTO(model[1], model[4], planCompleteDate, model[6], model[7], actualCompleteDate, state, model[9], model[10], updateTime, model[3]);
										  reDto.setSapNo(model[2]);
										  repairList.add(reDto);
										}
							}
						}
						 Tools.delFile(realpath+myFile[0].getOriginalFilename());
						
					} catch (IOException e) {
						logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
					}
				}
				int count=0;
				count=repairService.addList(repairList);
				Map<String, String> map=new HashMap<String, String>();
				map.put("count", String.valueOf(count));
				JSONObject jsonObject=new JSONObject(map);
				return jsonObject.toString();
				
			}
		  /**
		   * 检查维修单号是否可用
		   * 1.查询所有的维修单号
		   * 2.核对传入的repairOrderNumber是否存在
		   * @param request
		   * @param fileName
		   * @param version
		   * @param subfix
		   * @return isExit=true 存在；isExit=false 不存在
		   */
		  	@ResponseBody
			@RequestMapping(value="/checkRepairOrderNumber",produces="application/json;charset=UTF-8")
			public String checkRepairOrderNumber(HttpServletRequest request,String repairOrderNumber){
				List<String> repairNumberList=repairService.queryAllRepairNumber();
				JSONObject jsonObject=new JSONObject();
				String message="false";
				if(repairNumberList!=null && repairNumberList.contains(repairOrderNumber)){
					message="true";
				}
				try {
					jsonObject.put("isExit", message);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return jsonObject.toString();
			}
			  /**
			   * 根据机身码查询出货信息
			   * 自动显示SAP代码和产品名称
			   * @param request
			   * @param fileName
			   * @param version
			   * @param subfix
			   * @return isExit=true 存在；isExit=false 不存在
			   */
			  	@ResponseBody
				@RequestMapping(value="/checkShipByFuselage",produces="application/json;charset=UTF-8")
				public String checkShipByFuselage(HttpServletRequest request,String fuselage){
					List<ShippingrecordsDTO> list=shipmentsService.queryAllFuselage();
					String [] fuseLages;
					Map<String, Object> map=new HashMap<String, Object>();
					String message="false";
					if(list!=null){
						label:for (ShippingrecordsDTO shippingrecordsDTO : list) {
							fuseLages=shippingrecordsDTO.getInstrumentFuselage().split(",");
							for (int i = 0; i < fuseLages.length; i++) {
								if(fuseLages[i].equals(fuselage)){
									message="true";
									map.put("shipMents", shippingrecordsDTO);
									break label;
								}
							}
						}
					}
					map.put("isExit", message);
					JSONObject jsonObject=new JSONObject(map);
					return jsonObject.toString();
				}
		  	/**
			 * 导出维修记录单
			 * 1.根据维修单号查找维修记录
			 * 2.根据机身码关联出货记录
			 * 3.生成pdf文件
			 * 4.下载pdf文件
			 * 5.删除该文件
			 * @param request
			 * @param fileName文件名称
			 * @return
			 * 	Executable exc=new Executable();//打印PDF文件
				exc.openDocument("");
				exc.printDocument("");
			 * @throws IOException
			 */
			@RequestMapping("/exportRepair")
			public ResponseEntity<byte[]> exportRepair(HttpServletRequest request,String repairOrderNumber){
				 ResponseEntity<byte[]> responseEntity=null;
				ExportRepairRecorder repairRecorder=repairService.queryExportById(repairOrderNumber);
				ShippingrecordsDTO shipDto=checkShip(repairRecorder.getInstrumentFuselage());
				if(shipDto!=null){
					repairRecorder.setShippingDate(shipDto.getShippingDate());
				}
				String outputFilePath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH);
				String date=Tools.getDateString(new Date());
				String fileName=date+"_维修单";
				String realPath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
				 String rootPath=request.getRealPath("/");
				 fileName+=".pdf";
				 try {
						Map<String, String> map=new HashMap<>();
						map.put("repairOrderNumber", repairRecorder.getRepairOrderNumber());
						map.put("productName", repairRecorder.getProductName());
						map.put("instrumentFuselage", repairRecorder.getInstrumentFuselage());
						map.put("shippingDate",repairRecorder.getShippingDate());
						map.put("warrantyDate", repairRecorder.getWarrantyDate());
						map.put("faultDescription", "  "+repairRecorder.getFaultDescription());
						map.put("processingMethod", repairRecorder.getProcessingMethod()!=null ? "  "+repairRecorder.getProcessingMethod():"");
						map.put("repairMan", repairRecorder.getRepairMan());
						map.put("actualCompleteDate", repairRecorder.getActualCompleteDate());
						map.put("comments",repairRecorder.getComments()!=null ? "  "+repairRecorder.getComments():"");
						int count=0;
						if(StringUtils.isNotBlank(repairRecorder.getFaultPicture())){
							String [] faultPicture=repairRecorder.getFaultPicture().split(",");
							for (int i = 0; i < faultPicture.length; i++) {
								count=(i+1);
								map.put("faultPicture"+count, faultPicture[i]);
							}
						}
						if(StringUtils.isNotBlank(repairRecorder.getProcessingPicture())){
							String [] processingPicture=repairRecorder.getProcessingPicture().split(",");
							count=0;
							for (int i = 0; i < processingPicture.length; i++) {
								count=(i+1);
								map.put("processingPicture"+count, processingPicture[i]);
							}
						}
						//String rootPath,String realPath,String picturePath ,Map<String, String> map
						String picturePath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
					ItextTools.createRepairPdfDocument(rootPath,realPath+fileName, picturePath, map);//,repairRecorder.getRepairOrderNumber()+"-维修报告"
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				responseEntity=dyfileUtils.download(request,realPath, fileName);
				Tools.delFile(realPath+fileName);
				return responseEntity;
			}
//			/**
//			 * 导入出货记录
//			 * @param request
//			 * @param cirDto
//			 * @param operationType 导入方式 special标识历史维修导入
//			 * @param myFile
//			 * @return
//			 */
//			 @ResponseBody  
//			 @RequestMapping(value = "/importRepairs",produces="application/json;charset=UTF-8")
//			public String importRepairs(HttpServletRequest request,
//					@RequestParam(value = "myFile")MultipartFile[] myFile,String operationType){
//				String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH);
//				List<RepairRecordDTO> repairList=null;
//				String newFileName=myFile[0].getOriginalFilename().substring(0,myFile[0].getOriginalFilename().lastIndexOf("."));
//				if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename()) ){
//					try {
//						dyfileUtils.uploadFile(myFile, realpath,newFileName);
////						File file=new File(realpath+myFile[0].getOriginalFilename());
////						InputStream input=new FileInputStream(file);
//						String filePath=realpath+myFile[0].getOriginalFilename();
//						List<String[]> list=Excel.readExcelContent(filePath, 2);
//						repairList=new ArrayList<RepairRecordDTO>();
//						RepairRecordDTO reDto=null;
//						ShippingrecordsDTO shipDto=null;
//						String updateTime="";
//						for (String[] model : list) {// 序号   维修单号	产品名称	机身号	接收日期	计划完成日期	故障原因	维修人	实际完成日期	处理方法	信息备注
//							if(StringUtils.isNotBlank(model[1])){
//								shipDto=checkShip(model[3]);//根据机身号查找出货记录
//								//if(shipDto!=null){
//									 int state=1;
//									  String actualCompleteDate=model[8].equals("") ? null : model[8];
//									  String planCompleteDate= model[5];
//									  Date d=new Date();
//									  Date planDate=Tools.getDate(planCompleteDate);
//									  if(StringUtils.isBlank(actualCompleteDate) && planDate.before(d)){//实际完成日期为空，并且当前日期大于计划日期 ：未完成
//										  state=3;
//									  }else if(StringUtils.isNotBlank(actualCompleteDate)){//实际完成日期不为空 ：已完成
//										  state=2;
//									  }
//									updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
//									
//									if(operationType.equals("special")){//历史维修记录导入，设置SAP代码
//										reDto=new RepairRecordDTO(model[1], model[4], planCompleteDate, model[6], model[7], actualCompleteDate, state, model[9], model[10], updateTime, model[3]);
//										reDto.setSapNo(model[2]);
//									}else{
//										if(shipDto!=null){
//											reDto=new RepairRecordDTO(model[1], shipDto, model[4], planCompleteDate, model[6], model[7], actualCompleteDate, state, model[9], model[10], updateTime, model[3]);
//											reDto.setSapNo(model[2]);
//										}
//									}
//									repairList.add(reDto);
//								//}
//							}
//						}
//						 Tools.delFile(realpath+myFile[0].getOriginalFilename());
//						
//					} catch (IOException e) {
//						logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
//					}
//				}
//				int count=0;
//				count=repairService.addList(repairList);
//				Map<String, String> map=new HashMap<String, String>();
//				map.put("count", String.valueOf(count));
//				JSONObject jsonObject=new JSONObject(map);
//				return jsonObject.toString();
//				
//			}
				/**
				 * 删除维修记录单信息
				 * @param request
				 * @param id
				 * @return
				 */
				@ResponseBody
				@RequestMapping(value = "/delRepair", produces = "application/json;charset=UTF-8")
				public String delRepair(HttpServletRequest request,String repairOrderNumber){
					Map<String, Object> map=new HashMap<String, Object>();
					RepairRecordDTO recordDTO=repairService.queryById(repairOrderNumber);
					String faultPicture=recordDTO.getFaultPicture();
					String processingPicture=recordDTO.getProcessingPicture();
					String [] faultPic=null;
					String [] dealPic=null;
					if(StringUtils.isNotBlank(faultPicture)){
						faultPic=faultPicture.split(",");
					}
					if(StringUtils.isNotBlank(processingPicture)){
						dealPic=processingPicture.split(",");
					}
					int count=repairService.deleteById(repairOrderNumber);
					if(count>0){
						//删除维修图片
						String realPath = dyfileUtils.getrealPath(request,dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
						if(faultPic!=null){
							for (int i = 0; i <faultPic.length ; i++) {
								Tools.delFile(realPath + faultPic[i]);
							}
						}
						if(dealPic!=null){
							for (int i = 0; i <dealPic.length ; i++) {
								Tools.delFile(realPath + dealPic[i]);
							}
						}
						
						map.put("result", "success");
					}else{
						map.put("result", "error");
					}
					JSONObject json=new JSONObject(map);
					return json.toString();
				}
				
				  /**
				   *查询维修记录信息，进入编辑页面
				   * 自动显示SAP代码和产品名称
				   * @param request
				   * @param fileName
				   * @param version
				   * @param subfix
				   * @return isExit=true 存在；isExit=false 不存在
				   */
					@RequestMapping(value="/queryRepair")
					public ModelAndView queryRepair(HttpServletRequest request,String repairOrderNumber){
						ModelAndView result=new ModelAndView("repair_edit");
						RepairRecordDTO repairRecordDTO=repairService.queryById(repairOrderNumber);
						if(repairRecordDTO!=null){
							request.setAttribute("repairRecords", repairRecordDTO);
						}else{
							request.setAttribute("repairRecords", "");
						}
						if(StringUtils.isNotBlank(repairRecordDTO.getFaultPicture())){
							int countPicture=repairRecordDTO.getFaultPicture().split(",").length;
							request.setAttribute("countFaultPicture",4-countPicture!=0?4-countPicture:-1 );
						}
						if(StringUtils.isNotBlank(repairRecordDTO.getProcessingPicture())){
							int countPicture=repairRecordDTO.getProcessingPicture().split(",").length;
							request.setAttribute("countDealPicture",4-countPicture!=0?4-countPicture:-1 );
						}
						request.setAttribute("picturePath",dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
						request.setAttribute("nav", "repair");
						return result;
					}
					  /**
					   *查询维修记录信息，进入编辑页面
					   * 自动显示SAP代码和产品名称
					   * @param request
					   * @param fileName
					   * @param version
					   * @param subfix
					   * @return isExit=true 存在；isExit=false 不存在
					   */
						@ResponseBody
						@RequestMapping(value="/queryRepairDetail", produces = "application/json;charset=UTF-8") 
						public String queryRepairDetail(HttpServletRequest request,String repairOrderNumber){
							 Map<String, Object> map=new HashMap<String, Object>();
							 
							RepairRecordDTO repairRecordDTO=repairService.queryById(repairOrderNumber);
							if(repairRecordDTO!=null){
								map.put("repairRecords", repairRecordDTO);
							}else{
								map.put("repairRecords", "");
							}
							map.put("picturePath", dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
							 JSONObject json=new JSONObject(map);
							return json.toString();
						}
					  /**
					 * 修改维修记录
					 * 1.修改维修记录
					 * 2.删除图片
					 * @param request
					 * @param response
					 * @param myFile
					 * @return
					 * @throws JSONException 
					 */
					  @ResponseBody
					  @RequestMapping(value = "/updateRepaid", produces = "application/json;charset=UTF-8") 
					  public String updateRepaid(HttpServletRequest request,HttpServletResponse response, String  repairStr) throws JSONException {
						  Map<String, Object> map=new HashMap<String, Object>();
						  JSONObject json=new JSONObject(repairStr);
						  ShippingrecordsDTO shipDto=null;
						  RepairRecordDTO recordDTO=null;
						  int count=0;
						  int state=1;//1.正在处理
						  String actualCompleteDate=json.getString("actualCompleteDate");
						  String planCompleteDate= json.getString("planCompleteDate");
						  Date d=new Date();
						  Date planDate=Tools.getDate(planCompleteDate);
						  if(StringUtils.isBlank(actualCompleteDate) && planDate.before(d)){//实际完成日期为空，并且当前日期大于计划日期 ：逾期
							  state=3;
						  }else if(StringUtils.isNotBlank(actualCompleteDate)){//实际完成日期不为空 ：已完成
							  state=2;
						  }
						  recordDTO=new RepairRecordDTO(json.getString("repairOrderNumber"),json.getString("sapNo"),json.getString("receivedDate"),
								  planCompleteDate,json.getString("repairMan"),json.getString("faultDescription"),json.getString("faultPicture"),
								state,json.getString("processingMethod"),json.getString("processingPicture"),
								 actualCompleteDate,Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"),json.getString("comments")
										,json.getString("instrumentFuselage").equals("")?null:json.getString("instrumentFuselage"));
						  recordDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
						  count=repairService.updateBySelective(recordDTO);
//						  if(StringUtils.isNotBlank(json.getString("addType"))){//没有出货记录的维修记录写入方式
//							  count=repairService.updateBySelective(recordDTO);
//						  }else{
//							  shipDto=new ShippingrecordsDTO();
//							  shipDto.setId(Integer.parseInt(json.getString("shipmentNo")));
////							  recordDTO=new RepairRecordDTO(json.getString("repairOrderNumber"),json.getString("sapNo"),json.getString("receivedDate"),
////									  planCompleteDate,json.getString("repairMan").equals("")?null:json.getString("repairMan"),json.getString("faultDescription"),json.getString("faultPicture").equals("")?null:json.getString("faultPicture"),
////									state,json.getString("processingMethod").equals("")?null:json.getString("processingMethod"),json.getString("processingPicture").equals("")?null:json.getString("processingPicture"),
////									actualCompleteDate.equals("")?null:actualCompleteDate,Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"),json.getString("comments").equals("")?null:json.getString("comments")
////											,json.getString("instrumentFuselage").equals("")?null:json.getString("instrumentFuselage"));//,json.getString("instrumentFuselage").equals("")?null:json.getString("instrumentFuselage")
////							  recordDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
//							  recordDTO.setShipmentNo(shipDto);
//							   count=repairService.updateById(recordDTO);//
//						  }
						  if (count > 0) {
							  // 删除图片
							  String delpicture=json.getString("delpicture");
								if (delpicture != null) {
									String[] delPic = delpicture.split(",");
									String realPath = dyfileUtils.getrealPath(request,dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_REPAIR_PATH);
									for (int i = 0; i < delPic.length; i++) {
										Tools.delFile(realPath + delPic[i]);
									}
								}
								map.put("result", "success");
							} else {
								map.put("result", "error");
						}
						  json=new JSONObject(map);
						  return json.toString();
					  }
				public ShippingrecordsDTO checkShip(String fuselage){
					ShippingrecordsDTO shiDto=null;
					String [] fuselageList=null;
					List<ShippingrecordsDTO> list=shipmentsService.queryAllFuselage();
					if(list!=null){
						shipDTO:for (ShippingrecordsDTO shippingrecordsDTO : list) {
							fuselageList=shippingrecordsDTO.getInstrumentFuselage().split(",");
							for (int i = 0; i < fuselageList.length; i++) {
								if(fuselageList[i].equals(fuselage)){
									shiDto=shippingrecordsDTO;
									break shipDTO;
								}
								
							}
							
						}
					}
					return shiDto;
				
				}
}
