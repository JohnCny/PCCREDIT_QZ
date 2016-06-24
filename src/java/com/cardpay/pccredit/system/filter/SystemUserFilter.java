package com.cardpay.pccredit.system.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class SystemUserFilter extends BaseQueryFilter{
	
	
	private String orgId;
	
	private String oname;
	
	private String displayName;
	private String selectvaltmp;//已选择过的

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSelectvaltmp() {
		return selectvaltmp;
	}

	public void setSelectvaltmp(String selectvaltmp) {
		this.selectvaltmp = selectvaltmp;
	}
}
