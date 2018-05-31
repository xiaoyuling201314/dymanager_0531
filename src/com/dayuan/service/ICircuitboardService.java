package com.dayuan.service;


import java.util.List;

import com.dayuan.dto.CircuitboardDTO;

/**
 * 电路板BOM信息DAO
 * @author xiaoyuling
 *
 */
public interface ICircuitboardService  extends IBaseService<CircuitboardDTO>{

	public Integer queryRecordCount(String sapNo, String boardName);

	public List<String> queryAllName(String sapNo);

}