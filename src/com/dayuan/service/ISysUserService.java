package com.dayuan.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.dto.SysUserDTO;
/**
 * 2016-09-29
 * @author xiaoyuling
 *
 */
public interface ISysUserService {

	/**
	 * 用户登录
	 * @param model
	 * @return
	 */
	public SysUserDTO userLogin(String userName,String password);
	
	/**
	 * 分页查询方法
	 * @param userName
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	public List<SysUserDTO> queryList(String keys,int curPage,int pageSize);
	
	/**
	 * 根据ID查询信息方法
	 * @param id
	 * @return
	 */
	public SysUserDTO queryById(Object id);
	
	/**
	 * 删除信息方法
	 * @param id
	 */
	public int deleteById(Object id);
	
	/**
	 * 修改所有信息方法
	 * @param t
	 */
	public int updateById(SysUserDTO sysUserDTO);
	/**
	 * 添加信息方法
	 * @param t
	 */
	public int add(SysUserDTO sysUserDTO);
	/**
	 * 动态修改信息（传入的参数不为空则进行修改）
	 * @param t
	 * @return
	 */
	public int updateBySelective(SysUserDTO sysUserDTO);
	
	public int queryRecordCount(String userName);
	/**
	 * 查询所有的用户
	 * @return
	 */
	public List<SysUserDTO> queryAll();
	
}
