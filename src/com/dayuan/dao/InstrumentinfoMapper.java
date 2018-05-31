package com.dayuan.dao;


import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Instrumentinfo;
/**
 * 仪器信息DAO
 * @author xiaoyuling
 *
 */
public interface InstrumentinfoMapper extends BaseMapper<Instrumentinfo> {

	public Integer queryRecordCount(@Param("keys")String userName);
 
}