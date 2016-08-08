/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_za")
public class QzApplnZa extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String originator;
	private String originatorManager;
	private String initiator;
	private Date initDate;
	private String name;
	private String address;
	private String code;
	private String sug;
	private String branchSug;
	
	private String serialnumber;//授信维护 流程序列号 
	private String processStatus;//授信维护 审批状态
	
	private String nodeName;
	private String quotaProcessId;
	
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
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getQuotaProcessId() {
		return quotaProcessId;
	}
	public void setQuotaProcessId(String quotaProcessId) {
		this.quotaProcessId = quotaProcessId;
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
