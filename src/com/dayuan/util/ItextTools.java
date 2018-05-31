package com.dayuan.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.hwpf.usermodel.Range;
//import org.apache.poi.hwpf.usermodel.TableCell;
//import org.apache.poi.hwpf.usermodel.TableIterator;
//import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.ExportRepairRecorder;
import com.dayuan.bean.PackingListDetail;
import com.ibm.icu.util.RangeValueIterator.Element;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfTextArray;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;
import com.lowagie.text.rtf.RtfWriter2;

public class ItextTools {
   public static void createWordDocument(String rootPath,String realPath,String[] titleArray,List<ExportCircuitBoard> items,String wordTitle) throws DocumentException, IOException{
	   Document document=new Document(PageSize.A4);//设置页边距：左、右、上、下
	   RtfWriter2 write=RtfWriter2.getInstance(document, new FileOutputStream(realPath));
		 document.open();
		 document.setMargins(72f, 42f, 72f,72f);//设置页边距，上、下25.4毫米，即为72f，左、右31.8毫米，即为90f
		//设置PDF支持中文 
		BaseFont bfChinese=BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",false);
		//标题字体风格
		Font titleFont=new Font(bfChinese, 20,Font.BOLD);
		Table table=new Table(titleArray.length);
		table.setAutoFillEmptyCells(true);
		Image image=Image.getInstance(rootPath+"dist/img/login_logo.png");
		Paragraph par=new Paragraph();
		par.add(image);
		document.add(par);
		//正文字体风格
		Font font=setChineseFont(rootPath);
		Paragraph title=new Paragraph(wordTitle,titleFont);
		title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		title.setFont(titleFont);
		table.setPadding(2f);//设置间距
		table.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		table.setAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		document.add(title);

		//设置表格title	
		Cell cell=null;
		for (int i = 0; i < titleArray.length; i++) {
			cell=new Cell(new Paragraph(titleArray[i],font));
			cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
			cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
			cell.setHeader(true);
			cell.setBackgroundColor(Color.GRAY);
			table.addCell(cell);
		}
		//table.endHeaders();//设置每页显示表格title
		ExportCircuitBoard p=null;
		for (int i = 0; i <items.size(); i++) {
			p=items.get(i);
			cell=new Cell(String.valueOf(i+1));
			cell.setWidth(100);
			cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
			cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell=new Cell(p.getMaterielNo());
			table.addCell(cell);
			cell=new Cell(new Paragraph(p.getMaterielName(),font));
			table.addCell(cell);
			cell=new Cell(p.getModelSpecification());
			table.addCell(cell);
			String quantity=p.getQuantity()!=null ? p.getQuantity().toString() : "0";
			cell=new Cell(String.valueOf(quantity));
			cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
			cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell=new Cell(p.getComment());
			table.addCell(cell);
		}
		document.add(table);
		document.close();
   }
  
   public static void createPdfDocument(String rootPath,String realPath,String[] titleArray,List<ExportCircuitBoard> items,String titleName) throws DocumentException, IOException{
	   Document document=new Document(PageSize.A4,10,10,10,10);//设置页边距：左、右、上、下
	   PdfWriter write=PdfWriter.getInstance(document, new FileOutputStream(realPath));
	 document.open();
	//设置PDF支持中文 
	BaseFont bfChinese=BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",false);
	//标题字体风格
	Font titleFont=new Font(bfChinese, 20,Font.BOLD);
	Table table=new Table(titleArray.length);
	Image image=Image.getInstance(rootPath+"dist/img/login_logo.png");
	Paragraph par=new Paragraph();
	par.add(image);
	document.add(par);
	Paragraph title=new Paragraph(titleName,titleFont);
	title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
	title.setFont(titleFont);
	table.setPadding(2f);//设置间距
	table.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
	table.setAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
	document.add(title);
	Font font=setChineseFont(rootPath);
	//设置表格title	
	Cell cell=null;
	for (int i = 0; i < titleArray.length; i++) {
		cell=new Cell(new Paragraph(titleArray[i],font));
		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		cell.setHeader(true);
		cell.setBackgroundColor(Color.GRAY);
		table.addCell(cell);
	}
	//table.endHeaders();//设置每页显示表格title
	ExportCircuitBoard p=null;
	for (int i = 0; i <items.size(); i++) {
		p=items.get(i);
		cell=new Cell(String.valueOf(i+1));
		cell.setWidth(90);
		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		table.addCell(cell);
		cell=new Cell(p.getMaterielNo());
		table.addCell(cell);
		cell=new Cell(new Paragraph(p.getMaterielName(),font));
		table.addCell(cell);
		cell=new Cell(p.getModelSpecification());
		table.addCell(cell);
		String quantity=p.getQuantity()!=null ? p.getQuantity().toString() : "0";
		cell=new Cell(String.valueOf(quantity));
		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		table.addCell(cell);
		cell=new Cell(p.getComment());
		table.addCell(cell);
	}
	document.add(table);
	document.close();
	
   }
   /**
    * 维修报告
    * @param rootPath
    * @param realPath
    * @param titleArray
    * @param items
    * @param titleName
    * @throws DocumentException
    * @throws IOException
    */
   public static void createRepairPdfDocument(String rootPath,String realPath,String[] titleArray,ExportRepairRecorder repairRecorder,String titleName) throws DocumentException, IOException{
	   Document document=new Document(PageSize.A4,10,10,10,10);//设置页边距：左、右、上、下
	   PdfWriter write=PdfWriter.getInstance(document, new FileOutputStream(realPath));
	 document.open();
	//设置PDF支持中文 
	BaseFont bfChinese=BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",false);
	//标题字体风格
	Font titleFont=new Font(bfChinese, 20,Font.BOLD);
	Table table=new Table(4);
	Image image=Image.getInstance(rootPath+"dist/img/login_logo.png");
	//image.scaleAbsolute(100, 100);控制图片大小
	Paragraph par=new Paragraph();
	par.add(image);
	document.add(par);
	Paragraph title=new Paragraph(titleName,titleFont);
	title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
	title.setFont(titleFont);
	table.setPadding(2f);//设置间距
	table.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
	table.setAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
	document.add(title);
	//设置报告编号
	title=new Paragraph("报告编号："+repairRecorder.getRepairOrderNumber());
	document.add(title);
	Font font=setChineseFont(rootPath);
	//设置表格title	
	Cell cell=null;
	for (int i = 0; i < titleArray.length; i++) {
		cell=new Cell(new Paragraph(titleArray[i],font));
		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		cell.setHeader(true);
		cell.setBackgroundColor(Color.GRAY);
		table.addCell(cell);
		cell=new Cell(new Paragraph(""));
		table.addCell(cell);
	}
	//table.endHeaders();//设置每页显示表格title
	ExportCircuitBoard p=null;
//	for (int i = 0; i <items.size(); i++) {
//		p=items.get(i);
//		cell=new Cell(String.valueOf(i+1));
//		cell.setWidth(90);
//		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
//		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
//		table.addCell(cell);
//		cell=new Cell(p.getMaterielNo());
//		table.addCell(cell);
//		cell=new Cell(new Paragraph(p.getMaterielName(),font));
//		table.addCell(cell);
//		cell=new Cell(p.getModelSpecification());
//		table.addCell(cell);
//		String quantity=p.getQuantity()!=null ? p.getQuantity().toString() : "0";
//		cell=new Cell(String.valueOf(quantity));
//		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
//		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
//		table.addCell(cell);
//		cell=new Cell(p.getComment());
//		table.addCell(cell);
//	}
	document.add(table);
	document.close();
	
   }
   public static void createCircuitPdfDocument(String rootPath,String realPath,String[] titleArray,List<ExportCircuitBoard> items,String titleName) throws DocumentException, IOException{
	   Document document=new Document(PageSize.A4,10,10,10,10);//设置页边距：左、右、上、下
	   PdfWriter write=PdfWriter.getInstance(document, new FileOutputStream(realPath));
	 document.open();
	//设置PDF支持中文 
	BaseFont bfChinese=BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",false);
	//标题字体风格
	Font titleFont=new Font(bfChinese, 20,Font.BOLD);
	Table table=new Table(titleArray.length);
	Image image=Image.getInstance(rootPath+"dist/img/login_logo.png");
	Paragraph par=new Paragraph();
	par.add(image);
	document.add(par);
	Paragraph title=new Paragraph(titleName,titleFont);
	title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
	title.setFont(titleFont);
	table.setPadding(2f);//设置间距
	table.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
	table.setAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
	document.add(title);
	Font font=setChineseFont(rootPath);
	//设置表格title	
	Cell cell=null;
	for (int i = 0; i < titleArray.length; i++) {
		cell=new Cell(new Paragraph(titleArray[i],font));
		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		cell.setHeader(true);
		cell.setBackgroundColor(Color.GRAY);
		table.addCell(cell);
	}
	//table.endHeaders();//设置每页显示表格title
	ExportCircuitBoard p=null;
	for (int i = 0; i <items.size(); i++) {
		p=items.get(i);
		cell=new Cell(String.valueOf(i+1));
		cell.setWidth(90);
		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		table.addCell(cell);
		cell=new Cell(p.getMaterielNo());
		table.addCell(cell);
		cell=new Cell(new Paragraph(p.getMaterielName(),font));
		table.addCell(cell);
		cell=new Cell(p.getModelSpecification());
		table.addCell(cell);
		cell=new Cell(p.getFootprint());
		table.addCell(cell);
		cell=new Cell(p.getLocationNo());
		table.addCell(cell);
		String quantity=p.getQuantity()!=null ? p.getQuantity().toString() : "0";
		cell=new Cell(String.valueOf(quantity));
		cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.lowagie.text.Element.ALIGN_MIDDLE);
		table.addCell(cell);
		cell=new Cell(p.getComment());
		table.addCell(cell);
	}
	document.add(table);
	document.close();
	
   }
   public static void readPdfDocument(List<ExportCircuitBoard> items,String realPath) throws IOException{
//    
//	    PdfReader reader=new PdfReader(realPath);
//	    List list=SimpleBookmark.getBookmark(reader);
//	   for (java.util.Iterator i=list.iterator() ; i.hasNext();) {
//		ItextTools.showBookmark((Map) i.next());
//	}
//	  FileInputStream in=new FileInputStream(realPath);
//	  POIFSFileSystem pfs=new POIFSFileSystem(in);
//	  HWPFDocument hwpf=new HWPFDocument(pfs);
//	  Range range=hwpf.getRange();//获取文档的读取范围
//	  TableIterator it=new TableIterator(range);
//	  while(it.hasNext()){
//		  org.apache.poi.hwpf.usermodel.Table tb=it.next();
//		  for (int i = 0; i < tb.numRows(); i++) {//迭代行
//			TableRow tr=tb.getRow(i);
//			for (int j = 0; j < tr.numCells(); j++) {//迭代列
//				TableCell td=tr.getCell(j);
//				//System.out.println(td.text());
//				for (int k = 0; k < td.numParagraphs(); k++) {
//					org.apache.poi.hwpf.usermodel.Paragraph para=td.getParagraph(k);
//					String s=para.text();
//					//System.out.println(s.replaceAll("\r", "").replaceAll(" ","" ));
//				}
//			}
//		}
//	  }
	  
	  
   }
   /**
    * 导出维修报告，采用读取模板的方式
    * @param rootPath 项目根目录
    * @param realPath 新文档生成路径
    * @param picturePath 图片路径
    * @param repair
    * @throws DocumentException
    * @throws IOException
    */
   public static void createRepairPdfDocument(String rootPath,String realPath,String picturePath ,Map<String, String> map) throws DocumentException, IOException {
		String templatePath=rootPath+"dist/repairTemplate.pdf";
		String newPDFPath=realPath;
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		try {
			out=new FileOutputStream(newPDFPath);
			reader=new PdfReader(templatePath);//读取模板
			bos=new ByteArrayOutputStream();
			stamper=new PdfStamper(reader, bos);
			AcroFields form=stamper.getAcroFields();
			//设置中文
			BaseFont font= BaseFont.createFont(rootPath+"dist/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);//C:\\Windows\\Fonts\\STSONG.TTF
			ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
			fontList.add(font);
			form.setSubstitutionFonts(fontList);
		 Iterator<String> it=  form.getFields().keySet().iterator();
		int i=0;
		 while(it.hasNext()){
			String name=it.next().toString();
			switch (name) {
			case "faultPicture1":
			case "faultPicture2":
			case "faultPicture3":
			case "faultPicture4":
			case "processingPicture1":
			case "processingPicture2":
			case "processingPicture3":
			case "processingPicture4":
				String fileName=map.get(name);
				if(fileName!=null && StringUtils.isNotBlank(fileName)){
					Image pic=Image.getInstance(picturePath+"/"+map.get(name));
					float[] list = form.getFieldPositions(name);  
					PdfContentByte under=stamper.getOverContent(1);
					float x=list[1];
					float y=list[2];
					pic.scaleAbsolute(100, 70);//图片大小 90,70
					pic.setAbsolutePosition(x+5, y+5); 
					under.addImage(pic);  
				}
				
				break;

			default:
				form.setField(name, map.get(name));
				break;
			}

		}
		 
		stamper.setFormFlattening(true);//设置不可编辑
		stamper.close();
		Document doc=new Document();
		PdfCopy copy=new PdfCopy(doc, out);
		doc.open();
		PdfImportedPage importedPage=copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
		copy.addPage(importedPage);
		doc.close();
		System.out.println("生成成功！！！！！！！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
   public static void showBookmark(Map markBook){
	   ArrayList kids=(ArrayList) markBook.get("Kids");
	   if(kids==null){
		   return;
	   }
	   for(java.util.Iterator i=kids.iterator();i.hasNext();){
		  ItextTools.showBookmark((Map) i.next());
	   }
   }
   public static Font setChineseFont(String rootPath){
	   BaseFont bf=null;
	   Font font=null;
	try {///dymanager/WebRoot/dist/STSONG.TTF
		bf = BaseFont.createFont(rootPath+"dist/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);//C:\\Windows\\Fonts\\STSONG.TTF
		font=new Font(bf,12, Font.NORMAL);
	} catch (DocumentException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	  return font;
   }
   
   
   public static void main(String[] args) throws IOException {
	  // ItextTools.readPdfDocument(null,"E:\\xyl\\学习资料\\pack_List.docx");
//	try {
//		ItextTools.createWordDocument("F:\\test.doc", null, null);
//		System.out.println("生成成功");
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (DocumentException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
}
}
