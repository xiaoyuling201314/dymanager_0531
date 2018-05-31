package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.ManualDTO;
/**
 * 仪器说明书信息DAO
 * @author xiaoyuling
 *
 */
public interface IManualService extends IBaseService<ManualDTO>  {

 public Integer queryRecordCount(String sapNo, String manualName);
//查询所有的说明书名称
public List<String> queryAllName(String sapNo);
   
}