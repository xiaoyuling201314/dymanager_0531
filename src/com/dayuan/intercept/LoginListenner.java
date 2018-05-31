package com.dayuan.intercept;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.http.HttpRequest;

import com.dayuan.dto.SysUserDTO;
import com.dayuan.util.AppCommon;

public class LoginListenner implements HttpSessionAttributeListener {

	private Map<String, HttpSession> map = new HashMap<String, HttpSession>();  
	/**
	 * 向session中放入数据触发,检查用户是否登录
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
//		// TODO Auto-generated method stub
//		String name=event.getName();
//		if(name.equals("userSession")){
//			SysUserDTO sysUserDTO=(SysUserDTO) event.getValue();
//		    if(map.get(sysUserDTO.getUserName())!=null){
//		    	HttpSession session=map.get(sysUserDTO.getUserName());
//		    	session.removeAttribute(sysUserDTO.getUserName());
//		    	session.invalidate();
//		    }
//		    map.put(sysUserDTO.getUserName(), event.getSession()); 
//		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
//		String name=event.getName();
//		if(name.equals("userSession")){
//			SysUserDTO sysUserDTO=(SysUserDTO) event.getValue();
//			map.remove(sysUserDTO.getUserName());
//		}
	}


	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
	}

	public Map<String, HttpSession> getMap() {
		return map;
	}

	public void setMap(Map<String, HttpSession> map) {
		this.map = map;
	}


}
