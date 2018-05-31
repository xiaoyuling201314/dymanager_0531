package com.dayuan.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CertificateDTO;
import com.dayuan.dto.CertificateTypeDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.dto.ManualDTO;
import com.dayuan.service.ICertificateService;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;

@Controller
@RequestMapping("/certificateService")
public class CertificateController extends BaseController {
   private Logger logger=Logger.getLogger(CertificateController.class);
	@Autowired(required=false)
	private ICertificateService certificateService;
	//查询资质信息
	@RequestMapping("/queryCertificate")
	public ModelAndView queryCertificate(Model model,Integer id,String sapNo,String typeStr){
		ModelAndView result=null;
		String page="";
		List<CertificateTypeDTO> list=cerTypeService.queryAllType();
		model.addAttribute("cerTypeDTO", list);
		model.addAttribute("instruSapNo", sapNo);
		if(typeStr==null){
		result=new ModelAndView("instruments_qualification_patent_add");
		}else if(typeStr!=null && typeStr.equals("edit")){
			CertificateDTO certificateDTO=certificateService.queryById(id);
			model.addAttribute("certificateDTO", certificateDTO);
			result=new ModelAndView("instruments_qualification_patent_edit");
		}
		result.addObject("nav", "instruments_qualification");
		return result;
	}
	//新增资质信息
	@RequestMapping("/addCertficate")
	public String addCertficate(HttpServletRequest request,CertificateDTO certificateDTO,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_CERTIFICATE_PATH);
		String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
		int state;
		try {
			state = dyfileUtils.uploadFile(myFile, realpath,certificateDTO.getCalibrationCertificate());
		} catch (IOException e) {
			logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
		}
		certificateDTO.setCalibrationCertificate(certificateDTO.getCalibrationCertificate()+subfix);
		certificateDTO.setUpdateTime(Tools.getDateTimeString(new Date(), "yyyy-MM-dd"));
		certificateDTO.setSapNo(sapNo);
		if(StringUtils.isBlank(certificateDTO.getCalibEndTime())){
			certificateDTO.setCalibEndTime(null);
		}
		certificateService.addBySelective(certificateDTO);
		return "redirect:/instrumentService/queryInstrument.do?sapNo="+certificateDTO.getSapNo();
		
	}
	/**
	 * 更新资质证书
	 * @param request
	 * @param certificateDTO
	 * @param myFile
	 * @return
	 */
	@RequestMapping("/updateCert")
	public String updateCert(HttpServletRequest request,CertificateDTO certificateDTO,@RequestParam("myFile") MultipartFile[] myFile){
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_CERTIFICATE_PATH);
		System.out.println("当前路径为：" + realpath);
		String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
//		String calibrationCertificate="";
//		if(certificateDTO.getQualificationAttribution().equals("达元")){
//			calibrationCertificate+="GZDY-";
//		}else  if(certificateDTO.getQualificationAttribution().equals("绿洲")){
//			calibrationCertificate+="LZ-";
//		}else  if(certificateDTO.getQualificationAttribution().equals("天绿")){
//			calibrationCertificate+="TL-";
//		}
		try {
			 dyfileUtils.uploadFile(myFile, realpath,certificateDTO.getCalibrationCertificate());
		} catch (IOException e) {
			System.out.println(""+e.getMessage());
		}
		certificateDTO.setCalibrationCertificate(certificateDTO.getCalibrationCertificate()+subfix);
		certificateDTO.setUpdateTime(Tools.getDateTimeString(new Date(), "yyyy-MM-dd"));
		certificateService.updateBySelective(certificateDTO);
		certificateDTO.setCertificateType(certificateDTO.getCertificateType());
		return "redirect:/instrumentService/queryInstrument.do?sapNo="+certificateDTO.getSapNo();
		
	}
	/**
	 * 删除仪器资质
	 * 1.查询该资质记录
	 * 2.删除数据
	 * 3.删除相关的资质文件
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteCert",produces="application/json;charset=UTF-8")
	public String deleteCert(HttpServletRequest request,Integer id){
		CertificateDTO certificateDTO=certificateService.queryById(id);
		int state=certificateService.deleteById(id);
		System.out.println("执行结果为："+state);
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_CERTIFICATE_PATH);
		Tools.delFile(realpath+certificateDTO.getCalibrationCertificate());//删除文件
		PaginationData pData=new PaginationData();
		List<CertificateDTO> list=certificateService.queryList(sapNo, pData.getCurPage(), pData.getPageSize());
		pData.setRecordCount(certificateService.queryRecordCount(sapNo),pData.getPageSize());
		request.setAttribute("pData", pData);
		JSONArray jsonArray=new JSONArray(list);
		return jsonArray.toString();
		
	}
	/**
	 * 下载资质证书
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/downloadCert")
	public ResponseEntity<byte[]> downloadCert(HttpServletRequest request,HttpServletResponse response,Integer id) throws UnsupportedEncodingException{
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_CERTIFICATE_PATH);
		CertificateDTO certificateDTO=certificateService.queryById(id);
		String fileName=certificateDTO.getCalibrationCertificate();
//		if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>0){
//			fileName=URLEncoder.encode(fileName, "UTF-8");
//		}else{
//			fileName=new String(fileName.getBytes("UTF-8"), "ISO8859-1");
//		}
		ResponseEntity<byte[]> responseEntity=dyfileUtils.download(request,realpath, fileName);
		 if(responseEntity!=null){
			 return responseEntity;
		 }else{
			 return null;
		 }
		
	}
	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/certificateList")
	public String certificateList(HttpServletRequest request,String sapNo,PaginationData pagData, Integer curPage){//,String userName,int curPage,int pageSize
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<CertificateDTO> listData=certificateService.queryList(sapNo,curPage, pagData.getPageSize());
		pData.setRecordCount(certificateService.queryRecordCount(sapNo),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		request.setAttribute("pData", pData);
		return "instruments_qualification";
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}
	@ResponseBody
	@RequestMapping(value="/checkFileVersion",produces="application/json;charset=UTF-8")
	public String checkFileVersion(HttpServletRequest request,String calibrationCertificate,String subfix){
		String newName=calibrationCertificate+subfix;
		String sapNo=(String) request.getSession().getAttribute("instruSapNo");
		List<String> manualName=certificateService.queryAllName(sapNo);
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
