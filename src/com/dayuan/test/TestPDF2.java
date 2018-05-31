package com.dayuan.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfTable;


public class TestPDF2 {
		public static void main(String[] args) {
			String templatePath="C:\\Users\\xyl\\Desktop\\仪器维修报告3.pdf";
			String newPDFPath="E:\\工作文档\\newPDF.pdf";
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
				//form.setFieldProperty(arg0, arg1, arg2, arg3)
//				Image image=Image.getInstance(rootPath+"dist/img/login_logo.png");
//	              img.setAbsolutePosition(50, 680);  
//	             PdfContentByte over = stamper.getOverContent(1);  
//	              over.addImage(img);               
				String[] str={"001","实际不良图片","女","感恩的心","感谢有你","001","xiaoming","女","我们都有一个家，名字叫中华","兄弟姐妹都很多","爱我中华，健儿奋起步伐"};
			 Iterator<String> it=  form.getFields().keySet().iterator();
			int i=0;
			String str1="";
			 while(it.hasNext()){
				String name=it.next().toString();
				System.out.println(name);
				str1=str[i++];
				if(name.equals("fill_9")){
					Image pic=Image.getInstance("E:\\xyl\\dayuanManage\\02设计文档\\品质管理-流程图（修改v0.2）\\品质-流程图\\01.jpg");
					float[] list = form.getFieldPositions("fill_9");  
					PdfContentByte under=stamper.getOverContent(1);
					float x=list[1];
					float y=list[2];
					pic.scaleAbsolute(80, 100);
					pic.setAbsolutePosition(x+20, y+20);  
					under.addImage(pic);  
					Image pic2=Image.getInstance("E:\\xyl\\dayuanManage\\02设计文档\\品质管理-流程图（修改v0.2）\\品质-流程图\\02.jpg");
					pic2.scaleAbsolute(80, 100);
					pic2.setAbsolutePosition(x+pic.absoluteX(),y+20);
					under.addImage(pic2);  
					 form.setField(name, str1);
				}else
					form.setField(name, str1);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		public static void insertImage(PdfStamper ps,AcroFields s){
//			try {
//				List<AcroFields.FieldPosition> list = s.getFieldPositions("QR_CODE");  
//				PdfContentByte under=ps.getOverContent(1);
//				Rectangle signRect = list.get(0).position;  
//				float x=signRect.getLeft();
//				float y=signRect.getBottom();
//				image.setAbsolutePosition(x, y);  
//				image.scaleToFit(signRect.getWidth(), signRect.getHeight());  
//				  
//				  
//				under.addImage(image);  
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//		/** 
//		* 创建Chunk 
//		*  
//		* @return 
//		* @author WangMeng 
//		* @date 2016年6月16日 
//		*/  
//		public static Chunk CreateChunk()  
//		{  
//		BaseFont bfChinese;  
//		Chunk chunk = null;  
//		try  
//		{  
//		bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);  
//		Font fontChinese = new Font(bfChinese, 10 * 4 / 3);  
//		chunk = new Chunk("张三", fontChinese);  
//		}  
//		catch (Exception e)  
//		{  
//		e.printStackTrace();  
//		}  
//		  
//		  
//		return chunk;  
//		}  
//		/** 
//		* 插入文本 
//		*  
//		* @return 
//		* @author WangMeng 
//		* @date 2016年6月16日 
//		*/  
//		public static void insertText(PdfStamper ps, AcroFields s)  
//		{  
//		float[] list = s.getFieldPositions("CONNECT_NAME");  
//		PdfContentByte cb = ps.getOverContent(1);  
//		PdfPTable table = new PdfPTable(1);  
//		float tatalWidth =list[1] - list[2] - 1;  
//		table.setTotalWidth(tatalWidth);  
//		  
//		  
//		PdfPCell cell = new PdfPCell(new Phrase(CreateChunk()));  
//		cell.setFixedHeight(list[3] - list[4] - 1);  
//		cell.setBorderWidth(0);  
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
//		cell.setHorizontalAlignment(Element.ALIGN_CENTER);  
//		cell.setLeading(0, (float) 1.1);  
//		  
//		  
//		table.addCell(cell);  
//		table.writeSelectedRows(0, -1, rect.getLeft(), rect.getTop(), cb);  
//		}  
//		
}
