package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.CircuitboardDetail;
import com.dayuan.bean.Completemachinedetail;
import com.dayuan.bean.ExportCircuitBoard;

/**
 * 整机BOM详情DAO
 * @author xiaoyuling
 *
 */
public interface CompletemachinedetailMapper extends BaseMapper<Completemachinedetail> {

	
	 public List<Completemachinedetail> queryByBoardId(@Param("completeId")Integer id,@Param("keys")String keys, @Param("curPage")int curPage,@Param("pageSize")int pageSize);

	 public Integer queryRecordCount(@Param("completeId")Integer id, @Param("keys")String keys);
	 //根据电路板BOM编号查询详细物料编号
	 public List<String> queryAllByBoardId(@Param("completeId")Integer circuitId);

	 //根据sapNo
	 public List<ExportCircuitBoard> queryByCompleteIdList(@Param("completeId")Integer completeId);//@Param("list")List<String> sapNoList
   
}