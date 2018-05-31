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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.dayuan.bean.Certificate;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CertificateDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.service.InstrumentinfoService;
import com.dayuan.util.FileBean;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;
@Controller
@RequestMapping("/instrumentService")
public class InstrumentinfoController extends BaseController {
	
	private Logger logger=Logger.getLogger(InstrumentinfoController.class);
	
	// 处理时间的方法
		@InitBinder
		public void initBinder(ServletRequestDataBinder bin) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			CustomDateEditor cust = new CustomDateEditor(sdf, true);
			bin.registerCustomEditor(Date.class, cust);
		}
	/**
	 * 查看仪器资质证书信息
	 * @param request
	 * @param sapNo
	 * @return
	 */
	@RequestMapping("/queryInstrument")
	public ModelAndView queryInstrument(HttpServletRequest request,String sapNo,Integer curPage){
	InstrumentinfoDTO insDto=insService.queryById(sapNo);
	ModelAndView result=new ModelAndView("instruments_qualification");
	System.out.println(insDto.getProductName());
	PaginationData pData=new PaginationData();
	if (curPage == null) {
		curPage = pData.getCurPage();
	}
	List<CertificateDTO> list=certificateService.queryList(sapNo, pData.getCurPage(), pData.getPageSize());
	pData.setRecordCount(certificateService.queryRecordCount(sapNo),pData.getPageSize());
	pData.setCurPage(curPage);
	pData.setItemsData(list);	
	request.setAttribute("instrumentDTO", insDto);
	request.setAttribute("pData", pData);
	request.getSession().setAttribute("instruSapNo", insDto.getSapNo());
	String realPath=dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_CERTIFICATE_PATH;
	request.setAttribute("certificatePath", realPath);
	realPath=dyfileUtils.ROOTPATH+sapNo+dyfileUtils.INSTRU_IMPLEMENTATION_STANDARDS;
	request.setAttribute("implementStandards", realPath);
	request.setAttribute("picturePath",  dyfileUtils.INSTRU_PICTURE_PATH);
	result.addObject("nav", "instruments_qualification");
	return result;//certificateService/instruList
	}

	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/instruList")
	public ModelAndView instruList(HttpServletRequest request,String instruMentName,PaginationData pagData, Integer curPage){
		PaginationData pData=new PaginationData();
		ModelAndView result = new ModelAndView("instruments");
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		pData.setPageSize(4);
		List<InstrumentinfoDTO> listData=insService.queryList(instruMentName,curPage, pData.getPageSize());
		pData.setRecordCount(insService.queryRecordCount(instruMentName),pData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pData.getPageSize());
		pData.setItemsData(listData);
		request.setAttribute("pData", pData);
		request.setAttribute("instruMentName", instruMentName);
		request.setAttribute("picturePath",  dyfileUtils.INSTRU_PICTURE_PATH);
		result.addObject("nav", "instruments");  //导航高亮
		request.getSession().setAttribute("returnPage", curPage);
		return result;
	}
	/**
	 * 1.先查询记录数据
	 * 2.删除该数据
	 * 3.删除图片
	 * @param request
	 * @param sapNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteInstru",produces="application/json;charset=UTF-8")
	public String deleteInstru(HttpServletRequest request,String sapNo){
		InstrumentinfoDTO inDto=insService.queryById(sapNo);
		int state=insService.deleteById(sapNo);
		System.out.println("执行结果为："+state);
		//删除图片
		String picture=inDto.getPicture();
		String rootPath=request.getRealPath("/");
		if(StringUtils.isNotBlank(picture)){
			String [] array=picture.split(",");
			for(String pic:array){
				Tools.delFile(rootPath+dyfileUtils.INSTRU_PICTURE_PATH+pic);
			}
		}
		//删除文件
		System.out.println(rootPath+dyfileUtils.ROOTPATH+inDto.getSapNo());
		Tools.delFolder(rootPath+dyfileUtils.ROOTPATH+inDto.getSapNo());
		PaginationData pData=new PaginationData();
		pData.setPageSize(4);
		List<InstrumentinfoDTO> listData=insService.queryList(null,pData.getCurPage(), pData.getPageSize());
		pData.setRecordCount(insService.queryRecordCount(null),pData.getPageSize());
		request.setAttribute("pData", pData);
		JSONArray jsonArray=new JSONArray(listData);
		return jsonArray.toString();
		
	}
	/**
	 * 修改仪器信息
	 * 1.修改仪器信息
	 * 2.删除仪器图片
	 * @param request
	 * @param inDto
	 * @param myFile
	 * @return
	 */
	@RequestMapping("/updateInstru")
	public String updateInstru(HttpServletRequest request,InstrumentinfoDTO inDto,@RequestParam("myFile") MultipartFile[] myFile,String delpicture){
		inDto.setExecutionStandard("");
		inDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		if(StringUtils.isBlank(inDto.getListedTime())){
			inDto.setListedTime(null);
		}
		if(StringUtils.isBlank(inDto.getDelistingDate())){
			inDto.setDelistingDate(null);
		}

		if(myFile.length>0 &&StringUtils.isNotBlank(myFile[0].getOriginalFilename())){//上传执行标准文件
			String newFileName=inDto.getSapNo().trim()+"_"+ inDto.getExecutionStandard().trim();
			String realpath=dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+inDto.getSapNo()+dyfileUtils.INSTRU_IMPLEMENTATION_STANDARDS);
			String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
			try {
				int state = dyfileUtils.uploadFile(myFile, realpath,newFileName);
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
			
			inDto.setExecutionStandard(newFileName+subfix);
		}
		insService.updateBySelective(inDto);
		// 删除图片
		if (delpicture != null) {
			String[] delPic = delpicture.split(",");
			//String realPath=dyfileUtils.ROOTPATH+dyfileUtils.INSTRU_PICTURE_PATH;
			String realPath = dyfileUtils.getrealPath(request,dyfileUtils.INSTRU_PICTURE_PATH);
			for (int i = 0; i < delPic.length; i++) {
				Tools.delFile(realPath + delPic[i]);
			}
		}
		return "redirect:queryInstrument.do?sapNo="+inDto.getSapNo();
		
	}
	
	/**
	 * 新增仪器信息
	 * @param request
	 * @param inDto
	 * @param myFile
	 * @return
	 */
	@RequestMapping("/addInstru")
	public String addInstru(HttpServletRequest request,InstrumentinfoDTO inDto,@RequestParam("myFile") MultipartFile[] myFile){

		if(myFile.length>0 &&StringUtils.isNotBlank(myFile[0].getOriginalFilename())){//上传执行标准文件
			String newFileName=inDto.getProductName().trim()+"_"+ inDto.getExecutionStandard().trim();
			String realpath = dyfileUtils.getrealPath(request, dyfileUtils.ROOTPATH+ inDto.getSapNo()+ dyfileUtils.INSTRU_IMPLEMENTATION_STANDARDS);
			String subfix=myFile[0].getOriginalFilename().substring(myFile[0].getOriginalFilename().lastIndexOf('.'), myFile[0].getOriginalFilename().length());
			try {
				int state = dyfileUtils.uploadFile(myFile, realpath,newFileName);
			} catch (IOException e) {
				logger.error("文件上传失败,错误消息："+e.getMessage()+"发生错误代码信息"+e.getStackTrace());
			}
			inDto.setExecutionStandard(newFileName+subfix);
		}
		inDto.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));//, "yyyy-MM-dd"
		if(StringUtils.isBlank(inDto.getListedTime())){
			inDto.setListedTime(null);
		}
		if(StringUtils.isBlank(inDto.getDelistingDate())){
			inDto.setDelistingDate(null);
		}
		else{
			inDto.setState(0);//设置状态为退市
		}
		insService.addBySelective(inDto);
		return "redirect:instruList.do";
		
	}
	
	/**
	 * 查询仪器信息，进入编辑页面
	 * @param request
	 * @param sapNo
	 * @return
	 */
	@RequestMapping("/editInstru")
	public String editInstru(HttpServletRequest request,String sapNo){
		InstrumentinfoDTO insDto=insService.queryById(sapNo);
		request.setAttribute("instrumentDTO", insDto);
		request.setAttribute("picturePath", dyfileUtils.INSTRU_PICTURE_PATH);
		if(StringUtils.isNotBlank(insDto.getPicture())){
			int countPicture=insDto.getPicture().split(",").length;
			request.setAttribute("countPicture",4-countPicture!=0?4-countPicture:-1 );
		}
		return "instruments_edit";
	}
	
	/**
	 * 上传仪器图片
	 * @param request
	 * @param response
	 * @param myFile
	 * @return
	 */
	  @ResponseBody  
	  @RequestMapping(value = "/uploadPicture",produces="application/json;charset=UTF-8")  
	  public String uploadPicture(HttpServletRequest request,HttpServletResponse response, @RequestParam("myFile") MultipartFile[] myFile,String sapNo) {
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
			String realpath =dyfileUtils.getrealPath(request, dyfileUtils.INSTRU_PICTURE_PATH);
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
	  @ResponseBody  
	  @RequestMapping(value = "/querySapNo",produces="application/json;charset=UTF-8")  
	  public String querySapNo(String sapNo){
		  JSONObject jsonObject=new JSONObject();
		  InstrumentinfoDTO inDto=insService.queryById(sapNo);
		  if(inDto!=null){
			  try {
				jsonObject.put("result", "exitSapNo");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		  }
		  return jsonObject.toString();
	  }
	  @ResponseBody  
	  @RequestMapping(value = "/checkInstruBySapNo",produces="application/json;charset=UTF-8")  
	  public String checkInstruBySapNo(String sapNo){
		  JSONObject jsonObject=new JSONObject();
		  InstrumentinfoDTO inDto=insService.queryById(sapNo);
		  if(inDto!=null){
			  try {
				jsonObject.put("productName", inDto.getProductName());
				jsonObject.put("result", "true");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		  }
		  return jsonObject.toString();
	  }
}
