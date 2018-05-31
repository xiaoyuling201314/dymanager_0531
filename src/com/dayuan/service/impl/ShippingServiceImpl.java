package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dozer.MappingException;
import org.springframework.stereotype.Service;

import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.bean.PaginationData;
import com.dayuan.bean.Shippingrecords;
import com.dayuan.dto.PackinglistDTO;
import com.dayuan.dto.ShippingrecordsDTO;
import com.dayuan.service.IShippingrecordsService;
@Service("shipmentsService")
public class ShippingServiceImpl extends BaseSesrvice implements IShippingrecordsService {

	@Override
	public int add(ShippingrecordsDTO sDto) {
		Shippingrecords ship=new Shippingrecords();
		mapper.map(sDto, ship);
		return shipmentsMapper.add(ship);
	}

	@Override
	public int addBySelective(ShippingrecordsDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Object id) {
		return shipmentsMapper.deleteById(id);
	}

	@Override
	public int updateById(ShippingrecordsDTO sDto) {
		Shippingrecords ship=new Shippingrecords();
		mapper.map(sDto, ship);
		return shipmentsMapper.updateById(ship);
	}

	@Override
	public int updateBySelective(ShippingrecordsDTO sDto) {
		Shippingrecords ship=new Shippingrecords();
		mapper.map(sDto, ship);
		return shipmentsMapper.updateBySelective(ship);
	}

	@Override
	public ShippingrecordsDTO queryById(Object id) {
		Shippingrecords ship=shipmentsMapper.queryById(id);
		ShippingrecordsDTO sDto=null;
		if(ship!=null){
			sDto=new ShippingrecordsDTO();
			mapper.map(ship, sDto);
		}
		return sDto;
	}

	@Override
	public List<ShippingrecordsDTO> queryList(String sapNo, String keys,
			int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShippingrecordsDTO> queryList(String sapNo, int curPage,
			int pageSize) {
		List<Shippingrecords> list=shipmentsMapper.queryList(sapNo, PaginationData.getStartIndex(curPage, pageSize), pageSize);
		List<ShippingrecordsDTO> liDtos=new ArrayList<ShippingrecordsDTO>();
		ShippingrecordsDTO shipDTO=null;
		List<String> listFlage=null;
		String [] arrs;
		if(list!=null && list.size()>0){
			for (Shippingrecords shippingrecords : list) {
				shipDTO=new ShippingrecordsDTO();
				mapper.map(shippingrecords, shipDTO);
				//shipDTO.setCountRepair(repairMapper.queryCountByShipId(shipDTO.getId()));
				//查询维修数量
				listFlage=new ArrayList<>();
				arrs=shippingrecords.getInstrumentFuselage().split(",");
				for (int i = 0; i < arrs.length; i++) {
					listFlage.add(arrs[i]);
				}
				shipDTO.setCountRepair(repairMapper.queryCountByShipId(arrs));
				liDtos.add(shipDTO);
			}
		}
		return liDtos;
	}

	@Override
	public int addList(List<ShippingrecordsDTO> list) {
		int count = 0;
		try {
			Shippingrecords ship=null;
			for (ShippingrecordsDTO shippingrecordsDTO : list) {
				ship=new Shippingrecords();
				mapper.map(shippingrecordsDTO, ship);
				count+=shipmentsMapper.add(ship);
			}
		} catch (MappingException e) {
			count=-1;
			System.out.println(e.getMessage()+e.getStackTrace());
		}
		return count;
	}

	@Override
	public Integer queryRecordCount(String shipmentsKeys) {
		return shipmentsMapper.queryRecordCount(shipmentsKeys);
	}

	@Override
	public List<String> queryCustomer() {
		return shipmentsMapper.queryCustomer();
	}

	@Override
	public ShippingrecordsDTO viewshipMents(Integer id) {
		Shippingrecords ship=shipmentsMapper.viewshipMents(id);
		ShippingrecordsDTO sDto=null;
		if(ship!=null){
			sDto=new ShippingrecordsDTO();
			mapper.map(ship, sDto);
		}
		return sDto;
	}

	@Override
	public List<ExportShippingrecords> queryByListID(List<Integer> listID) {
		List<ExportShippingrecords> list=shipmentsMapper.queryByListID(listID);
		return list;
	}
	/**
	 * 批量删除出货记录
	 */
	@Override
	public int deleteByListId(List<Integer> lists) {
		int count=0;
		count=shipmentsMapper.deleteByListId(lists);
//		try {
//			for (Integer id : lists) {
//				count+=shipmentsMapper.deleteById(id);
//			}
//		} catch (Exception e) {
//			count=-1;
//			System.out.println(e.getMessage()+e.getStackTrace());
//		}
		return count;
	}

	@Override
	public List<ShippingrecordsDTO> queryAllFuselage() {
		List<Shippingrecords> list=	shipmentsMapper.queryAllFuselage();
		List<ShippingrecordsDTO> lisDtos=null;
		ShippingrecordsDTO shipDto=null;
		if(list!=null && list.size()>0){
			lisDtos=new ArrayList<ShippingrecordsDTO>();
			for (Shippingrecords shippingrecords : list) {
				shipDto=new ShippingrecordsDTO();
				mapper.map(shippingrecords, shipDto);
				lisDtos.add(shipDto);
			}
		}
		
		return lisDtos;
	}

	@Override
	public List<ExportShippingrecords> statisticsShip(String startTime,
			String endTime) {
		return shipmentsMapper.statisticsShip(startTime, endTime);
	}

	@Override
	public List<Shippingrecords> queryAllShipMents() {
		return shipmentsMapper.queryAllShipMents();
	}

}
