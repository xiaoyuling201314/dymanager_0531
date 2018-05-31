package com.dayuan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.CircuitboardDetail;
import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.Materiel;
import com.dayuan.dto.CircuitboardDetailDTO;

/**
 * 电路板BOM详情DAO
 * @author xiaoyuling
 *
 */
public interface CircuitboardDetailMapper extends BaseMapper<CircuitboardDetail>  {

 //public	List<CircuitboardDetail> queryByBoardId(@Param("keyId")Integer boardId);
 public List<CircuitboardDetail> queryByBoardId(@Param("cirId")Integer id,@Param("keys")String keys, @Param("curPage")int curPage,@Param("pageSize")int pageSize);

public Integer queryRecordCount(@Param("cirId")Integer id, @Param("keys")String keys);
//根据电路板BOM编号查询详细物料编号
public List<String> queryAllByBoardId(@Param("cirId")Integer circuitId);

//根据sapNo
public List<ExportCircuitBoard> queryByCircuitIdNoList(@Param("cirId")Integer circuitId);
}