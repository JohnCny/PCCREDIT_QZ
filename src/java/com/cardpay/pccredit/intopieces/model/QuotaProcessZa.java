package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;


/**    
* @Title: QuotaProcessZa.java  
* @Package com.cardpay.pccredit.intopieces.model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author tanwh    
* @date 2016年6月6日 下午1:43:40  
* @version V1.0    
*/
@ModelParam(table = "quota_process_za")
public class QuotaProcessZa extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String zaId;
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
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getZaId() {
		return zaId;
	}
	public void setZaId(String zaId) {
		this.zaId = zaId;
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
}
