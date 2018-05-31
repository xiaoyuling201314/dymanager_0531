package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.CertificateDTO;

public interface ICertificateService extends IBaseService<CertificateDTO> {

	public Integer queryRecordCount(String sapNo);

	public List<String> queryAllName(String sapNo);

}
