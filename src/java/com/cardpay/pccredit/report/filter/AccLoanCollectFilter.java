package com.cardpay.pccredit.report.filter;

import java.util.Date;
import java.util.List;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chinh
 *
 * 2015-8-1下午4:29:53
 */
public class AccLoanCollectFilter extends BaseQueryFilter{
	// 起始日期
	private String startDate;
	// 截止日期
	private String endDate;
	//产品类型
	private String productId;
	
	private String userId;
	
	private String filterTeamLeader;
	private String filterOrgId;//审核人的org_id 贷生活10万以下要过滤是否同一机构
	private String loginId;
	
	private String centerId;
	private String teamId;
	private String centerMgrId;
	
	private List<String> userIds;//
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getFilterTeamLeader() {
		return filterTeamLeader;
	}
	public void setFilterTeamLeader(String filterTeamLeader) {
		this.filterTeamLeader = filterTeamLeader;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getFilterOrgId() {
		return filterOrgId;
	}
	public void setFilterOrgId(String filterOrgId) {
		this.filterOrgId = filterOrgId;
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
