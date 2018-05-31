package com.dayuan.dto;

import java.io.Serializable;

/**
 * 证书类型
 * @author xiaoyuling
 *
 */
public class CertificateTypeDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer certificateNo;//证书编号

    private String certificateName;//证书类型

	public Integer getCertificateNo() {
		return certificateNo;
	}

	public CertificateTypeDTO() {
		super();
	}

	public CertificateTypeDTO(Integer certificateNo, String certificateName) {
		super();
		this.certificateNo = certificateNo;
		this.certificateName = certificateName;
	}

	public void setCertificateNo(Integer certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

    
}