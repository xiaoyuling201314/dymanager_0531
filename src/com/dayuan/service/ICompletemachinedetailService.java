package com.dayuan.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.dto.CompletemachinedetailDTO;

/**
 * 整机BOM详情DAO
 * @author xiaoyuling
 *
 */
public interface ICompletemachinedetailService extends IBaseService<CompletemachinedetailDTO> {

	List<CompletemachinedetailDTO> queryByBoardId(Integer id, String keys,
			Integer curPage, Integer pageSize);

	List<String> queryAllByBoardId(Integer compId);

	Integer queryRecordCount(Integer compleId, String object);

	List<ExportCircuitBoard> queryByCompleteIdList(Integer completeId);
   
}