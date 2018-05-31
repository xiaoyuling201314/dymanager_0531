package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.Document;
import com.dayuan.bean.Manual;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.DocumentDTO;
import com.dayuan.dto.ManualDTO;
import com.dayuan.service.IDocumentService;
import com.dayuan.util.Tools;
/**
 * 设计文档业务类
 * @author xiaoyuling
 *
 */
@Service("documentService")
public class DocumentServiceImpl extends BaseSesrvice implements IDocumentService {

	@Override
	public int add(DocumentDTO documentDTO) {
		Document docoDocument=new Document();
		mapper.map(documentDTO, docoDocument);
		return documentMapper.add(docoDocument);
	}

	@Override
	public int addBySelective(DocumentDTO documentDTO) {
		Document document=new Document();
		mapper.map(documentDTO, document);
		return documentMapper.addBySelective(document);
	}

	@Override
	public int deleteById(Object id) {
		return documentMapper.deleteById(id);
	}

	@Override
	public int updateById(DocumentDTO documentDTO) {
		Document document=new Document();
		mapper.map(documentDTO, document);
		return documentMapper.updateById(document);
	}

	@Override
	public int updateBySelective(DocumentDTO documentDTO) {
		Document document=new Document();
		mapper.map(documentDTO, document);
		return documentMapper.updateBySelective(document);
	}

	@Override
	public DocumentDTO queryById(Object id) {
	Document document=documentMapper.queryById(id);
	DocumentDTO docDto=null;
	if(document!=null){
		docDto=new DocumentDTO();
		mapper.map(document, docDto);
	}
		return docDto;
	}


	@Override
	public List<DocumentDTO> queryList(String keys, int curPage, int pageSize) {
		List<Document> list = documentMapper.queryList(keys, curPage,pageSize);
		DocumentDTO docDto = null;
		List<DocumentDTO> listDTO = new ArrayList<DocumentDTO>();
		for (Document dcoDocument : list) {
			docDto = new DocumentDTO();
			mapper.map(dcoDocument, docDto);
			listDTO.add(docDto);
		}
		return listDTO;
	}

	@Override
	public int addList(List<DocumentDTO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DocumentDTO> queryList(String sapNo, String fileName,
			int curPage, int pageSize) {
		List<Document> list = documentMapper.queryList(sapNo,fileName, PaginationData.getStartIndex(curPage, pageSize),pageSize);
		DocumentDTO docDto = null;
		List<DocumentDTO> listDTO = new ArrayList<DocumentDTO>();
		for (Document document : list) {
			docDto = new DocumentDTO();
			document.setUpdateTime(Tools.getFormatDateString(document.getUpdateTime()));
			mapper.map(document, docDto);
			listDTO.add(docDto);
		}
		return listDTO;
	}

//	@Override
//	public List<DocumentDTO> queryList(String SapNo, String version) {
//		return null;
//	}

	@Override
	public Integer queryRecordCount(String sapNo, String documentKeys) {
		return documentMapper.queryRecordCount(sapNo,documentKeys);
	}

	@Override
	public List<String> queryAllName(String sapNo) {
		return documentMapper.queryAllName(sapNo);
	}

}
