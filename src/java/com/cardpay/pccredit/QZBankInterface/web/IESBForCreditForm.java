package com.cardpay.pccredit.QZBankInterface.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年4月22日 下午1:45:33 
 * 程序的简单说明 
 */
public class IESBForCreditForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8727307345143713989L;
	private String clientNo;
	private String ccy;
	private String creditLimit;
	private String guaranteeMode;
	private String termType;
	private String term;
	private Date startDate;
	private Date endDate;
	private String aClientNo;
	private String clientName;
	private String sex;
	private String clientType;
	private String globalType;
	private String globalId;
	private String longGlobalType;
	private Date issDate;
	private Date globalEffDate;
	private String agriFlag;
	private String countryCode;
	private String regPermResidence;
	
	private String regPermResidence_1;
	private String regPermResidence_2;
	private String regPermResidence_3;
	
	private String address;
	private Date birthDate;
	private String education;
	private String degree;
	private Date signDate;
	private String holdCardMsg;
	private String passportFlag;
	private String creditLevel;
	private Date expiryDate;
	private String relClientFlag;
	private String ownBranchRelation;
	private String post;
	private String loanCardFlag;
	private String loanCardNo;
	private String mobile;
	private String higherOrgNo;
	private String acctExec;
	private Date openAcctDate;
	private String bussType;
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public String getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getGuaranteeMode() {
		return guaranteeMode;
	}
	public void setGuaranteeMode(String guaranteeMode) {
		this.guaranteeMode = guaranteeMode;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
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
	public String getaClientNo() {
		return aClientNo;
	}
	public void setaClientNo(String aClientNo) {
		this.aClientNo = aClientNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getGlobalType() {
		return globalType;
	}
	public void setGlobalType(String globalType) {
		this.globalType = globalType;
	}
	public String getGlobalId() {
		return globalId;
	}
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	public String getLongGlobalType() {
		return longGlobalType;
	}
	public void setLongGlobalType(String longGlobalType) {
		this.longGlobalType = longGlobalType;
	}
	public Date getIssDate() {
		return issDate;
	}
	public void setIssDate(Date issDate) {
		this.issDate = issDate;
	}
	public Date getGlobalEffDate() {
		return globalEffDate;
	}
	public void setGlobalEffDate(Date globalEffDate) {
		this.globalEffDate = globalEffDate;
	}
	public String getAgriFlag() {
		return agriFlag;
	}
	public void setAgriFlag(String agriFlag) {
		this.agriFlag = agriFlag;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getRegPermResidence() {
		return regPermResidence;
	}
	public void setRegPermResidence(String regPermResidence) {
		this.regPermResidence = regPermResidence;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getHoldCardMsg() {
		return holdCardMsg;
	}
	public void setHoldCardMsg(String holdCardMsg) {
		this.holdCardMsg = holdCardMsg;
	}
	public String getPassportFlag() {
		return passportFlag;
	}
	public void setPassportFlag(String passportFlag) {
		this.passportFlag = passportFlag;
	}
	public String getCreditLevel() {
		return creditLevel;
	}
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getRelClientFlag() {
		return relClientFlag;
	}
	public void setRelClientFlag(String relClientFlag) {
		this.relClientFlag = relClientFlag;
	}
	public String getOwnBranchRelation() {
		return ownBranchRelation;
	}
	public void setOwnBranchRelation(String ownBranchRelation) {
		this.ownBranchRelation = ownBranchRelation;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getLoanCardFlag() {
		return loanCardFlag;
	}
	public void setLoanCardFlag(String loanCardFlag) {
		this.loanCardFlag = loanCardFlag;
	}
	public String getLoanCardNo() {
		return loanCardNo;
	}
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHigherOrgNo() {
		return higherOrgNo;
	}
	public void setHigherOrgNo(String higherOrgNo) {
		this.higherOrgNo = higherOrgNo;
	}
	public String getAcctExec() {
		return acctExec;
	}
	public void setAcctExec(String acctExec) {
		this.acctExec = acctExec;
	}
	public Date getOpenAcctDate() {
		return openAcctDate;
	}
	public void setOpenAcctDate(Date openAcctDate) {
		this.openAcctDate = openAcctDate;
	}
	public String getRegPermResidence_1() {
		return regPermResidence_1;
	}
	public void setRegPermResidence_1(String regPermResidence_1) {
		this.regPermResidence_1 = regPermResidence_1;
	}
	public String getRegPermResidence_2() {
		return regPermResidence_2;
	}
	public void setRegPermResidence_2(String regPermResidence_2) {
		this.regPermResidence_2 = regPermResidence_2;
	}
	public String getRegPermResidence_3() {
		return regPermResidence_3;
	}
	public void setRegPermResidence_3(String regPermResidence_3) {
		this.regPermResidence_3 = regPermResidence_3;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
}
