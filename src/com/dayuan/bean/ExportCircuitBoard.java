package com.dayuan.bean;

public class ExportCircuitBoard {
	private String materielNo;// 物料编号

	private String materielName;// 物料名称

	private String modelSpecification;// 型号规格

	private String footprint;// 封装
	
	private String locationNo;// 位号

	private Integer quantity;// 数量

	private String comment;// 备注

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(String materielNo) {
		this.materielNo = materielNo;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getModelSpecification() {
		return modelSpecification;
	}

	public void setModelSpecification(String modelSpecification) {
		this.modelSpecification = modelSpecification;
	}

	public String getFootprint() {
		return footprint;
	}

	public void setFootprint(String footprint) {
		this.footprint = footprint;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

}
