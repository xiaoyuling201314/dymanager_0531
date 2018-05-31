package com.dayuan.bean;

import java.io.Serializable;

/**
 * 装箱清单详情表tbl_business_packingListDetail
 * 
 * @author xiaoyuling
 * 
 */
public class PackingListDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer packId;

	private String packingListVersion;// 装箱清单版本号

	private String sapNo;// 仪器SAP代码

	private Materiel materielNo;// 物料SAP代码

	private Integer quantity;

	private String updateTime;// 创建,修改时间

	private String comments;// 备注

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public PackingListDetail() {
		super();
	}

	public Materiel getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(Materiel materielNo) {
		this.materielNo = materielNo;
	}

	public Integer getPackId() {
		return packId;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public PackingListDetail(Integer id, Integer packId,
			String packingListVersion, String sapNo, Materiel materielNo,
			Integer quantity, String comments) {
		super();
		this.id = id;
		this.packId = packId;
		this.packingListVersion = packingListVersion;
		this.sapNo = sapNo;
		this.materielNo = materielNo;
		this.quantity = quantity;
		this.comments = comments;
	}

}