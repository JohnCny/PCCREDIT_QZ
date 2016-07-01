package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "quota_process_sx")
public class QuotaProcessSx extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String circleId;
	private String auditUser;
	private String serialNumber;
	private String auditOpinion;
	private String refusalReason;
	private String fallbackReason;
	private String applyReason;
	private String nextNodeId;
	private String delayAuditUser;
	private Date auditTime;
	private String processOpStatus;
	private String operateType;
	
	private String globalType;
	private String globalId;
	private String regPermResidence;
	private String regPermResidence1;
	private String regPermResidence2;
	private String regPermResidence3;
	private String address;
	private String mobile;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public String getRefusalReason() {
		return refusalReason;
	}
	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}
	public String getFallbackReason() {
		return fallbackReason;
	}
	public void setFallbackReason(String fallbackReason) {
		this.fallbackReason = fallbackReason;
	}
	public String getNextNodeId() {
		return nextNodeId;
	}
	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}
	public String getDelayAuditUser() {
		return delayAuditUser;
	}
	public void setDelayAuditUser(String delayAuditUser) {
		this.delayAuditUser = delayAuditUser;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getProcessOpStatus() {
		return processOpStatus;
	}
	public void setProcessOpStatus(String processOpStatus) {
		this.processOpStatus = processOpStatus;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
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
	public String getRegPermResidence() {
		return regPermResidence;
	}
	public void setRegPermResidence(String regPermResidence) {
		this.regPermResidence = regPermResidence;
	}
	public String getRegPermResidence1() {
		return regPermResidence1;
	}
	public void setRegPermResidence1(String regPermResidence1) {
		this.regPermResidence1 = regPermResidence1;
	}
	public String getRegPermResidence2() {
		return regPermResidence2;
	}
	public void setRegPermResidence2(String regPermResidence2) {
		this.regPermResidence2 = regPermResidence2;
	}
	public String getRegPermResidence3() {
		return regPermResidence3;
	}
	public void setRegPermResidence3(String regPermResidence3) {
		this.regPermResidence3 = regPermResidence3;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
