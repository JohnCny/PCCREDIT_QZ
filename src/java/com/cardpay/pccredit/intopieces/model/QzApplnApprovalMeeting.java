package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**    
* @Title: QzApplnApprovalMeeting.java  
* @Package com.cardpay.pccredit.intopieces.model  
* @Description: 排审表  
* @author tanwh    
* @date 2016年7月4日 上午11:18:20  
* @version V1.0    
*/

@ModelParam(table = "qz_appln_approval_meeting")
public class QzApplnApprovalMeeting  extends BusinessModel {
	
	private static final long serialVersionUID = 6033520239271743521L;
	
	private String applicationId;////进件编号
	private String customerId;//客户id
	private String userId;//审贷人id
	private String managerId;//客户经理ID
	private Date meetingTime;//会议时间
	private Integer lsh;//今天的第几个
	private String status;//是否完成 0未完成 1已完成
	
	private String customerName;//客户名称，显示用
	private String managerName;//客户经理名称，显示用
	private String displayTime;//会议时间，显示用
	private String userName;//审贷员名,显示用
	private String mindex;////今天的第几个,显示用
	private String productName;//产品名称 显示用
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public Date getMeetingTime() {
		return meetingTime;
	}
	public void setMeetingTime(Date meetingTime) {
		this.meetingTime = meetingTime;
	}
	public Integer getLsh() {
		return lsh;
	}
	public void setLsh(Integer lsh) {
		this.lsh = lsh;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getDisplayTime() {
		return displayTime;
	}
	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMindex() {
		return mindex;
	}
	public void setMindex(String mindex) {
		this.mindex = mindex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
