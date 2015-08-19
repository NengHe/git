package com.hexin.core.page;

import java.io.Serializable;

import com.hexin.core.constant.Constants;

/**
 * 
 * @ClassName: PageCondition
 * @Description:分页查询
 */
public class PageCondition implements Serializable {

	private static final long serialVersionUID = -6435905288498512596L;

	private int page = Constants.DEFAULT_PAGE_PAGE;// 当前页
	private int rows = Constants.DEFAULT_PAGE_ROWS;// 每页显示记录数

	private int offset; // 每页的起始偏移量

	private String sort;// 排序字段
	private String order = Constants.DEFAULT_PAGE_ORDER; // asc/desc

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getOffset() {

		if (this.page <= 0) {
			page = 1;
		}
		offset = (page - 1) * rows;
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
