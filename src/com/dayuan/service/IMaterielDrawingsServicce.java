package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.MaterielDrawingsDTO;

public interface IMaterielDrawingsServicce extends IBaseService<MaterielDrawingsDTO> {// extends
											// IBaseService<MaterielDrawingsDTO>
	/**
	 * 添加信息方法
	 * @param t
	 */
	public int add(MaterielDrawingsDTO t);

	/**
	 * 根据复合主键信息方法
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByCompositeId(String sapNo,String version);

	/**
	 * 查询该物料的所有图纸
	 * 
	 * @return
	 */
	public List<MaterielDrawingsDTO> queryDrawsById(String materielNo);

	/**
	 * 根据SapNo删除信息方法
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(String sapNo);
	
	public String queryLastDrawsById(String materielNo);

	public List<String> queryAllName(String sapNo);
}