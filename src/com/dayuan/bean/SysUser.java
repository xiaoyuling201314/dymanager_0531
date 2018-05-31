package com.dayuan.bean;

import java.util.Date;

public class SysUser {

	private String userName;// 用户名

	private String password;// 用户密码

	private String name;// 真实姓名

	private String email;// 邮箱账号

	private Integer state;// 账号状态,是否登录1=登录,0-注销

	private String department;// 所属部门

	private Integer superAdmin;// 是否为管理员,0=不是,1=是

	private String phone;// 联系方式

	private String comment;// 备注

	private String updateTime;// 修改时间
	
	private String rightList;//权限列表
	
	public SysUser(String userName, String password, String name, String email,
			Integer state, String department, Integer superAdmin, String phone,
			String comment, String updateTime, String rightList, String sessionId) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.email = email;
		this.state = state;
		this.department = department;
		this.superAdmin = superAdmin;
		this.phone = phone;
		this.comment = comment;
		this.updateTime = updateTime;
		this.rightList = rightList;
		this.sessionId = sessionId;
	}

	public String getRightList() {
		return rightList;
	}

	public void setRightList(String rightList) {
		this.rightList = rightList;
	}

	private String sessionId; // 用作记录登录成功后的sessionID
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(Integer superAdmin) {
		this.superAdmin = superAdmin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public SysUser() {
		super();
	}


}