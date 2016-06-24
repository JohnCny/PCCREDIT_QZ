/**
 * 
 */
package com.cardpay.pccredit.customer.filter;

import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:03:58
 */
public class MaintenanceFilter extends BaseQueryFilter{
	private String id;
	private String customerId;
	private String customerManagerId;
	private List<AccountManagerParameterForm> customerManagerIds;
	private String maintenanceGoal;
	private String maintenanceWay;
	private String maintenanceDay;
	private String createWay;
	private String endResult;
	private String remarksCreateReason;
	
	private Integer dataYear;
	private Integer dataMonth;
	
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getMaintenanceGoal() {
		return maintenanceGoal;
	}
	public void setMaintenanceGoal(String maintenanceGoal) {
		this.maintenanceGoal = maintenanceGoal;
	}
	public String getMaintenanceWay() {
		return maintenanceWay;
	}
	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}
	public String getMaintenanceDay() {
		return maintenanceDay;
	}
	public void setMaintenanceDay(String maintenanceDay) {
		this.maintenanceDay = maintenanceDay;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getRemarksCreateReason() {
		return remarksCreateReason;
	}
	public void setRemarksCreateReason(String remarksCreateReason) {
		this.remarksCreateReason = remarksCreateReason;
	}
	public List<AccountManagerParameterForm> getCustomerManagerIds() {
		return customerManagerIds;
	}
	public void setCustomerManagerIds(
			List<AccountManagerParameterForm> customerManagerIds) {
		this.customerManagerIds = customerManagerIds;
	}
	
}
