package com.cardpay.pccredit.QZBankInterface.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class EcifFilter extends BusinessFilter{
	
	private String id;//ecif id
	private String clientName;//客户名称
    private String globalId; //证件号码
    private String clientNo;//客户号 
    private String status;//状态
    private String userId;//客户经理id

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

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
