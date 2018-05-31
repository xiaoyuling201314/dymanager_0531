package com.dayuan.bean;

import java.io.Serializable;

/**
 * 电路板BOm单详情信息表：tbl_business_circuitboarddetail
 * 
 * @author xiaoyuling
 * 
 */
public class CircuitboardDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;// 编号

	private Integer circuitId;// 电路板BOM单编号

	private String sapNo;// 仪器SAP代码

	private String circuitBoardVersion;// 电路板BOM单版本号

	private Materiel materielNo;// 物料编号

	private String locationNo;// 位号

	private Integer quantity;// 数量

	private String updateTime;// 创建,修改时间

	private String comments;// 备注

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

	public Materiel getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(Materiel materielNo) {
		this.materielNo = materielNo;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getCircuitId() {
		return circuitId;
	}

	public void setCircuitId(Integer circuitId) {
		this.circuitId = circuitId;
	}

	public CircuitboardDetail() {
		super();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}