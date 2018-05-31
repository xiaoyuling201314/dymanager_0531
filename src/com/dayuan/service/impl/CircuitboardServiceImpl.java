package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.Circuitboard;
import com.dayuan.bean.Instrumentinfo;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CircuitboardDTO;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.service.ICircuitboardService;
import com.dayuan.util.Tools;
/**
 * 电路板业务类
 * @author xiaoyuling
 *
 */
@Service("circuitboardService")
public class CircuitboardServiceImpl extends BaseSesrvice implements ICircuitboardService {

	@Override
	public int add(CircuitboardDTO circuitboardDTO) {
		Circuitboard circuitboard=new Circuitboard();
		mapper.map(circuitboardDTO, circuitboard);
		circuitboardMapper.add(circuitboard);
		return circuitboard.getId();//获取返回额主键ID
	}

	@Override
	public int addBySelective(CircuitboardDTO circuitboardDTO) {
		Circuitboard circuitboard=new Circuitboard();
		mapper.map(circuitboardDTO, circuitboard);
		return circuitboardMapper.addBySelective(circuitboard);
	}

	@Override
	public int deleteById(Object id) {
		return circuitboardMapper.deleteById(id);
	}

	@Override
	public int updateById(CircuitboardDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBySelective(CircuitboardDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CircuitboardDTO queryById(Object id) {
	Circuitboard circuitboard=circuitboardMapper.queryById(id);
	CircuitboardDTO cirDto=null;
		if(circuitboard!=null){
		cirDto=new CircuitboardDTO();
		mapper.map(circuitboard, cirDto);
		}
		return cirDto;
	}

	public List<CircuitboardDTO> queryList(String keys, int curPage,
			int pageSize) {
	List<Circuitboard> list=circuitboardMapper.queryList(keys, curPage, pageSize);
	List<CircuitboardDTO> liDtos=new ArrayList<CircuitboardDTO>();
	CircuitboardDTO ciDto=null;
	if(list.size()>0){
		for (Circuitboard circuitboard : list) {
			ciDto=new CircuitboardDTO();
			mapper.map(circuitboard, ciDto);
			liDtos.add(ciDto);
		}
	}
		return liDtos;
	}


	@Override
	public int addList(List<CircuitboardDTO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CircuitboardDTO> queryList(String sapNo, String keys,
			int curPage, int pageSize) {
		List<Circuitboard> list = circuitboardMapper.queryList(sapNo, keys,PaginationData.getStartIndex(curPage, pageSize), pageSize);
		List<CircuitboardDTO> liDtos = new ArrayList<CircuitboardDTO>();
		CircuitboardDTO ciDto = null;
		if (list.size() > 0) {
			for (Circuitboard circuitboard : list) {
				ciDto = new CircuitboardDTO();
				circuitboard.setUpdateTime(Tools.getFormatDateString(circuitboard.getUpdateTime()));
				mapper.map(circuitboard, ciDto);
				liDtos.add(ciDto);
			}
		}
		return liDtos;
	}

	@Override
	public Integer queryRecordCount(String sapNo, String boardName) {
		return circuitboardMapper.queryRecordCount(sapNo,boardName);
	}

	public List<CircuitboardDTO> queryList(String SapNo, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> queryAllName(String sapNo) {
		return circuitboardMapper.queryAllName(sapNo);
	}

	
	

}
