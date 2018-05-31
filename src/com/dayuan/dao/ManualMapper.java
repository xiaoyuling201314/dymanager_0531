package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Manual;
/**
 * 仪器说明书信息DAO
 * @author xiaoyuling
 *
 */
public interface ManualMapper extends BaseMapper<Manual>  {

	public Integer queryRecordCount(@Param("keys") String sapNo,@Param("manualName")String manualName);
	
	/**
	 * 根据SapNo和版本号查询资质证书，电路板,整机BOM单,装箱清单版本信息
	 * 装箱清单,说明书,设计文档等信息
	 * @param sapNo
	 * @return
	 */
	public List<Manual> queryList(@Param("keys")String sapNo,@Param("manualName")String manualName,@Param("curPage")int curPage,@Param("pageSize")int pageSize);

	public List<String> queryAllName(@Param("keys")String sapNo);
	
   
}