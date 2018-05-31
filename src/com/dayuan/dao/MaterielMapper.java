package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Materiel;
/**
 * 物料信息DAO
 * @author xiaoyuling
 *
 */
public interface MaterielMapper extends BaseMapper<Materiel> {
	/**
	 * 查询所有的物料信息，用于导出
	 * @return
	 */
    public List<Materiel> queryAll(@Param("keys")String materielName);

	public Integer queryRecordCount(@Param("keys")String userName);

	public List<Materiel> queryByMaterielKeys(@Param("keys")String materielKeys);
	//根据sapNo
	public List<Materiel> queryBySapNoList(@Param("list")List<String> sapNoList);

	public List<String> queryAllMaterNo();
}