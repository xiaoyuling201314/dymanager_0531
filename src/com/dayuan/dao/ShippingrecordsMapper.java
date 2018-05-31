package com.dayuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuan.bean.Document;
import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.bean.Shippingrecords;

public interface ShippingrecordsMapper extends BaseMapper<Shippingrecords> {

	
	public List<Shippingrecords> queryList(@Param("shipKeys")String shipKeys,@Param("curPage")int curPage,@Param("pageSize")int pageSize);

	Integer queryRecordCount(@Param("shipKeys")String shipKeys);

	public List<String> queryCustomer();

	public Shippingrecords viewshipMents(@Param("id")Integer id);

	public List<ExportShippingrecords> queryByListID(@Param("list")List<Integer> listID);

	public int deleteByListId(@Param("list")List<Integer> lists);

	public List<Shippingrecords> queryAllFuselage();
	/**
	 * 统计出货数量，用于报表数据源
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ExportShippingrecords> statisticsShip(@Param("startTime")String startTime,@Param("endTime")String endTime);

	public List<Shippingrecords> queryAllShipMents();
	
	/*
	 * select sum(quantity),sapNo,(select sum(quantity) from tbl_business_shippingrecords where shippingDate>'2016-11-11' and shippingDate<'2016-12-10' ) as totalCount 
from tbl_business_shippingrecords where shippingDate>'2016-11-11' and shippingDate<'2016-12-10'  group by sapNo;
	 * */
   
}