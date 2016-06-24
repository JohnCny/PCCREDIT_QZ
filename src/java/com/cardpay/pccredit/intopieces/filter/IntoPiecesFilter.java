package com.cardpay.pccredit.intopieces.filter;

import java.util.Date;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class IntoPiecesFilter extends BusinessFilter{
	
	private String id;//进件编号
	private String chineseName;//客户名称
    private String productName; //产品名称
    private String cardId; //证件号码
    
    private String status;
    
    private String  userId;
    
    private String productId;//产品id
    
    private String originalName;
    private String batchId;
    private String isUpload;
    private String type;//操作类型
    
    private String taskId;
    private String clientNo;
    private String first_flag;
    private String filterTeamLeader;//团队长查看各小组成员的进件 1要过滤 0 不过滤
    
    private String cus_mgr_id;
    private Date startDate;
    private Date endDate;
    private String check_approve_flag;
    
    private String viewType;//单张查看 翻页查看
    
    private String isContinue;//续授信
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getFirst_flag() {
		return first_flag;
	}
	public void setFirst_flag(String first_flag) {
		this.first_flag = first_flag;
	}
	public String getFilterTeamLeader() {
		return filterTeamLeader;
	}
	public void setFilterTeamLeader(String filterTeamLeader) {
		this.filterTeamLeader = filterTeamLeader;
	}
	public String getCus_mgr_id() {
		return cus_mgr_id;
	}
	public void setCus_mgr_id(String cus_mgr_id) {
		this.cus_mgr_id = cus_mgr_id;
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
	public String getCheck_approve_flag() {
		return check_approve_flag;
	}
	public void setCheck_approve_flag(String check_approve_flag) {
		this.check_approve_flag = check_approve_flag;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getIsContinue() {
		return isContinue;
	}
	public void setIsContinue(String isContinue) {
		this.isContinue = isContinue;
	}
}
