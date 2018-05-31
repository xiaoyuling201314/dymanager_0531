package com.dayuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dayuan.bean.PaginationData;
import com.dayuan.bean.SysUser;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.MaterielDrawingsDTO;
import com.dayuan.service.IMaterielDrawingsServicce;
import com.dayuan.util.Excel;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.Tools;
import com.dayuan.util.ZipCompressorByAnt;
import com.dayuan.util.dyfileUtils;
import com.mysql.fabric.xmlrpc.base.Array;
@Controller
@RequestMapping("/materielDrawService")
public class MaterielDrawingsController extends BaseController{
	private Logger logger=Logger.getLogger(MaterielDrawingsController.class);
	@Autowired
	private IMaterielDrawingsServicce maDrawingsServicce;

		//1.新增图纸信息，先将图纸新上传至服务器
		//2.将物料图纸数据保存至session中，重定向至materielList方法中获取列表数据
		//3.在物料图纸页面获取数据显示
		@SuppressWarnings("unchecked")
//		@ResponseBody
//		@RequestMapping(value="/uploadMaterielDraws",produces="application/json;charset=UTF-8")
//		public String uploadMaterielDraws(HttpServletRequest request,MaterielDrawingsDTO	maDrawingsDTO,@RequestParam(value = "myFile", required = false)MultipartFile[] myFile,String type){//,@RequestParam("myFile") MultipartFile[] myFile new MaterielDrawingsDTO("01.1602", "1.3", "456", "", new Data(), "");
//			String realpath=dyfileUtils.getrealPath(request, dyfileUtils.MATERIEL_DRAWSING_PATH);
//			String fileName=myFile[0].getOriginalFilename();
//			String suffix=fileName.substring(fileName.lastIndexOf("."));
//			String newFileName="GZDY-"+maDrawingsDTO.getDrawingName()+maDrawingsDTO.getVersion();
//			try {
//				dyfileUtils.uploadFile(myFile,realpath, newFileName);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			maDrawingsDTO.setDrawingName(newFileName+suffix);
//			//request.getSession().setAttribute("maDrawingsDTO", maDrawingsDTO);
//			if(type!=null && type.equals("edit")){//在物料编辑时，可直接添加或删除图纸
//				maDrawingsDTO.setUpdateTime(Tools.getDateString(new Date()));
//				maDrawingsServicce.add(maDrawingsDTO);
//			}
//			List<MaterielDrawingsDTO> listData=null;
//				listData=(List<MaterielDrawingsDTO>) request.getSession().getAttribute("drawsList")!=null ? (List<MaterielDrawingsDTO>) request.getSession().getAttribute("drawsList") : new ArrayList<MaterielDrawingsDTO>();
//				if(maDrawingsDTO!=null){
//				listData.add(maDrawingsDTO);
//				}
//			request.getSession().removeAttribute("drawsList");
//			request.getSession().setAttribute("drawsList", listData);
//			org.json.JSONArray jsonArray=new org.json.JSONArray(listData);
//			return jsonArray.toString();
//		}
		
		@ResponseBody
		@RequestMapping(value="/uploadMaterielDraws",produces="application/json;charset=UTF-8")
		public String uploadMaterielDraws(HttpServletRequest request,MaterielDrawingsDTO maDrawingsDTO,@RequestParam(value = "myFile", required = false)MultipartFile[] myFile,String type,String materDrawsNo){//,@RequestParam("myFile") MultipartFile[] myFile new MaterielDrawingsDTO("01.1602", "1.3", "456", "", new Data(), "");
			String realpath=dyfileUtils.getrealPath(request,dyfileUtils.ROOTPATH+dyfileUtils.MATERIEL_DRAWSING_PATH+maDrawingsDTO.getMaterielNo());
			String fileName=myFile[0].getOriginalFilename();
			String suffix=fileName.substring(fileName.lastIndexOf("."));
			String newFileName=maDrawingsDTO.getDrawingName().trim()+"_"+maDrawingsDTO.getVersion().trim();
			List<MaterielDrawingsDTO> listData=null;
			try {
				dyfileUtils.uploadFile(myFile,realpath, newFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			maDrawingsDTO.setDrawingName(newFileName+suffix);
			if(type!=null && type.equals("edit")){//在物料编辑时，可直接添加或删除图纸
				listData=materielService.queryById(maDrawingsDTO.getMaterielNo()).getDrawings();
				maDrawingsDTO.setUpdateTime(Tools.getDateString(new Date()));
				maDrawingsServicce.add(maDrawingsDTO);
			}else{
				listData=new ArrayList<MaterielDrawingsDTO>();
			}
		   if(maDrawingsDTO!=null){
				   listData.add(maDrawingsDTO);
		   }
			org.json.JSONArray jsonArray=new org.json.JSONArray(listData);
			request.setAttribute("maDrawings", maDrawingsDTO);
//			Map<String , MaterielDrawingsDTO> map=new HashMap<String, MaterielDrawingsDTO>();
//			map.put("drawsing", maDrawingsDTO);
//			org.json.JSONObject jsonObject=new org.json.JSONObject(map);
			return jsonArray.toString();
		}
	//1.新增图纸信息，先将图纸新上传至服务器
	//2.将物料图纸数据保存至session中，重定向至materielList方法中获取列表数据
	//3.在物料图纸页面获取数据显示
	@ResponseBody
	@RequestMapping(value="/deleteMaterielDraws",produces="application/json;charset=UTF-8")
	public String deleteMaterielDraws(HttpServletRequest request,String id,String type,String materNo){//,@RequestParam("myFile") MultipartFile[] myFile new MaterielDrawingsDTO("01.1602", "1.3", "456", "", new Data(), "");
		List<MaterielDrawingsDTO> maList=null;
		//String realPath=dyfileUtils.getrealPath(request, dyfileUtils.MATERIEL_DRAWSING_PATH);
		String realPath=dyfileUtils.getrealPath(request,dyfileUtils.ROOTPATH+dyfileUtils.MATERIEL_DRAWSING_PATH);
		if(type!=null && type.equals("edit")){//此时id为图纸编号
			MaterielDrawingsDTO maDrawingsDTO=maDrawingsServicce.queryById(id);
			if(maDrawingsDTO!=null)
			{
				String materielNo=maDrawingsDTO.getMaterielNo();
				maDrawingsServicce.deleteById(id);
				Tools.delFile(realPath+materielNo+"/"+maDrawingsDTO.getDrawingName());
				maList=maDrawingsServicce.queryDrawsById(materielNo);//重新弄查询物料图纸列表
			}
		}else{//首次新增,直接删除文件
		  Tools.delFile(realPath+materNo+"/"+id);
		}
		org.json.JSONArray json=new org.json.JSONArray(maList);
		return json.toString();
	}
	/**
	 * 根据物料编号查询图纸列表
	 * @author xyl
	 * @param materielNo 物料编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectDrawsList",produces="application/json;charset=UTF-8")
	public String selectDrawsList(HttpServletRequest request,String materielNo){//new MaterielDrawingsDTO("01.1602", "1.3", "456", "", new Data(), "");
		List<MaterielDrawingsDTO> list=maDrawingsServicce.queryDrawsById(materielNo);
		org.json.JSONArray jsonArray=new org.json.JSONArray(list);
		//request.getSession().setAttribute("drawsList",list);
		return jsonArray.toString();//redirect:materielDrawList.do
	}
	
	@RequestMapping("/deleteMateriel")
	public String deleteMateriel(String  materielNo,String version){
		int count=maDrawingsServicce.deleteByCompositeId(materielNo, version);
		System.out.println("执行结果为："+count);
		return "redirect:materielDrawList.do";
	}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/materielDrawList")
//	public String materielList(HttpServletRequest request,String materielNo,String type){
//		PaginationData p=new PaginationData();
//		List<MaterielDrawingsDTO> listData=null;
//		if(type!=null && type.equals("add")){
//			listData=(List<MaterielDrawingsDTO>) request.getSession().getAttribute("drawsList")!=null ? (List<MaterielDrawingsDTO>) request.getSession().getAttribute("drawsList") : new ArrayList<MaterielDrawingsDTO>();
//			MaterielDrawingsDTO maDrawingsDTO=(MaterielDrawingsDTO) request.getSession().getAttribute("maDrawingsDTO");
//			if(maDrawingsDTO!=null){
//			listData.add(maDrawingsDTO);
//			}
//			request.getSession().removeAttribute("maDrawingsDTO");
//			request.getSession().removeAttribute("drawsList");
//		}else{
//			 listData=maDrawingsServicce.queryDrawsById(materielNo);
//		}
//		request.getSession().setAttribute("drawsList", listData);
//		return "materials_drawing";//materials_add.jsp
//	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/materielDrawList")
	public String materielList(HttpServletRequest request,String materielNo,String type){
		List<MaterielDrawingsDTO> listData=null;
		if(type!=null && type.equals("add")){
			listData=(List<MaterielDrawingsDTO>) request.getSession().getAttribute("drawsList")!=null ? (List<MaterielDrawingsDTO>) request.getSession().getAttribute("drawsList") : new ArrayList<MaterielDrawingsDTO>();
			MaterielDrawingsDTO maDrawingsDTO=(MaterielDrawingsDTO) request.getSession().getAttribute("maDrawingsDTO");
			if(maDrawingsDTO!=null){
			listData.add(maDrawingsDTO);
			}
			request.getSession().removeAttribute("maDrawingsDTO");
			request.getSession().removeAttribute("drawsList");
		}else{
			 listData=maDrawingsServicce.queryDrawsById(materielNo);
		}
		request.getSession().setAttribute("drawsList", listData);
		return "materials_add";//materials_add.jsp
	}
	
	/**
	 * 下载物料图纸信息
	 * @param request
	 * @param fileName文件名称
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/downloadDrawing")
	public ResponseEntity<byte[]> downloadDrawing(HttpServletRequest request,
			String drawsList,String materielNo){
		String realPath=dyfileUtils.getrealPath(request,dyfileUtils.ROOTPATH+dyfileUtils.MATERIEL_DRAWSING_PATH+materielNo+"/");
		String [] fileList=drawsList.split(",");
		 ResponseEntity<byte[]> responseEntity=null;
		if(fileList.length>1){
			List<File> list=new ArrayList<File>();
			File file=null;
			String zipFile=request.getRealPath("/")+materielNo;
			Tools.createFolder(zipFile);
			File target=new File(zipFile);
			for (int i = 0; i < fileList.length; i++) {
				file=new File(realPath+fileList[i]);
				try {
					FileUtils.copyFileToDirectory(file, target);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ZipCompressorByAnt zip=new ZipCompressorByAnt(request.getRealPath("/")+materielNo+".zip");
			zip.compressExe(zipFile);
			 responseEntity=dyfileUtils.download(request,request.getRealPath("/"), materielNo+".zip");
			 Tools.delFolder(zipFile);//删除文件
			 Tools.delFolder(zipFile+".zip");//删除文件
		}else{
			responseEntity=dyfileUtils.download(request,realPath, fileList[0]);
		}
			if(responseEntity!=null){
			
			 return responseEntity;
		 }else{
			 return null;
		 }
	}
	
	
	 	@ResponseBody
	    @RequestMapping("/gexingSet")
	    public int gexingSet1(
	            @RequestParam(value = "file", required = false)MultipartFile file,
	            String name, String addrInfo, HttpServletRequest request) {  //name和addrinfo是表单提交的数据 因为文件上传有可能带有其他参数   但是名字要与表单里的名字一样
	        String fileName = file.getOriginalFilename(); //获取文件名
	        return 1;
	 	}
	 	@ResponseBody
		@RequestMapping(value="/checkFileVersion",produces="application/json;charset=UTF-8")
		public String checkFileVersion(HttpServletRequest request,String materielNo,String fileName,String version,String subfix){
			String newName=fileName+"_"+version+subfix;
			List<String> manualName=maDrawingsServicce.queryAllName(materielNo);
			JSONObject jsonObject=new JSONObject();
			String message="false";
			for (String name : manualName) {
				if(newName.equals(name)){
					message="true";
					break;
				}
			}
			
			jsonObject.put("isExit", message);
			return jsonObject.toString();
		}
}
