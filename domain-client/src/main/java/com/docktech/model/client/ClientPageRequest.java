package com.docktech.model.client;

public class ClientPageRequest {

	private Integer page;
	private Integer linesPerPage;
	private String direction;
	private String orderBy;

	public ClientPageRequest(Integer page, Integer linesPerPage, String direction, String orderBy) {
		super();
		this.page = page;
		this.linesPerPage = linesPerPage;
		this.setDirection(direction);
		this.orderBy = orderBy;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLinesPerPage() {
		return linesPerPage;
	}

	public void setLinesPerPage(Integer linesPerPage) {
		this.linesPerPage = linesPerPage;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
