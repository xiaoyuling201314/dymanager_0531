package com.dayuan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dayuan.dto.MaterielDrawingsDTO;

/**
 * 物料信息表tbl_ business _materiel
 * 
 * @author xiaoyuling
 * 
 */
public class Materiel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String materielNo;// 物料编号

	private String materielName;// 物料名称

	private MaterieType materielTypeId;// 物料类型

	private String modelSpecification;// 型号规格

	private String footprint;// 封装

	private String picture;// 图片
	
	private String updateTime;// 创建,修改时间

	private String comment;// 备注
	
	private String mtypeName;//物料类型名称，用于物料导出时使用
	
	public String getMtypeName() {
		return mtypeName;
	}

	public void setMtypeName(String mtypeName) {
		this.mtypeName = mtypeName;
	}

	private List<MaterielDrawings> drawings;//图纸列表

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<MaterielDrawings> getDrawings() {
		return drawings!=null ? drawings : new ArrayList<MaterielDrawings>();
	}

	public void setDrawings(List<MaterielDrawings> drawings) {
		this.drawings = drawings;
	}

	
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Materiel(String materielNo, String materielName,
			MaterieType materielTypeId, String modelSpecification,
			String footprint, String picture, String comment,
			List<MaterielDrawings> drawings) {
		super();
		this.materielNo = materielNo;
		this.materielName = materielName;
		this.materielTypeId = materielTypeId;
		this.modelSpecification = modelSpecification;
		this.footprint = footprint;
		this.picture = picture;
		this.comment = comment;
		this.drawings = drawings;
	}

	public MaterieType getMaterielTypeId() {
		return materielTypeId;
	}

	public void setMaterielTypeId(MaterieType materielTypeId) {
		this.materielTypeId = materielTypeId;
	}

	public Materiel() {
		super();
	}

}