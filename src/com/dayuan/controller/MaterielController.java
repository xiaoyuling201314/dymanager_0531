package com.dayuan.controller;

import java.io.IOException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.PaginationData;
import com.dayuan.dto.MaterieTypeDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.MaterielDrawingsDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.util.Excel;
import com.dayuan.util.FileBean;
import com.dayuan.util.Tools;
import com.dayuan.util.dyfileUtils;

@Controller
@RequestMapping("/materielService")
public class MaterielController extends BaseController{//extends BaseController
	private Logger logger=Logger.getLogger(MaterielController.class);
	/**
	 * 添加物料信息
	 * 1.添加物料信息
	 * 2.添加图纸信息
	 * @param request
	 * @param materielDTO
	 * @param materDraws
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addMateriel")
	public String addMateriel(HttpServletRequest request,MaterielDTO materielDTO,String [] version){//,@RequestParam("myFile") MultipartFile[] myFile
		materielDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		materielDTO.setModelSpecification(materielDTO.getModelSpecification().replace(" ", "&nbsp;"));
		materielService.add(materielDTO);
		//新增物料图纸信息
		List<MaterielDrawingsDTO> list=materielDTO.getDrawings();
		List<MaterielDrawingsDTO> addList=null;
		if(list!=null && list.size()>0){
			addList=new ArrayList<MaterielDrawingsDTO>();
			for (MaterielDrawingsDTO materielDrawingsDTO : list) {
				if(StringUtils.isNotBlank(materielDrawingsDTO.getDrawingName())){
					materielDrawingsDTO.setMaterielNo(materielDTO.getMaterielNo());
					materielDrawingsDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
					addList.add(materielDrawingsDTO);
				}
			}
			maDrawingsServicce.addList(addList);
		}
		return "redirect:materielList.do";
	}
	/**
	 * 上传物料图片
	 * @param request
	 * @param response
	 * @param myFile
	 * @return
	 */
	  @ResponseBody  
	  @RequestMapping(value = "/uploadMater",produces="application/json;charset=UTF-8")  
	  public String uploadMater(HttpServletRequest request,HttpServletResponse response, @RequestParam("myFile") MultipartFile[] myFile) {
		 List<FileBean> fileRealPath =new ArrayList<FileBean>();
		 FileBean fBean=null;
		 //创建一个通用的多部分解析器 
		Map<String, Object> map=new HashMap<String, Object>();
		 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断 request 是否有文件上传,即多部分请求  
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 转换成多部分request
			Iterator<String> iter = multiRequest.getFileNames();// 取得request中的所有文件名
			String absolutePath = dyfileUtils.getrealPath(request, dyfileUtils.MATERIEL_PICTURE_PATH);
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
							dyfileUtils.uploadImageFile(file, absolutePath, myFileName);//上传文件
							absolutePath += myFileName;
							fBean.setUrl(absolutePath);//设置图片绝对路径
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
	
	  /**
	 * 删除整个物料信息，包括物料图纸信息
	 * @param materielNo
	 * @return
	 */
	 @ResponseBody
	@RequestMapping(value="/deleteMateriel",produces="application/json;charset=UTF-8")
	public String deleteMateriel(HttpServletRequest request,String materielNo,Integer curPage){
		MaterielDTO materielDTO=materielService.queryById(materielNo);
		 int count=materielService.deleteById(materielNo);//"01.1601"
		System.out.println("执行结果为："+count);
		//删除文件
		String rootPath=request.getRealPath("/");
		//String realPath=dyfileUtils.getrealPath(request,dyfileUtils.ROOTPATH+dyfileUtils.MATERIEL_DRAWSING_PATH+materielNo);
		String pictList=materielDTO.getPicture();
		if(count>0){
			if(StringUtils.isNotBlank(pictList)){
				String [] picture=pictList.split(",");
				for (int i = 0; i < picture.length; i++) {
					Tools.delFile(rootPath+dyfileUtils.MATERIEL_PICTURE_PATH+picture[i]);//删除图片信息
				}
			}
			Tools.delFolder(rootPath+dyfileUtils.ROOTPATH+dyfileUtils.MATERIEL_DRAWSING_PATH+materielNo);
			//Tools.deleteFolder(realPath);//删除图纸信息
		}
		//重新查询物料信息
		PaginationData pData=new PaginationData();
		List<MaterielDTO> listData=materielService.queryList(null,curPage, pData.getPageSize());
		pData.setRecordCount(materielService.queryRecordCount(null),pData.getPageSize());
		pData.setPageSize(pData.getPageSize());
		//pData.setItemsData(listData);
		pData.setCurPage(curPage);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		JSONArray jsonArray=new JSONArray(listData);
		return jsonArray.toString();
	}
	 
	/**
	 * 物料列表查询
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/materielList")
	public ModelAndView materielList(HttpServletRequest request,String materielName,PaginationData pagData, Integer curPage){//,String userName,int curPage,int pageSize
		ModelAndView result = new ModelAndView("materials");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<MaterielDTO> listData=materielService.queryList(materielName,curPage, pagData.getPageSize());
		pData.setRecordCount(materielService.queryRecordCount(materielName),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		request.setAttribute("materielName", materielName);
		result.addObject("nav", "materials");  //导航高亮
		request.setAttribute("picturePath",  dyfileUtils.MATERIEL_PICTURE_PATH);
		return result;
	}
	
	/**
	 * 查看物料详细信息
	 * 1.查询物料类型
	 * 2.修改物料信息
	 * 3.查找物料图纸信息
	 * @param request
	 * @param materielNo
	 * @return
	 */
	@RequestMapping("selectDetail")
	 public String selectDetail(HttpServletRequest request,String materielNo){
		MaterielDTO materielDTO=materielService.queryById(materielNo);
		List<MaterieTypeDTO> materTypeList=materieTypeService.queryAllType();
		request.setAttribute("materTypeList", materTypeList);
//		if(StringUtils.isNotBlank(materielDTO.getPicture())){
//			int countPicture=materielDTO.getPicture().split(",").length;
//			request.setAttribute("countPicture",4-countPicture!=0?4-countPicture:-1 );
//		}
		request.setAttribute("materielDTO", materielDTO);
		request.setAttribute("picturePath",  dyfileUtils.MATERIEL_PICTURE_PATH);
		request.setAttribute("drawsCount",  materielDTO.getDrawings().size());
		return "materials_edit";
	}
	
	/**
	 * 修改物料详细信息
	 * 1.查询物料类型
	 * 2.修改物料信息
	 * 3.修改图纸信息
	 * @param request oldPicture//原有的图片信息
	 * @param materielNo
	 * @return
	 */
	@RequestMapping("/updateMateriel")
	 public String updateMateriel(HttpServletRequest request,MaterielDTO materielDTO,String delpicture){//,String oldPicture
		//materielDTO.setPicture(oldPicture+","+materielDTO.getPicture());
		materielDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		materielDTO.setModelSpecification(materielDTO.getModelSpecification().replace(" ", "&nbsp;"));
		int state=materielService.updateBySelective(materielDTO);
		
//		List<MaterielDrawingsDTO> addList=null;
//		List<MaterielDrawingsDTO> list=materielDTO.getDrawings();
//		if(materielDTO.getDrawings()!=null && materielDTO.getDrawings().size()>0){
//				addList=new ArrayList<MaterielDrawingsDTO>();
//				for (MaterielDrawingsDTO materielDrawingsDTO : list) {
//					if(StringUtils.isNotBlank(materielDrawingsDTO.getDrawingName())){
//						materielDrawingsDTO.setMaterielNo(materielDTO.getMaterielNo());
//						materielDrawingsDTO.setUpdateTime(Tools.getDateString(new Date()));
//						addList.add(materielDrawingsDTO);
//					}
//				}
//				maDrawingsServicce.addList(addList);
//		}
		
		//删除图片
		if(delpicture!=null){
			 String [] delPic=delpicture.split(",");
			String realpath = dyfileUtils.getrealPath(request, dyfileUtils.MATERIEL_PICTURE_PATH);
			for (int i = 0; i < delPic.length; i++) {
				 Tools.delFile(realpath+delPic[i]);
			}
		}
		//删除图纸
		
		 System.out.println("操作结果为："+state);
		 return "redirect:materielList.do";
	}
	
	/**
	 * 导出物料信息
	 * 1.查询所有的物料信息
	 * 2.生成excel文件
	 * 3.下载excel文件完成导出
	 * 4.删除该文件
	 * @param request
	 * @param fileName文件名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/exportMateriel")
	public ResponseEntity<byte[]> exportMateriel(HttpServletRequest request,String materielName){
		 ResponseEntity<byte[]> responseEntity=null;
		List<MaterielDTO> list=materielService.queryAll(materielName);
		List<MaterielDrawingsDTO> liDrawingsDTOs=null;
		for (MaterielDTO materielDTO : list) {
		String materielNo=materielDTO.getMaterielNo();
		liDrawingsDTOs=maDrawingsServicce.queryDrawsById(materielNo);
		materielDTO.setDrawings(liDrawingsDTOs);
		materielDTO.setDrawingsStr(materielDTO.getDrawingsStr());
		System.out.println("图纸列表："+materielDTO.getDrawingsStr());
		}//str+=maDrawingsDTO.getDrawingName()+"-"+maDrawingsDTO.getVersion()+"-"+maDrawingsDTO.getReviser()+"-"+maDrawingsDTO.getRevisedRecord()+"="+maDrawingsDTO.getUpdateTime()
		String []titleArray={"序号","物料编号","物料类型","物料名称","型号规格","封装","图片","图纸名称--版本--修订人--修订记录--修订时间","备注"};//"materielTypeName",
		String []entityFieldNameArray={"id","materielNo","mtypeName","materielName","modelSpecification","footprint","picture","drawingsStr","comment"};//"materielTypeName",
		String []totalFieldNameArray=null;
		SysUserDTO sysUserDTO=(SysUserDTO) request.getSession().getAttribute("userSession");
		String outputFilePath=dyfileUtils.getrealPath(request, dyfileUtils.MATERIEL_PICTURE_PATH);
		int state=Tools.createFolder(outputFilePath);;
		String fileName="物料列表"+Tools.getDateString(new Date())+".xls";//"123_materiel.xls";
		int []celWidth={5000,5000,4500,4500,5000,10000,10000,15000,4500};
		if(state==0){//目录创建成功后执行文件生成和下载操作
			Excel.outputExcelFile(null, "食安科技----物料列表", titleArray, entityFieldNameArray,celWidth, list, totalFieldNameArray, outputFilePath+fileName);
			 responseEntity=dyfileUtils.download(request,outputFilePath, fileName);
				 Tools.delFile(outputFilePath+fileName);
		}
		return responseEntity;
	}
	
	/**
	 * 导入物料信息
	 * @param request
	 * @param response
	 * @param myFile
	 *            上传文件对象数组
	 * @param model
	 * @return
	 */
	 @ResponseBody  
	 @RequestMapping(value = "/importMateriel",produces="application/json;charset=UTF-8")  
	public String importMateriel(HttpServletRequest request,
			@RequestParam(value = "materFile", required = false)MultipartFile[] materFile) {
		String realpath =dyfileUtils.getrealPath(request, dyfileUtils.MATERIEL_PICTURE_PATH);
		System.out.println("当前路径为：" + realpath);
		int count=0;
		try {
		dyfileUtils.importFile(materFile, realpath);//上传要导入的对象
//		File file=new File(realpath+materFile[0].getOriginalFilename());
//		InputStream input=new FileInputStream(file);
		List<String[]> list=Excel.readExcelContent(realpath+materFile[0].getOriginalFilename(),2);
		List<MaterielDTO> maList=new ArrayList<MaterielDTO>();
		List<MaterielDrawingsDTO> mDtos=new ArrayList<MaterielDrawingsDTO>();
		MaterielDTO materielDTO=null;
		MaterielDrawingsDTO mDrawingsDTO=null;
		String updateTime="";
		List<String> materNo=materielService.queryAllMaterNo();
		if(list!=null){
		for (String[] model : list) {
			if(!materNo.contains(model[1]) && StringUtils.isNotBlank(model[1])){
			updateTime=Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss");
			if(StringUtils.isNotBlank(model[4])){
				model[4]=model[4].replace(" ", "&nbsp;");
			}
			materielDTO=new MaterielDTO(model[1].trim(), model[3].trim(), materieTypeService.queryById(model[2].trim()), model[4], model[5], model[6],updateTime, model[8]);
			if(!model[7].equals("") && model[7]!=null){
			String [] draws=model[7].split(",");
			String [] draws2=null;
			for (int i = 0; i < draws.length; i++) {
				draws2=draws[i].split("--");
				mDrawingsDTO=new MaterielDrawingsDTO(model[1], draws2[1], draws2[0], draws2[2], draws2[4], draws2[3]);
				mDtos.add(mDrawingsDTO);
			}
			}
			maList.add(materielDTO);
			count+=1;
			}
		}
		}
		System.out.println("物料数量为："+maList.size()+"图纸数量为："+mDtos.size());
//		 Tools.delFile(realpath+materFile[0].getOriginalFilename());
		 //批量插入物料和图纸信息		
		if(count>0){//物料添加成功
			 materielService.addList(maList);
			 maDrawingsServicce.addList(mDtos);
		}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		Tools.delFile(realpath+materFile[0].getOriginalFilename());//删除文件
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
	}
	  @ResponseBody  
	  @RequestMapping(value = "/queryMaterielNo",produces="application/json;charset=UTF-8")  
	  public String querySapNo(String materielNo){
		  JSONObject jsonObject=new JSONObject();
		  MaterielDTO maDto=materielService.queryById(materielNo);
		  if(maDto!=null){
			  try {
				jsonObject.put("result", "exitMaterielNo");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		  }
		  return jsonObject.toString();
	  }
	
}
