package com.dayuan.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.ExportRepairRecorder;
import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.bean.RepairRecord;
import com.dayuan.bean.Shippingrecords;
import com.dayuan.dto.RepairRecordDTO;

public interface RepairRecordMapper extends BaseMapper<RepairRecord> {

	
	public List<RepairRecord> queryList(@Param("repairKeys")String shipKeys,@Param("state")int state,@Param("curPage")int curPage,@Param("pageSize")int pageSize);

	Integer queryRecordCount(@Param("repairKeys")String repairKeys);

	public List<String> queryAllRepairNumber();

	public ExportRepairRecorder queryExportById(@Param("repairOrderNumber")String repairOrderNumber);

	public Integer queryCountByShipId(String [] arrs);

	public Integer queryRecordCount(@Param("repairKeys")String repairKeys, @Param("state")Integer state);

	/**
	 * 统计返修数量，用于报表数据源
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ExportRepairRecorder> statisticsShip(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("percent")String percent);
	
	/**
	 * 统计返修数量，用于报表数据源
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ExportRepairRecorder> statisticsPieShip(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
}