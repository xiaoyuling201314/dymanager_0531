package com.dayuan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dayuan.bean.PaginationData;
import com.dayuan.dto.SysUserDTO;
import com.dayuan.service.ISysUserService;
import com.dayuan.util.AppCommon;
import com.dayuan.util.Tools;


@Controller
@RequestMapping("/userService")
public class UserController extends BaseController {
	private Logger logger=Logger.getLogger(UserController.class);
	
	@Autowired(required=false)
	private ISysUserService userService;
	/**
	 * 用户登录请求
	 * @param request
	 * @param response
	 * @param userName 用户名
	 * @param password 用户密码
	 * @param rememberMe 是否记住密码
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, String userName, String password,
			String rememberMe, Model model) throws IOException {
		ModelAndView result = new ModelAndView();
		String newPassword = Tools.getSecurityString(password, "MD5");
		SysUserDTO sysUserDTO = userService.userLogin(userName, newPassword);
		if (sysUserDTO != null) {// 返回数据并更新用户登录状态
			//request.getSession().setAttribute("loginuser", sysUserDTO);
			model.addAttribute("users", sysUserDTO);
			sysUserDTO.setState(1);
			userService.updateBySelective(sysUserDTO);
			sysUserDTO.setSessionId(request.getSession().getId());
			request.getSession().setAttribute("userSession", sysUserDTO);
			if (rememberMe != null) {// 记住密码，将账号信息保存在cookie中
				Cookie nameCookie = new Cookie("userName", userName);
				nameCookie.setMaxAge(60 * 60 * 24 * 7);
				nameCookie.setPath("/");
				response.addCookie(nameCookie);
				Cookie pwdCookie = new Cookie("password", password);
				pwdCookie.setMaxAge(60 * 60 * 24 * 7);
				pwdCookie.setPath("/");
				response.addCookie(pwdCookie);
				Cookie isRememberCookie = new Cookie("rememberPwd", rememberMe);
				isRememberCookie.setMaxAge(60 * 60 * 24 * 7);
				isRememberCookie.setPath("/");// 设置具有访问权限的
				response.addCookie(isRememberCookie);
			} else {// 不记住密码，将cookie信息删除
				Cookie[] cookies = request.getCookies();
				Cookie cookie = null;
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						cookie = cookies[i];
						if ("userName".equals(cookie.getName())
								|| "password".equals(cookie.getName())
								|| "rememberPwd".equals(cookie.getName())) {
							cookie.setMaxAge(0);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
					}
				}

			}
			//单点登录
			ServletContext application=request.getSession().getServletContext();
			List<HttpSession> sessions=null;
			if(application.getAttribute("userCount")==null){
				sessions=new ArrayList<HttpSession>();
				application.setAttribute("userCount", sessions);
			}else{
				sessions=(List<HttpSession>) application.getAttribute("userCount");
			}
			new LoginManager().login(sessions,request.getSession());//验证用户是否登录
			// 获取权限列表
			if (sysUserDTO.getRightList() != null) {
				String[] rights = sysUserDTO.getRightList().split(",");
				request.getSession().setAttribute("rightList", rights);
			}
			request.getSession().setAttribute("nav", "home"); // 导航高亮
			result.setViewName("redirect:/view/index.jsp");
		} else {
			request.getSession().setAttribute("userSession", "null");
			request.getSession().setAttribute("error", "用户名或密码错误");
			result.setViewName("redirect:/login.jsp");
		}
		
		return result;
	}
	/**
	 * 用户退出请求
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		SysUserDTO sysUserDTO=(SysUserDTO) request.getSession().getAttribute("userSession");
		if(sysUserDTO!=null){//更行用户登录状态
			SysUserDTO sysDto=new SysUserDTO();
			sysDto.setUserName(sysUserDTO.getUserName());
			sysDto.setState(0);
			userService.updateBySelective(sysDto);
			System.out.println("用户"+sysUserDTO.getUserName()+"退出系统");
		}

		//单点登录
		ServletContext application=request.getSession().getServletContext();
		@SuppressWarnings("unchecked")
		List<HttpSession> sessions=(List<HttpSession>) application.getAttribute("userCount");
		HttpSession session=request.getSession();
		SysUserDTO syDto=null;
		if(sessions!=null && session!=null){
			for (HttpSession httpSession : sessions) {
				syDto=(SysUserDTO)session.getAttribute("userSession");
				if(syDto!=null && ((SysUserDTO)httpSession.getAttribute("userSession")).getUserName().equals(syDto.getUserName())){
					sessions.remove(httpSession);
					httpSession.invalidate();
					break;
				}
			}
		}
		request.getSession().removeAttribute("rightList");
		return "redirect:/login.jsp";//重定向到用户登录页面
	}
	
	@RequestMapping("/addUser")
	public String addUser(SysUserDTO sysUserDTO, Model model) {
		sysUserDTO.setSuperAdmin(0);
		sysUserDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
		sysUserDTO.setPassword(Tools.getSecurityString(
				sysUserDTO.getPassword(), "MD5"));// 对密码进行加密
			String right = sysUserDTO.getRightList();// 设置父级权限
			if (right.contains("A")) {
				right += ",A";
			} 
			if (right.contains("B")) {
				right += ",B";
			} 
			if (right.contains("E")) {
				right += ",E";
			}
			if (right.contains("C")) {
				right += ",C";
			}
			if (right.contains("D")) {
				right += ",D";
			}
			
			sysUserDTO.setRightList(right);
			userService.add(sysUserDTO);
		return "redirect:userList.do";
	}
	
	@ResponseBody
	@RequestMapping(value="/checkUser",produces="application/json;charset=UTF-8")
	public String checkUser(HttpServletRequest request,String userName){
		List<SysUserDTO> sysUserDTOs = userService.queryAll();
		Map<String, Object> map=new HashMap<String, Object>();
		int count=0;
		for (SysUserDTO sysUserDTO2 : sysUserDTOs) {
			if (sysUserDTO2.getUserName().equals(userName)) {
				count+=1;
			}
		}
		map.put("isExit", count);
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
	}
	
	@RequestMapping("/updateUser")
	public String updateUser(SysUserDTO sysUserDTO,String rightList){//SysUserDTO sysUserDTO,
		//SysUserDTO sysUserDTO=new SysUserDTO("xiaoling6", "123", "小玲", "123@qq.com", 1, "销售部2", 0, "",  "",new Date());
		sysUserDTO.setUpdateTime(Tools.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
	  SysUserDTO oldSysUserDTO=userService.queryById(sysUserDTO.getUserName());
		String right =rightList;// 设置父级权限
			if(!oldSysUserDTO.getRightList().equals(right)){
				if (right.contains("A")) {
					right += ",A";
				} 
				if (right.contains("B")) {
					right += ",B";
				} 
				if (right.contains("E")) {
					right += ",E";
				}
				if (right.contains("C")) {
					right += ",C";
				}
				if (right.contains("D")) {
					right += ",D";
				}
				
			}
			sysUserDTO.setRightList(right);
			String oldPassword=oldSysUserDTO.getPassword();
			String newPassword=sysUserDTO.getPassword();
			if(!oldPassword.equals(newPassword)){
			sysUserDTO.setPassword(Tools.getSecurityString(newPassword, "MD5"));
		}
		int count=userService.updateById(sysUserDTO);
		System.out.println("执行结果为："+count);
		return "redirect:userList.do";
	}
	
	@RequestMapping("/editUser")
	public String editUser(HttpServletRequest request,Model model,String userName){
		SysUserDTO sysUserDTO=userService.queryById(userName);
		model.addAttribute("sysUserDTO", sysUserDTO);
		return "users_edit";
	}
	@ResponseBody
	@RequestMapping(value="/deleteUser",produces="application/json;charset=UTF-8")
	public String deleteUser(HttpServletRequest request,HttpServletResponse response,String  myName,Integer curPage){//异步删除
		int count=userService.deleteById(myName);//xiaoling3
		System.out.println("执行结果为："+count);
		PaginationData pData=new PaginationData();
		
		List<SysUserDTO> listData=userService.queryList("",curPage, pData.getPageSize());
		pData.setRecordCount(userService.queryRecordCount(""),pData.getPageSize());
		pData.setPageSize(pData.getPageSize());
		pData.setCurPage(curPage);
		pData.setStartIndex((pData.getCurPage()-1)*pData.getPageSize());
		request.setAttribute("pData", pData);
		JSONArray jsonArray=new JSONArray(listData);
		return jsonArray.toString();
	}
	/**
	 *查询和分页共用方法
	 *@param  userName查询关键字
	 *@author xyl
	 */
	@RequestMapping("/userList")
	public ModelAndView  userList(HttpServletRequest request,String userName,PaginationData pagData, Integer curPage){//Integer curPage,Integer pageSize
		ModelAndView result=new ModelAndView("users_add_list");
		PaginationData pData=new PaginationData();
		if (curPage == null) {
			curPage = pagData.getCurPage();
		}
		List<SysUserDTO> listData=userService.queryList(userName,curPage, pagData.getPageSize());
		pData.setRecordCount(userService.queryRecordCount(userName),pagData.getPageSize());
		pData.setCurPage(curPage);
		pData.setPageSize(pagData.getPageSize());
		pData.setItemsData(listData);
		pData.setStartIndex((curPage-1)*pagData.getPageSize());
		request.setAttribute("pData", pData);
		request.setAttribute("userName", userName);
		result.addObject("nav", "userList");
		return result;
	}
	
	@RequestMapping("/selectDetail")
	public ModelAndView selectDetail(HttpServletRequest request,Model model,String userName,String type){
	ModelAndView result=null;
	SysUserDTO sysUserDTO=userService.queryById(userName);
	model.addAttribute("sysUserDTO", sysUserDTO);
	if(type!=null && "view".equals(type)){
		result=new ModelAndView("users_view");
		result.addObject("nav", "userList");
	}else{
		result=new ModelAndView("users");
		result.addObject("nav", "userDetail"); 
	}
	return result;
	}
	 @ResponseBody  
	 @RequestMapping(value = "/modifyPassword",produces="application/json;charset=UTF-8")  
	public String modifyPassword(HttpServletRequest request,String password,String newPassword){
		SysUserDTO sysUserDTO=(SysUserDTO) request.getSession().getAttribute("userSession");
		int count=0;
		if(sysUserDTO!=null){//更行用户登录状态
			String encPassword=Tools.getSecurityString(password, "MD5");
			if(encPassword.equals(sysUserDTO.getPassword())){
				SysUserDTO sysDto=new SysUserDTO();
				sysDto.setUserName(sysUserDTO.getUserName());
				sysDto.setPassword(Tools.getSecurityString(newPassword, "MD5"));
				count=userService.updateBySelective(sysDto);
			}else{
				count=-1;
			}
			
		}
		//request.setAttribute("message.password", "修改成功");
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		JSONObject jsonObject=new JSONObject(map);
		return jsonObject.toString();
	}
}
