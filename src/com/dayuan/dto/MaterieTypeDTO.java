package com.dayuan.dto;

import java.io.Serializable;

public class MaterieTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer materielTypeId;//物料类型编号

    private String materielTypeName;//物料类型名称

	public MaterieTypeDTO() {
		super();
	}

	public Integer getMaterielTypeId() {
		return materielTypeId;
	}

	public void setMaterielTypeId(Integer materielTypeId) {
		this.materielTypeId = materielTypeId;
	}

	public String getMaterielTypeName() {
		return materielTypeName;
	}

	public void setMaterielTypeName(String materielTypeName) {
		this.materielTypeName = materielTypeName;
	}

	public MaterieTypeDTO(Integer materielTypeId, String materielTypeName) {
		super();
		this.materielTypeId = materielTypeId;
		this.materielTypeName = materielTypeName;
	}

  
}