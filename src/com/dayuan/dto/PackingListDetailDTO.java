package com.dayuan.dto;

import java.io.Serializable;

import com.dayuan.bean.Materiel;

/**
 * 装箱清单详情表tbl_business_packingListDetail
 * 
 * @author xiaoyuling
 * 
 */
public class PackingListDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer packId;

	private String packingListVersion;// 装箱清单版本号

	private String sapNo;// 仪器SAP代码

	private MaterielDTO materielNo;// 物料SAP代码

	private Integer quantity;

	private String updateTime;// 创建,修改时间

	private String comment;// 备注

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

	public PackingListDetailDTO() {
		super();
	}

	public MaterielDTO getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(MaterielDTO materielNo) {
		this.materielNo = materielNo;
	}

	public Integer getPackId() {
		return packId;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public PackingListDetailDTO(Integer id, Integer packId,
			String packingListVersion, String sapNo, MaterielDTO materielNo,
			Integer quantity, String comment) {
		super();
		this.id = id;
		this.packId = packId;
		this.packingListVersion = packingListVersion;
		this.sapNo = sapNo;
		this.materielNo = materielNo;
		this.quantity = quantity;
		this.comment = comment;
	}

	public PackingListDetailDTO(MaterielDTO materielNo, Integer quantity,String updateTime) {
		super();
		this.materielNo = materielNo;
		this.quantity = quantity;
		this.updateTime=updateTime;
	}

}