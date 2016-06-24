package com.cardpay.pccredit.QZBankInterface.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class IESBForCircleReturnMap{
	private static final long serialVersionUID = 1L;
	private String id;
	private String clientName;
	private String clientNo;
	private String contractAmt;
	private String globalId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getContractAmt() {
		return contractAmt;
	}
	public void setContractAmt(String contractAmt) {
		this.contractAmt = contractAmt;
	}
	public String getGlobalId() {
		return globalId;
	}
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
}
