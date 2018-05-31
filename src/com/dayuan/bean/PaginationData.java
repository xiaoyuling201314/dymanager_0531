package com.dayuan.bean;

import java.util.ArrayList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 带分页信息的数据列表信息
 * 
 */
public class PaginationData {
	private List itemsData; // 数据
	private Integer recordCount; // 记录总数
	private Integer pageCount; // 总页数
	private Integer curPage=1; // 当前页码
	private Integer pageSize=10; // 每页记录数
	private StringBuilder pageLink; // 分页链接
	private Integer startIndex;//分页起始记录数
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	private int numScalar = 10; // 显示分页数字的个数(可选,默认10)
	private boolean showRecordCountInfo = true; // 是否显示记录总数信息(可选,默认true)

	public static final int PAGE_SIZE = 10;

	public PaginationData() {
		itemsData = new ArrayList();
		recordCount = -1;
		pageCount = 0;
		curPage = 1;
		pageSize = 10;
	}

	public PaginationData(int pagesize) {
		recordCount = -1;
		pageCount = 0;
		curPage = 1;
		pageSize = pagesize;
	}
	public static int getStartIndex(int curPage,int pageSize){
		return (curPage==0?0:(curPage-1)*pageSize);
		
	}
	public Integer getStartIndex() {
		return startIndex;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}


	public List getItemsData() {
		return itemsData;
	}

	public void setItemsData(List itemsData) {
		this.itemsData = itemsData;
	}

	public Integer getPageCount() {
		return pageCount;
	}


	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount, Integer pagesize) {
		this.recordCount = recordCount;
		this.pageSize = pagesize;
		if (this.recordCount == 0) {
			return;
		}
		this.pageCount = (this.recordCount - 1) / this.pageSize + 1;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}