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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.Completemachine;
import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CircuitboardDTO;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.CompletemachineDTO;
import com.dayuan.dto.CompletemachinedetailDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.MaterielDrawingsDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.util.Excel;
import com.dayuan.util.ItextTools;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/completeService")
public class CompleteController extends BaseController {
	
 private Logger logger=Logger.getLogger(CompleteController.class);
   
   /**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/completeList")
	public ModelAndView completeList(HttpServletRequest request,String completeName,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_complete_bom");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<CompletemachineDTO> listData=comService.queryList(sapNo,completeName,curPage, pagData.getPageSize());
		pData.setRecordCount(comService.queryRecordCount(sapNo,completeName),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		request.setAttribute("completeName", completeName);
		result.addObject("nav", "instruments_complete_bom");
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/deleteComplete",produces="application/json;charset=UTF-8")
	public String  deleteComplete(HttpServletRequest request,Integer comBoardId){
		int state=comService.deleteById(comBoardId);
		System.out.println("执行结果为："+state);
		//重新查询数据记录
		PaginationData pData=new PaginationData();
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<CompletemachineDTO> listData=comService.queryList(sapNo,null,pData.getCurPage(), pData.getPageSize());
		pData.setRecordCount(comService.queryRecordCount(sapNo,null),pData.getPageSize());
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		JSONArray jsonArray=new JSONArray(listData);
		return jsonArray.toString();
	}
	/**
	 * 添加BOM单，包括上传文件
	 * @param request
	 * @param cirDto
	 * @param myFile
	 * @return
	 */
	 @ResponseBody  
	 @RequestMapping(value = "/addComplete",produces="application/json;charset=UTF-8")
	public String addComplete(HttpServletRequest request,CompletemachineDTO completerDTO,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_COMPLETERBOARD_PATH);
		List<CompletemachinedetailDTO> mDtos=null;
		if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename()) ){
			String newFileName=completerDTO.getCompleteMachineName().trim()+"_"+completerDTO.getCompleteMachineVersion().trim();
			String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
			try {
				dyfileUtils.uploadFile(myFile, realpath, newFileName);
//				File file=new File(realpath+newFileName+subfix);
//				InputStream input=new FileInputStream(file);
				String filePath=realpath+newFileName+subfix;
				List<String[]> list=Excel.readExcelContent(filePath, 2);
				 mDtos=new ArrayList<CompletemachinedetailDTO>();
				 CompletemachinedetailDTO comDto=null;
				MaterielDTO materielNo=null;
				String updateTime="";
				for (String[] model : list) {//[3, 01.1604, 金标卡-检测主板, JinBiaoKa-V1.4, , , 25]
					if(StringUtils.isNotBlank(model[1])){
					materielNo=new MaterielDTO();
					materielNo.setMaterielNo(model[1].trim());
					int quantity=0;
					if(!model[4].trim().equals("")){
						 quantity=Integer.parseInt(model[4]);
					}
					updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
					comDto=new CompletemachinedetailDTO(materielNo, quantity,updateTime);
					mDtos.add(comDto);
					}
				}
				 Tools.delFile(realpath+newFileName);
				
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
		}
		completerDTO.setSapNo(sapNo);
		completerDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		completerDTO.setCompleteMachineName(completerDTO.getCompleteMachineName().trim()+"_"+completerDTO.getCompleteMachineVersion().trim());
		int circuitId=comService.addBySelective(completerDTO);//返回序列号
		int count=0;
		if(mDtos!=null && mDtos.size()>0){
			for (CompletemachinedetailDTO cDto : mDtos) {//设置BOM单编号
				cDto.setCompleId(circuitId);
			}
			count=compdetailService.addList(mDtos);
		}
		if(count==-1){//物料导入失败，删除BOM单信息
			comService.deleteById(circuitId);
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
		//return "redirect:completeList.do";
		
	}
	 
	 	/**
		 * 导入BOM单文件
		 * @param request
		 * @param cirDto
		 * @param myFile
		 * @return
		 */
		 @ResponseBody  
		 @RequestMapping(value = "/importComplete",produces="application/json;charset=UTF-8")
		public String importComplete(HttpServletRequest request,@RequestParam(value="completeId")Integer completeId,
				@RequestParam(value = "myFile")MultipartFile[] myFile){
			String sapNo=(String) request.getSession().getAttribute("instruSapNo");
			String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_COMPLETERBOARD_PATH);
			List<CompletemachinedetailDTO> mDtos=null;
			String newFileName=myFile[0].getOriginalFilename().substring(0,myFile[0].getOriginalFilename().lastIndexOf("."));
			if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename()) ){
				try {
					dyfileUtils.uploadFile(myFile, realpath,newFileName);
//					File file=new File(realpath+myFile[0].getOriginalFilename());
//					InputStream input=new FileInputStream(file);
					String filePath=realpath+myFile[0].getOriginalFilename();
					List<String[]> list=Excel.readExcelContent(filePath, 2);
					List<String> sapNoList=compdetailService.queryAllByBoardId(completeId);//查询已有物料信息，避免导入重复
					 mDtos=new ArrayList<CompletemachinedetailDTO>();
					 CompletemachinedetailDTO comDto=null;
					MaterielDTO materielNo=null;
					String updateTime="";
					if(list!=null){
					for (String[] model : list) {//[3, 01.1604, 金标卡-检测主板, JinBiaoKa-V1.4, , , 25]
						if(StringUtils.isNotBlank(model[1]) && !sapNoList.contains(model[1])){
						materielNo=new MaterielDTO();
						materielNo.setMaterielNo(model[1].trim());
						int quantity=0;
						if(!model[4].trim().equals("")){
							 quantity=Integer.parseInt(model[4]);
						}
						updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
						comDto=new CompletemachinedetailDTO(materielNo, quantity,updateTime);
						mDtos.add(comDto);
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
				for (CompletemachinedetailDTO cDto : mDtos) {//设置BOM单编号
					cDto.setCompleId(completeId);
				}
				count=compdetailService.addList(mDtos);
			}
			Map<String, String> map=new HashMap<String, String>();
			map.put("count", String.valueOf(count));
			JSONObject jsonObject=new JSONObject(map);
			return jsonObject.toString();
			
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
	@RequestMapping("/selectComplete")//查看电路板BOM单详情
	public ModelAndView selectComplete(HttpServletRequest request,Integer id,String completeKeys,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_complete_bom_details");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<CompletemachinedetailDTO> boardList=compdetailService.queryByBoardId(id, completeKeys, curPage,pagData.getPageSize());
		pData.setRecordCount(compdetailService.queryRecordCount(id, completeKeys),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(boardList);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		CompletemachineDTO comDto=comService.queryById(id);
		request.setAttribute("comDto", comDto);
		request.setAttribute("picturePath",  dyfileUtils.MATERIEL_PICTURE_PATH);
		checkIsShow(request);//根据权限配置判断添加物料时新增物料按钮的显示与隐藏
		result.addObject("nav", "instruments_complete_bom");
		request.setAttribute("completeKeys", completeKeys);
		//查询物料信息
		PaginationData pDataMater=new PaginationData();
		 pDataMater.setCurPage(1);
		List<MaterielDTO> listData=materielService.queryList(null,pDataMater.getCurPage(), pDataMater.getPageSize());
		pDataMater.setRecordCount(materielService.queryRecordCount(null),pDataMater.getPageSize());
		pDataMater.setPageSize(pDataMater.getPageSize());
		pDataMater.setItemsData(listData);
		pDataMater.setStartIndex((pDataMater.getCurPage()-1)*pDataMater.getPageSize());
		request.setAttribute("pDataMateriel", pDataMater);
		return result;
		
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
	@ResponseBody
	@RequestMapping(value="/selectCompleteByCompleteKeys",produces="application/json;charset=UTF-8")//查看电路板BOM单详情
	public String selectCompleteByCompleteKeys(HttpServletRequest request,Integer id,String completeKeys,PaginationData pagData, Integer curPage){
		//ModelAndView result=new ModelAndView("instruments_complete_bom_details");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<CompletemachinedetailDTO> boardList=compdetailService.queryByBoardId(id, completeKeys, curPage,pagData.getPageSize());
		pData.setRecordCount(compdetailService.queryRecordCount(id, completeKeys),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(boardList);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		//request.setAttribute("pData", pData);
		CompletemachineDTO comDto=comService.queryById(id);
		//request.setAttribute("comDto", comDto);
		//request.setAttribute("picturePath",  dyfileUtils.MATERIEL_PICTURE_PATH);
		//checkIsShow(request);//根据权限配置判断添加物料时新增物料按钮的显示与隐藏
		//result.addObject("nav", "instruments_complete_bom");
		//request.setAttribute("completeKeys", completeKeys);
		JSONArray json=new JSONArray(boardList);
		return json.toString();
		
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
	@RequestMapping("/exportCompleteBoard")
	public ResponseEntity<byte[]> exportCompleteBoard(HttpServletRequest request,Integer compId,String type){
		 ResponseEntity<byte[]> responseEntity=null;
		 //1.BOm单号，详细信息
		 CompletemachineDTO comDto=comService.queryById(compId);
		 List<String> sapNoList=compdetailService.queryAllByBoardId(compId);
		 List<ExportCircuitBoard> list=null;
		 if(sapNoList.size()>0){
			  list=compdetailService.queryByCompleteIdList(compId);
		 }else{
			 list=new ArrayList<ExportCircuitBoard>();
		 }
		String []titleArray={"序号","物料编号","物料名称","型号规格","数量","备注"};//"位号",
		String []entityFieldNameArray={"id","materielNo","materielName","modelSpecification","quantity","comment"};//"materielTypeName",
		
		String []totalFieldNameArray=null;
		SysUserDTO sysUserDTO=(SysUserDTO) request.getSession().getAttribute("userSession");
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String outputFilePath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_COMPLETERBOARD_PATH);
		
		String fileName=comDto.getSapNo()+"-"+comDto.getCompleteMachineName();
		if(type.equals("excel")){
				int []celWidth={5000,5000,4500,4500,10000,15000};//10000,
				fileName+=".xls";
			Excel.outputExcelFile(null,fileName, titleArray, entityFieldNameArray,celWidth, list, totalFieldNameArray, outputFilePath+fileName);
		}else{
				fileName+=".pdf";
				try {
					String rootPath=request.getRealPath("/");
					ItextTools.createPdfDocument(rootPath,outputFilePath+fileName, titleArray, list,comDto.getSapNo()+"-"+comDto.getCompleteMachineName());
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
	//List<MaterielDTO> list=materielService.queryList(materielKeys, curPage, pageSize);
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
		//JSONArray json=new JSONArray(listData);
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
	public String deleteDetail(HttpServletRequest request,Integer comDetailId,Integer curPage){
		CompletemachinedetailDTO comDetail=compdetailService.queryById(comDetailId);
		int state=compdetailService.deleteById(comDetailId);//cDetailDTO
		PaginationData pData=new PaginationData();
		List<CompletemachinedetailDTO> boardList=compdetailService.queryByBoardId(comDetail.getCompleId(), null, curPage,pData.getPageSize());
		pData.setRecordCount(compdetailService.queryRecordCount(comDetail.getCompleId(), null),pData.getPageSize());
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
	public String addDetail(HttpServletRequest request,Integer completeId,String materNoList){
		System.out.println(completeId+materNoList);
		String [] array=materNoList.split(",");
		List<CompletemachinedetailDTO> list=new ArrayList<CompletemachinedetailDTO>();
		List<String> listMaterNo=compdetailService.queryAllByBoardId(completeId);
		MaterielDTO mater=null;
		CompletemachinedetailDTO comDetailDTO=null;
		String exitMaterNo="";
		for (int i = 0; i < array.length; i++) {
			if(listMaterNo.size()>0){
				if(!listMaterNo.contains(array[i])){
					comDetailDTO=new CompletemachinedetailDTO();
					comDetailDTO.setCompleId(completeId);
					mater=new MaterielDTO();
					mater.setMaterielNo(array[i]);
					comDetailDTO.setMaterielNo(mater);
					comDetailDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
					comDetailDTO.setQuantity(0);
					list.add(comDetailDTO);
				}else{
					if(exitMaterNo.equals("")){
						exitMaterNo+=array[i];
					}else{
						exitMaterNo+=","+array[i];
					}
				}
				
			}else{
				comDetailDTO=new CompletemachinedetailDTO();
				comDetailDTO.setCompleId(completeId);
				mater=new MaterielDTO();
				mater.setMaterielNo(array[i]);
				comDetailDTO.setMaterielNo(mater);
				comDetailDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
				comDetailDTO.setQuantity(0);
				list.add(comDetailDTO);
			}
			
		}
		compdetailService.addList(list);
		//重新查询详细信息，返回页面
		PaginationData pData=new PaginationData();
		List<CompletemachinedetailDTO> boardList=compdetailService.queryByBoardId(completeId, null, pData.getCurPage(),pData.getPageSize());
		pData.setRecordCount(compdetailService.queryRecordCount(completeId, null),pData.getPageSize());
		pData.setItemsData(boardList);
		request.setAttribute("pData", pData);
		CompletemachineDTO comDto2=comService.queryById(completeId);
		request.setAttribute("comDto", comDto2);
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
	public String updateDetail(HttpServletRequest request,Integer quantityNum,Integer completerId,String comment){
		CompletemachinedetailDTO comDetailDTO=compdetailService.queryById(completerId);
		comDetailDTO.setQuantity(quantityNum!=null ? quantityNum : null);
		comDetailDTO.setComment(comment!=null && !comment.trim().equals("") ? comment : null);
		comDetailDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		int state=compdetailService.updateBySelective(comDetailDTO);
		Map<String, String> map=new HashMap<String, String>();
		map.put("result", "success");
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
	}
	@ResponseBody
	@RequestMapping(value="/checkFileVersion",produces="application/json;charset=UTF-8")
	public String checkFileVersion(HttpServletRequest request,String completeMachineName,String completeMachineVersion){
		String newName=completeMachineName+"_"+completeMachineVersion;
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<String> manualName=comService.queryAllName(sapNo);
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
}
