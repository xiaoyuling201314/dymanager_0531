package com.dayuan.service;

import java.util.List;

import com.dayuan.dto.DocumentDTO;
/**
 * 设计文档信息DAO
 * @author xiaoyuling
 *
 */
public interface IDocumentService extends IBaseService<DocumentDTO> {

	public Integer queryRecordCount(String sapNo, String string);

	public List<String> queryAllName(String sapNo);
   
}