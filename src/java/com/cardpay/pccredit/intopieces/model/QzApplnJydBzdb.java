/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_jyd_bzdb")
public class QzApplnJydBzdb extends BusinessModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String jydId;
	private String name;
	private String cardId;
	public String getJydId() {
		return jydId;
	}
	public void setJydId(String jydId) {
		this.jydId = jydId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
}
