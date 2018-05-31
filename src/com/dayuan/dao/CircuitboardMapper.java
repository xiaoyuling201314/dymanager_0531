package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Certificate;
import com.dayuan.bean.Circuitboard;
import com.dayuan.bean.Manual;

/**
 * 电路板BOM信息DAO
 * @author xiaoyuling
 *
 */
public interface CircuitboardMapper  extends BaseMapper<Circuitboard>{

	public 	Integer queryRecordCount(@Param("keys")String sapNo,@Param("boardName")String boardName);
	/**
	 * 根据SapNo和版本号查询资质证书，电路板,整机BOM单,装箱清单版本信息
	 * 装箱清单,说明书,设计文档等信息
	 * @param sapNo
	 * @return
	 */
	public List<Circuitboard> queryList(@Param("keys")String sapNo,@Param("boardName")String boardName,@Param("curPage")int curPage,@Param("pageSize")int pageSize);
	
	public List<String> queryAllName(@Param("keys")String sapNo);
	
}