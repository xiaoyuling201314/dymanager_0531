package com.dayuan.bean;

import java.io.Serializable;

/**
 * 整机BOM单详情信息表tbl_business_completemachinedetail
 * 
 * @author xiaoyuling
 * 
 */
public class Completemachinedetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;// 序号

	private Integer compleId;

	private String sapNo;// SAP代码

	private String completeMachineVersion;// 整机BOM单版本号

	private Materiel materielNo;// 器件名称

	private String quantity;// 数量

	private String updateTime;// 创建,修改时间

	private String comments;// 备注

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompleteMachineVersion() {
		return completeMachineVersion;
	}

	public void setCompleteMachineVersion(String completeMachineVersion) {
		this.completeMachineVersion = completeMachineVersion;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Completemachinedetail() {
		super();
	}

	public Materiel getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(Materiel materielNo) {
		this.materielNo = materielNo;
	}

	public Integer getCompleId() {
		return compleId;
	}

	public void setCompleId(Integer compleId) {
		this.compleId = compleId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Completemachinedetail(int id, Integer compleId, String sapNo,
			String completeMachineVersion, Materiel materielNo,
			String quantity, String comment) {
		super();
		this.id = id;
		this.compleId = compleId;
		this.sapNo = sapNo;
		this.completeMachineVersion = completeMachineVersion;
		this.materielNo = materielNo;
		this.quantity = quantity;
		this.comments = comment;
	}

}