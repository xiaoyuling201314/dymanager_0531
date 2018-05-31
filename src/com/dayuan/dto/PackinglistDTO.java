package com.dayuan.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 装箱清单tbl_business_packingList
 * 
 * @author xiaoyuling
 * 
 */
public class PackinglistDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String sapNo;// 仪器SAP代码
	
	private String packingListVersion;// 装箱清单版本号

	private String packingListName;// 装箱清单名称

	private String createPerson;// 修订者

	private String updateTime;// 修改时间
	
	private String revisedRecord;//修订记录

	private List<PackingListDetailDTO> pacListDetails;
	
	public List<PackingListDetailDTO> getPacListDetails() {
		return pacListDetails;
	}

	public void setPacListDetails(List<PackingListDetailDTO> pacListDetails) {
		this.pacListDetails = pacListDetails;
	}
	public String getPackingListVersion() {
		return packingListVersion;
	}

	public void setPackingListVersion(String packingListVersion) {
		this.packingListVersion = packingListVersion;
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

	public PackinglistDTO() {
		super();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRevisedRecord(String revisedRecord) {
		this.revisedRecord = revisedRecord;
	}

	public PackinglistDTO(String sapNo, String packingListVersion,
			String packingListName, String createPerson, String updateTime,
			String revisedRecord) {
		super();
		this.sapNo = sapNo;
		this.packingListVersion = packingListVersion;
		this.packingListName = packingListName;
		this.createPerson = createPerson;
		this.updateTime = updateTime;
		this.revisedRecord = revisedRecord;
	}

}