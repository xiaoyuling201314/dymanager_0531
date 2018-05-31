package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.SysUser;
import com.dayuan.dto.SysUserDTO;


public interface SysUserMapper extends BaseMapper<SysUser> {
	/**
	 * 用户登录
	 * @param model
	 * @return
	 */
	public SysUser userLogin(@Param("userName")String userName,@Param("password")String password);
	public int queryRecordCount(@Param("keys")String userName);
	public List<SysUser> queryAll();
}