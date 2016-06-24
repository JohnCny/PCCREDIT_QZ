/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_dbrxx")
public class QzApplnDbrxx extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String customerId;
	private String applicationId;
	private String name;
	private String sex;
	private Date birthday;
	private String yeah;
	private String globalType;
	private String globalId;
	private String haveGlobalIdCopy;
	private String phone_1;
	private String phone_2;
	private String phoneOther;
	private String workUnitName;
	private String workYear;
	private String workUnitAddr;
	private String workUnitPhone;
	private String yeahIncome;
	private String haveProveFile;
	private String fileName;
	private String marriage;
	private String homePhone;
	private String homeAddr;
	private String homeType;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getYeah() {
		return yeah;
	}
	public void setYeah(String yeah) {
		this.yeah = yeah;
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
	public String getHaveGlobalIdCopy() {
		return haveGlobalIdCopy;
	}
	public void setHaveGlobalIdCopy(String haveGlobalIdCopy) {
		this.haveGlobalIdCopy = haveGlobalIdCopy;
	}
	public String getPhone_1() {
		return phone_1;
	}
	public void setPhone_1(String phone_1) {
		this.phone_1 = phone_1;
	}
	public String getPhone_2() {
		return phone_2;
	}
	public void setPhone_2(String phone_2) {
		this.phone_2 = phone_2;
	}
	public String getPhoneOther() {
		return phoneOther;
	}
	public void setPhoneOther(String phoneOther) {
		this.phoneOther = phoneOther;
	}
	public String getWorkUnitName() {
		return workUnitName;
	}
	public void setWorkUnitName(String workUnitName) {
		this.workUnitName = workUnitName;
	}
	public String getWorkYear() {
		return workYear;
	}
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	public String getWorkUnitAddr() {
		return workUnitAddr;
	}
	public void setWorkUnitAddr(String workUnitAddr) {
		this.workUnitAddr = workUnitAddr;
	}
	public String getWorkUnitPhone() {
		return workUnitPhone;
	}
	public void setWorkUnitPhone(String workUnitPhone) {
		this.workUnitPhone = workUnitPhone;
	}
	public String getYeahIncome() {
		return yeahIncome;
	}
	public void setYeahIncome(String yeahIncome) {
		this.yeahIncome = yeahIncome;
	}
	public String getHaveProveFile() {
		return haveProveFile;
	}
	public void setHaveProveFile(String haveProveFile) {
		this.haveProveFile = haveProveFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getHomeType() {
		return homeType;
	}
	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}
}
