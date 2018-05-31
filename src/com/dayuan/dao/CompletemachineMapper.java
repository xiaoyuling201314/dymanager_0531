package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Circuitboard;
import com.dayuan.bean.Completemachine;
/**
 * 整机BOM信息DAO
 * @author xiaoyuling
 *
 */
public interface CompletemachineMapper extends BaseMapper<Completemachine> {
	
	public 	Integer queryRecordCount(@Param("keys")String sapNo,@Param("completeName")String completeName);
	/**
	 * 根据SapNo和版本号查询资质证书，电路板,整机BOM单,装箱清单版本信息
	 * 装箱清单,说明书,设计文档等信息
	 * @param sapNo
	 * @return
	 */
	public List<Completemachine> queryList(@Param("keys")String sapNo,@Param("completeName")String boardName,@Param("curPage")int curPage,@Param("pageSize")int pageSize);
	//查询所有的BOM单名称
	public List<String> queryAllName(@Param("keys")String sapNo);

}