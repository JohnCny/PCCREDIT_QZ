/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.manager.dao.ManagerAssessmentScoreDao;
import com.cardpay.pccredit.manager.dao.comdao.ManagerAssessmentScoreCommDao;
import com.cardpay.pccredit.manager.filter.ManagerAssessmentScoreFilter;
import com.cardpay.pccredit.manager.model.Khjltjzl;
import com.cardpay.pccredit.manager.model.ManagerAssessmentScore;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
import com.cardpay.pccredit.manager.web.ManagerAssessmentScoreForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * 描述 ：客户经理评估标准service
 * @author 张石树
 *
 * 2014-11-13 下午2:29:45
 */
@Service
public class ManagerAssessmentScoreService {
	
	private Logger logger = Logger.getLogger(ManagerAssessmentScoreService.class);

	@Autowired
	private ManagerAssessmentScoreDao managerAssessmentScoreDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired 
	private CustomerInforCommDao customerInforCommDao;
	
	@Autowired
	private ManagerAssessmentScoreCommDao managerAssessmentScoreCommDao;

	/**
	 * 分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerAssessmentScoreForm> findManagerAssessmentScoreByFilter(
			ManagerAssessmentScoreFilter filter) {
		List<ManagerAssessmentScoreForm> assessmentScoreForms = managerAssessmentScoreDao.findManagerAssessmentScoreByFilter(filter);
		int size = managerAssessmentScoreDao.findManagerAssessmentScoreCountByFilter(filter);
		QueryResult<ManagerAssessmentScoreForm> qs = new QueryResult<ManagerAssessmentScoreForm>(size, assessmentScoreForms);
		return qs;
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ManagerAssessmentScoreForm findManagerAssessmentScoreById(String id){
		return managerAssessmentScoreDao.findManagerAssessmentScoreById(id);
	}

	/**
	 * 更新评估信息
	 * @param managerAssessmentScore
	 */
	public void updateManagerAssessmentScore(
			ManagerAssessmentScore managerAssessmentScore) {
		commonDao.updateObject(managerAssessmentScore);
	}
	
	/**
	 * 统计客户经理已授信额度
	 * @param managerId
	 * @return
	 */
	public Double getManagerApplyQuota(String managerId){
		List<CustomerInfor> customerInfors = customerInforCommDao.findCustomerByManagerId(managerId);
		if(customerInfors != null && customerInfors.size() > 0){
			List<String> customerIds = new ArrayList<String>();
			for(CustomerInfor infor : customerInfors){
				customerIds.add(infor.getId());
			}
			return managerAssessmentScoreCommDao.sumAppApplyQuota(customerIds);
		} else {
			return 0.0;
		}
	}
	
	
	
	/*
	 * 根据年月获取评估结果
	 */
	public List<TyManagerAssessment> getAssessByMonth(int year,int month){
		String sql = "select * from ty_manager_assessment where year='"+year+"' and month='"+month+"'";
		return commonDao.queryBySql(TyManagerAssessment.class, sql, null);
	}
	
	public List<TyManagerAssessment> getAssessByMonth1(int year,int month){
		String sql = "select * from ty_manager_assessment where TOTAL_SCORE is  null and year='"+year+"' and month='"+month+"'";
		return commonDao.queryBySql(TyManagerAssessment.class, sql, null);
	}
	

	/**
	 * 每月初定时生成当月评估表
	 */
	public void addAssessment(){
	String sql ="insert into TY_MANAGER_ASSESSMENT										  "+
				"  (id,                                                                   "+
				"   year,                                                                 "+
				"   month,                                                                "+
				"   customer_id,                                                          "+
				"   score,                                                                "+
				"   total_score,                                                          "+
				"   create_time,                                                          "+
				"   create_user,                                                          "+
				"   customer_name,                                                        "+
				"   remark)                                                               "+
				"  (select sys_guid(),                                                    "+
				"          s.data_year,                                                   "+
				"          s.data_month,                                                  "+
				"          s.assessor,                                                    "+
				"          '',                                                            "+
				"          s.total_score,                                                 "+
				"          s.created_time,                                                "+
				"          s.created_time,                                                "+
				"          u.display_name,                                                "+
				"          OTHER_EVALUATION                                               "+
				"     from manager_assessment_score s,sys_user u where u.id =s.assessor)  ";
		commonDao.execute(sql);
	}
	
	/**
	 * 保存评估
	 * @param managerAssessmentScore
	 * @throws ParseException 
	 */
	public void saveAssessmentScore(HttpServletRequest request) throws ParseException {
		String assessId = request.getParameter("assessId");
		String remark = request.getParameter("remark");
		String totalScore = request.getParameter("totalScore");
		String score = request.getParameter("avgScore");
		String createTime = request.getParameter("createTime");
		String createUser = request.getParameter("createUser");
		TyManagerAssessment assessment = commonDao.findObjectById(TyManagerAssessment.class, assessId);
		assessment.setRemark(remark);
		assessment.setTotalScore(totalScore);
		assessment.setScore(score);
		assessment.setCreateUser(createUser);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟 
		if(createTime !=null &&createTime!=""){
			java.util.Date date=sdf.parse(createTime); 
			assessment.setCreateTime(date);
		}
		commonDao.updateObject(assessment);
	}
	
	
	public void insertKhjxbczd(Khjltjzl zx){
		commonDao.insertObject(zx);
	}
	
	public int findJxCountByParam(String year,String month,String managerId){
		return managerAssessmentScoreDao.findJxCountByParam(year,month,managerId);
	} 
	
	public Khjltjzl findKhjljxById(String productId) {
		return commonDao.findObjectById(Khjltjzl.class, productId);

	}
	
	public void updateJxbc(Khjltjzl jx){
		commonDao.updateObject(jx);
	}
	
	public QueryResult<TyManagerAssessment> findManagerAssessmentScoreByFilterqz(
			ManagerAssessmentScoreFilter filter) {
		
		List<TyManagerAssessment> assessmentScoreForms = managerAssessmentScoreDao.findManagerAssessmentScoreByFilterqz(filter);
		int size = managerAssessmentScoreDao.findManagerAssessmentScoreCountByFilterqz(filter);
		QueryResult<TyManagerAssessment> qs = new QueryResult<TyManagerAssessment>(size, assessmentScoreForms);
		return qs;
	}
	
	public TyManagerAssessment findManagerAssessmentScoreById1(String id){
		return managerAssessmentScoreDao.findManagerAssessmentScoreByIdqz(id);
	}
}
