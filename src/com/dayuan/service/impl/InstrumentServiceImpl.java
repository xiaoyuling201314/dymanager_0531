package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.Instrumentinfo;
import com.dayuan.bean.PaginationData;
import com.dayuan.bean.SysUser;
import com.dayuan.dto.InstrumentinfoDTO;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.service.InstrumentinfoService;
import com.dayuan.util.Tools;
/**
 * 仪器信息业务类
 * @author xiaoyuling
 *
 */
@Service("instrumentService")
public class InstrumentServiceImpl extends BaseSesrvice implements InstrumentinfoService {

	@Override
	public int add(InstrumentinfoDTO insDto) {
		Instrumentinfo instrumentinfo=new Instrumentinfo();
		mapper.map(insDto, instrumentinfo);
		return instrumentMapper.add(instrumentinfo);
	}

	@Override
	public int addBySelective(InstrumentinfoDTO insDto) {
		Instrumentinfo instrumentinfo=new Instrumentinfo();
		mapper.map(insDto, instrumentinfo);
		return instrumentMapper.addBySelective(instrumentinfo);
	}

	@Override
	public int deleteById(Object id) {
		return instrumentMapper.deleteById(id);
	}

	@Override
	public int updateById(InstrumentinfoDTO insDto) {
		Instrumentinfo instrumentinfo=new Instrumentinfo();
		mapper.map(insDto, instrumentinfo);
		return instrumentMapper.updateById(instrumentinfo);
	}

	@Override
	public int updateBySelective(InstrumentinfoDTO insDto) {
		Instrumentinfo instrumentinfo=new Instrumentinfo();
		mapper.map(insDto, instrumentinfo);
		return instrumentMapper.updateBySelective(instrumentinfo);
	}

	@Override
	public InstrumentinfoDTO queryById(Object id) {
		Instrumentinfo instr=instrumentMapper.queryById(id);
		InstrumentinfoDTO inDto=null;
		if(instr!=null){
			inDto=new InstrumentinfoDTO();
			mapper.map(instr, inDto);
		}
		return inDto;
	}


	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@Override
	public List<InstrumentinfoDTO> queryList(String keys, int curPage,
			int pageSize) {
		List<Instrumentinfo> list = instrumentMapper.queryList(keys, PaginationData.getStartIndex(curPage, pageSize),pageSize);
		InstrumentinfoDTO insDto = null;
		List<InstrumentinfoDTO> listDTO = new ArrayList<InstrumentinfoDTO>();
		if(list!=null && list.size()>0){
			for (Instrumentinfo instrumentinfo : list) {
				insDto = new InstrumentinfoDTO();
				mapper.map(instrumentinfo, insDto);
				listDTO.add(insDto);
			}
		}
		return listDTO;
	}


	@Override
	public int addList(List<InstrumentinfoDTO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<InstrumentinfoDTO> queryList(String SapNo, String version,
			int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<InstrumentinfoDTO> queryList(String SapNo, String version) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Integer queryRecordCount(String userName) {
		return instrumentMapper.queryRecordCount(userName);
	}

}
