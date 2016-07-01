package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "LOCAL_EXCEL_COLLECT_FIELD")
public class LocalExcelCollectField extends BusinessModel {
	
	private static final long serialVersionUID = -8470111754965975277L;
	 
	 private String customerId;
	 private String productId;
	 private String applicationId;
	 private String localExcelId;
	 private Date createdTime;
	 private String jynx;           
	 private String zc;             
	 private String qy;           
	 private String zcfzl;          
	 private String yye;            
	 private String nkzpsl;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getLocalExcelId() {
		return localExcelId;
	}
	public void setLocalExcelId(String localExcelId) {
		this.localExcelId = localExcelId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getJynx() {
		return jynx;
	}
	public void setJynx(String jynx) {
		this.jynx = jynx;
	}
	public String getZc() {
		return zc;
	}
	public void setZc(String zc) {
		this.zc = zc;
	}
	public String getQy() {
		return qy;
	}
	public void setQy(String qy) {
		this.qy = qy;
	}
	public String getZcfzl() {
		return zcfzl;
	}
	public void setZcfzl(String zcfzl) {
		this.zcfzl = zcfzl;
	}
	public String getYye() {
		return yye;
	}
	public void setYye(String yye) {
		this.yye = yye;
	}
	public String getNkzpsl() {
		return nkzpsl;
	}
	public void setNkzpsl(String nkzpsl) {
		this.nkzpsl = nkzpsl;
	}
	 
	 
	 
	
	
	
	
}