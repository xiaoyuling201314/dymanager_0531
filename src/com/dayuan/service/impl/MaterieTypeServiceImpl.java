package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.CertificateType;
import com.dayuan.bean.MaterieType;
import com.dayuan.dto.CertificateTypeDTO;
import com.dayuan.dto.MaterieTypeDTO;
import com.dayuan.service.IMaterieTypeService;
@Service("materieTypeService")
public class MaterieTypeServiceImpl extends BaseSesrvice implements IMaterieTypeService {

	@Override
	public int add(MaterieTypeDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addBySelective(MaterieTypeDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Object id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(MaterieTypeDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBySelective(MaterieTypeDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MaterieTypeDTO queryById(Object id) {
		MaterieTypeDTO maTypeDTO=null;
		MaterieType materieType=materieTypeMapper.queryById(id);
		if(materieType!=null){
			maTypeDTO=new MaterieTypeDTO();
		    mapper.map(materieType, maTypeDTO);
		}
		return maTypeDTO;
	}

	@Override
	public List<MaterieTypeDTO> queryAllType() {
		List<MaterieType> list=materieTypeMapper.queryAllType();
		List<MaterieTypeDTO> liDtos=new ArrayList<MaterieTypeDTO>();
		MaterieTypeDTO materieTypeDTO=null;
		if(list.size()>0){
			for (MaterieType materieType : list) {
				materieTypeDTO=new MaterieTypeDTO();
				mapper.map(materieType, materieTypeDTO);
				liDtos.add(materieTypeDTO);
			}
		}
		return liDtos;
	}

	@Override
	public List<MaterieTypeDTO> queryList(String keys, int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addList(List<MaterieTypeDTO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MaterieTypeDTO> queryList(String SapNo, String version,
			int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<MaterieTypeDTO> queryList(String SapNo, String version) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
