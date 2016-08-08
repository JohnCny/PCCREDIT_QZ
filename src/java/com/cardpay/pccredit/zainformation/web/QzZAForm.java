package com.cardpay.pccredit.zainformation.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年5月7日 上午10:41:20 
 * 台帐
 */
public class QzZAForm extends BaseForm{
	
	private static final long serialVersionUID = 5996843132228198449L;
	
	private String originator;
	private String originatorManager;
	private String initiator;
	private Date initDate;
	private String name;
	private String address;
	private String code;
	private String sug;
	private String branchSug;
	
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public Date getInitDate() {
		return initDate;
	}
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSug() {
		return sug;
	}
	public void setSug(String sug) {
		this.sug = sug;
	}
	public String getOriginatorManager() {
		return originatorManager;
	}
	public void setOriginatorManager(String originatorManager) {
		this.originatorManager = originatorManager;
	}
	public String getBranchSug() {
		return branchSug;
	}
	public void setBranchSug(String branchSug) {
		this.branchSug = branchSug;
	}
}
