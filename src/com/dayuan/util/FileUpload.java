package com.dayuan.util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Service
public class FileUpload {
	
	private  String url;//图片绝对路径
	private  String fileName;//文件名称
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	
//	/**
//	 * springMVC包装类上传文件
//	 * 
//	 * 适用于单/多文件上传
//	 * @throws IOException 
//	 * @throws IllegalStateException 
//	 * 
//	 * 
//	 * @param 
//	 * 			001：p2p web前端
//	 *		    002：p2p管理后台
//	 *      	003：p2p担保后台
//	 *	        004：p2p api
//	 *		request：request
//	 *@return
//	 *		List<FileUpload>:返回信息列表
//	 * */
//	public List<FileUpload> multiFileUpload2(HttpServletRequest request) 
//			throws IllegalStateException, IOException{
//		 List<FileUpload> fileRealPath =new ArrayList<FileUpload>();
// 		 FileUpload f=new FileUpload();
//		
//		 //创建一个通用的多部分解析器 
//		 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		//判断 request 是否有文件上传,即多部分请求  
//		 if(multipartResolver.isMultipart(request)){  
//			//转换成多部分request    
//	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
//	    		
//	    		//取得request中的所有文件名  
//	            Iterator<String> iter = multiRequest.getFileNames();  
//	            String realpath = request.getRealPath("/");
//        		PropertiesInfo p=PropertiesInfo.getInstance();
//        		String newFileName =realpath +p.getKeyValue("manual.path");
//        		Tools.createFolder(newFileName);
//	            while(iter.hasNext()){  
//	             
//	                //取得上传文件  
//	                MultipartFile file = multiRequest.getFile(iter.next());  
//	                if(file != null){  
//	                    //取得当前上传文件的文件名称  
//	                    String myFileName = file.getOriginalFilename();  
//	                    String extName = (myFileName.substring(myFileName.lastIndexOf("."))).toLowerCase();
//	                    //判断文件是否存在  
//	                    if(!"".equals(myFileName.trim())){
//	                        //定义上传路径  
//	                    	newFileName+=myFileName;
//	                        dyfileUtils.saveFile(realpath, myFileName, file);
//	    					f.setUrl(newFileName);
//	    					f.setPath(myFileName);
//	    					fileRealPath.add(f);  //把图片路径保存到列表
//	                    }  
//	                }  
//	               
//	            } 
//		 }
//		
//		return fileRealPath;
//	}
}
