package com.dayuan.dto;

import java.io.Serializable;
import java.util.Date;

import com.dayuan.bean.CertificateType;
import com.dayuan.bean.Instrumentinfo;

/**
 * @author xiaoyuling
 * 
 */
public class CertificateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;// 编号

	private String sapNo;// 仪器SAP代码

	private String calibrationCertificate;// 证书名称

	private CertificateType certificateType;// 证书类型

	private String qualificationAttribution;// 资质归属

	private String calibStartTime;// 证书有效起始时间
	
	private String calibStartTimeStr;// 证书有效起始时间

	private String calibEndTime;// 有效结束时间
	
	private String reviser;// 修订者

	private String updateTime;// 修改时间

	private String validCalib;//证书状态0:正常，1：长期有效,2.已过期,3即将过期(三个月前提示)
	

	public String getValidCalib() {
		return validCalib;
	}

	public void setValidCalib(String validCalib) {
		this.validCalib = validCalib;
	}

	public String getCalibStartTimeStr() {
		return calibStartTimeStr;
	}

	public void setCalibStartTimeStr(String calibStartTimeStr) {
		this.calibStartTimeStr = calibStartTimeStr;
	}

	public CertificateDTO() {
		super();
	}

	public String getQualificationAttribution() {
		return qualificationAttribution;
	}

	public void setQualificationAttribution(String qualificationAttribution) {
		this.qualificationAttribution = qualificationAttribution;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCalibrationCertificate() {
		return calibrationCertificate;
	}

	public void setCalibrationCertificate(String calibrationCertificate) {
		this.calibrationCertificate = calibrationCertificate;
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

	public CertificateDTO(Integer id, String sapNo,
			String calibrationCertificate, CertificateType certificateType,
			String qualificationAttribution, String calibStartTime,
			String calibEndTime, String reviser, String updateTime) {
		super();
		this.id = id;
		this.sapNo = sapNo;
		this.calibrationCertificate = calibrationCertificate;
		this.certificateType = certificateType;
		this.qualificationAttribution = qualificationAttribution;
		this.calibStartTime = calibStartTime;
		this.calibEndTime = calibEndTime;
		this.reviser = reviser;
		this.updateTime = updateTime;
	}

	public String getCalibStartTime() {
		return calibStartTime;
	}

	public void setCalibStartTime(String calibStartTime) {
		this.calibStartTime = calibStartTime;
	}

	public String getCalibEndTime() {
		return calibEndTime;
	}

	public void setCalibEndTime(String calibEndTime) {
		this.calibEndTime = calibEndTime;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}


}