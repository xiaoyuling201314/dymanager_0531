package com.dayuan.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 出货记录实体
 * @author xiaoyuling
 *
 */
public class ExportShippingrecords{
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer id;
	
    private String sapNo;//仪器SAP码
    
    private String productName;//产品名称

    private String customer;//客户名称

    private Integer quantity;//数量

    private String instrumentFuselage;//机身码

    private String inspectionMan;//检验人

    private String shippingDate;//出货日期

    private String comments;//备注
    
    private Integer totalCount;//合计出货数量，用于报表
    
    private String softwareVersion;//软件版本信息 add by xiaoyuling 2017-03-20

	public String getSapNo() {
		return sapNo==null ? null : sapNo.trim();
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getInstrumentFuselage() {
		return instrumentFuselage;
	}

	public void setInstrumentFuselage(String instrumentFuselage) {
		this.instrumentFuselage = instrumentFuselage;
	}

	public String getInspectionMan() {
		return inspectionMan;
	}

	public void setInspectionMan(String inspectionMan) {
		this.inspectionMan = inspectionMan;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

  
}