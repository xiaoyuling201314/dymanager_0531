package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.CertificateDTO;
import com.dayuan.dto.CertificateTypeDTO;
/**
 * 2016-09-29
 * @author xiaoyuling
 *
 */
public interface ICertificateTypeService  {//extends IBaseService<CertificateDTO>
	/**
	 * 查询所有的资质类型信息
	 * @return
	 */
   public List<CertificateTypeDTO> queryAllType();
}