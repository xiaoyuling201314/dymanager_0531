package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Document;
import com.dayuan.bean.Manual;
/**
 * 设计文档信息DAO
 * @author xiaoyuling
 *
 */
public interface DocumentMapper extends BaseMapper<Document> {
	
	public Integer queryRecordCount(@Param("keys") String sapNo,@Param("documentName")String manualName);
	
	public List<Document> queryList(@Param("keys")String sapNo,@Param("documentName")String manualName,@Param("curPage")int curPage,@Param("pageSize")int pageSize);

	public List<String> queryAllName(@Param("keys")String sapNo);
	
}