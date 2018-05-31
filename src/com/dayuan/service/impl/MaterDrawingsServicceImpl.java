package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.Materiel;
import com.dayuan.bean.MaterielDrawings;
import com.dayuan.dto.MaterielDrawingsDTO;
import com.dayuan.service.IMaterielDrawingsServicce;
/**
 * 物料图纸业务类
 * @author xiaoyuling
 *
 */
@Service("materielDrawService")
public class MaterDrawingsServicceImpl extends BaseSesrvice implements IMaterielDrawingsServicce {

	
	@Override
	public int deleteByCompositeId(String sapNo,String version) {
		return maDrawingsMapper.deleteByCompositeId(sapNo,version);
	}

	@Override
	public List<MaterielDrawingsDTO> queryDrawsById(String materielNo) {
	List<MaterielDrawingsDTO> listDTO=new ArrayList<MaterielDrawingsDTO>();
	List<MaterielDrawings> list=maDrawingsMapper.queryDrawsById(materielNo);
	MaterielDrawingsDTO maDto=null;
	if(list.size()>0){
		for (MaterielDrawings materielDrawings : list) {
			maDto=new MaterielDrawingsDTO();
			mapper.map(materielDrawings, maDto);
			listDTO.add(maDto);
		}
	}
		return listDTO;
	}

	@Override
	public int add(MaterielDrawingsDTO maDrawingsDTO) {
		MaterielDrawings materDrawings=new MaterielDrawings();
		mapper.map(maDrawingsDTO, materDrawings);
		return maDrawingsMapper.add(materDrawings);
	}

	@Override
	public int deleteById(String sapNo) {
		return maDrawingsMapper.deleteById(sapNo);
	}

	@Override
	public int addBySelective(MaterielDrawingsDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Object id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(MaterielDrawingsDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBySelective(MaterielDrawingsDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MaterielDrawingsDTO queryById(Object id) {
		MaterielDrawingsDTO maDto=null;
		MaterielDrawings maDrawings=maDrawingsMapper.queryById(id);
		if(maDrawings!=null){
			maDto=new MaterielDrawingsDTO();
			mapper.map(maDrawings, maDto);
		}
		return maDto;
	}

	@Override
	public List<MaterielDrawingsDTO> queryList(String SapNo, int curPage,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addList(List<MaterielDrawingsDTO> list) {
		int state=0;
		try {
			List<MaterielDrawings> malist=new ArrayList<MaterielDrawings>();
			MaterielDrawings materielDraws=null;
			if(list.size()>0){
				for (MaterielDrawingsDTO maDto : list) {
					materielDraws=new MaterielDrawings();
					mapper.map(maDto, materielDraws);
					malist.add(materielDraws);
					maDrawingsMapper.add(materielDraws);
				}
				
			}
		} catch (Exception e) {
			state=-1;
			System.out.println("============"+e.getMessage()+"============");
		}
		return state;
	}

	@Override
	public List<MaterielDrawingsDTO> queryList(String SapNo, String version,
			int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<MaterielDrawingsDTO> queryList(String SapNo, String version) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String queryLastDrawsById(String materielNo) {
		return maDrawingsMapper.queryLastDrawsById(materielNo);
	}

	@Override
	public List<String> queryAllName(String sapNo) {
		// TODO Auto-generated method stub
		return maDrawingsMapper.queryAllName(sapNo);
	}

}
