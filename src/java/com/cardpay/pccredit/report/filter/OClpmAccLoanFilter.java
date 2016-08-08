package com.cardpay.pccredit.report.filter;

import java.util.Date;
import java.util.List;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chinh
 *
 * 2015-8-1下午4:29:53
 */
public class OClpmAccLoanFilter extends BaseQueryFilter{
	// 开始时间
	private Date startDate;
	// 结束时间
	private Date endDate;
	//客户经理号
	private String managerId;
    //机构代码
	private String orgId;
	//客户名称
	private String clientName;
	//客户经理Id
	private String userId;
	
	private String productId;
	

	private String centerId;
	private String teamId;
	private String centerMgrId;

	private List<String> userIds;//
	
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getCenterMgrId() {
		return centerMgrId;
	}
	public void setCenterMgrId(String centerMgrId) {
		this.centerMgrId = centerMgrId;
	}
	public List<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
}
