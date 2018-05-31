package com.dayuan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dayuan.bean.CircuitboardDetail;
import com.dayuan.bean.MaterielDrawings;
import com.dayuan.util.Tools;

/**
 * 物料信息表tbl_ business _materiel
 * 
 * @author xiaoyuling
 * 
 */
public class MaterielDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String materielNo;// 物料编号

	private String materielName;// 物料名称

	private MaterieTypeDTO materielTypeId;// 物料类型

	private String modelSpecification;// 型号规格

	private String footprint;// 封装

	private String picture;// 图片

	private String comment;// 备注
	
	private String updateTime;// 创建,修改时间
	
	private String mtypeName;//物料类型名称，用于物料导出时使用
	
	private String  drawingsStr;//用于导出物料图纸时描述物料图纸详情信息
	
	private String showDrawings;//物料列表显示的图纸名称
	
	//private String firsPicture;//显示图片信息
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getShowDrawings() {
		return showDrawings;
	}

	public void setShowDrawings(String showDrawings) {
		this.showDrawings = showDrawings;
	}
	private List<MaterielDrawingsDTO> drawings=new ArrayList<MaterielDrawingsDTO>();//图纸列表
	
	public String getDrawingsStr() {
		String str="";
		if(drawings.size()>0){
			for (MaterielDrawingsDTO maDrawingsDTO : drawings) {
				if(str.equals("")){
					str+=maDrawingsDTO.getDrawingName()+"--"+maDrawingsDTO.getVersion()+"--"+maDrawingsDTO.getReviser()+"--"+maDrawingsDTO.getRevisedRecord()+"--"+maDrawingsDTO.getUpdateTime();
				}else{//\r\n
					str+=","+maDrawingsDTO.getDrawingName()+"--"+maDrawingsDTO.getVersion()+"--"+maDrawingsDTO.getReviser()+"--"+maDrawingsDTO.getRevisedRecord()+"--"+maDrawingsDTO.getUpdateTime();
				}
			}
		}
		return str;
	}

	public void setDrawingsStr(String drawingsStr) {
		this.drawingsStr = drawingsStr;
	}

	public String getMtypeName() {
		return mtypeName;
	}

	public void setMtypeName(String mtypeName) {
		this.mtypeName = mtypeName;
	}

	
	public MaterielDTO() {
		super();
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


	public MaterieTypeDTO getMaterielTypeId() {
		return materielTypeId;
	}

	public void setMaterielTypeId(MaterieTypeDTO materielTypeId) {
		this.materielTypeId = materielTypeId;
	}

	public List<MaterielDrawingsDTO> getDrawings() {
		return drawings!=null ? drawings : new ArrayList<MaterielDrawingsDTO>();
	}

	public void setDrawings(List<MaterielDrawingsDTO> drawings) {
		this.drawings = drawings;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public MaterielDTO(String materielNo, String materielName,
			MaterieTypeDTO materielTypeId, String modelSpecification,
			String footprint, String picture, String comment,
			List<MaterielDrawingsDTO> drawings) {
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
	public MaterielDTO(String materielNo, String materielName,
			MaterieTypeDTO materielTypeId, String modelSpecification,
			String footprint, String picture,String updateTime, String comment) {
		super();
		this.materielNo = materielNo;
		this.materielName = materielName;
		this.materielTypeId = materielTypeId;
		this.modelSpecification = modelSpecification;
		this.footprint = footprint;
		this.picture = picture;
		this.updateTime=updateTime;
		this.comment = comment;
	}

//	public String getFirsPicture() {
//		String [] picarray=picture.split(",");
//		firsPicture=picarray[picarray.length-1];
//		return firsPicture;
//	}

}