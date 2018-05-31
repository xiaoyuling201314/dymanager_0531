package com.dayuan.bean;

import java.io.Serializable;
import java.util.Date;

public class MaterielDrawings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Integer id;

	private String materielNo;// 物料SAP代码

	private String version;// 版本号

	private String drawingName;// 图纸名称

	private String reviser;// 修订人

	private String updateTime;// 上传时间
	
	private String revisedRecord;// 修订记录
	
	public MaterielDrawings() {
		super();
	}
	
	public MaterielDrawings(Integer id, String materielNo, String version,
			String drawingName, String reviser, String updateTime,
			String revisedRecord) {
		super();
		this.id = id;
		this.materielNo = materielNo;
		this.version = version;
		this.drawingName = drawingName;
		this.reviser = reviser;
		this.updateTime = updateTime;
		this.revisedRecord = revisedRecord;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	public String getDrawingName() {
		return drawingName;
	}
	public String getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(String materielNo) {
		this.materielNo = materielNo;
	}
	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
	}

	public String getReviser() {
		return reviser;
	}

	public void setReviser(String reviser) {
		this.reviser = reviser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRevisedRecord() {
		return revisedRecord;
	}

	public void setRevisedRecord(String revisedRecord) {
		this.revisedRecord = revisedRecord;
	}

}