package com.dayuan.bean;

import java.io.Serializable;

public class PageModel implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String titile;//名称
	private String link;//链接
	private String pagebgsrc;
	private String description;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getPagebgsrc() {
		return pagebgsrc;
	}
	public void setPagebgsrc(String pagebgsrc) {
		this.pagebgsrc = pagebgsrc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
