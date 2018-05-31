package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.bean.CircuitboardDetail;
import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.Materiel;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.service.ICircuitboardDetailService;
import com.dayuan.util.BatchInsert;
/**
 * 电路板详情业务类
 * @author xiaoyuling
 *
 */
@Service("circuitboardDetailService")
public class CircuitboardDetailServiceImpl extends BaseSesrvice implements ICircuitboardDetailService {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	private Logger logger=Logger.getLogger(CircuitboardDetailServiceImpl.class);
	@Override
	public int add(CircuitboardDetailDTO circuitboardDetailDTO) {
		CircuitboardDetail circuitboardDetail=new CircuitboardDetail();
		mapper.map(circuitboardDetailDTO, circuitboardDetail);
		return cirDetailMapper.add(circuitboardDetail);
	}

	@Override
	public int addBySelective(CircuitboardDetailDTO circuitboardDetailDTO) {
		CircuitboardDetail circuitboardDetail=new CircuitboardDetail();
		mapper.map(circuitboardDetailDTO, circuitboardDetail);
		return cirDetailMapper.addBySelective(circuitboardDetail);
	}

	@Override
	public int deleteById(Object id) {
		return cirDetailMapper.deleteById(id);
	}

	@Override
	public int updateById(CircuitboardDetailDTO cirDetailDTO) {
		CircuitboardDetail circuitboardDetail=new CircuitboardDetail();
		mapper.map(cirDetailDTO, circuitboardDetail);
		return cirDetailMapper.updateById(circuitboardDetail);
	}

	@Override
	public int updateBySelective(CircuitboardDetailDTO cirDto) {
		CircuitboardDetail ciDetail=new CircuitboardDetail();
		mapper.map(cirDto, ciDetail);
		return cirDetailMapper.updateBySelective(ciDetail);
	}

	@Override
	public CircuitboardDetailDTO queryById(Object id) {
		CircuitboardDetail cirDetail=cirDetailMapper.queryById(id);
		CircuitboardDetailDTO ciDto=null;
		if(cirDetail!=null){
			ciDto=new CircuitboardDetailDTO();
			mapper.map(cirDetail, ciDto);
		}
		return ciDto;
	}


	@Override
	public List<CircuitboardDetailDTO> queryList(String sapNo, int curPage,
			int pageSize) {
		List<CircuitboardDetail> list=cirDetailMapper.queryList(sapNo,curPage,pageSize);
		List<CircuitboardDetailDTO> liDtos=new ArrayList<CircuitboardDetailDTO>();
		CircuitboardDetailDTO ciDetailDTO=null;
		MaterielDTO maDto=null;
		if(list.size()>0){
			for (CircuitboardDetail circuitboardDetail : list) {
				ciDetailDTO=new CircuitboardDetailDTO();
				mapper.map(circuitboardDetail, ciDetailDTO);
//				maDto=setDrawsing(ciDetailDTO.getMaterielNo());//设置显示图纸
//				ciDetailDTO.setMaterielNo(maDto);
				liDtos.add(ciDetailDTO);
			}
		}
		return liDtos;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addList(List<CircuitboardDetailDTO> list) {
		int state=0;
		try {
			List<CircuitboardDetail> cirList=new ArrayList<CircuitboardDetail>();
			CircuitboardDetail circuitboardDetail=null;
			if(list.size()>0){
				for (CircuitboardDetailDTO cirDto : list) {
					circuitboardDetail=new CircuitboardDetail();
					mapper.map(cirDto, circuitboardDetail);
					state+=cirDetailMapper.add(circuitboardDetail);
				}
			}
		} catch (Exception e) {
			state=-1;
			System.out.println(e.getMessage()+e.getStackTrace());
			
		}
		
		return state;
	}

	@Override
	public List<CircuitboardDetailDTO> queryList(String sapNo, String version,
			int curPage, int pageSize) {
		List<CircuitboardDetail> list=cirDetailMapper.queryList(sapNo, version,curPage,pageSize);
		List<CircuitboardDetailDTO> liDtos=new ArrayList<CircuitboardDetailDTO>();
		CircuitboardDetailDTO ciDetailDTO=null;
		if(list.size()>0){
			for (CircuitboardDetail circuitboardDetail : list) {
				ciDetailDTO=new CircuitboardDetailDTO();
				mapper.map(circuitboardDetail, ciDetailDTO);
				liDtos.add(ciDetailDTO);
			}
		}
		return liDtos;
	}

//	@Override
//	public List<CircuitboardDetailDTO> queryList(String sapNo, String version) {
//		List<CircuitboardDetail> list=cirDetailMapper.queryList(sapNo, version);
//		List<CircuitboardDetailDTO> liDtos=new ArrayList<CircuitboardDetailDTO>();
//		CircuitboardDetailDTO ciDetailDTO=null;
//		if(list.size()>0){
//			for (CircuitboardDetail circuitboardDetail : list) {
//				ciDetailDTO=new CircuitboardDetailDTO();
//				mapper.map(circuitboardDetail, ciDetailDTO);
//				liDtos.add(ciDetailDTO);
//			}
//		}
//		return liDtos;
//	}

	@Override
	public List<CircuitboardDetailDTO> queryByBoardId(Integer id,String keys, int curPage,
			int pageSize) {
		List<CircuitboardDetail> list=cirDetailMapper.queryByBoardId(id,keys,PaginationData.getStartIndex(curPage, pageSize),pageSize);
		List<CircuitboardDetailDTO> liDtos=new ArrayList<CircuitboardDetailDTO>();
		CircuitboardDetailDTO ciDetailDTO=null;
		Materiel materiel=null;
		if(list.size()>0){
			for (CircuitboardDetail circuitboardDetail : list) {
				ciDetailDTO=new CircuitboardDetailDTO();
				materiel=circuitboardDetail.getMaterielNo();
				materiel.setDrawings(maDrawingsMapper.queryDrawsById(circuitboardDetail.getMaterielNo().getMaterielNo()));
				circuitboardDetail.setMaterielNo(materiel);
				mapper.map(circuitboardDetail, ciDetailDTO);
//				maDto=setDrawsing(ciDetailDTO.getMaterielNo());//设置显示图纸
//				ciDetailDTO.setMaterielNo(maDto);
				liDtos.add(ciDetailDTO);
			}
		}
		return liDtos;
	}

	@Override
	public Integer queryRecordCount(Integer id, String keys) {
		return cirDetailMapper.queryRecordCount(id,keys);
	}

	@Override
	public List<String> queryAllByBoardId(Integer circuitId) {
		List<String> list=cirDetailMapper.queryAllByBoardId(circuitId);
		return list;
	}
	@Override
	public List<ExportCircuitBoard> queryByCircuitIdNoList(Integer circuitId) {
		List<ExportCircuitBoard> list=cirDetailMapper.queryByCircuitIdNoList(circuitId);
		return list;
	}

}
