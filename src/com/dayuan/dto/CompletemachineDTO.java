package com.dayuan.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dayuan.bean.Completemachinedetail;

/**
 * 整机BOM信息表tbl_business_completemachine
 * 
 * @author xiaoyuling
 * 
 */
public class CompletemachineDTO implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	 
	private String sapNo;//仪器SAP代码

	private String completeMachineVersion;//整机BOM单版本号

	private String completeMachineName;//整机BOM单名称;

	private String createPerson;// 创建者

	private String updateTime;// 创建,修改时间

	private String revisedRecord;// 备注

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

	public String getCompleteMachineVersion() {
		return completeMachineVersion;
	}

	public void setCompleteMachineVersion(String completeMachineVersion) {
		this.completeMachineVersion = completeMachineVersion;
	}

	public String getCompleteMachineName() {
		return completeMachineName;
	}

	public void setCompleteMachineName(String completeMachineName) {
		this.completeMachineName = completeMachineName;
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

	public CompletemachineDTO() {
		super();
	}

	public CompletemachineDTO(Integer id, String sapNo,
			String completeMachineVersion, String completeMachineName,
			String createPerson, String updateTime, String revisedRecord) {
		super();
		this.id = id;
		this.sapNo = sapNo;
		this.completeMachineVersion = completeMachineVersion;
		this.completeMachineName = completeMachineName;
		this.createPerson = createPerson;
		this.updateTime = updateTime;
		this.revisedRecord = revisedRecord;
	}


}