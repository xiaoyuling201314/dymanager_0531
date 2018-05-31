package com.dayuan.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dayuan.bean.RepairRecord;
/**
 * 出货记录DTO
 * @author xiaoyuling
 *
 */
public class ShippingrecordsDTO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;//编号

    private InstrumentinfoDTO sapNo;//仪器SAP码

    private String customer;//客户名称

    private Integer quantity;//数量

    private String instrumentFuselage;//机身码

    private String inspectionDate;//检验日期

    private String inspectionMan;//检验人

    private String shippingDate;//出货日期

    private String updateTime;//更新时间

    private String comments;//备注
    
    private String softwareVersion;//软件版本信息 add by xiaoyuling 2017-03-20
    
    private Integer countRepair;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public InstrumentinfoDTO getSapNo() {
		return sapNo;
	}

	public void setSapNo(InstrumentinfoDTO sapNo) {
		this.sapNo = sapNo;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer==null ? null : customer.trim();
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
		this.instrumentFuselage = instrumentFuselage==null ? null : instrumentFuselage.trim();
	}

	public String getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(String inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public String getInspectionMan() {
		return inspectionMan;
	}

	public void setInspectionMan(String inspectionMan) {
		this.inspectionMan = inspectionMan==null ? null : inspectionMan.trim();
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
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

//	public ShippingrecordsDTO(InstrumentinfoDTO sapNo, String customer,
//			Integer quantity, String instrumentFuselage, String inspectionDate,
//			String inspectionMan, String shippingDate,
//			String comments) {
//		super();
//		this.sapNo = sapNo;
//		this.customer = customer;
//		this.quantity = quantity;
//		this.instrumentFuselage = instrumentFuselage;
//		this.inspectionDate = inspectionDate;
//		this.inspectionMan = inspectionMan;
//		this.shippingDate = shippingDate;
//		this.comments = comments;
//	}
	//SAP代码 	客户名称	数量	出货日期	检验人	机身号	备注  updateTime
	public ShippingrecordsDTO(InstrumentinfoDTO sapNo, String customer,
			Integer quantity,String instrumentFuselage,String shippingDate,String inspectionMan,  
			String comments,String updateTime) {
		super();
		this.sapNo = sapNo;
		this.customer = customer;
		this.quantity = quantity;
		this.inspectionMan = inspectionMan;
		this.shippingDate = shippingDate;
		this.instrumentFuselage = instrumentFuselage;
		this.comments = comments;
		this.updateTime = updateTime;
	}
	public ShippingrecordsDTO(String customer,
			Integer quantity, String instrumentFuselage, String inspectionDate,
			String inspectionMan, String shippingDate,
			String comments) {
		super();
		this.customer = customer;
		this.quantity = quantity;
		this.instrumentFuselage = instrumentFuselage;
		this.inspectionDate = inspectionDate;
		this.inspectionMan = inspectionMan;
		this.shippingDate = shippingDate;
		this.comments = comments;
	}

	public ShippingrecordsDTO() {
		super();
	}

	public Integer getCountRepair() {
		return countRepair;
	}

	public void setCountRepair(Integer countRepair) {
		this.countRepair = countRepair;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}



}