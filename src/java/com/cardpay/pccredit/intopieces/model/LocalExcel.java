package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "local_excel")
public class LocalExcel extends BusinessModel {
	
	private static final long serialVersionUID = -8470111754965975277L;
	
	private String customerId;
	private String productId;
	private String applicationId;
	private String attachment;
	private String uri;
	
	private String  sheetKhxx;      
	private String  sheet_zcfz;   
	private String  sheetSy;
	private String encodeType;
	private String decodeType;
	
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
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getSheetKhxx() {
		return sheetKhxx;
	}
	public void setSheetKhxx(String sheetKhxx) {
		this.sheetKhxx = sheetKhxx;
	}
	public String getSheet_zcfz() {
		return sheet_zcfz;
	}
	public void setSheet_zcfz(String sheet_zcfz) {
		this.sheet_zcfz = sheet_zcfz;
	}
	public String getSheetSy() {
		return sheetSy;
	}
	public void setSheetSy(String sheetSy) {
		this.sheetSy = sheetSy;
	}
	public String getEncodeType() {
		return encodeType;
	}
	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
	}
	public String getDecodeType() {
		return decodeType;
	}
	public void setDecodeType(String decodeType) {
		this.decodeType = decodeType;
	}
	
}