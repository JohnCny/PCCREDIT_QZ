/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_jyd_dydb")
public class QzApplnJydDydb extends BusinessModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String jydId;
	private String userName;
	private String goodsName;
	private String goodsCount;
	private String card;
	private String goodsValues;
	
	public String getJydId() {
		return jydId;
	}
	public void setJydId(String jydId) {
		this.jydId = jydId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getGoodsValues() {
		return goodsValues;
	}
	public void setGoodsValues(String goodsValues) {
		this.goodsValues = goodsValues;
	}

}
