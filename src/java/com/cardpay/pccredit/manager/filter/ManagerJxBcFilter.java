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
public class ManagerJxBcFilter extends BaseQueryFilter{
	
	private String customerManagerId;
	private List<AccountManagerParameterForm> customerManagerIds;
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public List<AccountManagerParameterForm> getCustomerManagerIds() {
		return customerManagerIds;
	}
	public void setCustomerManagerIds(
			List<AccountManagerParameterForm> customerManagerIds) {
		this.customerManagerIds = customerManagerIds;
	}
	
	
	
}
