package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.Manual;
import com.dayuan.bean.Instrumentinfo;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.ManualDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.service.IManualService;
import com.dayuan.util.Tools;
/**
 * 说明书业务类
 * @author xiaoyuling
 *
 */
@Service("instructionsService")
public class ManualServiceImpl extends BaseSesrvice implements IManualService {

	@Override
	public int add(ManualDTO instructionsDTO) {
		Manual instructions=new Manual();
		mapper.map(instructionsDTO, instructions);
		return manualMapper.add(instructions);
	}

	@Override
	public int addBySelective(ManualDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Object id) {
		return manualMapper.deleteById(id);
	}

	@Override
	public int updateById(ManualDTO instructionsDTO) {
		Manual instructions=new Manual();
		mapper.map(instructionsDTO, instructions);
		return manualMapper.updateById(instructions);
	}

	@Override
	public int updateBySelective(ManualDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ManualDTO queryById(Object id) {
		Manual manual=manualMapper.queryById(id);
		ManualDTO manualDTO=new ManualDTO();
		mapper.map(manual, manualDTO);
		return manualDTO;
	}


	@Override
	public List<ManualDTO> queryList(String keys, int curPage,
			int pageSize) {
		List<Manual> list = manualMapper.queryList(keys, curPage,pageSize);
		ManualDTO insDto = null;
		List<ManualDTO> listDTO = new ArrayList<ManualDTO>();
		for (Manual instructions : list) {
			insDto = new ManualDTO();
			mapper.map(instructions, insDto);
			listDTO.add(insDto);
		}
		return listDTO;
	}

	@Override
	public int addList(List<ManualDTO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ManualDTO> queryList(String sapNo, String fileName,
			int curPage, int pageSize) {
		List<Manual> list = manualMapper.queryList(sapNo,fileName, PaginationData.getStartIndex(curPage, pageSize),pageSize);
		ManualDTO manualDTO = null;
		List<ManualDTO> listDTO = new ArrayList<ManualDTO>();
		for (Manual manual : list) {
			manualDTO = new ManualDTO();
			manual.setUpdateTime(Tools.getFormatDateString(manual.getUpdateTime()));
			mapper.map(manual, manualDTO);
			listDTO.add(manualDTO);
		}
		return listDTO;
	}

//	@Override
//	public List<ManualDTO> queryList(String SapNo, String version) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Integer queryRecordCount(String sapNo,String manualName) {
		return manualMapper.queryRecordCount(sapNo,manualName);
	}

	@Override
	public List<String> queryAllName(String sapNo) {
		return manualMapper.queryAllName(sapNo);
	}


}
