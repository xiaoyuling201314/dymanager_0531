package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.MaterielDrawings;

public interface MaterielDrawingsMapper extends BaseMapper<MaterielDrawings> {// extends BaseMapper<MaterielDrawings> 
	/**
	 * 添加信息方法
	 * @param t
	 */
	public int add(MaterielDrawings t);

	/**
	 * 根据复合主键删除信息方法
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByCompositeId(@Param("materielNo")String sapNo,@Param("version")String version);

	/**
	 * 根据SapNo删除信息方法
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(String sapNo);
	
	/**
	 * 查询该物料的所有图纸
	 * 
	 * @return
	 */
	public List<MaterielDrawings> queryDrawsById(@Param("materielNo")String materielNo);

	public String queryLastDrawsById(String materielNo);

	public List<String> queryAllName(@Param("materielNo")String sapNo);
	
	

}