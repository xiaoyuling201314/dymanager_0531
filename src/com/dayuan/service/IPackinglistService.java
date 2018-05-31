package com.dayuan.service;

import java.util.List;

import com.dayuan.bean.Packinglist;
import com.dayuan.dto.PackinglistDTO;
/**
 * 装箱清单DAO
 * @author xiaoyuling
 *
 */
public interface IPackinglistService extends IBaseService<PackinglistDTO> {

	public Integer queryRecordCount(String sapNo, String packingListName);

	public List<String> queryAllName(String sapNo);
    
}