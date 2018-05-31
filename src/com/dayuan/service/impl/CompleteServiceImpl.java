package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.Completemachine;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CompletemachineDTO;
import com.dayuan.service.ICompletemachineService;
import com.dayuan.util.Tools;
/**
 * 整机BOM业务类
 * @author xiaoyuling
 *
 */
@Service("completeService")
public class CompleteServiceImpl extends BaseSesrvice implements ICompletemachineService {

	@Override
	public int add(CompletemachineDTO comDto) {
		Completemachine completemachine=new Completemachine();
		mapper.map(comDto, completemachine);
		return compleMapper.add(completemachine);
	}

	@Override
	public int addBySelective(CompletemachineDTO comDto) {
		Completemachine completemachine=new Completemachine();
		mapper.map(comDto, completemachine);
		compleMapper.addBySelective(completemachine);
		return completemachine.getId();
	}

	@Override
	public int deleteById(Object id) {
		return compleMapper.deleteById(id);
	}

	@Override
	public int updateById(CompletemachineDTO completemachineDTO) {
		Completemachine completemachine=new Completemachine();
		mapper.map(completemachineDTO, completemachine);
		return compleMapper.updateById(completemachine);
	}

	@Override
	public int updateBySelective(CompletemachineDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CompletemachineDTO queryById(Object id) {
		Completemachine completemachine=compleMapper.queryById(id);
		CompletemachineDTO comDto=null;
		if(completemachine!=null){
			comDto=new CompletemachineDTO();
			mapper.map(completemachine, comDto);
		}
		
		return comDto;
	}

	@Override
	public List<CompletemachineDTO> queryList(String keys, int curPage,
			int pageSize) {
		List<CompletemachineDTO> liDtos=new ArrayList<CompletemachineDTO>();
		List<Completemachine> list=compleMapper.queryList(keys, curPage, pageSize);
		CompletemachineDTO cDto=null;
		if(list.size()>0){
			for (Completemachine completemachine : list) {
				cDto=new CompletemachineDTO();
				mapper.map(completemachine, cDto);
				liDtos.add(cDto);
			}
		}
		return liDtos;
	}

	@Override
	public int addList(List<CompletemachineDTO> list) {
	
		return 0;
	}

	@Override
	public List<CompletemachineDTO> queryList(String keys,
			String completeName, int curPage, int pageSize) {
		List<CompletemachineDTO> liDtos=new ArrayList<CompletemachineDTO>();
		List<Completemachine> list=compleMapper.queryList(keys,completeName, PaginationData.getStartIndex(curPage, pageSize), pageSize);
		CompletemachineDTO cDto=null;
		if(list.size()>0){
			for (Completemachine completemachine : list) {
				cDto=new CompletemachineDTO();
				completemachine.setUpdateTime(Tools.getFormatDateString(completemachine.getUpdateTime()));
				mapper.map(completemachine, cDto);
				liDtos.add(cDto);
			}
		}
		return liDtos;
	}

//	@Override
//	public List<CompletemachineDTO> queryList(String keys, String version) {
//		
//		List<CompletemachineDTO> liDtos=new ArrayList<CompletemachineDTO>();
//		List<Completemachine> list=compleMapper.queryList(keys, version);
//		CompletemachineDTO cDto=null;
//		if(list.size()>0){
//			for (Completemachine completemachine : list) {
//				cDto=new CompletemachineDTO();
//				mapper.map(completemachine, cDto);
//				liDtos.add(cDto);
//			}
//		}
//		return liDtos;
//	}

	@Override
	public Integer queryRecordCount(String sapNo, String completeName) {
		return compleMapper.queryRecordCount(sapNo, completeName);
	}

	@Override
	public List<String> queryAllName(String sapNo) {
		return compleMapper.queryAllName(sapNo);
	}

}
