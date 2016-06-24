/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_dbrxx_fc")
public class QzApplnDbrxxFc extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String dbrxxId;
	private int lsh;
	private String addr;
	private String price;
	private String usrSituation;
	private String haveCopy;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUsrSituation() {
		return usrSituation;
	}
	public void setUsrSituation(String usrSituation) {
		this.usrSituation = usrSituation;
	}
	public String getHaveCopy() {
		return haveCopy;
	}
	public void setHaveCopy(String haveCopy) {
		this.haveCopy = haveCopy;
	}

}
