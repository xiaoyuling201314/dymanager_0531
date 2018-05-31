package com.dayuan.bean;

import java.io.Serializable;

public class PageLink implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String pageId;     //页面ID
	
	private String pageLink;   //页面链接
	
	public PageLink() {}
	
	public PageLink(String pageId, String pageLink) {
		this.pageId = pageId;
		this.pageLink = pageLink;
	}
	
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getPageLink() {
		return pageLink;
	}
	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}

}
