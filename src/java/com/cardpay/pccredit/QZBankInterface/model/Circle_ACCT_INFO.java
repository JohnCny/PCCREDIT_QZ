package com.cardpay.pccredit.QZBankInterface.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author shaoming
 *
 */
@ModelParam(table = "QZ_IESB_FOR_CIRCLE_ACCT_INFO",generator=IDType.assigned)
public class Circle_ACCT_INFO extends BusinessModel{
	private static final long serialVersionUID = 1L;
	
	private String circleId;
	private String acctChrt;
	private String openAcctDate;
	private String acctNo;
	private String acctName;
	private String openAcctBranchId;
	private String openAcctBranchName;
	private String payAmt;
	private String glCode;
	private String ccy;
	private String cInterbankId;
	private String ownBranchFlag;
	
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getOwnBranchFlag() {
		return ownBranchFlag;
	}
	public void setOwnBranchFlag(String ownBranchFlag) {
		this.ownBranchFlag = ownBranchFlag;
	}
	public String getAcctChrt() {
		return acctChrt;
	}
	public void setAcctChrt(String acctChrt) {
		this.acctChrt = acctChrt;
	}
	public String getOpenAcctDate() {
		return openAcctDate;
	}
	public void setOpenAcctDate(String openAcctDate) {
		this.openAcctDate = openAcctDate;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getOpenAcctBranchId() {
		return openAcctBranchId;
	}
	public void setOpenAcctBranchId(String openAcctBranchId) {
		this.openAcctBranchId = openAcctBranchId;
	}
	public String getOpenAcctBranchName() {
		return openAcctBranchName;
	}
	public void setOpenAcctBranchName(String openAcctBranchName) {
		this.openAcctBranchName = openAcctBranchName;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public String getcInterbankId() {
		return cInterbankId;
	}
	public void setcInterbankId(String cInterbankId) {
		this.cInterbankId = cInterbankId;
	}
}
