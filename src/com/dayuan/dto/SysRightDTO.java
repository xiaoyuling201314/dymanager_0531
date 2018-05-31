package com.dayuan.dto;

import java.io.Serializable;

/**
 * 用户权限表sys_right
 * 
 * @author xiaoyuling
 * 
 */
public class SysRightDTO {
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

public SysRightDTO() {
	super();
}

public SysRightDTO(String rightId, String rightName) {
	super();
	this.rightId = rightId;
	this.rightName = rightName;
}

}