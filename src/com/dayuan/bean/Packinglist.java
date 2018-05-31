package com.dayuan.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dayuan.dto.PackingListDetailDTO;

/**
 * 装箱清单tbl_business_packingList
 * 
 * @author xiaoyuling
 * 
 */
public class Packinglist implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	
	private String packingListVersion;// 装箱清单版本号

	private String sapNo;// 仪器SAP代码
	
	private String packingListName;// 装箱清单名称

	private String createPerson;// 修订者

	private String updateTime;// 修改时间
	
	private String revisedRecord;//修订记录
	
	private List<PackingListDetail> pacListDetails;
	
	public List<PackingListDetail> getPacListDetails() {
		return pacListDetails;
	}

	public void setPacListDetails(List<PackingListDetail> pacListDetails) {
		this.pacListDetails = pacListDetails;
	}

	public String getPackingListName() {
		return packingListName;
	}

	public void setPackingListName(String packingListName) {
		this.packingListName = packingListName;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPackingListVersion() {
		return packingListVersion;
	}

	public void setPackingListVersion(String packingListVersion) {
		this.packingListVersion = packingListVersion;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}


	public String getRevisedRecord() {
		return revisedRecord;
	}

	public void setRevisedRecord(String revisedRecord) {
		this.revisedRecord = revisedRecord;
	}

	public Packinglist() {
		super();
	}

	public Packinglist(Integer id, String packingListVersion, String sapNo,
			String packingListName, String createPerson, String updateTime,
			String revisedRecord) {
		super();
		this.id = id;
		this.packingListVersion = packingListVersion;
		this.sapNo = sapNo;
		this.packingListName = packingListName;
		this.createPerson = createPerson;
		this.updateTime = updateTime;
		this.revisedRecord = revisedRecord;
	}


}