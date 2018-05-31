package com.dayuan.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 维修记录实体
 * @author xiaoyuling
 *
 */
public class RepairRecord implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String repairOrderNumber;//维修单号

    private String instrumentFuselage;//机身码

    private Shippingrecords shipmentNo;//出货记录编号

    private String faultDescription;//故障描述

    private String faultPicture;//故障图片
    
    private String receivedDate;//接收日期

    private String planCompleteDate;//计划完成日期

    private Integer state;//状态

    private String processingMethod;//处理方法

    private String processingPicture;//处理图片

    private String actualCompleteDate;//实际完成日期

    private String repairMan;//维修人

    private String updateTime;//更新时间

    private String comments;//备注
    
    private String sapNo;//仪器sapNo,用于处理历史出货记录的返修统计

    private String productName;//保存仪器名称
    
	public String getRepairOrderNumber() {
		return repairOrderNumber;
	}

	public void setRepairOrderNumber(String repairOrderNumber) {
		this.repairOrderNumber = repairOrderNumber==null ? null :repairOrderNumber.trim();
	}

	public String getInstrumentFuselage() {
		return instrumentFuselage;
	}

	public void setInstrumentFuselage(String instrumentFuselage) {
		this.instrumentFuselage = instrumentFuselage==null ? null : instrumentFuselage.trim();
	}


	public String getFaultDescription() {
		return faultDescription;
	}

	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription==null ? null : faultDescription.trim();
	}

	public String getFaultPicture() {
		return faultPicture;
	}

	public void setFaultPicture(String faultPicture) {
		this.faultPicture = faultPicture==null ? null : faultPicture.trim();
	}

	public String getPlanCompleteDate() {
		return planCompleteDate;
	}

	public void setPlanCompleteDate(String planCompleteDate) {
		this.planCompleteDate = planCompleteDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getProcessingMethod() {
		return processingMethod;
	}

	public void setProcessingMethod(String processingMethod) {
		this.processingMethod = processingMethod ==null ? null : processingMethod.trim();
	}

	public String getProcessingPicture() {
		return processingPicture;
	}

	public void setProcessingPicture(String processingPicture) {
		this.processingPicture = processingPicture ==null ? null :processingPicture.trim();
	}

	public String getActualCompleteDate() {
		return actualCompleteDate;
	}

	public void setActualCompleteDate(String actualCompleteDate) {
		this.actualCompleteDate = actualCompleteDate;
	}

	public String getRepairMan() {
		return repairMan;
	}

	public void setRepairMan(String repairMan) {
		this.repairMan = repairMan==null ? null : repairMan.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments==null ? null : comments.trim();
	}

	public Shippingrecords getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(Shippingrecords shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate ==null ? null : receivedDate.trim();
	}

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

  
}