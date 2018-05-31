package com.dayuan.dto;

import java.io.Serializable;

import com.dayuan.bean.Circuitboard;
import com.dayuan.bean.Materiel;

/**
 * 电路板BOm单详情信息表：tbl_business_circuitboarddetail
 * 
 * @author xiaoyuling
 * 
 */
public class CircuitboardDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;// 编号

	private Integer circuitId;

	private MaterielDTO materielNo;// 物料编号

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

	public MaterielDTO getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(MaterielDTO materielNo) {
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

	public CircuitboardDetailDTO() {
		super();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public CircuitboardDetailDTO(Integer id, Integer circuitId,
			MaterielDTO materielNo, String locationNo, Integer quantity,
			String comment) {
		super();
		this.id = id;
		this.circuitId = circuitId;
		this.materielNo = materielNo;
		this.locationNo = locationNo;
		this.quantity = quantity;
		this.comments = comment;
	}

	public CircuitboardDetailDTO(MaterielDTO materielNo, String locationNo,
			Integer quantity,String date) {
		super();
		this.materielNo = materielNo;
		this.locationNo = locationNo;
		this.quantity = quantity;
		this.updateTime=date;
	}

}