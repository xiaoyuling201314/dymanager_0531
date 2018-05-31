package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Completemachine;
import com.dayuan.bean.Packinglist;
/**
 * 装箱清单DAO
 * @author xiaoyuling
 *
 */
public interface PackinglistMapper extends BaseMapper<Packinglist> {
	public 	Integer queryRecordCount(@Param("keys")String sapNo,@Param("packingListName")String completeName);
	/**
	 * 根据SapNo和版本号查询资质证书，电路板,整机BOM单,装箱清单版本信息
	 * 装箱清单,说明书,设计文档等信息
	 * @param sapNo
	 * @return
	 */
	public List<Packinglist> queryList(@Param("keys")String sapNo,@Param("packingListName")String boardName,@Param("curPage")int curPage,@Param("pageSize")int pageSize);
	
	public List<String> queryAllName(@Param("keys")String sapNo);

}