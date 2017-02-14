package com.cardpay.pccredit.intopieces.filter;

import java.util.Date;
import java.util.List;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

/**
 * CustomerApplicationProcess类的描述
 * 
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
public class CustomerApplicationProcessFilter extends BusinessFilter {

	private String loginId;
	private String applicationId;
	private String auditUser;
	private String serialNumber;
	private String auditOpinion;
	private String refusalReason;
	private String fallbackReason;
	private String processrecordId;
	private String nextNodeId;
	private String delayAuditUser;
	private Date auditTime;
	private String cardId;
	private String nodeName;//进件状态
	private String chineseName;
	
	private String filterTeamLeader;//团队长查看各小组成员的进件 1要过滤 0 不过滤
	
	private String filterOrgId;//审核人的org_id 贷生活10万以下要过滤是否同一机构
	
	private String filterApprovalMeeting;//判断是否需要过滤审贷会成员
	
	private String filterAmount20;//判断金额是否小于20万
	private String filterAmount20Above;//判断金额是否超过20万
	
	private List<String> userIds;
	private Date endDate;
	private String remindTime;
	private String endTime;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}

	public String getFallbackReason() {
		return fallbackReason;
	}

	public void setFallbackReason(String fallbackReason) {
		this.fallbackReason = fallbackReason;
	}

	public String getProcessrecordId() {
		return processrecordId;
	}

	public void setProcessrecordId(String processrecordId) {
		this.processrecordId = processrecordId;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getDelayAuditUser() {
		return delayAuditUser;
	}

	public void setDelayAuditUser(String delayAuditUser) {
		this.delayAuditUser = delayAuditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getFilterTeamLeader() {
		return filterTeamLeader;
	}

	public void setFilterTeamLeader(String filterTeamLeader) {
		this.filterTeamLeader = filterTeamLeader;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public String getFilterAmount20() {
		return filterAmount20;
	}

	public void setFilterAmount20(String filterAmount20) {
		this.filterAmount20 = filterAmount20;
	}

	public String getFilterAmount20Above() {
		return filterAmount20Above;
	}

	public void setFilterAmount20Above(String filterAmount20Above) {
		this.filterAmount20Above = filterAmount20Above;
	}
}
