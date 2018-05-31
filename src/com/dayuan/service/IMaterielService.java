package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.MaterielDTO;
/**
 * 物料信息DAO
 * @author xiaoyuling
 *
 */
public interface IMaterielService extends IBaseService<MaterielDTO> {
	 public List<MaterielDTO> queryAll(String materielName);

	public Integer queryRecordCount(String userName);
	/**
	 * 根据关键字查询物料信息，用于Bom单和装箱清单查询
	 * @param materielKeys
	 * @return
	 */
	public List<MaterielDTO> queryByMaterielKeys(String materielKeys);
	/**
	 * 根据sapNo列表查询物料信息
	 * @param sapNoList
	 * @return
	 */
	public List<MaterielDTO> queryBySapNoList(List<String> sapNoList);

	public List<String> queryAllMaterNo();
}