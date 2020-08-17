package com.digilytics.yatin.java.assignment.view.user;

public class DigilyticsUserResponse {

	private int rowParsed;
	private int rowFailed;
	private String errorFileUrl;

	public DigilyticsUserResponse(int rowParsed, int rowFailed) {

		super();
		this.rowParsed = rowParsed;
		this.rowFailed = rowFailed;
	}

	public DigilyticsUserResponse(int rowParsed, int rowFailed, String errorFileUrl) {

		super();
		this.rowParsed = rowParsed;
		this.rowFailed = rowFailed;
		this.errorFileUrl = errorFileUrl;
	}

	public int getRowParsed() {
		return rowParsed;
	}

	public void setRowParsed(int rowParsed) {
		this.rowParsed = rowParsed;
	}

	public int getRowFailed() {
		return rowFailed;
	}

	public void setRowFailed(int rowFailed) {
		this.rowFailed = rowFailed;
	}

	public String getErrorFileUrl() {
		return errorFileUrl;
	}

	public void setErrorFileUrl(String errorFileUrl) {
		this.errorFileUrl = errorFileUrl;
	}

}
