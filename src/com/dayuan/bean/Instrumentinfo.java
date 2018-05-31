package com.dayuan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 仪器信息表tbl_business_instrumentinfo
 * 
 * @author xiaoyuling
 * 
 */
public class Instrumentinfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String sapNo;// SAP代码

	private String brand;// 品牌名称

	private String productName;// 产品名称

	private String productLinel;// 归属生产线
	
	private String listedTime;// 上市日期
	
	private String picture;//仪器图片

	private String executionStandard;// 执行标准
	
	private int state;//状态0=在售；1=退市

	private String delistingDate;// 退市日期

	private String updateTime;// 修改时间

	private String netWeight;//裸机重量

	private String netSize;//裸机尺寸

	private String grossWeight;//装箱重量

	private String grossSize;//装箱尺寸

	private String comment;// 备注
	
	private List<Certificate> cerList=new ArrayList<Certificate>();//资质证书列表
	
	private List<Circuitboard> cirList=new ArrayList<Circuitboard>();//电路板BOM单
	
	private List<Completemachine> completeList=new ArrayList<Completemachine>();//整机BOM单
	
	private List<Document>  docList=new ArrayList<Document>();//设计文档
	
	private List<Manual> manualList=new ArrayList<Manual>();//说明书列表
	
	private List<Packinglist>  pList=new ArrayList<Packinglist>();//装箱清单

	public Instrumentinfo() {
		super();
	}

	
	public Instrumentinfo(String sapNo, String brand, String productName,
			String productLinel, String listedTime, String picture,
			String executionStandard, int state, String delistingDate,
			String updateTime, String netWeight, String netSize,
			String grossWeight, String grossSize, String comment) {
		super();
		this.sapNo = sapNo;
		this.brand = brand;
		this.productName = productName;
		this.productLinel = productLinel;
		this.listedTime = listedTime;
		this.picture = picture;
		this.executionStandard = executionStandard;
		this.state = state;
		this.delistingDate = delistingDate;
		this.updateTime = updateTime;
		this.netWeight = netWeight;
		this.netSize = netSize;
		this.grossWeight = grossWeight;
		this.grossSize = grossSize;
		this.comment = comment;
	}


	public List<Certificate> getCerList() {
		return cerList;
	}

	public void setCerList(List<Certificate> cerList) {
		this.cerList = cerList;
	}

	public List<Circuitboard> getCirList() {
		return cirList;
	}

	public void setCirList(List<Circuitboard> cirList) {
		this.cirList = cirList;
	}

	public List<Completemachine> getCompleteList() {
		return completeList;
	}

	public void setCompleteList(List<Completemachine> completeList) {
		this.completeList = completeList;
	}

	public List<Document> getDocList() {
		return docList;
	}

	public void setDocList(List<Document> docList) {
		this.docList = docList;
	}

	public List<Manual> getManualList() {
		return manualList;
	}

	public void setManualList(List<Manual> manualList) {
		this.manualList = manualList;
	}

	public List<Packinglist> getpList() {
		return pList;
	}

	public void setpList(List<Packinglist> pList) {
		this.pList = pList;
	}
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductLinel() {
		return productLinel;
	}

	public void setProductLinel(String productLinel) {
		this.productLinel = productLinel;
	}

	public String getListedTime() {
		return listedTime;
	}

	public void setListedTime(String listedTime) {
		this.listedTime = listedTime;
	}

	public String getExecutionStandard() {
		return executionStandard;
	}

	public void setExecutionStandard(String executionStandard) {
		this.executionStandard = executionStandard;
	}

	public String getDelistingDate() {
		return delistingDate;
	}

	public void setDelistingDate(String delistingDate) {
		this.delistingDate = delistingDate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getNetSize() {
		return netSize;
	}

	public void setNetSize(String netSize) {
		this.netSize = netSize;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getGrossSize() {
		return grossSize;
	}

	public void setGrossSize(String grossSize) {
		this.grossSize = grossSize;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}