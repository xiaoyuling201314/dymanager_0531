package com.dayuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dayuan.dto.CircuitboardDTO;
import com.dayuan.dto.CircuitboardDetailDTO;
import com.dayuan.dto.MaterielDTO;
import com.dayuan.service.ICircuitboardDetailService;

@Controller
public class TestController {
	@Autowired
	private ICircuitboardDetailService circuitboardService;
	
	@RequestMapping("/test")
	public void AddCircuit(){
//		CircuitboardDetailDTO dto=new CircuitboardDetailDTO();
//		CircuitboardDTO cto=new CircuitboardDTO();
//		cto.setId(2);
//		dto.setCircuitId(cto);
//		MaterielDTO maDto=new MaterielDTO();
//		maDto.setMaterielNo("01.1607");
//		dto.setMaterielNo(maDto);
//		circuitboardService.add(dto);
//		System.out.println("lllllllllllllllllllllllll");
	}
	
}
