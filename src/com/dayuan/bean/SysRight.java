package com.dayuan.bean;

import java.io.Serializable;

/**
 * 用户权限表sys_right
 * 
 * @author xiaoyuling
 * 
 */
public class SysRight implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rightId;// 权限ID
	
	private String rightName;// 权限名称

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public SysRight() {
		super();
	}

	public SysRight(String rightId, String rightName) {
		super();
		this.rightId = rightId;
		this.rightName = rightName;
	}
	
	
}