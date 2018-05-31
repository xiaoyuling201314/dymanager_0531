package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.PaginationData;
import com.dayuan.bean.SysUser;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.service.ISysUserService;

@Service("userService")
public class UserServiceImpl extends BaseSesrvice implements ISysUserService {
	/**
	 * 用户登录方法实现
	 */
	@Override
	public SysUserDTO userLogin(String userName, String password) {
		SysUser sysUser = sysUserMapper.userLogin(userName, password);
		SysUserDTO sysUserDTO =null;
		if(sysUser!=null){
			sysUserDTO = new SysUserDTO();
			mapper.map(sysUser, sysUserDTO);
		}
		
		return sysUserDTO;
	}

	/**
	 * 新增用户实现方法
	 * 
	 * @author xyl
	 */
	@Override
	public int add(SysUserDTO sysUserDTO) {
		SysUser sysUser = new SysUser();
		mapper.map(sysUserDTO, sysUser);
		return sysUserMapper.add(sysUser);
	}

	/**
	 * 根据主键删除用户信息
	 * 
	 * @author xyl
	 */
	public int deleteById(Object id) {
		return sysUserMapper.deleteById(id);
	}

	/**
	 * 修改用户信息
	 * 
	 * @author xyl
	 */
	@Override
	public int updateById(SysUserDTO sysDto) {
		SysUser sysUser = new SysUser();
		mapper.map(sysDto, sysUser);
		return sysUserMapper.updateById(sysUser);
	}

	/**
	 * 
	 * @author xyl
	 */
	@Override
	public int updateBySelective(SysUserDTO sysDto) {
		SysUser sysUser = new SysUser();
		mapper.map(sysDto, sysUser);
		return sysUserMapper.updateBySelective(sysUser);
	}

	/**
	 * 根据主键查询数据
	 * 
	 * @author xyl
	 */
	@Override
	public SysUserDTO queryById(Object id) {
		SysUser sysUser = sysUserMapper.queryById(id);
		SysUserDTO sysUserDTO = new SysUserDTO();
		mapper.map(sysUser, sysUserDTO);
		return sysUserDTO;
	}

	/**
	 * 分页查询数据
	 */
	@Override
	public List<SysUserDTO> queryList(String userName, int curPage, int pageSize) {
		
		List<SysUser> list = sysUserMapper.queryList(userName, PaginationData.getStartIndex(curPage, pageSize),
				pageSize);
		SysUserDTO sysUserDTO = null;
		List<SysUserDTO> listDTO = new ArrayList<SysUserDTO>();
		for (SysUser sysUser : list) {
			sysUserDTO = new SysUserDTO();
			mapper.map(sysUser, sysUserDTO);
			listDTO.add(sysUserDTO);
		}
		return listDTO;
	}

	@Override
	public int queryRecordCount(String userName) {
		return sysUserMapper.queryRecordCount(userName);
	}

	@Override
	public List<SysUserDTO> queryAll() {
		List<SysUser> list = sysUserMapper.queryAll();
		SysUserDTO sysUserDTO = null;
		List<SysUserDTO> listDTO = new ArrayList<SysUserDTO>();
		for (SysUser sysUser : list) {
			sysUserDTO = new SysUserDTO();
			mapper.map(sysUser, sysUserDTO);
			listDTO.add(sysUserDTO);
		}
		return listDTO;
	}

}
