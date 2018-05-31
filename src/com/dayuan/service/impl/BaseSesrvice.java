package com.dayuan.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.dao.CertificateMapper;
import com.dayuan.dao.CertificateTypeMapper;
import com.dayuan.dao.CircuitboardDetailMapper;
import com.dayuan.dao.CircuitboardMapper;
import com.dayuan.dao.CompletemachineMapper;
import com.dayuan.dao.CompletemachinedetailMapper;
import com.dayuan.dao.DocumentMapper;
import com.dayuan.dao.ManualMapper;
import com.dayuan.dao.InstrumentinfoMapper;
import com.dayuan.dao.MaterieTypeMapper;
import com.dayuan.dao.MaterielDrawingsMapper;
import com.dayuan.dao.MaterielMapper;
import com.dayuan.dao.PackingListDetailMapper;
import com.dayuan.dao.PackinglistMapper;
import com.dayuan.dao.RepairRecordMapper;
import com.dayuan.dao.ShippingrecordsMapper;
import com.dayuan.dao.SysRightMapper;
import com.dayuan.dao.SysUserMapper;
import com.dayuan.dto.MaterielDTO;
@Service
public class BaseSesrvice {
	@Autowired
	protected SysUserMapper sysUserMapper;
	@Autowired
	protected Mapper mapper;
	@Autowired
	protected InstrumentinfoMapper instrumentMapper;
	@Autowired
	protected CertificateMapper certificateMapper;
	@Autowired
	protected CertificateTypeMapper cerTypeMapper;
	@Autowired
	protected CircuitboardMapper circuitboardMapper;
	@Autowired
	protected CircuitboardDetailMapper cirDetailMapper;
	@Autowired
	protected CompletemachineMapper compleMapper;
	@Autowired
	protected CompletemachinedetailMapper compleDMapper;
	@Autowired
	protected DocumentMapper documentMapper;
	@Autowired
	protected ManualMapper manualMapper;
	@Autowired
	protected MaterielDrawingsMapper maDrawingsMapper;
	@Autowired
	protected MaterielMapper materielMapper;
	@Autowired
	protected MaterieTypeMapper materieTypeMapper;
	@Autowired
	protected PackingListDetailMapper pacDetailMapper;
	@Autowired
	protected PackinglistMapper packinglistMapper;
	@Autowired
	protected SysRightMapper sysRightMapper;
	@Autowired
	protected ShippingrecordsMapper shipmentsMapper;
	@Autowired
	protected RepairRecordMapper repairMapper;
	
	public MaterielDTO setDrawsing(MaterielDTO materielDTO){
		String	showDrawings=maDrawingsMapper.queryLastDrawsById(materielDTO.getMaterielNo());
		materielDTO.setShowDrawings(showDrawings);
		return materielDTO;
	}
	
}
