package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.PackingListDetail;
import com.dayuan.bean.Packinglist;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.PackingListDetailDTO;
import com.dayuan.dto.PackinglistDTO;
import com.dayuan.service.IPackinglistService;
import com.dayuan.util.Tools;
@Service("packinglistService")
public class PackinglistServiceImpl extends BaseSesrvice implements IPackinglistService {

	@Override
	public int add(PackinglistDTO paDto) {
		Packinglist packinglist=new Packinglist();
		mapper.map(paDto, packinglist);
		packinglistMapper.add(packinglist);
		return packinglist.getId();
	}

	@Override
	public int addBySelective(PackinglistDTO paDto) {
		Packinglist packinglist=new Packinglist();
		mapper.map(paDto, packinglist);
		packinglistMapper.addBySelective(packinglist);
		return packinglist.getId();
	}

	@Override
	public int deleteById(Object id) {
		return packinglistMapper.deleteById(id);
	}

	@Override
	public int updateById(PackinglistDTO paDto) {
		Packinglist packinglist=new Packinglist();
		mapper.map(paDto, packinglist);
		return packinglistMapper.updateById(packinglist);
	}

	@Override
	public int updateBySelective(PackinglistDTO paDto) {
		Packinglist paDetail=new Packinglist();
		mapper.map(paDto, paDetail);
		return packinglistMapper.updateBySelective(paDetail);
	}

	@Override
	public PackinglistDTO queryById(Object id) {
		Packinglist packinglist=packinglistMapper.queryById(id);
		PackinglistDTO packDTO=null;
		if(packinglist!=null){
			packDTO=new PackinglistDTO();
			mapper.map(packinglist, packDTO);
		}
		return packDTO;
	}

	@Override
	public List<PackinglistDTO> queryList(String keys, int curPage, int pageSize) {
		List<PackinglistDTO> listDTO=new ArrayList<PackinglistDTO>();
		PackinglistDTO packDto=null;
		List<Packinglist> list=packinglistMapper.queryList(keys, curPage,pageSize);
		if(list.size()>0){
			for (Packinglist packinglist : list) {
				packDto=new PackinglistDTO();
				mapper.map(packinglist, packDto);
				listDTO.add(packDto);
			}
		}
		return listDTO;
	}

	@Override
	public int addList(List<PackinglistDTO> list) {
		
		return 0;
	}

	@Override
	public List<PackinglistDTO> queryList(String sapNo, String version,
			int curPage, int pageSize) {
		List<PackinglistDTO> listDTO=new ArrayList<PackinglistDTO>();
		PackinglistDTO paDto=null;
		List<Packinglist> list=packinglistMapper.queryList(sapNo, version,PaginationData.getStartIndex(curPage, pageSize),pageSize);
		if(list.size()>0){
			for (Packinglist packinglist : list) {
				paDto=new PackinglistDTO();
				packinglist.setUpdateTime(Tools.getFormatDateString(packinglist.getUpdateTime()));
				mapper.map(packinglist, paDto);
				listDTO.add(paDto);
			}
		}
		return listDTO;
	}

//	@Override
//	public List<PackinglistDTO> queryList(String sapNo, String version) {
//		List<PackinglistDTO> listDTO=new ArrayList<PackinglistDTO>();
//		PackinglistDTO pDto=null;
//		List<Packinglist> list=packinglistMapper.queryList(sapNo, version);
//		if(list.size()>0){
//			for (Packinglist packinglist : list) {
//				pDto=new PackinglistDTO();
//				mapper.map(packinglist, pDto);
//				listDTO.add(pDto);
//			}
//		}
//		return listDTO;
//	}

	@Override
	public Integer queryRecordCount(String sapNo, String packingListName) {
		return packinglistMapper.queryRecordCount(sapNo,packingListName);
	}

	@Override
	public List<String> queryAllName(String sapNo) {
		return packinglistMapper.queryAllName(sapNo);
	}

}
