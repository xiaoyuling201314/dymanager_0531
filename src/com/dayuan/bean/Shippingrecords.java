package com.dayuan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 出货记录实体
 * @author xiaoyuling
 *
 */
public class Shippingrecords implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;//编号

    private Instrumentinfo sapNo;//仪器SAP码

    private String customer;//客户名称

    private Integer quantity;//数量

    private String instrumentFuselage;//机身码

    private String inspectionDate;//检验日期

    private String inspectionMan;//检验人

    private String shippingDate;//出货日期

    private String updateTime;//更新时间

    private String comments;//备注
    
    private String softwareVersion;//软件版本信息 add by xiaoyuling 2017-03-20

    private List<RepairRecord> repairList;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Instrumentinfo getSapNo() {
		return sapNo;
	}

	public void setSapNo(Instrumentinfo sapNo) {
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

	public List<RepairRecord> getRepairList() {
		return repairList;
	}

	public void setRepairList(List<RepairRecord> repairList) {
		this.repairList =repairList;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

}