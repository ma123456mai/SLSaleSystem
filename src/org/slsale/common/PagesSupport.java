package org.slsale.common;

import java.util.ArrayList;
import java.util.List;

public class PagesSupport {

	// 记录总条数
	private Integer totalCount = 0;
	// 总页面数
	private Integer pageCount;
	// 页面容量
	private Integer pageSize = 2;
	// 当前页
	private Integer page = 1;
	// 当前页前和后显示页码数量
	private Integer num = 3;
	// 当前页列表（数据列表）
	private List items = new ArrayList();

	public Integer getTotalCount() {
		return totalCount;
	}

	// 计算总页数
	public void setTotalCount(Integer totalCount) {
		if (totalCount <= 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
			if (this.totalCount % this.pageSize == 0) {
				this.pageCount = this.totalCount / this.pageSize;
			} else {
				this.pageCount = this.totalCount / this.pageSize + 1;
			}
		}
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	// 控制页面显示容量
	public void setPageSize(Integer pageSize) {
		if (pageSize <= 0) {
			this.pageSize = 1;
		} else {
			this.pageSize = pageSize;
		}
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	// 获取前一个页面
	public Integer gerPrev() {
		return page - 1;
	}

	// 获取后一个页面
	public Integer gerNext() {
		return page + 1;
	}

	// 获取最后一个面
	public Integer getLast() {
		return pageCount;
	}

	// 判断是否存在前一个页面
	public boolean getIsPrev() {
		if (page > 1) {
			return true;
		}
		return false;
	}

	// 判断是否存在后一个页面
	public boolean getIsNext() {
		if (page < pageCount && pageCount != null) {
			return true;
		}
		return false;
	}

	// 当前页面前num页
	public List<Integer> getPrevPages() {
		List<Integer> list = new ArrayList<Integer>();
		// 起始显示的页码
		Integer frontstart = 1;
		if (page > num) {
			frontstart = page - num;
		}
		for (Integer i = frontstart; i < page; i++) {
			list.add(i);
		}
		return list;
	}

	// 当前页面后num页
	public List<Integer> getNextPages() {
		List<Integer> list = new ArrayList<Integer>();
		// 起始显示的页码
		Integer endCount = page;
		if (pageCount != null) {
			if((page + num) < pageCount && num < pageCount){
				endCount = page + num;
			}else{
				endCount = pageCount;
			}
		}
		for (Integer i = page + 1; i <= endCount; i++) {
			list.add(i);
		}
		return list;
	}
}
