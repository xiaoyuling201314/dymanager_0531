package com.dayuan.service;

import java.util.List;

import com.dayuan.bean.ExportCircuitBoard;
import com.dayuan.dto.PackingListDetailDTO;
/**
 * 装箱清单详情DAO
 * @author xiaoyuling
 *
 */
public interface IPackingListDetailService extends IBaseService<PackingListDetailDTO> {

	public List<PackingListDetailDTO> queryByBoardId(Integer id, String keys,
			Integer curPage, Integer pageSize);

	public List<String> queryAllByBoardId(Integer packpId);

	public List<ExportCircuitBoard> queryByPackIdList(Integer packpId);

	public Integer queryRecordCount(Integer packId, String keys);
    
}