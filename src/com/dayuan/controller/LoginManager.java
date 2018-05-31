package com.dayuan.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dayuan.dto.SysUserDTO;

public class LoginManager {

	public HttpSession login(List<HttpSession> sessions, HttpSession session) {
		List<HttpSession> sessionde=new ArrayList<HttpSession>();
		for (HttpSession httpSession : sessions) {
			try {
				if(((SysUserDTO)httpSession.getAttribute("userSession")).getUserName().equals(((SysUserDTO)session.getAttribute("userSession")).getUserName())){
					sessions.remove(httpSession);
					httpSession.invalidate();
					break;
				}
			} catch (Exception e) {
				sessionde.add(httpSession);
			}
		}
		for (int i = 0; i < sessionde.size(); i++) {
			sessions.remove(sessionde.get(i));
		}
		sessions.add(session);
		return null;
		
	}
 
}
