package com.dayuan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.bean.Instrumentinfo;
import com.dayuan.bean.PaginationData;
import com.dayuan.bean.Shippingrecords;
import com.dayuan.dto.CompletemachineDTO;
import com.dayuan.dto.CompletemachinedetailDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.PackinglistDTO;
import com.dayuan.dto.ShippingrecordsDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.util.Excel;
import com.dayuan.util.ItextTools;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/shipmentsService")
public class ShippingrecordsController extends BaseController {
	 private Logger logger=Logger.getLogger(ShippingrecordsController.class);
	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/shipList")
	public ModelAndView shipList(HttpServletRequest request,String shipmentsKeys,PaginationData pagData, Integer curPage){
		PaginationData pData=new PaginationData();
		ModelAndView result = new ModelAndView("shipments");
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<ShippingrecordsDTO> listData=shipmentsService.queryList(shipmentsKeys,curPage, pData.getPageSize());
		pData.setRecordCount(shipmentsService.queryRecordCount(shipmentsKeys),pData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pData.getPageSize());
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		pData.setItemsData(listData);
		request.setAttribute("pData", pData);
		request.setAttribute("shipmentsKeys", shipmentsKeys);
		result.addObject("nav", "shield");  //导航高亮
		return result;
	}
     /**
      * 新增出货信息
      * 1.手工录入
      * 2.导入excel文件数据
      * @param request
      * @param shiDto
      * @param myfile
      * @return
      */
	 @ResponseBody  
	 @RequestMapping(value = "/addShipments",produces="application/json;charset=UTF-8")
	public String addShipments(HttpServletRequest request,ShippingrecordsDTO shiDto,@RequestParam(value="myfile",required=false)MultipartFile[] myfile){
		Map<String, String> map=new HashMap<String, String>();
		int count=0;
			
		if(myfile!=null&& StringUtils.isNotBlank(myfile[0].getOriginalFilename())){//导入出货记录
		    
			map.put("count", String.valueOf(count));
		}else{//手工录入数据
			shiDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
			 count=shipmentsService.add(shiDto);
			if(count>0){
				map.put("result", "success");
			}
		}
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
	}
	
	    /**
      * 新增出货信息
      * 1.手工录入
      * 2.导入excel文件数据
      * @param request
      * @param shiDto
      * @param myfile
      * @return
	     * @throws JSONException 
      */
	 @ResponseBody  
	 @RequestMapping(value = "/inputShipments",produces="application/json;charset=UTF-8")
	public String inputShipments(HttpServletRequest request,
			String shiDto) throws JSONException {
		Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		JSONObject json=new JSONObject(shiDto);
		InstrumentinfoDTO inDto=new InstrumentinfoDTO();
		inDto.setSapNo(json.getString("sapNo"));
		/**
		 * com.dayuan.dto.ShippingrecordsDTO.ShippingrecordsDTO(InstrumentinfoDTO sapNo, String customer, Integer quantity, String instrumentFuselage, String shippingDate, String inspectionMan, String comments, String updateTime)

		 */
		ShippingrecordsDTO shiDtoEntity=new ShippingrecordsDTO(inDto, json.getString("customer"), Integer.parseInt(json.getString("quantity")), json.getString("instrumentFuselage"), json.getString("shippingDate"), json.getString("inspectionMan"), json.getString("comments"),Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		//shiDtoEntity.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		shiDtoEntity.setSoftwareVersion(json.getString("softwareVersion"));
		count = shipmentsService.add(shiDtoEntity);
		if (count > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "error");
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	     /**
	      * 新增出货信息
	      * 1.手工录入
	      * @param request
	      * @param shiDto
	      * @param myfile
	      * @return
	      */
		 @ResponseBody  
		 @RequestMapping(value = "/selectInstrument",produces="application/json;charset=UTF-8")
		public String selectInstrument(HttpServletRequest request,String sapNo){
			Map<String, Object> map=new HashMap<String, Object>();
			InstrumentinfoDTO inDto=insService.queryById(sapNo);
			if(inDto!=null){
				map.put("inDto", inDto);
			}else
				map.put("inDto", "");
			
			JSONObject jsonObject=new JSONObject(map);
			return jsonObject.toString();
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
	 * 查询所有的客户名称
	 * 用于客户名称输入框自动补全
	 * @param request
	 * @param customerKeys
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCustomer", produces = "application/json;charset=UTF-8")
	public String queryCustomer(HttpServletRequest request) {
		List<String> customer=shipmentsService.queryCustomer();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("customer", customer);
		JSONObject json=new JSONObject(map);
		return json.toString();
	}
	@RequestMapping(value = "/viewshipMents")
	public ModelAndView viewshipMents(HttpServletRequest request,Integer id){
		ModelAndView result=new ModelAndView("shipments_edit");
		ShippingrecordsDTO shipMents=shipmentsService.viewshipMents(id);
		request.setAttribute("shipMents", shipMents);
		return result;
	}
	/**
	 * 修改出货记录信息
	 * @param request
	 * @param shiDto
	 * @return
	 * @throws JSONException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateshipMents", produces = "application/json;charset=UTF-8")
	public String updateshipMents(HttpServletRequest request,String shiDto) throws JSONException{
		Map<String, Object> map=new HashMap<String, Object>();
		JSONObject json=new JSONObject(shiDto);
		Integer id=Integer.parseInt(json.getString("id"));
		ShippingrecordsDTO shiDtoEntity=new ShippingrecordsDTO(json.getString("customer"), Integer.parseInt(json.getString("quantity")), json.getString("instrumentFuselage"), json.getString("shippingDate"), json.getString("inspectionMan"), json.getString("shippingDate"), json.getString("comments"));
		shiDtoEntity.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		shiDtoEntity.setId(id);
		shiDtoEntity.setSoftwareVersion(json.getString("softwareVersion"));
		int count=shipmentsService.updateBySelective(shiDtoEntity);
		if(count>0){
			map.put("result", "success");
		}else{
			map.put("result", "error");
		}
		json=new JSONObject(map);
		return json.toString();
	}
	/**
	 * 删除出货信息
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delShipMents", produces = "application/json;charset=UTF-8")
	public String delShipMents(HttpServletRequest request,Integer id){
		Map<String, Object> map=new HashMap<String, Object>();
		int count=shipmentsService.deleteById(id);
		if(count>0){
			map.put("result", "success");
		}else{
			map.put("result", "error");
		}
		JSONObject json=new JSONObject(map);
		return json.toString();
	}
	
	/**
	 * 批量删除出货信息
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delShipMentsList", produces = "application/json;charset=UTF-8")
	public String delShipMentsList(HttpServletRequest request,String shipIdList){
		Map<String, Object> map=new HashMap<String, Object>();
		 String [] listID=shipIdList.split(",");
		List<Integer> lists=new ArrayList<Integer>();
		for (int i = 0; i < listID.length; i++) {
			lists.add(Integer.parseInt(listID[i]));
		}
		int count=shipmentsService.deleteByListId(lists);
		if(count>0){
			map.put("result", "success");
		}else{
			map.put("result", "error");
		}
		JSONObject json=new JSONObject(map);
		return json.toString();
	}
	/**
	 * 查看出货记录详情方法
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryshipMents", produces = "application/json;charset=UTF-8")
	public String queryshipMents(HttpServletRequest request,Integer id){
		Map<String, Object> map=new HashMap<String, Object>();
		ShippingrecordsDTO shipMents=shipmentsService.viewshipMents(id);
		map.put("shipMents", shipMents);
		JSONObject json=new JSONObject(map);
		return json.toString();
	}
	/**
	 * 导出出货记录单
	 * 1.根据出货记录ID查找记录集合
	 * 2.生成excel文件
	 * 3.下载excel文件完成导出
	 * 4.删除该文件
	 * @param request
	 * @param fileName文件名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/exportShipments")
	public ResponseEntity<byte[]> exportShipments(HttpServletRequest request,String shipIdList){
		 ResponseEntity<byte[]> responseEntity=null;
		 //1.BOm单号，详细信息
		 String [] listID=shipIdList.split(",");
		 List<ExportShippingrecords> list=null;
		 if(listID.length>0){
			 List<Integer> lists=new ArrayList<Integer>();
			 for (int i = 0; i < listID.length; i++) {
				 lists.add(Integer.parseInt(listID[i]));
			}
			  list=shipmentsService.queryByListID(lists);
		 }else{
			 list=new ArrayList<ExportShippingrecords>();
		 }
		String []titleArray={"序号","SAP代码","产品名称","客户名称","软件版本","数量","机身号","出货日期","检验人","备注"};//"产品名称","","SAP代码",
		String []entityFieldNameArray={"id","sapNo","productName","customer","softwareVersion","quantity","instrumentFuselage","shippingDate","inspectionMan","comments"};//"sapNo",
		
		String []totalFieldNameArray=null;
		String outputFilePath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH);
		String date=Tools.getDateString(new Date());
		String fileName=date+"_出货记录";
				int []celWidth={2000,5000,10000,10000,4500,4500,4500,10000,2000,15000};//4500,
				fileName+=".xls";
			Excel.outputExcelFile(null,fileName, titleArray, entityFieldNameArray,celWidth, list, totalFieldNameArray, outputFilePath+fileName);
	
		responseEntity=dyfileUtils.download(request,outputFilePath, fileName);
		Tools.delFile(outputFilePath+fileName);
		return responseEntity;
	}
	/**
	 * 导入出货记录
	 * @param request
	 * @param cirDto
	 * @param myFile
	 * @return
	 */
	 @ResponseBody  
	 @RequestMapping(value = "/importShipMents",produces="application/json;charset=UTF-8")
	public String importShipMents(HttpServletRequest request,
			@RequestParam(value = "myFile")MultipartFile[] myFile){
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH);
		List<ShippingrecordsDTO> shipList=null;
		String newFileName=myFile[0].getOriginalFilename().substring(0,myFile[0].getOriginalFilename().lastIndexOf("."));
		if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename()) ){
			try {
				dyfileUtils.uploadFile(myFile, realpath,newFileName);
//				File file=new File(realpath+myFile[0].getOriginalFilename());
//				InputStream input=new FileInputStream(file);
				String filePath=realpath+myFile[0].getOriginalFilename();
				List<String[]> list=Excel.readExcelContent(filePath, 2);
				shipList=new ArrayList<ShippingrecordsDTO>();
				 ShippingrecordsDTO shiDto=null;
				InstrumentinfoDTO insDto=null;
				String updateTime="";
				String comments="";
				//检查导入数据是否重复
				int count=0;
				for (String[] model : list) {//序号	SAP代码	产品名称	客户名称  版本号	数量  机身号 	出货日期	检验人		备注
					if(StringUtils.isNotBlank(model[1])){
						insDto=new InstrumentinfoDTO();
						insDto.setSapNo(model[1].trim());
					int quantity=0;
					if(!model[5].trim().equals("")){
						 quantity=Integer.parseInt(model[5]);
					}
					if(model.length<10){
						comments="";
					}else{
						comments=model[9];
					}
					updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
					model[5]=model[5].replace("，", ",");
					shiDto=new ShippingrecordsDTO(insDto, model[3],quantity,model[6],model[7],model[8],comments,updateTime);
					shiDto.setSoftwareVersion(model[4] ==null ? "" : model[4].trim());
					shipList.add(shiDto);
					}
				}
				 Tools.delFile(realpath+myFile[0].getOriginalFilename());
				
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
		}
		shipmentsService.addList(shipList);
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(shipList.size()));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
		
	}
	 public List<ExportShippingrecords> getDataSet(String startTime,String endTime){
		 Map<String, Object> map=new HashMap<String, Object>();
		 List<ExportShippingrecords> list=shipmentsService.statisticsShip(startTime,endTime);
		if(list==null){
			list=new ArrayList<>();
		}
		 return list;
	 }
}
