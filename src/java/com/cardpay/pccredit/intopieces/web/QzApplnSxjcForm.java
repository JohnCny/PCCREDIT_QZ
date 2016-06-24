package com.cardpay.pccredit.intopieces.web;

import com.wicresoft.jrad.base.database.model.BaseModel;

public class QzApplnSxjcForm extends BaseModel{
	
	/**
	 * 三性检测form
	 */
	private static final long serialVersionUID = 1L;

	private String applicationId;
	
	private String reality;
	
	private String complete;
	
	private String standard;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getReality() {
		return reality;
	}

	public void setReality(String reality) {
		this.reality = reality;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	

}
