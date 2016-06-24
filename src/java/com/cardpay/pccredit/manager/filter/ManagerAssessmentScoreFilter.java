/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * 描述 ：客户经理评估Filter
 * @author 张石树
 *
 * 2014-11-13 下午2:34:51
 */
public class ManagerAssessmentScoreFilter extends BaseQueryFilter{
	
	private String assessedName;
	
	private Integer dataYear;
	
	private Integer dataMonth;
	
	private List<String> subManagerIds;
	private List<AccountManagerParameterForm> customerManagerIds;
	private String customerManagerId;
	

	public List<AccountManagerParameterForm> getCustomerManagerIds() {
		return customerManagerIds;
	}

	public void setCustomerManagerIds(
			List<AccountManagerParameterForm> customerManagerIds) {
		this.customerManagerIds = customerManagerIds;
	}

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getAssessedName() {
		return assessedName;
	}

	public void setAssessedName(String assessedName) {
		this.assessedName = assessedName;
	}

	public List<String> getSubManagerIds() {
		return subManagerIds;
	}

	public void setSubManagerIds(List<String> subManagerIds) {
		this.subManagerIds = subManagerIds;
	}

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}

	public Integer getDataMonth() {
		return dataMonth;
	}

	public void setDataMonth(Integer dataMonth) {
		this.dataMonth = dataMonth;
	}
	
}
