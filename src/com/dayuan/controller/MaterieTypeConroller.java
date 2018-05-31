package com.dayuan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.dto.MaterieTypeDTO;
import com.dayuan.service.IMaterieTypeService;

@Controller
@RequestMapping("/materieTypeService")
public class MaterieTypeConroller {
	@Autowired
	private IMaterieTypeService materieTypeService;
	/**
	 * 查询所有物料种类信息
	 * @author xyl
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryAll")
	public ModelAndView selectAllType(HttpServletRequest request,Model model,String type){
		ModelAndView result=null;
		List<MaterieTypeDTO> materTypeList=materieTypeService.queryAllType();
		request.setAttribute("materTypeList", materTypeList);
		if(type==null && "edit".equals(type) )
			result=new ModelAndView("materials_edit");
		else
			result=new ModelAndView("materials_add");
		request.setAttribute("nav", "materials");
		return result;
	}
}
