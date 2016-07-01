package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:50:49
 */
public class ManagerSalaryForm extends BaseForm {
	private static final long serialVersionUID = 1L;
   
	private String id;
	
	private String customerId;
	// 客户经理姓名
	private String managerName;
	// 客户经理奖励激励金额（包含风险准备金 ）= 绩效工资
	private String rewardIncentiveInformation;
	// 扣除金额
	private String deductAmount;
	// 底薪
	private String basePay;
	// 津贴
	private String allowance;
	// 责任工资
	private String dutySalary;
	// 返还金额
	private String returnPrepareAmount;
	// 准备金额总额
	private String insertPrepareAmount;
	// 风险准备金余额
	private String riskReserveBalances;
	// 罚款
	private String fine;
	
	private String year;
	
	private String month;
	
	private String describe;
	
	private String own;//本月工资
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwn() {
		return own;
	}

	public void setOwn(String own) {
		this.own = own;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRewardIncentiveInformation() {
		return rewardIncentiveInformation;
	}

	public void setRewardIncentiveInformation(String rewardIncentiveInformation) {
		this.rewardIncentiveInformation = rewardIncentiveInformation;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getBasePay() {
		return basePay;
	}

	public void setBasePay(String basePay) {
		this.basePay = basePay;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}

	public String getReturnPrepareAmount() {
		return returnPrepareAmount;
	}

	public void setReturnPrepareAmount(String returnPrepareAmount) {
		this.returnPrepareAmount = returnPrepareAmount;
	}

	public String getInsertPrepareAmount() {
		return insertPrepareAmount;
	}

	public void setInsertPrepareAmount(String insertPrepareAmount) {
		this.insertPrepareAmount = insertPrepareAmount;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getRiskReserveBalances() {
		return riskReserveBalances;
	}

	public void setRiskReserveBalances(String riskReserveBalances) {
		this.riskReserveBalances = riskReserveBalances;
	}
}
