package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Certificate;
/**
 * 仪器证书信息DAO
 * @author xiaoyuling
 *
 */
public interface CertificateMapper extends BaseMapper<Certificate> {
   
	public Integer queryRecordCount(@Param("keys")String sapNo);

	public List<String> queryAllName(@Param("keys")String sapNo);
}