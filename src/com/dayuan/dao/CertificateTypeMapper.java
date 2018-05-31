package com.dayuan.dao;

import java.util.List;

import com.dayuan.bean.CertificateType;
/**
 * 资质类型DAO接口 
 * @author xiaoyuling
 *
 */
public interface CertificateTypeMapper {//extends BaseMapper<CertificateType> 
	/**
	 * 查询所有的资质类型信息
	 * @return
	 */
   public List<CertificateType> queryAllType();
   
}