package com.cardpay.pccredit.manager.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.ManagerAssessmentScoreFilter;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
import com.cardpay.pccredit.manager.web.ManagerAssessmentScoreForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 描述 ：客户经理评估Dao
 * @author 张石树
 *
 * 2014-11-13 下午2:37:17
 */
@Mapper
public interface ManagerAssessmentScoreDao {

	/**
	 * 分页查询信息
	 * @param filter
	 * @return
	 */
	List<ManagerAssessmentScoreForm> findManagerAssessmentScoreByFilter(ManagerAssessmentScoreFilter filter);

	/**
	 * 分页查询统计 
	 * @param filter
	 * @return
	 */
	int findManagerAssessmentScoreCountByFilter(ManagerAssessmentScoreFilter filter);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	ManagerAssessmentScoreForm findManagerAssessmentScoreById(@Param("id") String id);
	
	TyManagerAssessment findManagerAssessmentScoreByIdqz(@Param("id") String id);
	
	public int findJxCountByParam(@Param("year")String year,@Param("month")String month,@Param("managerId")String managerId);
	
	/**
	 * 分页查询信息
	 * @param filter
	 * @return
	 */
	List<TyManagerAssessment> findManagerAssessmentScoreByFilterqz(ManagerAssessmentScoreFilter filter);

	/**
	 * 分页查询统计 
	 * @param filter
	 * @return
	 */
	int findManagerAssessmentScoreCountByFilterqz(ManagerAssessmentScoreFilter filter);
	
}
