package com.cardpay.pccredit.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.report.model.HomeTips;
import com.cardpay.pccredit.report.model.NameValueRecord;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-12-18下午3:47:49
 */
@Mapper
public interface StatisticalCommonDao {

	/**
     * 统计当前进件状况
     * @return
     */
	public List<NameValueRecord> statisticalApplicationStatus();
	
	/**
     * 统计当前贷款状况
     * @return
     */
	public List<NameValueRecord> statisticalCreditStatus();
	
	/**
     * 统计当前卡片状况
     * @return
     */
	public List<NameValueRecord> statisticalCardStatus();

	public List<HomeTips> getUsedCreditByUserId(CustomerApplicationProcessFilter filter);

	public List<HomeTips> getExpiryClientNum(CustomerApplicationProcessFilter filter);
	public List<HomeTips> getAlreadyExpiryClientNum(CustomerApplicationProcessFilter filter);
}
