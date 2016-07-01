package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * Description of ProductAttribute
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */

public class KhjljxbcForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	private String  year;                        
	private String  month;                       
	private String  custmanagerid;               
	private Long    activeCustomerNum;        
	private Long    actualActiveCustomerNum;  
	private String  monthActualReceiveIncome; 
	private String  otherRetailProductAwards; 
	private String  balanceDailyAverage;
	private String  overdueLoan;
	private String  displayName;
	private String  accountabilityRiskMargin;
	private String  monthAverageKpiScore;
	private String  overdueLoanJd;
	
	
	public String getOverdueLoanJd() {
		return overdueLoanJd;
	}
	public void setOverdueLoanJd(String overdueLoanJd) {
		this.overdueLoanJd = overdueLoanJd;
	}
	public String getMonthAverageKpiScore() {
		return monthAverageKpiScore;
	}
	public void setMonthAverageKpiScore(String monthAverageKpiScore) {
		this.monthAverageKpiScore = monthAverageKpiScore;
	}
	public String getAccountabilityRiskMargin() {
		return accountabilityRiskMargin;
	}
	public void setAccountabilityRiskMargin(String accountabilityRiskMargin) {
		this.accountabilityRiskMargin = accountabilityRiskMargin;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getCustmanagerid() {
		return custmanagerid;
	}
	public void setCustmanagerid(String custmanagerid) {
		this.custmanagerid = custmanagerid;
	}
	public Long getActiveCustomerNum() {
		return activeCustomerNum;
	}
	public void setActiveCustomerNum(Long activeCustomerNum) {
		this.activeCustomerNum = activeCustomerNum;
	}
	public Long getActualActiveCustomerNum() {
		return actualActiveCustomerNum;
	}
	public void setActualActiveCustomerNum(Long actualActiveCustomerNum) {
		this.actualActiveCustomerNum = actualActiveCustomerNum;
	}
	public String getMonthActualReceiveIncome() {
		return monthActualReceiveIncome;
	}
	public void setMonthActualReceiveIncome(String monthActualReceiveIncome) {
		this.monthActualReceiveIncome = monthActualReceiveIncome;
	}
	public String getOtherRetailProductAwards() {
		return otherRetailProductAwards;
	}
	public void setOtherRetailProductAwards(String otherRetailProductAwards) {
		this.otherRetailProductAwards = otherRetailProductAwards;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getBalanceDailyAverage() {
		return balanceDailyAverage;
	}
	public void setBalanceDailyAverage(String balanceDailyAverage) {
		this.balanceDailyAverage = balanceDailyAverage;
	}
	public String getOverdueLoan() {
		return overdueLoan;
	}
	public void setOverdueLoan(String overdueLoan) {
		this.overdueLoan = overdueLoan;
	}
	
	
	
	


}
