package com.dayuan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.MaterielDTO;

/**
 * 电路板BOM详情DAO
 * @author xiaoyuling
 *
 */
@Service("circuitboardDetailService")
public interface ICircuitboardDetailService extends IBaseService<CircuitboardDetailDTO> {

	public List<CircuitboardDetailDTO> queryByBoardId(Integer circuitId,String keys, int curPage,int pageSize);

	public Integer queryRecordCount(Integer id, String keys);
	//根据BOM编号查询详细列表
	public List<String> queryAllByBoardId(Integer circuitId);
	/**
	 * 根据sapNo列表查询物料信息
	 * @param sapNoList
	 * @return
	 */
	public List<ExportCircuitBoard> queryByCircuitIdNoList(Integer circuitId);
  
}