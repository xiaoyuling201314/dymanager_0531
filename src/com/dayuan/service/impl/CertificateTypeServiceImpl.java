package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dayuan.bean.CertificateType;
import com.dayuan.dto.CertificateDTO;
import com.dayuan.dto.CertificateTypeDTO;
import com.dayuan.service.ICertificateTypeService;
/**
 * 证书类型业务类
 * @author xiaoyuling
 *
 */
@Service("certificateTypeService")
public class CertificateTypeServiceImpl extends BaseSesrvice implements ICertificateTypeService {

	@Override
	public List<CertificateTypeDTO> queryAllType() {
	List<CertificateType> list=cerTypeMapper.queryAllType();
	List<CertificateTypeDTO> listDTO=new ArrayList<CertificateTypeDTO>();
	CertificateTypeDTO cTypeDTO=null;
	if(list.size()>0){
		for (CertificateType certificateType : list) {
			cTypeDTO=new CertificateTypeDTO();
			mapper.map(certificateType, cTypeDTO);
			listDTO.add(cTypeDTO);
		}
	}
		return listDTO;
	}

}
