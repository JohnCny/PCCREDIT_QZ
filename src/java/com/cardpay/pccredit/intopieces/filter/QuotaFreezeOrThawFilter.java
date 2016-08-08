package com.cardpay.pccredit.intopieces.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class QuotaFreezeOrThawFilter extends BusinessFilter{
	
	private String customerName;
	
	private String certNo;
	
	private String clientNo;
	
	private String loanStatus;
	
	private String userId;

	private String filterTeamLeader;//团队长查看各小组成员的进件 1要过滤 0 不过滤
	
	private String filterOrgId;//审核人的org_id 贷生活10万以下要过滤是否同一机构
	
	private String filterApprovalMeeting;//判断是否需要过滤审贷会成员
	
	private String status;
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFilterTeamLeader() {
		return filterTeamLeader;
	}

	public void setFilterTeamLeader(String filterTeamLeader) {
		this.filterTeamLeader = filterTeamLeader;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilterOrgId() {
		return filterOrgId;
	}

	public void setFilterOrgId(String filterOrgId) {
		this.filterOrgId = filterOrgId;
	}

	public String getFilterApprovalMeeting() {
		return filterApprovalMeeting;
	}

	public void setFilterApprovalMeeting(String filterApprovalMeeting) {
		this.filterApprovalMeeting = filterApprovalMeeting;
	}
}
