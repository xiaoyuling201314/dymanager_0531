package com.dayuan.dto;

import java.io.Serializable;
import java.util.Date;

import com.dayuan.bean.Instrumentinfo;

/**
 * 仪器说明书信息表tbl_business_instructions
 * 
 * @author xiaoyuling
 * 
 */
public class ManualDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String sapNo;//仪器SAP代码

    private String version;//版本号
    
	private String fileName;// 说明书名称

	private String reviser;// 修订人

	private String updateTime;// 更新时间

	private String revisedRecord;// 修订记录

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReviser() {
		return reviser;
	}

	public void setReviser(String reviser) {
		this.reviser = reviser;
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

	public ManualDTO() {
		super();
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public ManualDTO(String sapNo, String version,
			String fileName, String reviser, String updateTime,
			String revisedRecord) {
		super();
		this.sapNo = sapNo;
		this.version = version;
		this.fileName = fileName;
		this.reviser = reviser;
		this.updateTime = updateTime;
		this.revisedRecord = revisedRecord;
	}

}