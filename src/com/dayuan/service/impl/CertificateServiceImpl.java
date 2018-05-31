package com.dayuan.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dayuan.bean.Certificate;
import com.dayuan.bean.Circuitboard;
import com.dayuan.bean.CircuitboardDetail;
import com.dayuan.bean.PaginationData;
import com.dayuan.dto.CertificateDTO;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.service.ICertificateService;
import com.dayuan.util.Tools;
import com.ibm.icu.text.SimpleDateFormat;
/**
 * 资质证书业务类
 * @author xiaoyuling
 *
 */
@Service("certificate")
public class CertificateServiceImpl extends BaseSesrvice implements ICertificateService {

	@Override
	public int add(CertificateDTO certificateDTO) {
		Certificate certificate=new Certificate();
		mapper.map(certificateDTO, certificate);
		return certificateMapper.add(certificate);
	}

	@Override
	public int addBySelective(CertificateDTO certificateDTO) {
		Certificate certificate=new Certificate();
		mapper.map(certificateDTO, certificate);
		return certificateMapper.add(certificate);
	}

	@Override
	public int deleteById(Object id) {
		return certificateMapper.deleteById(id);
	}

	@Override
	public int updateById(CertificateDTO cerDto) {
		Certificate certificate=new Certificate();
		mapper.map(cerDto, certificate);
		return certificateMapper.updateById(certificate);
	}

	@Override
	public int updateBySelective(CertificateDTO cerDto) {
		Certificate certificate=new Certificate();
		mapper.map(cerDto, certificate);
		return certificateMapper.updateBySelective(certificate);
	}

	@Override
	public CertificateDTO queryById(Object id) {
		Certificate certificate=certificateMapper.queryById(id);
		CertificateDTO certificateDTO=new CertificateDTO();
		mapper.map(certificate, certificateDTO);
		//certificateDTO.setCalibStartTimeStr(Tools.getDateString(certificateDTO.getCalibStartTime()));
		//certificateDTO.setCalibEndTimeStr(Tools.getDateString(certificateDTO.getCalibEndTime()));
		return certificateDTO;
	}


	@Override
	public List<CertificateDTO> queryList(String sapNo, int curPage, int pageSize) {
		List<Certificate> list=certificateMapper.queryList(sapNo, PaginationData.getStartIndex(curPage, pageSize),pageSize);
		List<CertificateDTO> liDtos=new ArrayList<CertificateDTO>();
		CertificateDTO ciDetailDTO=null;
		if(list.size()>0){
			for (Certificate certificate : list) {
				ciDetailDTO=new CertificateDTO();
				certificate.setUpdateTime(Tools.getFormatDateString(certificate.getUpdateTime()));
				mapper.map(certificate, ciDetailDTO);
				if(StringUtils.isBlank(ciDetailDTO.getCalibEndTime())){
					ciDetailDTO.setValidCalib("(长期有效)");
				}else if(compareDate(ciDetailDTO.getCalibEndTime())==1){
					ciDetailDTO.setValidCalib("<br/><label style='color:red;'>(已过期)</label>");
				}
				else if(compareDate(ciDetailDTO.getCalibEndTime())==2){
					ciDetailDTO.setValidCalib("<br/><label style='color:orange;'>(有效期小于3个月)</label>");
				}else{
					ciDetailDTO.setValidCalib("");
				}
				liDtos.add(ciDetailDTO);
			}
		}
		return liDtos;
	}
	/**
	 * 比较时间大小，返回1：已过期，返回2：即将过期
	 * @param date
	 * @return
	 */
	public int compareDate(String  date){
		int state=0;
		Date calibEndTime=Tools.getDate(date);
		Date nowDate=new Date();
		if(calibEndTime.getTime()<nowDate.getTime()){
			state=1;
		}else{
			Calendar cl1=Calendar.getInstance();
			Calendar cl2=Calendar.getInstance();
			cl1.setTime(calibEndTime);
			cl2.setTime(nowDate);
			int month=(cl1.get(Calendar.YEAR)-cl2.get(Calendar.YEAR))*12-(cl1.get(Calendar.MONTH)-cl2.get(Calendar.MONTH));
			if(month<=3){
				state=2;
			}
		}
		
		return state;
	}
	@Override
	public int addList(List<CertificateDTO> list) {
		return 0;
	}

	@Override
	public List<CertificateDTO> queryList(String sapNo, String version,
			int curPage, int pageSize) {
		List<Certificate> list=certificateMapper.queryList(sapNo, version,curPage,pageSize);
		List<CertificateDTO> liDtos=new ArrayList<CertificateDTO>();
		CertificateDTO ciDetailDTO=null;
		if(list.size()>0){
			for (Certificate certificate : list) {
				ciDetailDTO=new CertificateDTO();
				certificate.setUpdateTime(Tools.getFormatDateString(certificate.getUpdateTime()));
				mapper.map(certificate, ciDetailDTO);
				liDtos.add(ciDetailDTO);
			}
		}
		return liDtos;
	}
//
//	@Override
//	public List<CertificateDTO> queryList(String sapNo, String version) {
//		List<Certificate> list=certificateMapper.queryList(sapNo, version);
//		List<CertificateDTO> liDtos=new ArrayList<CertificateDTO>();
//		CertificateDTO ciDetailDTO=null;
//		if(list.size()>0){
//			for (Certificate certificate : list) {
//				ciDetailDTO=new CertificateDTO();
//				mapper.map(certificate, ciDetailDTO);
//				liDtos.add(ciDetailDTO);
//			}
//		}
//		return liDtos;
//	}

	@Override
	public Integer queryRecordCount(String sapNo) {
		return certificateMapper.queryRecordCount(sapNo);
	}

	@Override
	public List<String> queryAllName(String sapNo) {
		return certificateMapper.queryAllName(sapNo);
	}

	
}
