package com.dayuan.dao;

import java.util.List;

import com.dayuan.bean.CertificateType;
import com.dayuan.bean.MaterieType;

/**
 * 物料种类DAO接口
 * @author xiaoyuling
 *
 */
public interface MaterieTypeMapper extends BaseMapper<MaterieType> {
	/**
	 * 查询所有的物料种类信息
	 * @return
	 */
   public List<MaterieType> queryAllType();
   
}