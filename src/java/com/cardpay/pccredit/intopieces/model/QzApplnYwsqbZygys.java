/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_ywsqb_zygys")
public class QzApplnYwsqbZygys extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String ywsqbId;
	private int lsh;
	private String name;
	private String rate;
	private String condition;
	
	public String getYwsqbId() {
		return ywsqbId;
	}
	public void setYwsqbId(String ywsqbId) {
		this.ywsqbId = ywsqbId;
	}
	public int getLsh() {
		return lsh;
	}
	public void setLsh(int lsh) {
		this.lsh = lsh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
