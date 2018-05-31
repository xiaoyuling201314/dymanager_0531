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

import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.CompletemachineDTO;
import com.dayuan.dto.CompletemachinedetailDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.MaterielDrawingsDTO;
import com.dayuan.dto.PackingListDetailDTO;
import com.dayuan.dto.PackinglistDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.util.Excel;
import com.dayuan.util.ItextTools;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;
import com.lowagie.text.DocumentException;
import com.mysql.fabric.xmlrpc.base.Array;

@Controller
@RequestMapping("/packlingService")
public class PackinglistController extends BaseController {
	
 private Logger logger=Logger.getLogger(CompleteController.class);
   
   /**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/packingList")
	public ModelAndView packingList(HttpServletRequest request,String packingListName,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_plist");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<PackinglistDTO> listData=packingService.queryList(sapNo,packingListName,curPage, pagData.getPageSize());
		pData.setRecordCount(packingService.queryRecordCount(sapNo,packingListName),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		request.setAttribute("packingListName", packingListName);
		result.addObject("nav", "instruments_plist");
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/deletePacking",produces="application/json;charset=UTF-8")
	public String  deletePacking(HttpServletRequest request,Integer packListId){
		int state=packingService.deleteById(packListId);
		System.out.println("执行结果为："+state);
		//重新查询数据记录
		PaginationData pData=new PaginationData();
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<PackinglistDTO> listData=packingService.queryList(sapNo,null,pData.getCurPage(), pData.getPageSize());
		pData.setRecordCount(packingService.queryRecordCount(sapNo,null),pData.getPageSize());
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
	 @RequestMapping(value = "/addPacking",produces="application/json;charset=UTF-8")
	public String addPacking(HttpServletRequest request,PackinglistDTO packinglistDTO,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_PACKLISK_PATH);
		List<PackingListDetailDTO> pDtos=null;
		if(myFile.length>0 &&StringUtils.isNotBlank(myFile[0].getOriginalFilename())){
			String newFileName=packinglistDTO.getPackingListName().trim()+"_"+packinglistDTO.getPackingListVersion().trim();
			String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
			try {
				dyfileUtils.uploadFile(myFile, realpath, newFileName);
//				File file=new File(realpath+newFileName+subfix);
//				InputStream input=new FileInputStream(file);
				String filePath=realpath+newFileName+subfix;
				List<String[]> list=Excel.readExcelContent(filePath, 2);
				pDtos=new ArrayList<PackingListDetailDTO>();
				PackingListDetailDTO pDetailDto=null;
				MaterielDTO materielNo=null;
				String updateTime="";
				for (String[] model : list) {//[3, 01.1604, 金标卡-检测主板, JinBiaoKa-V1.4, , , 25]
					if(StringUtils.isNotBlank(model[1])){
					materielNo=new MaterielDTO();
					materielNo.setMaterielNo(model[1]);
					int quantity=0;
					if(!model[4].trim().equals("")){
						 quantity=Integer.parseInt(model[4]);
					}
					updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
					pDetailDto=new PackingListDetailDTO(materielNo, quantity,updateTime);
					pDtos.add(pDetailDto);
					}
				}
				 Tools.delFile(realpath+newFileName);
				
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
		}
		packinglistDTO.setSapNo(sapNo);
		packinglistDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		packinglistDTO.setPackingListName(packinglistDTO.getPackingListName().trim()+"_"+packinglistDTO.getPackingListVersion().trim());
		int circuitId=packingService.add(packinglistDTO);//返回序列号
		int count=0;
		if(pDtos!=null && pDtos.size()>0){
			for (PackingListDetailDTO pDto : pDtos) {//设置BOM单编号
				pDto.setPackId(circuitId);
			}
			count=paDetailService.addList(pDtos);
		}
		if(count==-1){//物料导入失败，删除BOM单信息
			packingService.deleteById(circuitId);
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
		//return "redirect:packingList.do";
		
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
	@RequestMapping("/selectPackList")//查看BOM单详情
	public ModelAndView selectPackList(HttpServletRequest request,Integer id,String packKeys,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_plist_details");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<PackingListDetailDTO> boardList=paDetailService.queryByBoardId(id, packKeys, curPage,pagData.getPageSize());
		pData.setRecordCount(paDetailService.queryRecordCount(id, packKeys),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(boardList);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		PackinglistDTO pDto=packingService.queryById(id);
		request.setAttribute("pDto", pDto);
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		request.setAttribute("picturePath",dyfileUtils.MATERIEL_PICTURE_PATH);
		checkIsShow(request);//根据权限配置判断添加物料时新增物料按钮的显示与隐藏
		result.addObject("nav", "instruments_plist");
		request.setAttribute("packKeys", packKeys);
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
	@RequestMapping("/exportPackingBoard")
	public ResponseEntity<byte[]> exportPackingBoard(HttpServletRequest request,Integer packpId,String type){
		 ResponseEntity<byte[]> responseEntity=null;
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realPath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_PACKLISK_PATH);
		 PackinglistDTO pDto=packingService.queryById(packpId);
		 List<String> sapNoList=paDetailService.queryAllByBoardId(packpId);
		 List<ExportCircuitBoard> list=null;
		 if(sapNoList.size()>0){
			  list=paDetailService.queryByPackIdList(packpId);
		 }else{
			 list=new ArrayList<ExportCircuitBoard>();
		 }
		
		 String []titleArray={"序号","物料编号","物料名称","型号规格","数量","备注"};
		 //SysUserDTO sysUserDTO=(SysUserDTO) request.getSession().getAttribute("userSession");
		 String fileName=pDto.getSapNo()+"-"+pDto.getPackingListName();
		 String wordTitle=pDto.getSapNo()+"-"+pDto.getPackingListName();
		 try {
			 String rootPath=request.getRealPath("/");
			 if(type.equals("doc")){
				 fileName+=".doc";
				 ItextTools.createWordDocument(rootPath,realPath+fileName, titleArray, list,wordTitle);
			 }else if(type.equals("pdf")){
				 fileName+=".pdf";
				 ItextTools.createPdfDocument(rootPath,realPath+fileName, titleArray, list,pDto.getSapNo()+"-"+pDto.getPackingListName());
			 }else{
				 fileName+=".xls";
				 String []entityFieldNameArray={"id","materielNo","materielName","modelSpecification","quantity","comment"};//"materielTypeName",
				 int []celWidth={5000,5000,4500,4500,10000,15000};//10000,
				 
				 Excel.outputExcelFile(null, pDto.getSapNo()+"-"+pDto.getPackingListName(), titleArray, entityFieldNameArray,celWidth, list, null, realPath+fileName);
			 }
			 responseEntity=dyfileUtils.download(request,realPath, fileName);
			 Tools.delFile(realPath+fileName);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 //1.BOm单号，详细信息
//		 PackinglistDTO pDto=packingService.queryById(packpId);
//		 List<String> sapNoList=paDetailService.queryAllByBoardId(packpId);
//		List<ExportCircuitBoard> list=paDetailService.queryBySapNoList(sapNoList);
//		String []titleArray={"序号","物料编号","物料名称","型号规格","数量","备注"};//"位号",
//		String []entityFieldNameArray={"id","materielNo","materielName","modelSpecification","quantity","comment"};//"materielTypeName",
//		
//		String []totalFieldNameArray=null;
//		SysUserDTO sysUserDTO=(SysUserDTO) request.getSession().getAttribute("userSession");
//		String outputFilePath=getrealPath(request, "packList.path");
//		int state=Tools.createFolder(outputFilePath);;
//		String fileName="123_completerBoard.xls";
//		int []celWidth={5000,5000,4500,4500,10000,15000};//10000,
//		if(state==0){//目录创建成功后执行文件生成和下载操作
//			Excel.outputExcelFile(null, "食安科技----物料列表", titleArray, entityFieldNameArray,celWidth, list, totalFieldNameArray, outputFilePath+fileName);
//			 responseEntity=dyfileUtils.download(request,outputFilePath, fileName);
//				 Tools.delFile(outputFilePath+fileName);
//		}
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
	public String deleteDetail(HttpServletRequest request,Integer packDetailId,Integer curPage){
		PackingListDetailDTO packDetail=paDetailService.queryById(packDetailId);
		int state=paDetailService.deleteById(packDetailId);//cDetailDTO
		PaginationData pData=new PaginationData();
		List<PackingListDetailDTO> boardList=paDetailService.queryByBoardId(packDetail.getPackId(), null, curPage,pData.getPageSize());
		pData.setRecordCount(paDetailService.queryRecordCount(packDetail.getPackId(), null),pData.getPageSize());
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
	public String addDetail(HttpServletRequest request,Integer packId,String materNoList){
		System.out.println(packId+materNoList);
		String [] array=materNoList.split(",");
		List<PackingListDetailDTO> list=new ArrayList<PackingListDetailDTO>();
		List<String> listMaterNo=paDetailService.queryAllByBoardId(packId);
		MaterielDTO mater=null;
		String exitMaterNo="";
		PackingListDetailDTO packDetailDTO=null;
		for (int i = 0; i < array.length; i++) {
			if(listMaterNo.size()>0){
				if(!listMaterNo.contains(array[i])){
					packDetailDTO=new PackingListDetailDTO();
					packDetailDTO.setPackId(packId);
					mater=new MaterielDTO();
					mater.setMaterielNo(array[i]);
					packDetailDTO.setMaterielNo(mater);
					packDetailDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
					packDetailDTO.setQuantity(0);
					list.add(packDetailDTO);
				}else{
					if(exitMaterNo.equals("")){
						exitMaterNo+=array[i];
					}else{
						exitMaterNo+=","+array[i];
					}
				}
				
			}else{
				packDetailDTO=new PackingListDetailDTO();
				packDetailDTO.setPackId(packId);
				mater=new MaterielDTO();
				mater.setMaterielNo(array[i]);
				packDetailDTO.setMaterielNo(mater);
				packDetailDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
				packDetailDTO.setQuantity(0);
				list.add(packDetailDTO);
			}

		}
		paDetailService.addList(list);
		//重新查询详细信息，返回页面
		PaginationData pData=new PaginationData();
		List<PackingListDetailDTO> boardList=paDetailService.queryByBoardId(packId, null, pData.getCurPage(),pData.getPageSize());
		pData.setRecordCount(paDetailService.queryRecordCount(packId, null),pData.getPageSize());
		pData.setItemsData(boardList);
		request.setAttribute("pData", pData);
		PackinglistDTO cirDto2=packingService.queryById(packId);
		request.setAttribute("packDto", cirDto2);
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
	public String updateDetail(HttpServletRequest request,Integer quantityNum,Integer packId,String comment){
		PackingListDetailDTO packDetailDTO=paDetailService.queryById(packId);
		packDetailDTO.setQuantity(quantityNum!=null ? quantityNum : null);
		packDetailDTO.setComment(comment!=null && !comment.trim().equals("") ? comment : null);
		packDetailDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		int state=paDetailService.updateBySelective(packDetailDTO);
		Map<String, String> map=new HashMap<String, String>();
		map.put("result", "success");
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
	}
	@ResponseBody
	@RequestMapping(value="/checkFileVersion",produces="application/json;charset=UTF-8")
	public String checkFileVersion(HttpServletRequest request,String packingListName,String packingListVersion){
		String newName=packingListName+"_"+packingListVersion;
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<String> manualName=packingService.queryAllName(sapNo);
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
	 @RequestMapping(value = "/importPacking",produces="application/json;charset=UTF-8")
	public String importPacking(HttpServletRequest request,@RequestParam(value="packId")Integer packId,
			@RequestParam(value = "myFile")MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_COMPLETERBOARD_PATH);
		List<PackingListDetailDTO> pDtos=null;
		String newFileName=myFile[0].getOriginalFilename().substring(0,myFile[0].getOriginalFilename().lastIndexOf("."));
		if(myFile.length>0 && StringUtils.isNotBlank(myFile[0].getOriginalFilename()) ){
			try {
				dyfileUtils.uploadFile(myFile, realpath,newFileName);
//				File file=new File(realpath+myFile[0].getOriginalFilename());
//				InputStream input=new FileInputStream(file);
				String filePath=realpath+myFile[0].getOriginalFilename();
				List<String[]> list=Excel.readExcelContent(filePath, 2);
				List<String> sapNoList=paDetailService.queryAllByBoardId(packId);//查询已有物料信息，避免导入重复
				pDtos=new ArrayList<PackingListDetailDTO>();
				PackingListDetailDTO pDetailDto=null;
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
					pDetailDto=new PackingListDetailDTO(materielNo, quantity,updateTime);
					pDtos.add(pDetailDto);
					}
				}
				}
				 Tools.delFile(realpath+myFile[0].getOriginalFilename());
				
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
		}
		int count=0;
		if(pDtos!=null && pDtos.size()>0){
			for (PackingListDetailDTO pDto : pDtos) {//设置BOM单编号
				pDto.setPackId(packId);
			}
			count=paDetailService.addList(pDtos);
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
		
	}
}
