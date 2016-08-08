/*
 * Copyright 2013 Wicresoft; Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @Title: QzApplnDshJyd.java
 * @Package com.cardpay.pccredit.intopieces.model
 * @Description: 贷生活决议单
 * @author tanwh
 * @date 2016年6月21日 下午3:01:08
 * @version V1.0
 */
public class QzApplnDshJydForm extends BaseForm{

	private static final long serialVersionUID = 1L;

	private String customerId;
	private String applicationId;

	private String managerBrId;
	private String managerId;
	private String clientName;
	private String globalId;
	private String discussItems;
	private String sugAmt;
	private String sugDeadline;
	private String sugRate;
	private String loanUseType;
	private String sugRepayType;
	private String coborrower1Name;
	private String coborrower1Cardid;
	private String coborrower2Name;
	private String coborrower2Cardid;
	private String guarantee1Name;
	private String guarantee1Cardid;
	private String guarantee2Name;
	private String guarantee2Cardid;
	private String managerName;
	private String helpManagerName;
	private String verifyItems;
	private String amt;
	private String rate;
	private String deadline;
	private String repaymentTerm;
	private String repayType;
	private String guaranteeItems;
	private String otherSug;
	private String focusContent;
	private String otherRequirement;
	private String refuseReason;
	private String checkManagerId;
	private String checkManagerName;
	private String subBranchManagerSug;
	private String subBranchManagerId;
	private String centerSug;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getManagerBrId() {
		return managerBrId;
	}
	public void setManagerBrId(String managerBrId) {
		this.managerBrId = managerBrId;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
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
	public String getDiscussItems() {
		return discussItems;
	}
	public void setDiscussItems(String discussItems) {
		this.discussItems = discussItems;
	}
	public String getSugAmt() {
		return sugAmt;
	}
	public void setSugAmt(String sugAmt) {
		this.sugAmt = sugAmt;
	}
	public String getSugDeadline() {
		return sugDeadline;
	}
	public void setSugDeadline(String sugDeadline) {
		this.sugDeadline = sugDeadline;
	}
	public String getSugRate() {
		return sugRate;
	}
	public void setSugRate(String sugRate) {
		this.sugRate = sugRate;
	}
	public String getLoanUseType() {
		return loanUseType;
	}
	public void setLoanUseType(String loanUseType) {
		this.loanUseType = loanUseType;
	}
	public String getSugRepayType() {
		return sugRepayType;
	}
	public void setSugRepayType(String sugRepayType) {
		this.sugRepayType = sugRepayType;
	}
	public String getCoborrower1Name() {
		return coborrower1Name;
	}
	public void setCoborrower1Name(String coborrower1Name) {
		this.coborrower1Name = coborrower1Name;
	}
	public String getCoborrower1Cardid() {
		return coborrower1Cardid;
	}
	public void setCoborrower1Cardid(String coborrower1Cardid) {
		this.coborrower1Cardid = coborrower1Cardid;
	}
	public String getCoborrower2Name() {
		return coborrower2Name;
	}
	public void setCoborrower2Name(String coborrower2Name) {
		this.coborrower2Name = coborrower2Name;
	}
	public String getCoborrower2Cardid() {
		return coborrower2Cardid;
	}
	public void setCoborrower2Cardid(String coborrower2Cardid) {
		this.coborrower2Cardid = coborrower2Cardid;
	}
	public String getGuarantee1Name() {
		return guarantee1Name;
	}
	public void setGuarantee1Name(String guarantee1Name) {
		this.guarantee1Name = guarantee1Name;
	}
	public String getGuarantee1Cardid() {
		return guarantee1Cardid;
	}
	public void setGuarantee1Cardid(String guarantee1Cardid) {
		this.guarantee1Cardid = guarantee1Cardid;
	}
	public String getGuarantee2Name() {
		return guarantee2Name;
	}
	public void setGuarantee2Name(String guarantee2Name) {
		this.guarantee2Name = guarantee2Name;
	}
	public String getGuarantee2Cardid() {
		return guarantee2Cardid;
	}
	public void setGuarantee2Cardid(String guarantee2Cardid) {
		this.guarantee2Cardid = guarantee2Cardid;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getHelpManagerName() {
		return helpManagerName;
	}
	public void setHelpManagerName(String helpManagerName) {
		this.helpManagerName = helpManagerName;
	}
	public String getVerifyItems() {
		return verifyItems;
	}
	public void setVerifyItems(String verifyItems) {
		this.verifyItems = verifyItems;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getRepaymentTerm() {
		return repaymentTerm;
	}
	public void setRepaymentTerm(String repaymentTerm) {
		this.repaymentTerm = repaymentTerm;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getGuaranteeItems() {
		return guaranteeItems;
	}
	public void setGuaranteeItems(String guaranteeItems) {
		this.guaranteeItems = guaranteeItems;
	}
	public String getOtherSug() {
		return otherSug;
	}
	public void setOtherSug(String otherSug) {
		this.otherSug = otherSug;
	}
	public String getFocusContent() {
		return focusContent;
	}
	public void setFocusContent(String focusContent) {
		this.focusContent = focusContent;
	}
	public String getOtherRequirement() {
		return otherRequirement;
	}
	public void setOtherRequirement(String otherRequirement) {
		this.otherRequirement = otherRequirement;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public String getCheckManagerId() {
		return checkManagerId;
	}
	public void setCheckManagerId(String checkManagerId) {
		this.checkManagerId = checkManagerId;
	}
	public String getCheckManagerName() {
		return checkManagerName;
	}
	public void setCheckManagerName(String checkManagerName) {
		this.checkManagerName = checkManagerName;
	}
	public String getSubBranchManagerSug() {
		return subBranchManagerSug;
	}
	public void setSubBranchManagerSug(String subBranchManagerSug) {
		this.subBranchManagerSug = subBranchManagerSug;
	}
	public String getSubBranchManagerId() {
		return subBranchManagerId;
	}
	public void setSubBranchManagerId(String subBranchManagerId) {
		this.subBranchManagerId = subBranchManagerId;
	}
	public String getCenterSug() {
		return centerSug;
	}
	public void setCenterSug(String centerSug) {
		this.centerSug = centerSug;
	}
}
