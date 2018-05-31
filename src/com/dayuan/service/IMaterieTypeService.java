package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.MaterieTypeDTO;
import com.dayuan.dto.MaterielDTO;

/**
 * 2016-09-29
 * @author xiaoyuling
 *
 */
public interface IMaterieTypeService extends IBaseService<MaterieTypeDTO> {
	/**
	 * 查询所有的物料种类信息
	 * @return
	 */
	 public List<MaterieTypeDTO> queryAllType();
}