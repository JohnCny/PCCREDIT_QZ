/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_dbrxx_dkjl")
public class QzApplnDbrxxDkjl extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String dbrxxId;
	private int lsh;
	private String loanType;
	private String amount;
	private String deadline;
	private Date loanDate;
	private String purpose;
	private String remainAmount;
	private String guntForOther;
	private String guntAmount;
	private String guntDeadline;
	public String getDbrxxId() {
		return dbrxxId;
	}
	public void setDbrxxId(String dbrxxId) {
		this.dbrxxId = dbrxxId;
	}
	public int getLsh() {
		return lsh;
	}
	public void setLsh(int lsh) {
		this.lsh = lsh;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}
	public String getGuntForOther() {
		return guntForOther;
	}
	public void setGuntForOther(String guntForOther) {
		this.guntForOther = guntForOther;
	}
	public String getGuntAmount() {
		return guntAmount;
	}
	public void setGuntAmount(String guntAmount) {
		this.guntAmount = guntAmount;
	}
	public String getGuntDeadline() {
		return guntDeadline;
	}
	public void setGuntDeadline(String guntDeadline) {
		this.guntDeadline = guntDeadline;
	}
}
