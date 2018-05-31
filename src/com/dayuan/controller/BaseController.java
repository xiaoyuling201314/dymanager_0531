package com.dayuan.controller;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.StandardChartTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dayuan.bean.SysUser;
import com.dayuan.service.ICertificateService;
import com.dayuan.service.ICertificateTypeService;
import com.dayuan.service.ICircuitboardDetailService;
import com.dayuan.service.ICircuitboardService;
import com.dayuan.service.ICompletemachineService;
import com.dayuan.service.ICompletemachinedetailService;
import com.dayuan.service.IMaterieTypeService;
import com.dayuan.service.IMaterielDrawingsServicce;
import com.dayuan.service.IMaterielService;
import com.dayuan.service.IPackingListDetailService;
import com.dayuan.service.IPackinglistService;
import com.dayuan.service.IRepairRecordService;
import com.dayuan.service.IShippingrecordsService;
import com.dayuan.service.InstrumentinfoService;
import com.dayuan.util.Excel;
import com.dayuan.util.PropertiesInfo;
import com.dayuan.util.dyfileUtils;

@Controller
public class BaseController {
	private Logger logger = Logger.getLogger(BaseController.class);
	@Autowired
	protected ICircuitboardService circuitboardService;
	@Autowired
	protected ICircuitboardDetailService cirDetailService;
	@Autowired
	protected ICircuitboardDetailService cDetailService;
	@Autowired
	protected IMaterielDrawingsServicce maDrawingsServicce;
	@Autowired
	protected IMaterielService materielService;
	@Autowired
	protected IMaterieTypeService materieTypeService;
	@Autowired
	protected ICompletemachineService comService;
	@Autowired
	protected ICompletemachinedetailService compdetailService;
	@Autowired
	protected IPackinglistService packingService;
	@Autowired
	protected IPackingListDetailService paDetailService;
	@Autowired
	protected InstrumentinfoService insService;
	@Autowired
	protected ICertificateService certificateService;
	@Autowired
	protected ICertificateTypeService cerTypeService;
	@Autowired
	protected IRepairRecordService repairService;
	@Autowired
	protected IShippingrecordsService shipmentsService;
	/**
	 * 文件下载方法
	 * 
	 * @param request
	 * @param fileName文件名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/downloads")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			String fileName) {
		String[] titleArray = { "userName", "email", "department", "updateTime" };
		String[] entityFieldNameArray = { "userName", "email", "department",
				"updateTime" };
		String[] totalFieldNameArray = null;
		List<SysUser> userList = null;
		String outputFilePath = request.getRealPath("/");// +"123.xls"
		int[] celWidth = { 5000, 4500, 4500, 3000, 45000, 4500, 3000, 5000,
				5000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 7000,
				3000, 3000, 3000, 5000, 5000, 3000, 3000, 3000, 5000, 5000,
				3000, 3000, 3000, 3000, 3000 };
		Excel.outputExcelFile(null, null, titleArray, entityFieldNameArray,
				celWidth, userList, totalFieldNameArray, outputFilePath
						+ fileName);
		ResponseEntity<byte[]> responseEntity = dyfileUtils.download(request,
				outputFilePath, fileName);
		if (responseEntity != null) {
			return responseEntity;
		} else {
			logger.error("文件下载对象为空");
			return null;
		}
	}

	// 处理时间的方法
	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}

	/**
	 * 上传多个文件或图片
	 * 
	 * @param request
	 * @param response
	 * @param myFile
	 *            上传文件对象数组
	 * @param model
	 * @return
	 */
	@RequestMapping("/fileUploads")
	public String uploadFiles(HttpServletRequest request,
			@RequestParam("myFile") MultipartFile[] myFile) {
		String realpath = request.getRealPath("/");
		PropertiesInfo p = PropertiesInfo.getInstance();
		realpath += p.getKeyValue("certificatePath");
		System.out.println("当前路径为：" + realpath);
		int state = 0;
		try {
			state = dyfileUtils.uploadFile(myFile, realpath, "123");
		} catch (IOException e) {
			state = -1;
			e.printStackTrace();
		}
		if (state == 0) {
			// PaginationData pData=new PaginationData(pagesize);
			// pData.setCurPage(curpage);
			// pData.setRecordCount(100);
			// pData.setRecordCount(100, pagesize);
			// request.setAttribute("pData",pData);
			// request.setAttribute("curpage", pData.getCurPage());
			// request.setAttribute("pagesize", pData.getPageSize());
			return "message";
		} else {
			return "error";
		}
	}
  
	public void checkIsShow(HttpServletRequest request){
		String[] rights =(String[]) request.getSession().getAttribute("rightList");
		String isShow="NO";
		if(rights!=null && rights.length>0){
		for (int i = 0; i < rights.length; i++) {
			if(rights[i].contains("C")){
				isShow="YES";
			}
		}
		}
		request.setAttribute("isShow",isShow);
	}
	public StandardChartTheme setFont(){
		StandardChartTheme theme=new StandardChartTheme("CN");
		theme.setExtraLargeFont(new Font("黑体", Font.PLAIN,20));
		theme.setLargeFont(new Font("宋体", Font.PLAIN,12));
		theme.setRegularFont(new Font("宋体", Font.PLAIN,12));
		theme.setSmallFont(new Font("宋体", Font.PLAIN,12));
		return theme;
	}
}
