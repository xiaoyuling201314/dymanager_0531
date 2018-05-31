package com.dayuan.test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dayuan.util.ItextTools;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;


public class TestPDF {
		public static void main(String[] args) {
			String templatePath="C:\\Users\\xyl\\Desktop\\仪器维修报告4.pdf";
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
				//设置中文
				BaseFont font= BaseFont.createFont("E:\\apache-tomcat-8.5.5\\webapps\\dymanager\\"+"dist/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);//C:\\Windows\\Fonts\\STSONG.TTF
				ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
				fontList.add(font);
				form.setSubstitutionFonts(fontList);
				String[] str={"001dfffffffffffffffffffffffffffffffffffffffffffffffff的四个四个认同和推广计划开回家看见没听到过事实上事实上事实上事实上输卵管的发生的房间卡啊啊啊啊啊啊啊啊啊啊啊啊阿杜法规和萨股东和嘎嘎嘎灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌个电话案说法忽视的更好多岁嘎哈和独爱点水傲骨方法反反复复方法反反复复方法反反复复方法反反复复方法反反复复方法反反复复反复反复复发的覅是有够is而安东尼吕珍九小碧池V红烧豆腐孩子来的技术开发方法是刚刚灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌哥哥干啥事都","实际不良图片是刚刚灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌热鹅鹅鹅饿鹅鹅鹅饿告诉爸爸不不不不不不不不不不不不不不不不不不不不不不不不不不不不不不发的事实上事实上事实上事实上事实上事实上事实上事实上手工的方法反反复复方法反反复复凤飞飞反复发生","女","感恩的心","感谢有你","001是否该是多少度刚刚灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌哥哥","xiaoming","女","我们都有一个家，名字叫中华","兄弟姐妹都很多","爱我中华，健儿奋起步伐","fdgds事实上事实上事实上事实上事实上事实上事实上事实上","fdgds","fdgds","fdgds","fdgds","fdgds","fdgds"};
			 Iterator<String> it=  form.getFields().keySet().iterator();
			int i=0;
			//String str1="";
			 while(it.hasNext()){
				String name=it.next().toString();
				System.out.println(name);
				//str1=str[i++];
				if(name.equals("fill_21")){
					Image pic=Image.getInstance("E:\\xyl\\dayuanManage\\02设计文档\\品质管理-流程图（修改v0.2）\\品质-流程图\\01.jpg");
//				   pic.setDpi(100, 100);
//				   pic.setBorderWidth(200);
//				   pic.scaleAbsolute(80, 100);
					float[] list = form.getFieldPositions("fill_21");  
					PdfContentByte under=stamper.getOverContent(1);
					float x=list[1];
					float y=list[2];
					pic.scaleAbsolute(90, 70);//图片大小
					pic.setAbsolutePosition(x+5, y+5);  
					under.addImage(pic);  
//					Image pic2=Image.getInstance("E:\\xyl\\dayuanManage\\02设计文档\\品质管理-流程图（修改v0.2）\\品质-流程图\\02.jpg");
//					pic2.scaleAbsolute(80, 100);
//					pic2.setAbsolutePosition(x+pic.absoluteX(),y+20);
//					under.addImage(pic2);  
					 //form.setField(name, str[i++]);
				}else
					form.setField(name, str[i++]);
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
}
