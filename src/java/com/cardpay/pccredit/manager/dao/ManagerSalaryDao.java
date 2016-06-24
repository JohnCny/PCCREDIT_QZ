package com.cardpay.pccredit.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.model.ManagerSalaryForm;
import com.cardpay.pccredit.manager.model.SalaryParameter;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:55:52
 */
@Mapper
public interface ManagerSalaryDao {
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<ManagerSalaryForm> findManagerSalarysByFilter(ManagerSalaryFilter filter);
	
	/**
	 * 统计记录数
	 * @param filter
	 * @return
	 */
	public int findManagerSalarysCountByFilter(ManagerSalaryFilter filter);
	
	/**
	 * 获取客户经理最大层数
	 * @return
	 */
	public int getMaxManagerLevel();
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<SalaryParameter> findSalaryParametersByFilter(@Param("level")int level,@Param("year")int year, @Param("month")int month);
	
	/**
	 * 获取客户经理管理绩效
	 * @param managerId
	 * @param year
	 * @param month
	 * @return
	 */
	public String getManagePerformance(@Param("managerId")String managerId, @Param("year")int year, @Param("month")int month);
	
	/**
	 * 通过年份和月份删除客户经理工资信息
	 * @param year
	 * @param month
	 * @return
	 */
	public int deleteManagerSalaryByYearAndMonth(@Param("year")int year, @Param("month")int month);
	/**
	 * 批量插入客户经理薪资
	 * @param list
	 * @return
	 */
	public int batchInsertManagerSalary(List<ManagerSalary> list);
	/**
	 * 得到风险保证余额
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getReturnPrepareAmountById(@Param("year") int year,@Param("month") int month,@Param("id") String id);
	/**
	 * 得到客户经理绩效工资
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getRewardIncentiveInformation(@Param("year") int year,@Param("month") int month,@Param("id") String id);
	public int findManagerSalaryCount(@Param("year")int year, @Param("month")int month);
	
}
