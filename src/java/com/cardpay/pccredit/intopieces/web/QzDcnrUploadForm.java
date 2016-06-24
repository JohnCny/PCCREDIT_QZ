package com.cardpay.pccredit.intopieces.web;

import com.wicresoft.jrad.base.database.model.BaseModel;

public class QzDcnrUploadForm extends BaseModel{
	
	/**
	 *调查内容附件form
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String customerId;
	
	private String reportId;
	
	private String reportName;
	
	private String loadStatus;
	
	private String applicationId;
	
	private String hetongId;
	
	private String userName;
	
	private String cardId;
	
	private String uploadId;
	
	private String fileName;
	
	private String remark;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(String loadStatus) {
		this.loadStatus = loadStatus;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHetongId() {
		return hetongId;
	}

	public void setHetongId(String hetongId) {
		this.hetongId = hetongId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}
