/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_dbrxx_jdc")
public class QzApplnDbrxxJdc extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String dbrxxId;
	private int lsh;
	private String cardNo;
	private String price;
	private Date buyDate;
	private String haveCopy_1;
	private String haveCopy_2;
	public String getDbrxxId() {
		return dbrxxId;
	}
	public void setDbrxxId(String dbrxxId) {
		this.dbrxxId = dbrxxId;
	}
	public int getLsh() {
		return lsh;
	}
	public void setLsh(int lsh) {
		this.lsh = lsh;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public String getHaveCopy_1() {
		return haveCopy_1;
	}
	public void setHaveCopy_1(String haveCopy_1) {
		this.haveCopy_1 = haveCopy_1;
	}
	public String getHaveCopy_2() {
		return haveCopy_2;
	}
	public void setHaveCopy_2(String haveCopy_2) {
		this.haveCopy_2 = haveCopy_2;
	}

}
