package com.cardpay.pccredit.report.model;

/**    
* @Title: HomeTips.java  
* @Package com.cardpay.pccredit.report.model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author tanwh    
* @date 2016年4月25日 上午10:47:14  
* @version V1.0    
*/
public class HomeTips {
	//用信提醒
	private String userId;//客户经理ID
	private String userName;//客户经理姓名
	private String allCreditAmount;//授信总额
	private String usedCreditAmount;//用信总额
	private double percent;//比例
	
	//贷后提醒
	private int endDateNum;//过期数
	private int remindDateNum;//过期提醒数
	
	//21日计息提醒
	private String clientName;
	private double psAmt;//应还
	private double setlAmt;//已还
	private double ownAmt;//欠
	
	//到期客户
	private String loanAmt;//借据金额
	private String expiryDate;//到期日期
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAllCreditAmount() {
		return allCreditAmount;
	}
	public void setAllCreditAmount(String allCreditAmount) {
		this.allCreditAmount = allCreditAmount;
	}
	public String getUsedCreditAmount() {
		return usedCreditAmount;
	}
	public void setUsedCreditAmount(String usedCreditAmount) {
		this.usedCreditAmount = usedCreditAmount;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getEndDateNum() {
		return endDateNum;
	}
	public void setEndDateNum(int endDateNum) {
		this.endDateNum = endDateNum;
	}
	public int getRemindDateNum() {
		return remindDateNum;
	}
	public void setRemindDateNum(int remindDateNum) {
		this.remindDateNum = remindDateNum;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public double getPsAmt() {
		return psAmt;
	}
	public void setPsAmt(double psAmt) {
		this.psAmt = psAmt;
	}
	public double getSetlAmt() {
		return setlAmt;
	}
	public void setSetlAmt(double setlAmt) {
		this.setlAmt = setlAmt;
	}
	public double getOwnAmt() {
		return ownAmt;
	}
	public void setOwnAmt(double ownAmt) {
		this.ownAmt = ownAmt;
	}
	public String getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
