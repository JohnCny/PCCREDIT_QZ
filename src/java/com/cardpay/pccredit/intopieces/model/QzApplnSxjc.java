/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * Description of XM_APPLN_SXJC
 * 
 * @author 贺珈
 * 
 * @created on Dec 29, 2013
 * 
 * @version $Id: XM_APPLN_SXJC.java 1650 2014-10-09 14:55:25Z 贺珈 $
 */
@ModelParam(table = "qz_appln_sxjc")
public class QzApplnSxjc extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String application_id;
	private String complete;
	private String reality;
	private String standard;
	public String getApplication_id() {
		return application_id;
	}
	public void setApplication_id(String application_id) {
		this.application_id = application_id;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public String getReality() {
		return reality;
	}
	public void setReality(String reality) {
		this.reality = reality;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}

	
}
