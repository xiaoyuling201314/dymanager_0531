package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Completemachinedetail;
import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.PackingListDetail;
/**
 * 装箱清单详情DAO
 * @author xiaoyuling
 *
 */
public interface PackingListDetailMapper extends BaseMapper<PackingListDetail> {

	 public List<PackingListDetail> queryByBoardId(@Param("packId")Integer id,@Param("keys")String keys, @Param("curPage")int curPage,@Param("pageSize")int pageSize);

	 public Integer queryRecordCount(@Param("packId")Integer id, @Param("keys")String keys);
	 //根据电路板BOM编号查询详细物料编号
	 public List<String> queryAllByBoardId(@Param("packId")Integer circuitId);

	 //根据sapNo
	 public List<ExportCircuitBoard> queryBypackIdList(@Param("packId")Integer packpId);
  
}