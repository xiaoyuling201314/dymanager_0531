package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.dozer.MappingException;
import org.springframework.stereotype.Service;

import com.dayuan.bean.ExportRepairRecorder;
import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.bean.PaginationData;
import com.dayuan.bean.RepairRecord;
import com.dayuan.bean.Shippingrecords;
import com.dayuan.dto.RepairRecordDTO;
import com.dayuan.dto.ShippingrecordsDTO;
import com.dayuan.service.IRepairRecordService;
@Service("repairService")
public class RepairRecordServiceImpl extends BaseSesrvice implements IRepairRecordService {

	@Override
	public int add(RepairRecordDTO repairRecordDTO) {
		int count=0;
		if(repairRecordDTO!=null){
			RepairRecord repairRecord=new RepairRecord();
			mapper.map(repairRecordDTO, repairRecord);
			count=repairMapper.add(repairRecord);
		}
		return count;
	}

	@Override
	public int addBySelective(RepairRecordDTO repairRecordDTO) {
		int count=0;
		if(repairRecordDTO!=null){
			RepairRecord repairRecord=new RepairRecord();
			mapper.map(repairRecordDTO, repairRecord);
			count=repairMapper.addBySelective(repairRecord);
		}
		return count;
	}

	@Override
	public int deleteById(Object id) {
		return repairMapper.deleteById(id);
	}

	@Override
	public int updateById(RepairRecordDTO reDto) {
		int count=0;
		try {
			RepairRecord repair=new RepairRecord();
			mapper.map(reDto, repair);
			count=repairMapper.updateById(repair);
		} catch (Exception e) {
			count=-1;
			System.out.println(e.getStackTrace());
		}
		return count;
	}

	@Override
	public int updateBySelective(RepairRecordDTO reDto) {
		int count=0;
		try {
			RepairRecord repair=new RepairRecord();
			mapper.map(reDto, repair);
			count=repairMapper.updateBySelective(repair);
		} catch (Exception e) {
			count=-1;
			System.out.println(e.getStackTrace()+e.getMessage());
		}
		return count;
	}

	@Override
	public RepairRecordDTO queryById(Object id) {
		RepairRecord repairRecord=repairMapper.queryById(id);
		RepairRecordDTO reDto=null;
		if(repairRecord!=null){
			reDto=new RepairRecordDTO();
			mapper.map(repairRecord, reDto);
		}
		return reDto;
	}

	@Override
	public List<RepairRecordDTO> queryList(String sapNo, String keys,
			int curPage, int pageSize) {
		
		return null;
	}

	@Override
	public List<RepairRecordDTO> queryList(String repairKeys, int curPage,
			int pageSize) {
		List<RepairRecord> list=repairMapper.queryList(repairKeys, PaginationData.getStartIndex(curPage, pageSize), pageSize);
		List<RepairRecordDTO> liDtos=new ArrayList<RepairRecordDTO>();
		RepairRecordDTO repairDTO=null;
		if(list!=null && list.size()>0){
			for (RepairRecord repairRecord : list) {
				repairDTO=new RepairRecordDTO();
				mapper.map(repairRecord, repairDTO);
				liDtos.add(repairDTO);
			}
		}
		return liDtos;
	}

	@Override
	public int addList(List<RepairRecordDTO> list) {
		List<RepairRecord> repairList=new ArrayList<RepairRecord>();
		RepairRecord repair=null;
		for (RepairRecordDTO repairRecordDTO : list) {
			repair=new RepairRecord();
			mapper.map(repairRecordDTO, repair);
			repairList.add(repair);
		}
		return repairMapper.addList(repairList);
//		int count = 0;
//		try {
//			RepairRecord repair=null;
//			for (RepairRecordDTO repairRecordDTO : list) {
//				repair=new RepairRecord();
//				mapper.map(repairRecordDTO, repair);
//				count+=repairMapper.add(repair);
//			}
//		} catch (MappingException e) {
//			count=-1;
//			System.out.println(e.getMessage()+e.getStackTrace());
//		}
//		return count;
	}

	@Override
	public Integer queryRecordCount(String repairKeys) {
		return repairMapper.queryRecordCount(repairKeys);
	}

	@Override
	public List<String> queryAllRepairNumber() {
		return repairMapper.queryAllRepairNumber();
	}

	@Override
	public ExportRepairRecorder queryExportById(String repairOrderNumber) {
		return repairMapper.queryExportById(repairOrderNumber);
	}

	@Override
	public List<RepairRecordDTO> queryList(String repairKeys, int state,
			int curPage, int pageSize) {
		List<RepairRecord> list=repairMapper.queryList(repairKeys, state,PaginationData.getStartIndex(curPage, pageSize), pageSize);
		List<RepairRecordDTO> liDtos=new ArrayList<RepairRecordDTO>();
		RepairRecordDTO repairDTO=null;
		if(list!=null && list.size()>0){
			for (RepairRecord repairRecord : list) {
				repairDTO=new RepairRecordDTO();
				mapper.map(repairRecord, repairDTO);
				liDtos.add(repairDTO);
			}
		}
		return liDtos;
	}

	@Override
	public Integer queryRecordCount(String repairKeys, Integer state) {
		return repairMapper.queryRecordCount(repairKeys,state);
	}

	@Override
	public List<ExportRepairRecorder> statisticsShip(String startTime,
			String endTime,String percent) {
		return repairMapper.statisticsShip(startTime, endTime,percent);
	}

	@Override
	public List<ExportRepairRecorder> statisticsPieShip(String startTime,
			String endTime) {
		return repairMapper.statisticsPieShip(startTime, endTime);
	}

}
