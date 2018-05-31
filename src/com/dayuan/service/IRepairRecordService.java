package com.dayuan.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.ExportRepairRecorder;
import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.bean.Packinglist;
import com.dayuan.dto.PackinglistDTO;
import com.dayuan.dto.RepairRecordDTO;
/**
 * 装箱清单DAO
 * @author xiaoyuling
 *
 */
public interface IRepairRecordService extends IBaseService<RepairRecordDTO> {

	Integer queryRecordCount(String repairKeys);

	List<String> queryAllRepairNumber();

	ExportRepairRecorder queryExportById(String repairOrderNumber);
	
	public List<RepairRecordDTO> queryList(String repairKeys,int state,int curPage,int pageSize);

	Integer queryRecordCount(String repairKeys, Integer state);

	/**
	 * 统计返修数量，用于报表数据源
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ExportRepairRecorder> statisticsShip(String startTime,String endTime,String percent);
	
	/**
	 * 统计返修数量，用于报表数据源
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ExportRepairRecorder> statisticsPieShip(String startTime,String endTime);
	
}