package com.dayuan.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.log4j.Logger;

/**
 * 描述:生成Excel文件和下载
 * @author xyl
 */
public class Excel {
	
	private final static Logger logger = Logger.getLogger(Excel.class);
	
	private static Method[] methods = null;
	
	/**
	 * 生成Excel文件
	 * @param totalDate  --统计日期(为null时不输出)
	 * @param tableTitle  --表格标题(为null时不输出)
	 * @param titleArray  --表格抬头数组
	 * @param entityFieldNameArray  --输出数据的实体字段名数组
	 * @param list  --数据实体集合
	 * @param totalFieldNameArray  --需要合计的实体字段名数组(为null时不输出)
	 * @param outputFilePath  --文件输出完整路径
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public static boolean outputExcelFile(
			final String totalDate, 
			final String tableTitle, 
			final String[] titleArray, 
			final String[] entityFieldNameArray, 
			final List list, 
			final String[] totalFieldNameArray, 
			final String outputFilePath) {
		
		System.out.println("开始生成Excel文件...");
		
		boolean mark = true;
		short rowIndex = -1;   //行索引
		short cellIndex = 0;   //列索引
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFCellStyle style = null;
			
			if(tableTitle != null && !"".equals(tableTitle)) {
				row = sheet.createRow(++rowIndex);   //rowIndex = 0
				sheet.addMergedRegion(new Region(0, (short)0, 0, (short)(titleArray.length-1))); 
				cell = row.createCell((short) 0);
				
				HSSFFont font = workbook.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				
				style = workbook.createCellStyle(); 
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style.setFont(font);
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(tableTitle);   //输出表格标题
			}
			
			//输出统计日期
			if(totalDate != null && !"".equals(totalDate)) {
				rowIndex ++; 
				row = sheet.createRow(rowIndex);
				sheet.addMergedRegion(new Region(1, (short)0, 1, (short)(titleArray.length-1)));
				cell = row.createCell((short) 0);
				style = workbook.createCellStyle(); 
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(totalDate);
			}
			
			//输出表格抬头
			rowIndex ++;
			cellIndex = 0;
			for(String title : titleArray) {
				row = sheet.createRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFFont font = workbook.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style = workbook.createCellStyle();
				style.setFont(font);
				cell.setCellStyle(style);
				cell.setCellValue(title);
				cellIndex ++;
			}
			if(list != null && list.size() > 0) {
				//输出列表数据
				for(Object entity : list) {
					rowIndex ++;
					cellIndex = 0;
					methods = entity.getClass().getDeclaredMethods();
					
					row = sheet.createRow(rowIndex);
					for(String entityFieldName : entityFieldNameArray) {
						Method method = getMethodByFieldName(entityFieldName);
						//System.out.println("符合条件的方法名:" + method.getName());
						Object value = method.invoke(entity, (Object[])null);
						cell = row.createCell(cellIndex);
						//if(method.getReturnType().toString().indexOf("String") > 0) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						//}else {
							//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						//}
						
						style = workbook.createCellStyle();
						style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
						style.setWrapText(true);
						style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
						cell.setCellStyle(style);
						
						cell.setCellValue((value!=null?value.toString():""));
						cellIndex ++;
					}
				}
				//输出合计
				if(totalFieldNameArray != null) {
					rowIndex ++;
					row = sheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("合计");
					
					//cellIndex = 0;
					for(int i=0;i<totalFieldNameArray.length;i++) {
						cellIndex = (short)indexOfStringArray(totalFieldNameArray[i], entityFieldNameArray);
						cell = row.createCell(cellIndex);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						
						float totalValue = 0f;
						Method method = null;
						for(Object entity : list) {
							method = getMethodByFieldName(totalFieldNameArray[i]);
							
							String methodName = method.getName();
							if(methodName.substring(methodName.lastIndexOf(".")+1, methodName.length()).equals("String")) {
								continue;
							}
							
							totalValue += Float.valueOf(method.invoke(entity, (Object[])null).toString());
						}
						System.out.print(titleArray[indexOfStringArray(totalFieldNameArray[i], entityFieldNameArray)]);
						System.out.println("合计:" + totalValue);
						cell.setCellValue(String.valueOf(totalValue));
					}
				}
			}
			FileOutputStream fOut = new FileOutputStream(outputFilePath);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			System.out.println("Excel文件已生成.");
		}catch(Exception e) {
			mark = false;
			e.printStackTrace();
			logger.error("生成Excel文件失败==>" + e.getMessage());
		}
		
		return mark;
	}
	
	
	/**
	 * 生成CVS文件
	 * @param titleArray  --表格抬头数组
	 * @param entityFieldNameArray  --输出数据的实体字段名数组
	 * @param list  --数据实体集合
	 * @param outputFilePath  --文件输出完整路径
	 * @return boolean
	 */
	public static boolean outputCsvFile(
			String[] titleArray, 
			String[] entityFieldNameArray, 
			List list, 
			String outputFilePath) {
		
		System.out.println("开始生成CSV文件...");		
		//if(list.isEmpty()) {
			//return false;
		//}
		boolean mark = true;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFilePath)));
			//输出表格抬头
			String titleString = "";
			for(String title : titleArray) {
				titleString += title + ",";
			}
			if(titleString.length() > 0) {
				titleString = titleString.substring(0, titleString.lastIndexOf(","));
				bw.write(titleString);
				bw.write("\r\n");
			}
			if(list != null && list.size() > 0) {
				StringBuffer sb = null;
				//输出列表数据
				for(Object entity : list) {
					sb = new StringBuffer();
					methods = entity.getClass().getDeclaredMethods();
					for(String entityFieldName : entityFieldNameArray) {
						Method method = getMethodByFieldName(entityFieldName);
						//System.out.println("符合条件的方法名:" + method.getName());
						Object value = method.invoke(entity, (Object[])null);
						sb.append((value!=null?value.toString():"")).append(",");
					}
					if(sb.toString().length() > 0) {
						bw.write(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
						bw.write("\r\n");
					}
				}
			}
			bw.close();
			System.out.println("CSV文件已生成.");
		}catch(Exception e) {
			mark = false;
			e.printStackTrace();
			logger.error("生成CSV文件失败.");
		}
		
		return mark;
	}
	
	//*******************************************************
	
	/**
	 * 生成Excel文件
	 * @param totalDate  --统计日期(为null时不输出)
	 * @param tableTitle  --表格标题(为null时不输出)
	 * @param titleArray  --表格title头数组
	 * @param entityFieldNameArray  --输出数据的实体字段名数组
	 * @param cellWidthArray  --列宽数组
	 * @param list  --数据实体集合
	 * @param outputFilePath  --文件输出完整路径
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public static boolean outputExcelFile(
			final String totalDate, 
			final String tableTitle, 
			final String[] titleArray, 
			final String[] entityFieldNameArray, 
			final int[] cellWidthArray,
			final List list, 
			final String[] totalFieldNameArray, 
			final String outputFilePath) {
		
		System.out.println("开始生成Excel文件...");
		
		boolean mark = true;
		short rowIndex = -1;   //行索引
		short cellIndex = 0;   //列索引
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFCellStyle style = null;
			
			if(tableTitle != null && !"".equals(tableTitle)) {
				row = sheet.createRow(++rowIndex);   //rowIndex = 0
				sheet.addMergedRegion(new Region(0, (short)0, 0, (short)(titleArray.length-1))); 
				cell = row.createCell((short) 0);
				
				HSSFFont font = workbook.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				
				style = workbook.createCellStyle(); 
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style.setFont(font);
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(tableTitle);   //输出表格标题
			}
			
			//输出统计日期
			if(totalDate != null && !"".equals(totalDate)) {
				rowIndex ++; 
				row = sheet.createRow(rowIndex);
				sheet.addMergedRegion(new Region(1, (short)0, 1, (short)(titleArray.length-1)));
				cell = row.createCell((short) 0);
				style = workbook.createCellStyle(); 
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(totalDate);
			}
			
			//输出表格抬头
			rowIndex ++;
			cellIndex = 0;
			for(String title : titleArray) {
				sheet.setColumnWidth(cellIndex, (short)cellWidthArray[cellIndex]);
				row = sheet.createRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFFont font = workbook.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style = workbook.createCellStyle();
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setFont(font);
				cell.setCellStyle(style);
				cell.setCellValue(title);
				cellIndex ++;
			}
			if(list != null && list.size() > 0) {
				//输出列表数据
				for(Object entity : list) {
					rowIndex ++;
					cellIndex = 0;
					methods = entity.getClass().getDeclaredMethods();
					row = sheet.createRow(rowIndex);
					for(String entityFieldName : entityFieldNameArray) {
						Method method = getMethodByFieldName(entityFieldName);
						//System.out.println("符合条件的方法名:" + method.getName());
						Object value = method.invoke(entity, (Object[])null);
						cell = row.createCell(cellIndex);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						style = workbook.createCellStyle();
						style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
						style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
						style.setBorderRight(HSSFCellStyle.BORDER_THIN);
						style.setBorderTop(HSSFCellStyle.BORDER_THIN);
						style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
						style.setWrapText(true);
						style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
						cell.setCellStyle(style);
						if(method.getName().equals("getId")){//add by xiaoyuling 2015-11-24  导出翻译
								value=rowIndex-1;
						}
						//cell.setCellValue((value!=null?value.toString():""));
						cell.setCellValue(new HSSFRichTextString((value!=null?value.toString():"")));
						cellIndex ++;
					}
				}
				//输出合计
				if(totalFieldNameArray != null) {
					rowIndex ++;
					row = sheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("合计");
					
					//cellIndex = 0;
					for(int i=0;i<totalFieldNameArray.length;i++) {
						cellIndex = (short)indexOfStringArray(totalFieldNameArray[i], entityFieldNameArray);
						cell = row.createCell(cellIndex);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						
						float totalValue = 0f;
						Method method = null;
						for(Object entity : list) {
							method = getMethodByFieldName(totalFieldNameArray[i]);
							String methodName = method.getName();
							if(methodName.substring(methodName.lastIndexOf(".")+1, methodName.length()).equals("String")) {
								continue;
							}
							totalValue += Float.valueOf(method.invoke(entity, (Object[])null).toString());
						}
						System.out.print(titleArray[indexOfStringArray(totalFieldNameArray[i], entityFieldNameArray)]);
						System.out.println("合计:" + totalValue);
						cell.setCellValue(String.valueOf(totalValue));
					}
				}
			}
			FileOutputStream fOut = new FileOutputStream(outputFilePath);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			System.out.println("Excel文件已生成.");
		}catch(Exception e) {
			mark = false;
			e.printStackTrace();
			logger.error("生成Excel文件失败==>" + e.getMessage());
		}
		
		return mark;
	}
	
	/**
	 * 生成Excel文件
	 * @param totalDate  --统计日期(为null时不输出)
	 * @param tableTitle  --表格标题(为null时不输出)
	 * @param titleArray  --表格抬头数组
	 * @param entityFieldNameArray  --输出数据的实体字段名数组
	 * @param cellWidthArray  --列宽数组
	 * @param list  --数据实体集合
	 * @param totalFieldNameArray  --需要合计的实体字段名数组(为null时不输出)
	 * @param outputFilePath  --文件输出完整路径
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public static boolean outputExcelFile(
			final String[] titleArray, 
			final int[] cellWidthArray,
			final List list, 
			final String outputFilePath) {
		
		System.out.println("开始生成Excel文件...");
		
		boolean mark = true;
		short rowIndex = -1;   //行索引
		short cellIndex = 0;   //列索引
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFCellStyle style = null;
			
			//输出表格抬头
			rowIndex ++;
			cellIndex = 0;
			for(String title : titleArray) {
				sheet.setColumnWidth(cellIndex, (short)cellWidthArray[cellIndex]);
				row = sheet.createRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFFont font = workbook.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style = workbook.createCellStyle();
				style.setFont(font);
				cell.setCellStyle(style);
				cell.setCellValue(title);
				cellIndex ++;
			}
			if(list != null && list.size() > 0) {
				//输出列表数据
				for(Object entity : list) {
					rowIndex ++;
					cellIndex = 0;
					methods = entity.getClass().getDeclaredMethods();
					
					row = sheet.createRow(rowIndex);
					//System.out.println("符合条件的方法名:" + method.getName());
					Object value =entity;
					cell = row.createCell(cellIndex);
					//if(method.getReturnType().toString().indexOf("String") > 0) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					//}else {
						//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					//}
					
					style = workbook.createCellStyle();
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
					style.setWrapText(true);
					style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
					cell.setCellStyle(style);
					
					cell.setCellValue((value!=null?value.toString():""));
				}
			}
			FileOutputStream fOut = new FileOutputStream(outputFilePath);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			System.out.println("Excel文件已生成.");
		}catch(Exception e) {
			mark = false;
			e.printStackTrace();
			logger.error("生成Excel文件失败==>" + e.getMessage());
		}
		
		return mark;
	}
	
	/**
	 * 生成Excel文件
	 * @param InputStream  --模版EXCEL文件流
	 * @param entityFieldNameArray  --输出数据的实体字段名数组
	 * @param list  --数据实体集合
	 * @param startRowIndex  --开始写入数据的行(从0开始计算)
	 * @param outputFilePath  --文件输出完整路径
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public static boolean outputExcelFile(
			final InputStream templateInputStream,
			final String[] entityFieldNameArray, 
			final List list, 
			final short startRowIndex,
			final String outputFilePath) {
		
		System.out.println("开始生成Excel文件...");
		
		boolean mark = true;
		short rowIndex = startRowIndex;   //行索引
		short cellIndex = 0;   //列索引
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(templateInputStream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFCellStyle style = null;
			
			if(list != null && list.size() > 0) {
				//输出列表数据
				for(Object entity : list) {
					cellIndex = 0;
					methods = entity.getClass().getDeclaredMethods();
					
					row = sheet.createRow(rowIndex);
					for(String entityFieldName : entityFieldNameArray) {
						Method method = getMethodByFieldName(entityFieldName);
						//System.out.println("符合条件的方法名:" + method.getName());
						Object value = method.invoke(entity, (Object[])null);
						cell = row.createCell(cellIndex);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						
						style = workbook.createCellStyle();
						style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
						style.setWrapText(true);
						style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
						cell.setCellStyle(style);
						
						cell.setCellValue((value!=null?value.toString():""));
						cellIndex ++;
					}
					rowIndex++;
				}
			}
			FileOutputStream fOut = new FileOutputStream(outputFilePath);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			System.out.println("Excel文件已生成.");
		}catch(Exception e) {
			mark = false;
			e.printStackTrace();
			logger.error("生成Excel文件失败==>" + e.getMessage());
		}
		
		return mark;
	}
	
//	/**
//	 * 读取EXCEL内容,读取xlsx格式有问题
//	 * @param excelInputStream  --EXCEL文件流
//	 * @param startRowIndex  --开始读取数据的行(从0开始计算)
//	 * @return List<String[]>
//	 */
//	public static List<String[]> readExcelContent(
//			final InputStream excelInputStream,
//			final int startRowIndex
//			) {
//		
//		List<String[]> contentList = new ArrayList<String[]>();
//		try {
//			Workbook wb = Workbook.getWorkbook(excelInputStream); 
//			Sheet sheet = wb.getSheet(0);
//			int rIndex = startRowIndex;
//			for(;rIndex<sheet.getRows();rIndex++) {
//				Cell cells[] = sheet.getRow(rIndex);
//					String[] lineArray = new String[cells.length];
//					for(int i=0;i<cells.length;i++) {
//						  if(cells[i].getType()==CellType.NUMBER && i!=1){//处理数字类型,物料编号不处理，只处理数量
//							  NumberCell numberCell = (NumberCell)cells[i]; 
//							  Integer value =(int) numberCell.getValue();
//							  lineArray[i] =String.valueOf(value);
//						  }else if(cells[i].getType()==CellType.LABEL && i==1){//处理物料编号
//							  String materNo=cells[i].getContents();
//							  lineArray[i] =materNo;
//						  } else  if(cells[i].getType()==CellType.DATE ){
//							  DateCell d=(DateCell) cells[i];
//							  lineArray[i] =Tools.getDateString(d.getDate());
//						  }
//						  else
//							lineArray[i] = cells[i].getContents()==null?"":cells[i].getContents();
//						}
//					contentList.add(lineArray);
//				}
//				
//			wb.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//			logger.error("读取Excel文件失败==>" + e.getMessage());
//			return null;
//		}
//		return contentList;
//	}
	
	/**
	 * 读取EXCEL内容
	 * @param excelInputStream  --EXCEL文件流
	 * @param startRowIndex  --开始读取数据的行(从0开始计算)
	 * @return List<String[]>
	 */
	public static List<String[]> readExcelContent(
			final String filePath,
			final int startRowIndex
			) {
		
		List<String[]> contentList = new ArrayList<String[]>();
		org.apache.poi.ss.usermodel.Workbook hsw=null;
		try {
		File file=new File(filePath);
		InputStream excelInputStream=new FileInputStream(file);
		boolean flag=isExcel2003(filePath);
		int rIndex = startRowIndex;
		String value="";
		if(flag){//读取xls格式的excel文件
			//hsw=new HSSFWorkbook(excelInputStream);
//			POIFSFileSystem fs=new POIFSFileSystem(excelInputStream);
//			hsw=WorkbookFactory.create(fs);
			HSSFWorkbook hsw2=new HSSFWorkbook(excelInputStream);
			HSSFSheet sheet	=hsw2.getSheetAt(0);
			for(;rIndex<=sheet.getLastRowNum();rIndex++) {
				HSSFRow hssfRow=sheet.getRow(rIndex);
				if(hssfRow!=null){
					System.out.println("总行数"+hssfRow.getLastCellNum()+"index:"+rIndex);
					String[] lineArray = new String[hssfRow.getLastCellNum()];
					for(short i=1;i<hssfRow.getLastCellNum();i++) {
						HSSFCell cell=hssfRow.getCell(i);
						if (cell != null) {
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && i != 1 ) {// 处理数字类型,物料编号不处理，只处理数量
								System.out.println(i+"值："+cell.getNumericCellValue()+"行数："+rIndex);
								value = getCellValue(cell);
							} else if (cell.getCellType() == Cell.CELL_TYPE_STRING
									&& i == 1) {// 处理物料编号
								value = getCellValue(cell);
							} else {
								value = getCellValue(cell);
							}
							lineArray[i] = value == null ? "" : value;
						}else{
							lineArray[i] ="";
						}
					
					}
					contentList.add(lineArray);
					}
				}
		}else{//读取xlsx格式的excel文件
			hsw=new XSSFWorkbook(excelInputStream); 
			org.apache.poi.ss.usermodel.Sheet sheet =hsw.getSheetAt(0);
			for(;rIndex<=sheet.getLastRowNum();rIndex++) {
				Row hssfRow=sheet.getRow(rIndex);
				if(hssfRow!=null){
					String[] lineArray = new String[hssfRow.getLastCellNum()];
					for(short i=1;i<hssfRow.getLastCellNum();i++) {
						Cell cell=hssfRow.getCell(i);
						if (cell != null) {
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
									&& i != 1 ) {// 处理数字类型,物料编号不处理，只处理数量
								value = getCellValue(cell);
							} else if (cell.getCellType() == Cell.CELL_TYPE_STRING
									&& i == 1) {// 处理物料编号
								value = getCellValue(cell);
							} else {
								value = getCellValue(cell);
							}
							lineArray[i] = value == null ? "" : value;
						}else{
							lineArray[i] ="";
						}
					
					}
					contentList.add(lineArray);
					}
				}
		}
		
			//HSSFWorkbook hsw=new HSSFWorkbook(excelInputStream);
			//org.apache.poi.ss.usermodel.Sheet sheet =hsw.getSheetAt(0);
			
//			for(;rIndex<sheet.getLastRowNum();rIndex++) {
//				Row hssfRow=sheet.getRow(rIndex);
//				if(hssfRow!=null){
//					String[] lineArray = new String[hssfRow.getLastCellNum()];
//					for(short i=0;i<hssfRow.getLastCellNum();i++) {
//						Cell cell=hssfRow.getCell(i);
//						if (cell != null) {
//							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
//									&& i != 1) {// 处理数字类型,物料编号不处理，只处理数量
//								value = getCellValue(cell);
//							} else if (cell.getCellType() == Cell.CELL_TYPE_STRING
//									&& i == 1) {// 处理物料编号
//								value = getCellValue(cell);
//							} else {
//								value = getCellValue(cell);
//							}
//							lineArray[i] = value == null ? "" : value;
//						}
//					
//					}
//					contentList.add(lineArray);
//					}
//				}
			excelInputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("读取Excel文件失败==>" + e.getMessage());
			return null;
		}
		return contentList;
	}
	//util *****************************************************************************************
	
	/**
	 * 根据字段名查找对应的getter方法
	 * @param filedName   --字段名
	 * @return Method
	 */
	public static Method getMethodByFieldName(String filedName) {
		Method method = null;
		for(Method _method : methods) {
			if(_method.getName().equalsIgnoreCase("get" + filedName)) {
				method = _method;
				break;
			}
		}
		return method;
	}
	
	/**
	 * 根据字段名查找对应的getter方法
	 * @param filedName   --字段名
	 * @return Method
	 */
	public static Method getMethodByFieldName(String filedName,Method [] methods) {
		Method method = null;
		for(Method _method : methods) {
			if(_method.getName().equalsIgnoreCase("get" + filedName)) {
				method = _method;
				break;
			}
		}
		return method;
	}
	
	/**
	 * 在字符串数组中查找字符串索引位置
	 * @param findStr  --要查找的字符串
	 * @param strArray  --字符串数组
	 * @return int
	 */
	private static int indexOfStringArray(String findStr, String[] strArray) {
		int index = 0;
		for(int i=0;i<strArray.length;i++) {
			if(strArray[i].equals(findStr)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 创建文件并且添加数据
	 * @param fileName--文件名
	 * @param subject--sheet名
	 * @param title--名称
	 * @param numbStr--号码列表
	 * @return int --1:成功；0:失败
	 */
	public static int createFile(String fileName,String subject,String title,String []numbStr) {
		// TODO Auto-generated method stub
		try{
			File file=new File(fileName);
			WritableWorkbook wwb=Workbook.createWorkbook(file);//创建xls文件
			WritableSheet ws=wwb.createSheet(subject, 0);
			//设置标题
			Label title1=new Label(0,0,title,getTitle());
			ws.addCell(title1);
			int row=1;
			for(int i=0;i<numbStr.length;i++){
				Label col1=new Label(0,row,numbStr[i],getNormolCell());
				ws.addCell(col1);
				row++;
			}
			ws.setColumnView(0, 30);
			wwb.write();
			wwb.close();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 添加数据
	 * @param fileName--文件名
	 * @param begin--开始
	 * @param numbStr--数据数组
	 * @return int--1:成功；0:失败
	 */
	public static int addData(String fileName,int begin,String []numbStr) {
		// TODO Auto-generated method stub
		try{
			File file=new File(fileName);
			Workbook wb=Workbook.getWorkbook(file);
			WritableWorkbook wwb=Workbook.createWorkbook(file,wb);//创建xls文件
			WritableSheet ws=wwb.getSheet(0);
			int row=1+begin*50;
			for(int i=0;i<numbStr.length;i++){
				Label col1=new Label(0,row,numbStr[i],getNormolCell());
				ws.addCell(col1);
				row++;
			}
			wwb.write();
			wwb.close();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
     * 设置头的样式
     * @return 
     */
    public static WritableCellFormat getHeader(){
    	WritableFont font = new  WritableFont(WritableFont.TIMES, 24 ,WritableFont.BOLD);//定义字体
        try {
        	font.setColour(Colour.BLUE);//蓝色字体
        } catch (WriteException e1) {
        	e1.printStackTrace();
        }
        WritableCellFormat format = new  WritableCellFormat(font);
        try {
        	format.setAlignment(jxl.format.Alignment.CENTRE);//左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//上下居中
            format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);//黑色边框
            format.setBackground(Colour.YELLOW);//黄色背景
        } catch (WriteException e) {
        	e.printStackTrace();
        }
        return format;
    }
    
    /**
     * 设置标题样式
     * @return
     */
    public static WritableCellFormat getTitle(){
    	WritableFont font = new  WritableFont(WritableFont.TIMES, 14);
        try {
       	    font.setColour(Colour.BLUE);//蓝色字体
        } catch (WriteException e1) {
       	    e1.printStackTrace();
        }
        WritableCellFormat format = new  WritableCellFormat(font);
        
        try {
       	    format.setAlignment(jxl.format.Alignment.CENTRE);//水平
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直
            format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);//边框
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }
    
    /**
     * 设置其他单元格样式
     * @return
     */
    public static WritableCellFormat getNormolCell(){//12号字体,上下左右居中,带黑色边框
    	WritableFont font = new  WritableFont(WritableFont.TIMES, 12);
        WritableCellFormat format = new  WritableCellFormat(font);
        try {
        	format.setAlignment(jxl.format.Alignment.LEFT);
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
            format.setWrap(true);//自动换行当内容过长时
        } catch (WriteException e) {
        	e.printStackTrace();
        }
        return format;
    }

    //判断从Excel文件中解析出来数据的格式   
    private static String getCellValue(Cell cell){  
        String value = null;  
        //简单的查检列类型   
        switch(cell.getCellType())  
        {  
            case Cell.CELL_TYPE_STRING://字符串   
                value = cell.getStringCellValue();
                break;  
            case Cell.CELL_TYPE_NUMERIC://数字   ,日期
            	if(DateUtil.isCellDateFormatted(cell)){
            		value=Tools.getDateString(cell.getDateCellValue());
            	}else{
            		cell.setCellType(Cell.CELL_TYPE_STRING);  
            		value = String.valueOf(cell.getStringCellValue());
            	}
                break;  
            case Cell.CELL_TYPE_BLANK:  
                value = "";  
                break;     
            case Cell.CELL_TYPE_FORMULA:  
                value = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN://boolean型值   
                value = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_ERROR:  
                value = String.valueOf(cell.getErrorCellValue());  
                break;  
            default:  
                break;  
        }  
        return value;  
    }  
    //判断从Excel文件中解析出来数据的格式   
    private static String getCellValue(HSSFCell cell){  
        String value = null;  
        //简单的查检列类型   
        switch(cell.getCellType())  
        {  
            case Cell.CELL_TYPE_STRING://字符串   
                value = cell.getStringCellValue();
                break;  
            case Cell.CELL_TYPE_NUMERIC://数字   ,日期
            	if(HSSFDateUtil.isCellDateFormatted(cell)){
            		value=Tools.getDateString(cell.getDateCellValue());
            	}else{
            		cell.setCellType(Cell.CELL_TYPE_STRING);  
            		value = String.valueOf(cell.getStringCellValue());
            	}
                break;  
            case Cell.CELL_TYPE_BLANK:  
                value = "";  
                break;     
            case Cell.CELL_TYPE_FORMULA:  
                value = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN://boolean型值   
                value = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_ERROR:  
                value = String.valueOf(cell.getErrorCellValue());  
                break;  
            default:  
                break;  
        }  
        return value;  
    }  
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(Cell cell) {
        String strCell = "";
        int number=0;
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
          //  strCell = String.valueOf(cell.getNumericCellValue());
        	number=(int) cell.getNumericCellValue();
        	strCell=String.valueOf(number);
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }
    public static boolean isExcel2003(String filePath)  
    {  
  
        return filePath.matches("^.+\\.(?i)(xls)$");  
  
    }  
    public static boolean isExcel2007(String filePath)  
    {  
  
        return filePath.matches("^.+\\.(?i)(xlsx)$");  
  
    }  
}
