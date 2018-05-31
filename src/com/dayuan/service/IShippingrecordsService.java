package com.dayuan.service;

import java.util.List;

import com.dayuan.bean.ExportShippingrecords;
import com.dayuan.bean.Shippingrecords;
import com.dayuan.dto.ShippingrecordsDTO;
/**
 * 装箱清单DAO
 * @author xiaoyuling
 *
 */
public interface IShippingrecordsService extends IBaseService<ShippingrecordsDTO> {

	Integer queryRecordCount(String shipmentsKeys);

	List<String> queryCustomer();

	ShippingrecordsDTO viewshipMents(Integer id);

	List<ExportShippingrecords> queryByListID(List<Integer> lists);

	int deleteByListId(List<Integer> lists);

	List<ShippingrecordsDTO> queryAllFuselage();

	List<ExportShippingrecords> statisticsShip(String startTime, String endTime);

	List<Shippingrecords> queryAllShipMents();


    
}