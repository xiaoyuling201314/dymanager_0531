package com.dayuan.dto;

import java.io.Serializable;

import com.dayuan.bean.Materiel;

/**
 * 整机BOM单详情信息表tbl_business_completemachinedetail
 * 
 * @author xiaoyuling
 * 
 */
public class CompletemachinedetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;// 序号

	private Integer compleId;

	private MaterielDTO materielNo;// 器件名称

	private Integer quantity;// 数量

	private String updateTime;// 创建,修改时间

	private String comment;// 备注

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public CompletemachinedetailDTO() {
		super();
	}

	public MaterielDTO getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(MaterielDTO materielNo) {
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

	public CompletemachinedetailDTO(int id, Integer compleId,
			MaterielDTO materielNo, Integer quantity, String comment) {
		super();
		this.id = id;
		this.compleId = compleId;
		this.materielNo = materielNo;
		this.quantity = quantity;
		this.comment = comment;
	}

	public CompletemachinedetailDTO(MaterielDTO materielNo, Integer quantity,String updateTime) {
		super();
		this.materielNo = materielNo;
		this.quantity = quantity;
		this.updateTime=updateTime;
	}

}