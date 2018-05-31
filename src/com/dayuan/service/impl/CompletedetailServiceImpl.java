package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.CircuitboardDetail;
import com.dayuan.bean.Completemachinedetail;
import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.bean.Materiel;
import com.dayuan.bean.PackingListDetail;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.CompletemachinedetailDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.service.ICompletemachinedetailService;
/**
 * 整机BOM单详情业务类
 * @author xiaoyuling
 *
 */
@Service("completedetailService")
public class CompletedetailServiceImpl extends BaseSesrvice implements ICompletemachinedetailService {

	@Override
	public int add(CompletemachinedetailDTO maDto) {
		Completemachinedetail completemachinedetail=new Completemachinedetail();
		mapper.map(maDto, completemachinedetail);
		return compleDMapper.add(completemachinedetail);
	}

	@Override
	public int addBySelective(CompletemachinedetailDTO comDto) {
		Completemachinedetail completemachinedetail=new Completemachinedetail();
		mapper.map(comDto, completemachinedetail);
		return compleDMapper.addBySelective(completemachinedetail);
	}

	@Override
	public int deleteById(Object id) {
		return compleDMapper.deleteById(id);
	}

	@Override
	public int updateById(CompletemachinedetailDTO comDto) {
		Completemachinedetail completemachinedetail=new Completemachinedetail();
		mapper.map(comDto, completemachinedetail);
		return compleDMapper.updateById(completemachinedetail);
	}

	@Override
	public int updateBySelective(CompletemachinedetailDTO comDto) {
		Completemachinedetail completemachinedetail=new Completemachinedetail();
		mapper.map(comDto, completemachinedetail);
		return compleDMapper.updateBySelective(completemachinedetail);
	}

	@Override
	public CompletemachinedetailDTO queryById(Object id) {
		Completemachinedetail completemachinedetail=compleDMapper.queryById(id);
		CompletemachinedetailDTO comDTO=null;
		if(completemachinedetail!=null){
			comDTO=new CompletemachinedetailDTO();
			mapper.map(completemachinedetail, comDTO);
		}
		return comDTO;
	}

	@Override
	public List<CompletemachinedetailDTO> queryList(String sapNo, int curPage,
			int pageSize) {
		List<CompletemachinedetailDTO> listDTO=new ArrayList<CompletemachinedetailDTO>();
		CompletemachinedetailDTO comDto=null;
		List<Completemachinedetail> list=compleDMapper.queryList(sapNo, curPage,pageSize);
		if(list.size()>0){
			for (Completemachinedetail completemachinedetail : list) {
				comDto=new CompletemachinedetailDTO();
				mapper.map(completemachinedetail, comDto);
				listDTO.add(comDto);
			}
		}
		return listDTO;
	}


	@Override
	public int addList(List<CompletemachinedetailDTO> list) {
		int state=0;
		try {
			List<Completemachinedetail> cirList=new ArrayList<Completemachinedetail>();
			Completemachinedetail completemachinedetail=null;
			for (CompletemachinedetailDTO comDto : list) {
				completemachinedetail=new Completemachinedetail();
				mapper.map(comDto, completemachinedetail);
				state+=compleDMapper.add(completemachinedetail);
			}
		} catch (Exception e) {
			state=-1;
			System.out.println(e.getMessage()+e.getStackTrace());
			
		}
		
		return state;
	}

	@Override
	public List<CompletemachinedetailDTO> queryList(String sapNo,
			String version, int curPage, int pageSize) {
		List<CompletemachinedetailDTO> listDTO=new ArrayList<CompletemachinedetailDTO>();
		CompletemachinedetailDTO comDto=null;
		List<Completemachinedetail> list=compleDMapper.queryList(sapNo, version,curPage,pageSize);
		if(list.size()>0){
			for (Completemachinedetail completemachinedetail : list) {
				comDto=new CompletemachinedetailDTO();
				mapper.map(completemachinedetail, comDto);
				listDTO.add(comDto);
			}
		}
		return listDTO;
	}

//	@Override
//	public List<CompletemachinedetailDTO> queryList(String sapNo, String version) {
//		List<CompletemachinedetailDTO> listDTO=new ArrayList<CompletemachinedetailDTO>();
//		CompletemachinedetailDTO comDto=null;
//		List<Completemachinedetail> list=compleDMapper.queryList(sapNo, version);
//		if(list.size()>0){
//			for (Completemachinedetail completemachinedetail : list) {
//				comDto=new CompletemachinedetailDTO();
//				mapper.map(completemachinedetail, comDto);
//				listDTO.add(comDto);
//			}
//		}
//		return listDTO;
//	}

	@Override
	public List<CompletemachinedetailDTO> queryByBoardId(Integer id,
			String keys, Integer curPage, Integer pageSize) {
		List<Completemachinedetail> list=compleDMapper.queryByBoardId(id,keys,PaginationData.getStartIndex(curPage, pageSize),pageSize);
		List<CompletemachinedetailDTO> liDtos=new ArrayList<CompletemachinedetailDTO>();
		CompletemachinedetailDTO comDetailDTO=null;
		Materiel materiel=null;
		if(list.size()>0){
			for (Completemachinedetail completemachinedetail : list) {
				comDetailDTO=new CompletemachinedetailDTO();
				materiel=completemachinedetail.getMaterielNo();
				materiel.setDrawings(maDrawingsMapper.queryDrawsById(completemachinedetail.getMaterielNo().getMaterielNo()));
				completemachinedetail.setMaterielNo(materiel);
				mapper.map(completemachinedetail, comDetailDTO);
//				maDto=setDrawsing(comDetailDTO.getMaterielNo());//设置显示图纸
//				comDetailDTO.setMaterielNo(maDto);
				liDtos.add(comDetailDTO);
			}
		}
		return liDtos;
	}

	@Override
	public List<String> queryAllByBoardId(Integer compId) {
		List<String> list=compleDMapper.queryAllByBoardId(compId);
		return list;
	}

	@Override
	public Integer queryRecordCount(Integer compleId, String  keys) {
		return compleDMapper.queryRecordCount(compleId,keys);
	}

	@Override
	public List<ExportCircuitBoard> queryByCompleteIdList(Integer completeId) {//List<String> sapNoList
		List<ExportCircuitBoard> list=compleDMapper.queryByCompleteIdList(completeId);
		return list;
	}

}
