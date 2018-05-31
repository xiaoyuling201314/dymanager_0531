package com.dayuan.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 电路板BOM单信息表tbl_business_circuitboard
 * 
 * @author xiaoyuling
 * 
 */
public class Circuitboard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String sapNo;//仪器SAP代码

	private String circuitBoardVersion;//电路板BOM单版本号

	private String circuitBoardSapNo;// 电路板Sap代码

	private String circuitBoardName;// 电路板BOM单名称

	private String createPerson;// 创建者

	private String updateTime;// 创建,修改时间

	private String revisedRecord;// 备注
	
	private List<CircuitboardDetail> circuitboardDetails;//电路板详情列表

	public List<CircuitboardDetail> getCircuitboardDetails() {
		return circuitboardDetails;
	}

	public void setCircuitboardDetails(List<CircuitboardDetail> circuitboardDetails) {
		this.circuitboardDetails = circuitboardDetails;
	}

	public Circuitboard() {
		super();
	}

	public String getCircuitBoardSapNo() {
		return circuitBoardSapNo;
	}

	public void setCircuitBoardSapNo(String circuitBoardSapNo) {
		this.circuitBoardSapNo = circuitBoardSapNo;
	}

	public String getCircuitBoardName() {
		return circuitBoardName;
	}

	public void setCircuitBoardName(String circuitBoardName) {
		this.circuitBoardName = circuitBoardName;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	

	public String getRevisedRecord() {
		return revisedRecord;
	}

	public void setRevisedRecord(String revisedRecord) {
		this.revisedRecord = revisedRecord;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public String getCircuitBoardVersion() {
		return circuitBoardVersion;
	}

	public void setCircuitBoardVersion(String circuitBoardVersion) {
		this.circuitBoardVersion = circuitBoardVersion;
	}

	public Circuitboard(Integer id, String sapNo, String circuitBoardVersion,
			String circuitBoardName, String createPerson, String updateTime,
			String revisedRecord) {
		super();
		this.id = id;
		this.sapNo = sapNo;
		this.circuitBoardVersion = circuitBoardVersion;
		this.circuitBoardName = circuitBoardName;
		this.createPerson = createPerson;
		this.updateTime = updateTime;
		this.revisedRecord = revisedRecord;
	}



}