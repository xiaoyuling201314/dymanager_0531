package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Certificate;
import com.dayuan.bean.SysUser;


/**
 * 公共方法接口定义
 * @author xiaoyuling
 *
 * @param <T>
 */
public interface BaseMapper<T> {
	/**
	 * 添加信息方法
	 * @param t
	 */
	public int add(T t);
	/**
	 * 动态添加信息（参数有值则进行添加）
	 * @param t
	 * @return
	 */
	public int addBySelective(T t);
	/**
	 * 删除信息方法
	 * @param id
	 */
	public int deleteById(Object id);
	
	/**
	 * 修改所有信息方法
	 * @param t
	 */
	public int updateById(T t);
	
	/**
	 * 动态修改信息（传入的参数不为空则进行修改）
	 * @param t
	 * @return
	 */
	public int updateBySelective(T t);
	
	/**
	 * 根据ID查询信息方法
	 * @param id
	 * @return
	 */
	public T queryById(Object id);
//	
//	/**
//	 * 根据SapNo和版本号查询电路板,整机BOM单,装箱清单详情信息
//	 * 装箱清单,说明书,设计文档等信息
//	 * @param sapNo
//	 * @return
//	 */
//	public List<T> queryList(@Param("keys")String SapNo,String version);
	
	/**
	 * 根据SapNo和版本号查询电路板,整机BOM单,装箱清单详情信息
	 * 装箱清单,说明书,设计文档等信息
	 * @param sapNo
	 * @return
	 */
	public List<T> queryList(@Param("keys")String sapNo,String version,@Param("curPage")int curPage,@Param("pageSize")int pageSize);
	/**
	 * 根据关键字查询信息
	 * 装箱清单,说明书,设计文档等信息
	 * @param sapNo
	 * @return
	 */
	public List<T> queryList(@Param("keys")String sapNo,@Param("curPage")int curPage,@Param("pageSize")int pageSize);
	/**
	 * 批量插入数据方法
	 * @param t
	 * @return
	 */
	public int addList(@Param("list")List<T> list);
}
