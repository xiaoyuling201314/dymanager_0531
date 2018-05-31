package com.dayuan.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.dayuan.bean.PaginationData;
import com.dayuan.dto.DocumentDTO;
import com.dayuan.dto.ManualDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.service.IDocumentService;
import com.dayuan.service.IManualService;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;

@Controller
@RequestMapping("/documentService")
public class DocumentController extends BaseController {
	private Logger logger=Logger.getLogger(DocumentController.class);
	@Autowired
	private IDocumentService documentService;
	
	@ResponseBody
	@RequestMapping(value="/deleteDocument",produces="application/json;charset=UTF-8")
	public String deleteDocument(HttpServletRequest request,Integer id,Integer curPage){
		DocumentDTO documentDTO=documentService.queryById(id);
		int state=documentService.deleteById(id);
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_DOCUMENT_PATH);
		Tools.delFile(realpath+documentDTO.getFileName());//删除文件
		//重新查询数据
		PaginationData pData=new PaginationData();
		List<DocumentDTO> listData=documentService.queryList(sapNo,null,curPage, pData.getPageSize());
		pData.setRecordCount(documentService.queryRecordCount(sapNo,null),pData.getPageSize());
		pData.setPageSize(pData.getPageSize());
		pData.setCurPage(curPage);
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		JSONArray jsonArray=new JSONArray(listData);
		return jsonArray.toString();
	}
	
	/**
	 * 下载说明书信息
	 * @param request
	 * @param fileName文件名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/downloadDocument")
	public ResponseEntity<byte[]> downloadDocument(HttpServletRequest request,
			Integer id){
		DocumentDTO documentDTO=documentService.queryById(id);
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_DOCUMENT_PATH);
		ResponseEntity<byte[]> responseEntity=dyfileUtils.download(request,realpath, documentDTO.getFileName());
		 if(responseEntity!=null){
			 return responseEntity;
		 }else{
			 return null;
		 }
	}
	
	@RequestMapping("/addDocument")
	public String addDocument(HttpServletRequest request,DocumentDTO docDto,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_DOCUMENT_PATH);
		System.out.println("当前路径为：" + realpath);
		String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
		String newFileName=docDto.getFileName().trim()+"_"+docDto.getVersion().trim();
		int state;
		try {
			state = dyfileUtils.uploadFile(myFile, realpath,newFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		docDto.setSapNo(request.getSession().getAttribute("instruSapNo").toString());
		docDto.setFileName(newFileName+subfix);
		docDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		documentService.add(docDto);
		return "redirect:documentList.do";
		
	}
	@RequestMapping("/editDocument")
	public String editDocument(HttpServletRequest request,String id,@RequestParam("myFile") MultipartFile[] myFile){
		DocumentDTO documentDTO=documentService.queryById(id);
		request.setAttribute("documentDTO", documentDTO);
		return "redirect:documentList.do";
		
	}
	
	@RequestMapping("/updateDocument")
	public String updateDocument(HttpServletRequest request,DocumentDTO docDto,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_DOCUMENT_PATH);
		System.out.println("当前路径为：" + realpath);
		String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
		String newFileName=docDto.getFileName()+"V"+docDto.getVersion();
		
		try {
			 dyfileUtils.uploadFile(myFile, realpath,newFileName);
		} catch (IOException e) {
			logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
		}
		docDto.setSapNo(request.getSession().getAttribute("instruSapNo").toString());
		docDto.setFileName(newFileName+subfix);
		docDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		documentService.updateBySelective(docDto);
		return "redirect:documentList.do";
		
	}
	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyls
	 */
	@RequestMapping("/documentList")
	public ModelAndView documentList(HttpServletRequest request,String documentName,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_document");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<DocumentDTO> listData=documentService.queryList(sapNo,documentName,curPage, pagData.getPageSize());
		pData.setRecordCount(documentService.queryRecordCount(sapNo,documentName),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		result.addObject("nav", "instruments_document");
		String realpath=dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_DOCUMENT_PATH;
		request.setAttribute("documentPath", realpath);
		request.setAttribute("documentName", documentName);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/checkFileVersion",produces="application/json;charset=UTF-8")
	public String checkFileVersion(HttpServletRequest request,String fileName,String version,String subfix){
		String newName=fileName+"_"+version+subfix;
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<String> manualName=documentService.queryAllName(sapNo);
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
