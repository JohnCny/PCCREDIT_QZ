package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**    
* @Title: ProductStuf.java  
* @Package com.cardpay.pccredit.product.model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author tanwh    
* @date 2016年4月29日 下午2:54:06  
* @version V1.0    
*/
@ModelParam(table = "product_stuf")
public class ProductStuf extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String productId;
	private String productType;
	private int stufIndex;
	private String stufCode;
	private String stufName;
	private String isUseable;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getStufIndex() {
		return stufIndex;
	}
	public void setStufIndex(int stufIndex) {
		this.stufIndex = stufIndex;
	}
	public String getStufCode() {
		return stufCode;
	}
	public void setStufCode(String stufCode) {
		this.stufCode = stufCode;
	}
	public String getStufName() {
		return stufName;
	}
	public void setStufName(String stufName) {
		this.stufName = stufName;
	}
	public String getIsUseable() {
		return isUseable;
	}
	public void setIsUseable(String isUseable) {
		this.isUseable = isUseable;
	}
}
