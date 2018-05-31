package com.dayuan.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class dyfileUtils {

	private static Logger logger = Logger.getLogger(dyfileUtils.class);
	private static int state = 0;
	
		//获取资源路径
		public static String INSTRU_CERTIFICATE_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.certificate.path");
		public static String INSTRU_PICTURE_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.picture.path");
		public static String INSTRU_DOCUMENT_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.document.path");
		public static String INSTRU_PACKLISK_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.packList.path");
		public static String INSTRU_MANUAL_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.manual.path");
		public static String INSTRU_CIRCUITBOARD_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.circuitBoard.path");
		public static String INSTRU_COMPLETERBOARD_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.completerBoard.path");
		public static String MATERIEL_PICTURE_PATH =PropertiesInfo.getInstance().getKeyValue("materiel.picture.path");
		public static String MATERIEL_DRAWSING_PATH =PropertiesInfo.getInstance().getKeyValue("materiel.drawsing.path");
		public static String ROOTPATH =PropertiesInfo.getInstance().getKeyValue("rootPath");
		public static String INSTRU_IMPLEMENTATION_STANDARDS =PropertiesInfo.getInstance().getKeyValue("instructions.implementationStandards.path");
		public static String INSTRU_REPAIR_PATH =PropertiesInfo.getInstance().getKeyValue("instructions.repair.path");
		public static Integer planCompleteDate=Integer.parseInt(PropertiesInfo.getInstance().getKeyValue("planCompleteDate"));
		public static String getrealPath(HttpServletRequest request, String root) {
			String realPath = request.getRealPath("/")+root;
			Tools.createFolder(realPath);
			return realPath;
		}


	/**
	 * 文件上传方法
	 * 
	 * @param myFile文件列表数组
	 * @param realpath上传路径
	 * @author xyl
	 * @return
	 * @throws IOException 
	 */
	public static int uploadFile(MultipartFile[] myFile, String realpath,
			String newFileName) throws IOException {
		CommonsMultipartFile msf = null;
		for (MultipartFile multipartFile : myFile) {
			if (!multipartFile.isEmpty() && multipartFile.getSize() != 0) {
				multipartFile.getOriginalFilename();
				System.out.println("multipartFile为空");
				msf = (CommonsMultipartFile) multipartFile;
				String name = msf.getFileItem().getName();
				String sufix = name.substring(name.lastIndexOf("."));
				saveFile(realpath, newFileName + sufix, multipartFile);
				// FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
				// new File(realpath, multipartFile.getOriginalFilename()));

				// 使用StreamsAPI方式拷贝文件
				// Streams.copy(multipartFile.getInputStream(),new
				// FileOutputStream(realpath+multipartFile.getOriginalFilename()),true);
				System.out.println("上传成功");
			}
		}
		return state;
	}
	
	/**
	 * 文件上传方法
	 * 
	 * @param myFile文件列表数组
	 * @param realpath上传路径
	 * @author xyl
	 * @return
	 * @throws IOException 
	 */
	public static int uploadImageFile(MultipartFile multipartFile, String realpath,
			String newFileName) throws IOException {
		File file = new File(realpath);
		CommonsMultipartFile msf = null;
			if (!multipartFile.isEmpty() && multipartFile.getSize() != 0) {
				multipartFile.getOriginalFilename();
				System.out.println("multipartFile为空");
				//msf = (CommonsMultipartFile) multipartFile;
//				String name = msf.getFileItem().getName();
//				String sufix = name.substring(name.lastIndexOf("."));
				saveFile(realpath, newFileName, multipartFile);
				// FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
				// new File(realpath, multipartFile.getOriginalFilename()));

				// 使用StreamsAPI方式拷贝文件
				// Streams.copy(multipartFile.getInputStream(),new
				// FileOutputStream(realpath+multipartFile.getOriginalFilename()),true);
				System.out.println("上传成功");
			}
		return state;
	}
	/**
	 * 文件上传方法
	 * 
	 * @param myFile文件列表数组
	 * @param realpath上传路径
	 * @author xyl
	 * @return
	 * @throws IOException 
	 */
	public static void importFile(MultipartFile[] myFile, String realpath) throws IOException {
		File file = new File(realpath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		CommonsMultipartFile msf = null;
		for (MultipartFile multipartFile : myFile) {
			if (!multipartFile.isEmpty() && multipartFile.getSize() != 0) {
				multipartFile.getOriginalFilename();
				System.out.println("multipartFile为空");
				msf = (CommonsMultipartFile) multipartFile;
				String name = msf.getFileItem().getName();
				String sufix = name.substring(name.lastIndexOf("."));
				 FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
				 new File(realpath, multipartFile.getOriginalFilename()));

				// 使用StreamsAPI方式拷贝文件
				// Streams.copy(multipartFile.getInputStream(),new
				// FileOutputStream(realpath+multipartFile.getOriginalFilename()),true);
				System.out.println("上传成功");
			}
		}
	}
	/**
	 * 文件下载方法
	 * 
	 * @param fileName文件名
	 * @param outputFilePath文件所在的绝对路径
	 * @param fileName要下载的文件名称
	 * @return
	 */
	@SuppressWarnings("finally")
	public static ResponseEntity<byte[]> download(HttpServletRequest request,
			String outputFilePath,String fileName) {
		
		ResponseEntity<byte[]> responseEntity = null;
		String dfileName = "";
		HttpHeaders headers = new HttpHeaders();
		try {
			dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", dfileName);
			File file = new File(outputFilePath + "\\" + fileName);// 文件绝对路径
			if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>0){
				responseEntity = new ResponseEntity<byte[]>(
						FileUtils.readFileToByteArray(file), headers,
						HttpStatus.CREATED);//HttpStatus.CREATED
			}else{
				responseEntity = new ResponseEntity<byte[]>(
						FileUtils.readFileToByteArray(file), headers,
						HttpStatus.OK);//HttpStatus.CREATED
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("文件下载异常===================="+e.getMessage());
		} catch (IOException e) {
			logger.error("文件下载异常===================="+e.getMessage());
		} finally {
			return responseEntity;
		}

	}

	/**
	 * 文件上传并重命名方法
	 * 
	 * @param realpath
	 * @param newFileName
	 * @param filedata
	 * @throws IOException 
	 */
	public static void saveFile(String realpath, String newFileName,
			MultipartFile filedata) throws IOException {
			FileOutputStream out = new FileOutputStream(realpath + "\\"
					+ newFileName);
			out.write(filedata.getBytes());
			out.flush();
			out.close();
	}

	/**
	 * 直接上传文件方法
	 * 
	 * @param myFile文件列表数组
	 * @param realpath上传路径
	 * @author xyl
	 * @return
	 */
	// public static int uploadFiles(MultipartFile[] myFile,String realpath){
	// File file = new File(realpath);
	// if (!file.exists() && !file.isDirectory()) {
	// file.mkdir();
	// }
	//
	// for (MultipartFile multipartFile : myFile) {
	// if (!multipartFile.isEmpty() && multipartFile.getSize()!=0) {
	// multipartFile.getOriginalFilename();
	//
	// System.out.println("multipartFile为空");
	// try {
	// FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
	// new File(realpath, multipartFile.getOriginalFilename()));
	// // 使用StreamsAPI方式拷贝文件
	// // Streams.copy(multipartFile.getInputStream(),new
	// // FileOutputStream(realpath+multipartFile.getOriginalFilename()),true);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// System.out.println("上传成功");
	// }
	// }
	// return state;
	// }
}
