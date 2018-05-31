package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.bean.Materiel;
import com.dayuan.bean.MaterielDrawings;
import com.dayuan.bean.PaginationData;
import com.dayuan.bean.SysUser;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.dto.MaterielDrawingsDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.service.IMaterielDrawingsServicce;
import com.dayuan.service.IMaterielService;
import com.dayuan.util.Tools;
/**
 * 物料信息业务类
 * @author xiaoyuling
 *
 */
@Service("materielService")
public class MaterielServiceImpl extends BaseSesrvice implements IMaterielService {
	
	@Override
	public int add(MaterielDTO materielDTO) {
		Materiel materiel=new Materiel();
		mapper.map(materielDTO, materiel);
		return materielMapper.add(materiel);
	}

	@Override
	public int addBySelective(MaterielDTO t) {
		Materiel materiel=new Materiel();
		mapper.map(t, materiel);
		return materielMapper.add(materiel);
	}

	@Override
	public int deleteById(Object id) {
		return materielMapper.deleteById(id);
	}

	@Override
	public int updateById(MaterielDTO t) {
		Materiel materiel=new Materiel();
		mapper.map(t, materiel);
		return materielMapper.updateById(materiel);
	}

	@Override
	public int updateBySelective(MaterielDTO t) {
		Materiel materiel=new Materiel();
		mapper.map(t, materiel);
		return materielMapper.updateBySelective(materiel);
	}

	@Override
	public MaterielDTO queryById(Object id) {
		Materiel materiel=materielMapper.queryById(id);
		MaterielDTO materielDTO=null;
		List<MaterielDrawings> maList=null;
		if(materiel!=null){
			maList=maDrawingsMapper.queryDrawsById(materiel.getMaterielNo());
			materielDTO=new MaterielDTO();
			materiel.setDrawings(maList);
			mapper.map(materiel, materielDTO);
		}
		return materielDTO;
	}

	/**
	 * 分页查询物料信息
	 * @author xyl
	 */
	@Override
	public List<MaterielDTO> queryList(String keys, int curPage, int pageSize) {
		List<Materiel> list=materielMapper.queryList(keys, PaginationData.getStartIndex(curPage, pageSize), pageSize);
		MaterielDTO materielDTO=null;
		List<MaterielDTO> listDTO=new ArrayList<MaterielDTO>();
		if(list.size()>0){
			for (Materiel materiel : list) {
				materielDTO=new MaterielDTO();
				materiel.setDrawings(maDrawingsMapper.queryDrawsById(materiel.getMaterielNo()));
				materiel.setUpdateTime(Tools.getFormatDateString(materiel.getUpdateTime()));
				mapper.map(materiel, materielDTO);
				listDTO.add(materielDTO);
			}
		}
			return listDTO;
	}


	@Override
	public int addList(List<MaterielDTO> list) {
		List<Materiel> malist=new ArrayList<Materiel>();
		Materiel materiel=null;
		if(list.size()>0){
			for (MaterielDTO maDto : list) {
					materiel=new Materiel();
					mapper.map(maDto, materiel);
					malist.add(materiel);
			}
			
		}
		return materielMapper.addList(malist);
	}

	@Override
	public List<MaterielDTO> queryAll(String materielName) {
		List<Materiel> list=materielMapper.queryAll(materielName);
		List<MaterielDTO> liDtos=new ArrayList<MaterielDTO>();
		MaterielDTO materielDTO=null;
		if(list.size()>0){
			for (Materiel materiel : list) {
				materielDTO=new MaterielDTO();
				mapper.map(materiel, materielDTO);
				liDtos.add(materielDTO);
				
			}
		}
		return liDtos;
	}

	@Override
	public List<MaterielDTO> queryList(String SapNo, String version,
			int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<MaterielDTO> queryList(String SapNo, String version) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Integer queryRecordCount(String userName) {
		return materielMapper.queryRecordCount(userName);
	}
	//查询物料信息
	@Override
	public List<MaterielDTO> queryByMaterielKeys(String materielKeys) {
		List<Materiel> list=materielMapper.queryByMaterielKeys(materielKeys);
		List<MaterielDTO> liDtos=new ArrayList<MaterielDTO>();
		MaterielDTO materielDTO=null;
		List<MaterielDrawings> listDraws=null;
		if(list.size()>0){
			for (Materiel materiel : list) {
				materielDTO=new MaterielDTO();
				listDraws=maDrawingsMapper.queryDrawsById(materiel.getMaterielNo());
				materiel.setDrawings(listDraws);
				mapper.map(materiel, materielDTO);
//				showDrawings=maDrawingsMapper.queryLastDrawsById(materielDTO.getMaterielNo());
//				materielDTO.setShowDrawings(showDrawings);
				liDtos.add(materielDTO);
			}
		}
		return liDtos;
	}

	@Override
	public List<MaterielDTO> queryBySapNoList(List<String> sapNoList) {
		List<Materiel> list=materielMapper.queryBySapNoList(sapNoList);
		List<MaterielDTO> liDtos=new ArrayList<MaterielDTO>();
		MaterielDTO materielDTO=null;
		if(list.size()>0){
			for (Materiel materiel : list) {
				materielDTO=new MaterielDTO();
				mapper.map(materiel, materielDTO);
				liDtos.add(materielDTO);
			}
		}
		return liDtos;
	}

	@Override
	public List<String> queryAllMaterNo() {
		return materielMapper.queryAllMaterNo();
	}


}
