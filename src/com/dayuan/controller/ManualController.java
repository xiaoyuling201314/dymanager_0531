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
import com.dayuan.dto.ManualDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.service.IManualService;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;

@Controller
@RequestMapping("/manualService")
public class ManualController {
	private Logger logger=Logger.getLogger(ManualController.class);
	@Autowired
  private IManualService manualService;
	@ResponseBody
	@RequestMapping(value="/deleteManual",produces="application/json;charset=UTF-8")
	public String deleteManual(HttpServletRequest request,Integer id,Integer curPage){
		ManualDTO manualDTO=manualService.queryById(id);
		int state=manualService.deleteById(id);
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_MANUAL_PATH);
		
		Tools.delFile(realpath+manualDTO.getFileName());//删除文件
		//重新查询数据
		PaginationData pData=new PaginationData();
		List<ManualDTO> listData=manualService.queryList(sapNo,null,curPage, pData.getPageSize());
		pData.setRecordCount(manualService.queryRecordCount(sapNo,null),pData.getPageSize());
		pData.setPageSize(pData.getPageSize());
		pData.setCurPage(curPage);
		//pData.setItemsData(listData);
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
	@RequestMapping("/downloadManual")
	public ResponseEntity<byte[]> downloadManual(HttpServletRequest request,
			Integer id){
		ManualDTO manualDTO=manualService.queryById(id);
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_MANUAL_PATH);
		 ResponseEntity<byte[]> responseEntity=dyfileUtils.download(request,realpath, manualDTO.getFileName());
		 if(responseEntity!=null){
			 return responseEntity;
		 }else{
			 return null;
		 }
	}
	
	@RequestMapping("/addManual")
	public String addManual(HttpServletRequest request,ManualDTO inDto,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_MANUAL_PATH);
		System.out.println("当前路径为：" + realpath);
		String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
		String newFileName=inDto.getFileName().trim()+"_"+inDto.getVersion().trim();
		int state;
		try {
			state = dyfileUtils.uploadFile(myFile, realpath,newFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		inDto.setSapNo(request.getSession().getAttribute("instruSapNo").toString());
		inDto.setFileName(newFileName+subfix);
		inDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		manualService.add(inDto);
		return "redirect:manualList.do";
		
	}
	@RequestMapping("/editManual")
	public String editManual(HttpServletRequest request,String id,@RequestParam("myFile") MultipartFile[] myFile){
		ManualDTO manualDTO=manualService.queryById(id);
		request.setAttribute("manualDTO", manualDTO);
		return "redirect:manualList.do";
		
	}
	
	@RequestMapping("/updateManual")
	public String updateManual(HttpServletRequest request,ManualDTO inDto,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_MANUAL_PATH);
		System.out.println("当前路径为：" + realpath);
		String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
		String newFileName=inDto.getFileName();
		try {
			 dyfileUtils.uploadFile(myFile, realpath,newFileName);
		} catch (IOException e) {
			logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
		}
		inDto.setSapNo(request.getSession().getAttribute("instruSapNo").toString());
		inDto.setFileName(newFileName+subfix);
		inDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		manualService.updateBySelective(inDto);
		return "redirect:manualList.do";
		
	}
	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyls
	 */
	@RequestMapping("/manualList")
	public ModelAndView manualList(HttpServletRequest request,String manualName,PaginationData pagData, Integer curPage){
		ModelAndView result=new ModelAndView("instruments_manual");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<ManualDTO> listData=manualService.queryList(sapNo,manualName,curPage, pagData.getPageSize());
		pData.setRecordCount(manualService.queryRecordCount(sapNo,manualName),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		result.addObject("nav", "instruments_manual");
		String realpath=dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_MANUAL_PATH;
		request.setAttribute("manualPath",realpath);
		request.setAttribute("manualName", manualName);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/checkFileVersion",produces="application/json;charset=UTF-8")
	public String checkFileVersion(HttpServletRequest request,String fileName,String version,String subfix){
		String newName=fileName+"_"+version+subfix;
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<String> manualName=manualService.queryAllName(sapNo);
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
