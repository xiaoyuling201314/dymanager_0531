package com.dayuan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CircuitboardDTO;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.CompletemachinedetailDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.dto.ManualDTO;
import com.dayuan.dto.MaterieTypeDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.MaterielDrawingsDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.service.ICircuitboardDetailService;
import com.dayuan.service.ICircuitboardService;
import com.dayuan.util.Excel;
import com.dayuan.util.ItextTools;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/circuitBoardService")
public class CircuitboardController extends BaseController {
	
 private Logger logger=Logger.getLogger(CircuitboardController.class);
   
   /**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/cirCuitList")
	public ModelAndView cirCuitList(HttpServletRequest request,String circuitKeys,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_pcb_bom");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<CircuitboardDTO> listData=circuitboardService.queryList(sapNo,circuitKeys,curPage, pagData.getPageSize());
		pData.setRecordCount(circuitboardService.queryRecordCount(sapNo,circuitKeys),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		request.setAttribute("circuitKeys", circuitKeys);
		result.addObject("nav", "instruments_pcb_bom");
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/deleteCirCuit",produces="application/json;charset=UTF-8")
	public String  deleteCirCuit(HttpServletRequest request,Integer cirBoardId){
		int state=circuitboardService.deleteById(cirBoardId);
		System.out.println("执行结果为："+state);
		//重新查询数据记录
		ModelAndView result=new ModelAndView("instruments_pcb_bom");
		PaginationData pData=new PaginationData();
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<CircuitboardDTO> listData=circuitboardService.queryList(sapNo,null,pData.getCurPage(), pData.getPageSize());
		pData.setRecordCount(circuitboardService.queryRecordCount(sapNo,null),pData.getPageSize());
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		result.addObject("nav", "instruments_pcb_bom");
		JSONArray jsonArray=new JSONArray(listData);
		return jsonArray.toString();
	}
	
	 @ResponseBody  
	 @RequestMapping(value = "/addCirCuit",produces="application/json;charset=UTF-8")
	public String addCirCuit(HttpServletRequest request,CircuitboardDTO cirDto,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_CIRCUITBOARD_PATH);
		List<CircuitboardDetailDTO> mDtos=null;
		if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename())){
			String newFileName=cirDto.getCircuitBoardName().trim()+"_"+cirDto.getCircuitBoardVersion().trim();
			String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
			try {
				dyfileUtils.uploadFile(myFile, realpath, newFileName);
//				File file=new File(realpath+newFileName+subfix);
//				InputStream input=new FileInputStream(file);
				String filePath=realpath+newFileName+subfix;
				List<String[]> list=Excel.readExcelContent(filePath, 2);
				 mDtos=new ArrayList<CircuitboardDetailDTO>();
				CircuitboardDetailDTO cirDetailDTO=null;
				MaterielDTO materielNo=null;
				String date=Tools.getDateTimeString(new Date());
				if(list!=null){
				for (String[] model : list) {//[3, 01.1604, 金标卡-检测主板, JinBiaoKa-V1.4, , , 25]
					//materielDTO=new MaterielDTO(model[1], model[3], materieTypeService.queryById(model[2]), model[4], model[5], model[6], model[8]);
					if(StringUtils.isNotBlank(model[1])){
						materielNo=new MaterielDTO();
						materielNo.setMaterielNo(model[1]);
						int quantity=0;
						if(!model[6].trim().equals("")){
							 quantity=Integer.parseInt(model[6]);
						}
						cirDetailDTO=new CircuitboardDetailDTO(materielNo, model[5], quantity,date);
						mDtos.add(cirDetailDTO);
					}
				}
				}
				 Tools.delFile(realpath+newFileName);
				
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
		}
		cirDto.setSapNo(sapNo);
		cirDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		cirDto.setCircuitBoardName(cirDto.getCircuitBoardName().trim()+"_"+cirDto.getCircuitBoardVersion().trim());
		int circuitId=circuitboardService.add(cirDto);//返回序列号
		int count=0;
		if(mDtos!=null && mDtos.size()>0){
			for (CircuitboardDetailDTO circuitboardDetailDTO : mDtos) {//设置BOM单编号
				circuitboardDetailDTO.setCircuitId(circuitId);
			}
			count=cirDetailService.addList(mDtos);
		}
		if(count==-1){//物料导入失败，删除BOM单信息
			circuitboardService.deleteById(circuitId);
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
		//return "redirect:cirCuitList.do";
		
	}
	/**
	 * 查看物料详细信息列表
	 * @param request
	 * @param id
	 * @param keys
	 * @param pagData
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/selectCirCuit")//查看电路板BOM单详情
	public ModelAndView selectCirCuit(HttpServletRequest request,Integer id,String circuitKeys,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_pcb_bom_details");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<CircuitboardDetailDTO> boardList=cirDetailService.queryByBoardId(id, circuitKeys, curPage,pagData.getPageSize());
		pData.setRecordCount(cirDetailService.queryRecordCount(id, circuitKeys),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(boardList);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		CircuitboardDTO cirDto=circuitboardService.queryById(id);
		request.setAttribute("cirDto", cirDto);
		request.setAttribute("picturePath", dyfileUtils.MATERIEL_PICTURE_PATH);
		checkIsShow(request);//根据权限配置判断添加物料时新增物料按钮的显示与隐藏
		result.addObject("nav", "instruments_pcb_bom");
		request.setAttribute("circuitKeys", circuitKeys);
		// 查询物料信息
		PaginationData pDataMater=new PaginationData();
		 pDataMater.setCurPage(1);
		List<MaterielDTO> listData = materielService.queryList(null, pDataMater.getCurPage(),
				pDataMater.getPageSize());
		pDataMater.setRecordCount(materielService.queryRecordCount(null),
				pDataMater.getPageSize());
		pDataMater.setPageSize(pDataMater.getPageSize());
		pDataMater.setItemsData(listData);
		pDataMater.setStartIndex((pDataMater.getCurPage() - 1)* pDataMater.getPageSize());
		request.setAttribute("pDataMateriel", pDataMater);
		return result;
		
	}
	
	/**
	 * 导出电路板BOM单
	 * 1.根据BOM单Id查询所有的物料信息
	 * 2.生成excel文件
	 * 3.下载excel文件完成导出
	 * 4.删除该文件
	 * @param request
	 * @param fileName文件名称
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/exportCircuitBoard")
	public ResponseEntity<byte[]> exportCircuitBoard(HttpServletRequest request,Integer circuitId,String type){
		 ResponseEntity<byte[]> responseEntity=null;
		 //1.BOm单号，详细信息
		 CircuitboardDTO cirDto=circuitboardService.queryById(circuitId);
		 List<String> sapNoList=cirDetailService.queryAllByBoardId(circuitId);
		 List<ExportCircuitBoard> list=null;
		 if(sapNoList.size()>0){
			  list=cirDetailService.queryByCircuitIdNoList(circuitId);
		 }else{
			 list=new ArrayList<ExportCircuitBoard>();
		 }
		String []titleArray={"序号","物料编号","物料名称","型号规格","封装","位号","数量","备注"};//"位号",
		String []entityFieldNameArray={"id","materielNo","materielName","modelSpecification","footprint","locationNo","quantity","comment"};//"materielTypeName",
		
		String []totalFieldNameArray=null;
		//SysUserDTO sysUserDTO=(SysUserDTO)request.getSession().getAttribute("userSession");
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String outputFilePath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_CIRCUITBOARD_PATH);
		String fileName=cirDto.getSapNo()+"-"+cirDto.getCircuitBoardName();
		if(type.equals("excel")){
			fileName+=".xls";
		   int []celWidth={5000,5000,4500,4500,3000,10000,10000,15000};//10000,
			Excel.outputExcelFile(null, fileName, titleArray, entityFieldNameArray,celWidth, list, totalFieldNameArray, outputFilePath+fileName);
			
		}else{
			fileName+=".pdf";
			try {
				String rootPath=request.getRealPath("/");
				ItextTools.createCircuitPdfDocument(rootPath,outputFilePath+fileName, titleArray, list,cirDto.getSapNo()+"-"+cirDto.getCircuitBoardName());
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		responseEntity=dyfileUtils.download(request,outputFilePath, fileName);
				 Tools.delFile(outputFilePath+fileName);
		return responseEntity;
	}
	
	
	/**检索物料信息
	 * 
	 * @param request
	 * @param materielKeys
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectMateriel",produces="application/json;charset=UTF-8")//查看电路板BOM单详情
	public String selectMateriel(HttpServletRequest request,String materielKeys,PaginationData pagData,Integer curPage){
//	List<MaterielDTO> list=materielService.queryByMaterielKeys(materielKeys);
//	JSONArray json=new JSONArray(list);
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<MaterielDTO> listData=materielService.queryList(materielKeys,curPage, pagData.getPageSize());
		pData.setRecordCount(materielService.queryRecordCount(materielKeys),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pDataMateriel", pData);
		request.setAttribute("materielName", materielKeys);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("listData", listData);
		map.put("pDataMateriel", pData);
		JSONObject json=new JSONObject(map);
		return json.toString();
	}
	
	/**
	 * 删除电路板列表信息
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteDetail",produces="application/json;charset=UTF-8")
	public String deleteDetail(HttpServletRequest request,Integer cDetailId,Integer curPage){
		CircuitboardDetailDTO cDetailDTO=cDetailService.queryById(cDetailId);
		cDetailService.deleteById(cDetailId);//cDetailDTO
		PaginationData pData=new PaginationData();
		List<CircuitboardDetailDTO> boardList=cirDetailService.queryByBoardId(cDetailDTO.getCircuitId(), null, curPage,pData.getPageSize());
		pData.setRecordCount(cirDetailService.queryRecordCount(cDetailDTO.getCircuitId(), null),pData.getPageSize());
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		pData.setCurPage(curPage);
		request.setAttribute("pData", pData);
		JSONArray jsonArray=new JSONArray(boardList);
		return jsonArray.toString();
	}
	/**
	 * 添加电路板列表信息
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addDetail",produces="application/json;charset=UTF-8")
	public String addDetail(HttpServletRequest request,Integer circuitId,String materNoList){
		System.out.println(circuitId+materNoList);
		String [] array=materNoList.split(",");
		List<CircuitboardDetailDTO> list=new ArrayList<CircuitboardDetailDTO>();
		List<String> listMaterNo=cirDetailService.queryAllByBoardId(circuitId);
		MaterielDTO mater=null;
		String updateTime="";
		String exitMaterNo="";
		CircuitboardDetailDTO cirDetailDTO=null;
		for (int i = 0; i < array.length; i++) {
			if(listMaterNo.size()>0){
				if(!listMaterNo.contains(array[i])){//物料未添加
					cirDetailDTO=new CircuitboardDetailDTO();
					cirDetailDTO.setCircuitId(circuitId);
					mater=new MaterielDTO();
					mater.setMaterielNo(array[i]);
					cirDetailDTO.setMaterielNo(mater);
					updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
					cirDetailDTO.setUpdateTime(updateTime);
					cirDetailDTO.setQuantity(0);
					list.add(cirDetailDTO);
				}else{//物料已添加
					if(exitMaterNo.equals("")){
						exitMaterNo+=array[i];
					}else{
						exitMaterNo+=","+array[i];
					}
				}
				
			}else{
				cirDetailDTO=new CircuitboardDetailDTO();
				cirDetailDTO.setCircuitId(circuitId);
				mater=new MaterielDTO();
				mater.setMaterielNo(array[i]);
				cirDetailDTO.setMaterielNo(mater);
				updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
				cirDetailDTO.setUpdateTime(updateTime);
				cirDetailDTO.setQuantity(0);
				list.add(cirDetailDTO);
			}

		}
		cirDetailService.addList(list);
		//重新查询详细信息，返回页面
		PaginationData pData=new PaginationData();
		List<CircuitboardDetailDTO> boardList=cirDetailService.queryByBoardId(circuitId, null, pData.getCurPage(),pData.getPageSize());
		pData.setRecordCount(cirDetailService.queryRecordCount(circuitId, null),pData.getPageSize());
		pData.setItemsData(boardList);
		request.setAttribute("pData", pData);
		CircuitboardDTO cirDto2=circuitboardService.queryById(circuitId);
		request.setAttribute("cirDto", cirDto2);
		Map<String, String> map=new HashMap<String, String>();
		if(!exitMaterNo.equals("")){
		 map.put("message", "物料:"+exitMaterNo+"不可重复添加,信息错误或选择有误");
		}else{
			map.put("message","");
		}
		JSONObject json=new JSONObject(map);
		return json.toString();
	}
	/**
	 * 编辑电路板列表信息
	 * 数量和位号
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateDetail",produces="application/json;charset=UTF-8")
	public String updateDetail(HttpServletRequest request,Integer quantityNum,String locationNo,String comments,Integer cirDetailId){
		CircuitboardDetailDTO ciDetailDTO=cDetailService.queryById(cirDetailId);
		ciDetailDTO.setQuantity(quantityNum!=null ? quantityNum : null);
		ciDetailDTO.setLocationNo(locationNo!=null && !locationNo.trim().equals("") ? locationNo : null);
		ciDetailDTO.setComments(comments!=null && !comments.trim().equals("") ? comments : null);
		ciDetailDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		int state=cDetailService.updateBySelective(ciDetailDTO);
		Map<String, String> map=new HashMap<String, String>();
		map.put("result", "success");
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
	}
	@ResponseBody
	@RequestMapping(value="/checkFileVersion",produces="application/json;charset=UTF-8")
	public String checkFileVersion(HttpServletRequest request,String circuitBoardName,String circuitBoardVersion){
		String newName=circuitBoardName+"_"+circuitBoardVersion;
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<String> manualName=circuitboardService.queryAllName(sapNo);
		JSONObject jsonObject=new JSONObject();
		String message="false";
		for (String name : manualName) {
			if(newName.equals(name)){
				message="true";
				break;
			}
		}
		
		try {
			jsonObject.put("isExit", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	/**
	 * 导入BOM单文件
	 * @param request
	 * @param cirDto
	 * @param myFile
	 * @return
	 */
	 @ResponseBody  
	 @RequestMapping(value = "/importCircuit",produces="application/json;charset=UTF-8")
	public String importCircuit(HttpServletRequest request,@RequestParam(value="circuitId")Integer circuitId,
			@RequestParam(value = "myFile")MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_COMPLETERBOARD_PATH);
		List<CircuitboardDetailDTO> mDtos=null;
		String newFileName=myFile[0].getOriginalFilename().substring(0,myFile[0].getOriginalFilename().lastIndexOf("."));
		if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename()) ){
			try {
				dyfileUtils.uploadFile(myFile, realpath,newFileName);
//				File file=new File(realpath+myFile[0].getOriginalFilename());
//				InputStream input=new FileInputStream(file);
				String filePath=realpath+myFile[0].getOriginalFilename();
				List<String[]> list=Excel.readExcelContent(filePath, 2);
				List<String> sapNoList=cirDetailService.queryAllByBoardId(circuitId);//查询已有物料信息，避免导入重复
				mDtos=new ArrayList<CircuitboardDetailDTO>();
				CircuitboardDetailDTO cirDetailDTO=null;
				MaterielDTO materielNo=null;
				String updateTime="";
				if(list!=null){
				for (String[] model : list) {//[3, 01.1604, 金标卡-检测主板, JinBiaoKa-V1.4, , , 25]
					if(StringUtils.isNotBlank(model[1]) && !sapNoList.contains(model[1])){
					materielNo=new MaterielDTO();
					materielNo.setMaterielNo(model[1].trim());
					int quantity=0;
					if(!model[6].trim().equals("")){
						 quantity=Integer.parseInt(model[6]);
					}
					updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
					cirDetailDTO=new CircuitboardDetailDTO(materielNo, model[5], quantity,updateTime);
					mDtos.add(cirDetailDTO);
					}
				}
				}
				 Tools.delFile(realpath+myFile[0].getOriginalFilename());
				
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
		}
		int count=0;
		if(mDtos!=null && mDtos.size()>0){
			for (CircuitboardDetailDTO circuitboardDetailDTO : mDtos) {//设置BOM单编号
				circuitboardDetailDTO.setCircuitId(circuitId);
			}
			count=cirDetailService.addList(mDtos);
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
		
	}
}
