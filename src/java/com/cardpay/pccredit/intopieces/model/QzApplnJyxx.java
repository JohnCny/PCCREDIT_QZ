/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;


import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_jyxx")
public class QzApplnJyxx extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private String applicationId;
	
	private String bussName;
	private String bussScope;
	private String bussStartYear;
	private String bussAddrType;
	private String bussAddrTypeOther;
	private String bussAddr;
	private String bussPhone;
	private String bussEmployeeNum;
	private String bussType;
	private String bussTypeOther;
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
	public String getBussName() {
		return bussName;
	}
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
	public String getBussScope() {
		return bussScope;
	}
	public void setBussScope(String bussScope) {
		this.bussScope = bussScope;
	}
	public String getBussStartYear() {
		return bussStartYear;
	}
	public void setBussStartYear(String bussStartYear) {
		this.bussStartYear = bussStartYear;
	}
	public String getBussAddrType() {
		return bussAddrType;
	}
	public void setBussAddrType(String bussAddrType) {
		this.bussAddrType = bussAddrType;
	}
	public String getBussAddrTypeOther() {
		return bussAddrTypeOther;
	}
	public void setBussAddrTypeOther(String bussAddrTypeOther) {
		this.bussAddrTypeOther = bussAddrTypeOther;
	}
	public String getBussAddr() {
		return bussAddr;
	}
	public void setBussAddr(String bussAddr) {
		this.bussAddr = bussAddr;
	}
	public String getBussPhone() {
		return bussPhone;
	}
	public void setBussPhone(String bussPhone) {
		this.bussPhone = bussPhone;
	}
	public String getBussEmployeeNum() {
		return bussEmployeeNum;
	}
	public void setBussEmployeeNum(String bussEmployeeNum) {
		this.bussEmployeeNum = bussEmployeeNum;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getBussTypeOther() {
		return bussTypeOther;
	}
	public void setBussTypeOther(String bussTypeOther) {
		this.bussTypeOther = bussTypeOther;
	}

}
