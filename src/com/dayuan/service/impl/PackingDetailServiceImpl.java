package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.CircuitboardDetail;
import com.dayuan.bean.Completemachinedetail;
import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.Materiel;
import com.dayuan.bean.PackingListDetail;
import com.dayuan.bean.Packinglist;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.CompletemachinedetailDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.PackingListDetailDTO;
import com.dayuan.service.IPackingListDetailService;
@Service("packingDetailService")
public class PackingDetailServiceImpl extends BaseSesrvice implements IPackingListDetailService {

	@Override
	public int add(PackingListDetailDTO paDto) {
		PackingListDetail paDetail=new PackingListDetail();
		mapper.map(paDto, paDetail);
		return pacDetailMapper.addBySelective(paDetail);
	}

	@Override
	public int addBySelective(PackingListDetailDTO paDto) {
		PackingListDetail paDetail=new PackingListDetail();
		mapper.map(paDto, paDetail);
		return pacDetailMapper.addBySelective(paDetail);
	}

	@Override
	public int deleteById(Object id) {
		return pacDetailMapper.deleteById(id);
	}

	@Override
	public int updateById(PackingListDetailDTO paDto) {
		PackingListDetail packingListDetail=new PackingListDetail();
		mapper.map(paDto, packingListDetail);
		return pacDetailMapper.updateById(packingListDetail);
	}

	@Override
	public int updateBySelective(PackingListDetailDTO paDto) {
		PackingListDetail paDetail=new PackingListDetail();
		mapper.map(paDto, paDetail);
		return pacDetailMapper.updateBySelective(paDetail);
	}

	@Override
	public PackingListDetailDTO queryById(Object id) {
		PackingListDetail paDetail=pacDetailMapper.queryById(id);
		PackingListDetailDTO packDTO=null;
		if(paDetail!=null){
			packDTO=new PackingListDetailDTO();
			mapper.map(paDetail, packDTO);
		}
		return packDTO;
	}

	@Override
	public List<PackingListDetailDTO> queryList(String keys, int curPage,
			int pageSize) {
		List<PackingListDetailDTO> listDTO=new ArrayList<PackingListDetailDTO>();
		PackingListDetailDTO packDto=null;
		List<PackingListDetail> list=pacDetailMapper.queryList(keys, curPage,pageSize);
		if(list.size()>0){
			for (PackingListDetail pDetail : list) {
				packDto=new PackingListDetailDTO();
				mapper.map(pDetail, packDto);
				listDTO.add(packDto);
			}
		}
		return listDTO;
	}

	@Override
	public int addList(List<PackingListDetailDTO> list) {
		int state=0;
		try {
			List<PackingListDetail> cirList=new ArrayList<PackingListDetail>();
			PackingListDetail pDetail=null;
			for (PackingListDetailDTO pDto : list) {
				pDetail=new PackingListDetail();
				mapper.map(pDto, pDetail);
				state+=pacDetailMapper.add(pDetail);
			}
		} catch (Exception e) {
			state=-1;
			e.getStackTrace();
		}
		
		return state;
	}

	@Override
	public List<PackingListDetailDTO> queryList(String sapNo, String version,
			int curPage, int pageSize) {
		List<PackingListDetailDTO> listDTO=new ArrayList<PackingListDetailDTO>();
		PackingListDetailDTO paDto=null;
		List<PackingListDetail> list=pacDetailMapper.queryList(sapNo, version,curPage,pageSize);
		if(list.size()>0){
			for (PackingListDetail pDetail : list) {
				paDto=new PackingListDetailDTO();
				mapper.map(pDetail, paDto);
				listDTO.add(paDto);
			}
		}
		return listDTO;
	}

//	@Override
//	public List<PackingListDetailDTO> queryList(String sapNo, String version) {
//		List<PackingListDetailDTO> listDTO=new ArrayList<PackingListDetailDTO>();
//		PackingListDetailDTO pDto=null;
//		List<PackingListDetail> list=pacDetailMapper.queryList(sapNo, version);
//		if(list.size()>0){
//			for (PackingListDetail pDetail : list) {
//				pDto=new PackingListDetailDTO();
//				mapper.map(pDetail, pDto);
//				listDTO.add(pDto);
//			}
//		}
//		return listDTO;
//	}

	@Override
	public List<PackingListDetailDTO> queryByBoardId(Integer id, String keys,
			Integer curPage, Integer pageSize) {
		List<PackingListDetail> list=pacDetailMapper.queryByBoardId(id,keys,PaginationData.getStartIndex(curPage, pageSize),pageSize);
		List<PackingListDetailDTO> liDtos=new ArrayList<PackingListDetailDTO>();
		PackingListDetailDTO paDto=null;
		Materiel materiel=null;
		if(list.size()>0){
			for (PackingListDetail paDetail : list) {
				paDto=new PackingListDetailDTO();
				materiel=paDetail.getMaterielNo();
				materiel.setDrawings(maDrawingsMapper.queryDrawsById(paDetail.getMaterielNo().getMaterielNo()));
				paDetail.setMaterielNo(materiel);
				mapper.map(paDetail, paDto);
//				maDto=setDrawsing(paDto.getMaterielNo());//设置显示图纸
//				paDto.setMaterielNo(maDto);
				liDtos.add(paDto);
			}
		}
		return liDtos;
	}

	@Override
	public List<String> queryAllByBoardId(Integer packpId) {
		List<String> list=pacDetailMapper.queryAllByBoardId(packpId);
		return list;
	}

	@Override
	public List<ExportCircuitBoard> queryByPackIdList(Integer packpId) {
		List<ExportCircuitBoard> list=pacDetailMapper.queryBypackIdList(packpId);
		return list;
	}

	@Override
	public Integer queryRecordCount(Integer packId, String keys) {
		return pacDetailMapper.queryRecordCount(packId,keys);
	}

}
