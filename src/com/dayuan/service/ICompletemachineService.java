package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.CompletemachineDTO;
/**
 * 整机BOM信息DAO
 * @author xiaoyuling
 *
 */
public interface ICompletemachineService extends IBaseService<CompletemachineDTO> {

 public	Integer queryRecordCount(String sapNo, String boardName);

 public List<String> queryAllName(String sapNo);
   
}