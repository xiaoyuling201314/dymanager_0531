package com.dayuan.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.dayuan.util.Tools;
/**
 * 维修记录导出实体
 * @author xiaoyuling
 *
 */
public class ExportRepairRecorder{
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer id;
	
	private String repairOrderNumber;//维修单号
	
    private String productName;//产品名称

    private String instrumentFuselage;//机身码

    private String shippingDate;//出货日期
    
    private String warrantyDate;//保修日期：出货日期延后15天算起，1年保修
    
    private String faultDescription;//故障描述

    private String faultPicture;//故障图片
    
    private String processingMethod;//处理方法
    
    private String processingPicture;//处理图片
    
    private String repairMan;//维修人

    private String actualCompleteDate;//实际完成日期;
    
    private String sapNo;//仪器SAP代码
    
    private Integer quantity;//统计维修数量

    private String comments;//备注

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInstrumentFuselage() {
		return instrumentFuselage;
	}

	public void setInstrumentFuselage(String instrumentFuselage) {
		this.instrumentFuselage = instrumentFuselage;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	/**
	 * 计算保修期：保修期=发货日期+15+一年
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getWarrantyDate() {
		String dateStr=shippingDate;
		if(dateStr!=null){
			Date d=Tools.getDate(getShippingDate());
			  Calendar c=Calendar.getInstance();
			  c.set(Calendar.YEAR, d.getYear());
			  int totalDay=c.getActualMaximum(Calendar.DAY_OF_YEAR);
			  d.setDate(d.getDate()+15+totalDay);
			  return Tools.getDateString(d);
		}else
		return null;
	}

	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getFaultDescription() {
		return faultDescription;
	}

	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}

	public String getFaultPicture() {
		return faultPicture;
	}

	public void setFaultPicture(String faultPicture) {
		this.faultPicture = faultPicture;
	}

	public String getProcessingMethod() {
		return processingMethod;
	}

	public void setProcessingMethod(String processingMethod) {
		this.processingMethod = processingMethod;
	}

	public String getProcessingPicture() {
		return processingPicture;
	}

	public void setProcessingPicture(String processingPicture) {
		this.processingPicture = processingPicture;
	}

	public String getRepairMan() {
		return repairMan;
	}

	public void setRepairMan(String repairMan) {
		this.repairMan = repairMan;
	}

	public String getActualCompleteDate() {
		return actualCompleteDate;
	}

	public void setActualCompleteDate(String actualCompleteDate) {
		this.actualCompleteDate = actualCompleteDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRepairOrderNumber() {
		return repairOrderNumber;
	}

	public void setRepairOrderNumber(String repairOrderNumber) {
		this.repairOrderNumber = repairOrderNumber;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
  
}