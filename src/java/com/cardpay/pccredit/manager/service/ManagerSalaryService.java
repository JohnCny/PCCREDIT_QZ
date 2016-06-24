package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.common.Arith;
import com.cardpay.pccredit.manager.dao.ManagerSalaryDao;
import com.cardpay.pccredit.manager.filter.ManagerCashConfigurationFilter;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.Khjltjzl;
import com.cardpay.pccredit.manager.model.ManagerCashConfiguration;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.model.ManagerSalaryForm;
import com.cardpay.pccredit.manager.model.SalaryParameter;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.cardpay.pccredit.manager.model.TyRiskMargin;
import com.cardpay.pccredit.manager.model.TyRiskMarginSpecific;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:56:18
 */
@Service
public class ManagerSalaryService {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerSalaryDao managerSalaryDao;
	
	@Autowired
	private ManagerCashConfigurationService managerCashConfigurationService;
	
	@Autowired
	private ManagerAssessmentScoreService managerAssessmentScoreService;
	
	@Autowired
	private ManagerPerformanceParametersService managerPerformanceParametersService;
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerSalaryForm> findManagerSalaryByFilter(ManagerSalaryFilter filter) {
		List<ManagerSalaryForm> list = managerSalaryDao.findManagerSalarysByFilter(filter);
		int size = managerSalaryDao.findManagerSalarysCountByFilter(filter);
		QueryResult<ManagerSalaryForm> qs = new QueryResult<ManagerSalaryForm>(size, list);
		return qs;
	}
	
	/**
	 * 插入客户经理薪资
	 * @param riskCustomer
	 * @return
	 */
	public String insertManagerSalary(ManagerSalary managerSalary) {
		if(managerSalary.getCreatedTime() == null){
			managerSalary.setCreatedTime(Calendar.getInstance().getTime());
		}
		if(managerSalary.getModifiedTime() == null){
			managerSalary.setModifiedTime(Calendar.getInstance().getTime());
		}
		commonDao.insertObject(managerSalary);
		return managerSalary.getId();
	}
	/**
	 * 计算客户经理月度薪资
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean calculateMonthlySalary(int year, int month){
		boolean flag = true;
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, 1);
			
			calendar.add(Calendar.MONTH, -1);
			
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			managerSalaryDao.deleteManagerSalaryByYearAndMonth(year, month);
			// 获取客户经理最大层级
			int maxLevel = managerSalaryDao.getMaxManagerLevel();
			for(int i = maxLevel; i > 0; i--){
				calculateLevelSalary(year, month, i);
			}
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 计算某个层级的客户经理的薪资
	 * @param year
	 * @param month
	 * @param level
	 * @return
	 */
	public void calculateLevelSalary(int year, int month, int level){
		// 
		List<SalaryParameter> list = managerSalaryDao.findSalaryParametersByFilter(level, year, month);
		Map<String, ManagerSalary> salaryMap = getManagerSalary(list, year, month);
		
		ManagerSalary managerSalary = null;
		SalaryParameter salaryParameter = null;
		for(int i = 0; i < list.size(); i++){
			salaryParameter = list.get(i);
			/*if("0000000049ea417e0149f05573f81d2e".equals(salaryParameter.getManagerId())){
				int a = 0;
			}*/
			managerSalary = salaryMap.get(salaryParameter.getManagerId());
			// 责任工资
			managerSalary.setDutySalary(salaryParameter.calculateDutySalary());
			// 津贴
			managerSalary.setAllowance(salaryParameter.getAllowance());
			// 底薪
			managerSalary.setBasePay(salaryParameter.getBasePay());
			// 返还金额(等于三年前本月存入)
			managerSalary.setReturnPrepareAmount(salaryParameter.getInsertPrepareAmount());
			
			// 管理绩效
			String managePerformance = "0";
			// 判断是否为叶子节点
			if(!salaryParameter.isLeaf()){
				// 获取客户经理管理绩效利息
				managePerformance = managerSalaryDao.getManagePerformance(managerSalary.getCustomerId(), year, month);
			}
			// 计算并设置绩效工资
			managerSalary.setRewardIncentiveInformation(salaryParameter.calculatePerformanceSalary(managePerformance));
			
			String reward = managerSalary.getRewardIncentiveInformation();
			// 计算并设置存入的风险准备金(乘以风险准备金权数)
			String insertPrepareAmount = Arith.mulReturnStr(getExtractRate(reward),reward);
			insertPrepareAmount = Arith.mulReturnStr(insertPrepareAmount,salaryParameter.getMarginExtractInfo());
			managerSalary.setInsertPrepareAmount(insertPrepareAmount);
			
			// 本月风险准备金余额  = 上月风险准备金余额  + 本月存入的风险准备金 - 返还金额(等于三年前本月存入)
			String reserveBalances = Arith.subReturnStr(Arith.addReturnStr(salaryParameter.getRiskReserveBalances(), managerSalary.getInsertPrepareAmount()), managerSalary.getReturnPrepareAmount());
			managerSalary.setRiskReserveBalances(reserveBalances);
			// 保存客户经理薪资
			insertManagerSalary(managerSalary);
		}
	}
	
	public Map<String, ManagerSalary> getManagerSalary(List<SalaryParameter> list, int year, int month){
		Map<String, ManagerSalary> hm = new HashMap<String, ManagerSalary>();
		ManagerSalary managerSalary = null;
		for(SalaryParameter salaryParameter : list){
			managerSalary = new ManagerSalary();
			managerSalary.setCustomerId(salaryParameter.getManagerId());
			managerSalary.setYear(year+"");
			managerSalary.setMonth(month+"");
			hm.put(managerSalary.getCustomerId(), managerSalary);
		}
		return hm;
	}
	
	/*
	 * 获取提取比率
	 */
	public String getExtractRate(String amount){
		ManagerCashConfigurationFilter filter = new ManagerCashConfigurationFilter();
		QueryResult<ManagerCashConfiguration> qs = managerCashConfigurationService.findManagerCashConfigurationByFilter(filter);
		for(ManagerCashConfiguration cashConfiguration : qs.getItems()){
			if(Arith.compare(amount, cashConfiguration.getStartAmount()) >= 0 
					&& Arith.compare(cashConfiguration.getEndAmount(), amount) >= 0){
				return Arith.divReturnStr(cashConfiguration.getMarginExtractInfo(), "100");
			}
		}
		return "0";
	}
	/**
	 * 得到风险保证余额
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getReturnPrepareAmountById(int year,int month,String id){
		if(StringUtils.isNotEmpty(id)){
			return managerSalaryDao.getReturnPrepareAmountById(year, month, id);
		}else{
			return "-1";
		}
	}
	/**
	 * 得到客户经理绩效工资
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getRewardIncentiveInformation(int year,int month,String id){
		if(StringUtils.isNotEmpty(id)){
			return managerSalaryDao.getRewardIncentiveInformation(year, month, id);
		}else{
			return "-1";
		}
	}
	
	

	/**
	 * 查询客户经理的KPI得分
	 */
	public TyManagerAssessment findKPI(int year,int month,String customerId){
		String sql ="select * from TY_MANAGER_ASSESSMENT where customer_id = '"+customerId +"'and year='"+year+"' and month='"+month+"'";
		List<TyManagerAssessment> tyManagerAssessment = commonDao.queryBySql(TyManagerAssessment.class, sql, null);
		if(tyManagerAssessment.size()>0){
			return tyManagerAssessment.get(0);
		}else{
			return null;
		}
	}
	
	public Khjltjzl findKhjltjzl(int year,int month,String customerManagerId){
		String sql ="select * from qz_khjl_tjzl where CUSTMANAGERID = '"+customerManagerId +"'and year='"+year+"' and month='"+month+"'";
		List<Khjltjzl> list = commonDao.queryBySql(Khjltjzl.class, sql, null);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	
	
	
	
	/**
	 * 计算客户经理月度薪资
	 * @param year
	 * @param month
	 * @return
	 * @throws Exception 
	 */
	public void docalculateMonthlySalaryTy(int year, int month) {
		//managerSalaryDao.deleteManagerSalaryByYearAndMonth(year, month);//风险那块有叠加问题 影响返回
		int count = managerSalaryDao.findManagerSalaryCount(year,month);
		if(count>0){throw new  RuntimeException("该月客户经理月度薪资已存在！");}
		
		//获取当月评估结果
		List<TyManagerAssessment> assessmentsList = managerAssessmentScoreService.getAssessByMonth(year, month);
		if(assessmentsList.size() == 0){throw new  RuntimeException("该月客户经理评估信息未生成！");}
		
		List<TyManagerAssessment> list = managerAssessmentScoreService.getAssessByMonth1(year, month);
		if(list.size() > 0 && list.size()>1){throw new RuntimeException("该月多个客户经理未KPI打分！");}
		
		//循环计算客户经理当月工资
		for(int i=0;i<assessmentsList.size();i++){
			TyManagerAssessment assessment = assessmentsList.get(i);
			
			//是否已经kPI打分
			if(assessment.getTotalScore() == null){throw new RuntimeException("该月客户经理"+assessment.getCustomerName()+"未KPI打分！");}
			
			//是否已经入录绩效补充参数
			Khjltjzl jx = this.findKhjltjzl(year,month,assessment.getCustomerId());
			if(jx == null){throw new RuntimeException("该月客户经理"+assessment.getCustomerName()+"绩效工资参数未入录！");}
			
			//获取此客户经理的绩效参数
			TyPerformanceParameters parameter = managerPerformanceParametersService.getParameterByLevel(assessment.getCustomerId());
			if(parameter!=null){
				calculateSalaryExactly(year, month, assessment.getTotalScore(),parameter,assessment.getCustomerId());
			}
		}
}
	
	
	/**
	 *具体每月工资计算 
	 */
	private void calculateSalaryExactly(int year,int month,String totalScore,TyPerformanceParameters parameter,String customerId){
		//工资 = 保障性工资+绩效工资+福
		Khjltjzl jx = this.findKhjltjzl(year,month,customerId);
		//1.当月新增活跃客户数（未确认）
		double monthAddCustomer = jx.getActiveCustomerNum();
		
		//2.当月实际活跃客户数（未确认）
		double monthRealityCustomer = jx.getActualActiveCustomerNum();
		
		//3.当月所管理的客户用信实收利息收入（未确认）
		double monthSumInterest = Double.parseDouble(jx.getMonthActualReceiveIncome());
		
		//4.根据逾期贷款率转化逾期扣款比例
		double overdueLoan = Double.parseDouble(jx.getOverdueLoan());
		double overdueLoanM = getOverLoanPer(overdueLoan);
		
		//5. KPI指数
		double kpi = 0;
		if(totalScore==null||totalScore.equals("")){
			kpi =0;
		}else{
			kpi = Float.parseFloat(totalScore)/100;
		}
		//6. 行内其他零售产品奖励（未确认）
		double other = Double.parseDouble(jx.getOtherRetailProductAwards());
		
		//新增客户奖+管户奖+贷款利息分润
		double str1 = monthAddCustomer * Double.parseDouble(parameter.getA())+
				      monthRealityCustomer * Double.parseDouble(parameter.getB())+
				      monthSumInterest * Double.parseDouble(parameter.getC());
		
		//绩效（新增客户奖+管户奖+贷款利息分润）×（1-逾期扣款率）×KPI指数+行内其他零售产品奖励
		double monthPerform = str1 * (1-overdueLoanM) * kpi + other;
		
		
		//计算风险保证金
			//本月新增风险保证金
		double addRisk = getAddRisk(monthPerform);
			//本月问责风险保证金（未确认）
		double deduceRisk = Double.parseDouble(jx.getAccountabilityRiskMargin());
			//本月返还风险保证金
		double outRisk = outRiskMargin(year,month,customerId);
		
		//计算得当月风险保证金
		double monthRisk = addRisk-deduceRisk-outRisk;
		
		//剩余风险保证金
		double lastRisk = 0;
		TyRiskMarginSpecific specific = new TyRiskMarginSpecific();
		
		//获取总风险保证金
		TyRiskMargin tyRiskMargin = getRiskMarginByCustomerId(customerId);
		//保存风险保证金总表
		if(tyRiskMargin!=null){
			double totalRisk = monthRisk + Double.parseDouble(tyRiskMargin.getTotalRiskMargin());
			tyRiskMargin.setTotalRiskMargin(String.valueOf(totalRisk));
			tyRiskMargin.setUpdateTime(new Date());
			commonDao.updateObject(tyRiskMargin);
			specific.setRiskId(tyRiskMargin.getId());
			lastRisk = totalRisk;
		}else{
			TyRiskMargin riskMargin = new TyRiskMargin();
			riskMargin.setId(IDGenerator.generateID());
			riskMargin.setCustomerId(customerId);
			riskMargin.setTotalRiskMargin(String.valueOf(monthRisk));
			riskMargin.setUpdateTime(new Date());
			commonDao.insertObject(riskMargin);
			specific.setRiskId(riskMargin.getId());
			lastRisk = monthRisk;
		}
		//保存log表
		specific.setInRiskMargin(String.valueOf(addRisk));
		specific.setOutRiskMargin(String.valueOf(outRisk));
		specific.setDeductRiskMargin(String.valueOf(deduceRisk));
		specific.setYear(year+"");
		specific.setMonth(month+"");
		commonDao.insertObject(specific);
		
		//保存当月工资单
		ManagerSalary salary = new ManagerSalary();
		salary.setYear(year+"");
		salary.setMonth(month+"");
		salary.setCustomerId(customerId);
		salary.setBasePay(parameter.getBasicPerformance());//固定工资
		salary.setRewardIncentiveInformation(String.valueOf(monthPerform));//绩效工资
		salary.setReturnPrepareAmount(String.valueOf(outRisk));//返还金额
		salary.setInsertPrepareAmount(String.valueOf(addRisk));//存入的风险准备金
		salary.setRiskReserveBalances(String.valueOf(lastRisk));//风险准备金余额
		salary.setDeductAmount(String.valueOf(deduceRisk));//扣除金额
		commonDao.insertObject(salary);
	}
	
	/*
	 * 逾期扣款比例
	 */
	private double getOverLoanPer(double overdueLoan){
		if(overdueLoan==0){
			return 0;
		}else if(overdueLoan<=0.005){
			return 0.05;
		}else if(overdueLoan>0.005&&overdueLoan<=0.01){
			return 0.1;
		}else if(overdueLoan>0.01&&overdueLoan<=0.015){
			return  0.2;
		}else{
			return 0.3;
		}
	}
	/*
	 * 新增风险保证金计算
	 */
	private double getAddRisk(double monthPerform){
		double addRisk = 0;
		if(monthPerform<=2000){
			return addRisk;
		}else if(monthPerform>2000&&monthPerform<=5000){
			addRisk = (monthPerform-2000)*0.1;
		}else{
			addRisk=3000*0.1+(monthPerform-5000)*0.2;
		}
		return addRisk;
	}
	
	/*
	 * 返还风险保证金
	 */
	private double outRiskMargin(int year,int month,String customerId){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		
		calendar.add(Calendar.MONTH, -18);
		//获取18个月前的时间
		int  year_18 = calendar.get(Calendar.YEAR);
		int month_18 = calendar.get(Calendar.MONTH);
		//查询18月前风险保证金
		TyRiskMarginSpecific specific = getRiskMarginSpecific(year_18,month_18,customerId);
		if(specific==null){
			return 0;
		}else{
			return (Double.parseDouble(specific.getInRiskMargin())-Double.parseDouble(specific.getDeductRiskMargin()))>0
					?Double.parseDouble(specific.getInRiskMargin())-Double.parseDouble(specific.getDeductRiskMargin()):0;
		}
	}
	
	/*
	 * 根据customerId获取总风险保证金
	 */
	public TyRiskMargin getRiskMarginByCustomerId(String customerId){
		String sql = "select * from ty_risk_margin where customer_id='"+customerId+"'";
		List<TyRiskMargin> tyRiskMarginsList = commonDao.queryBySql(TyRiskMargin.class, sql, null);
		if(tyRiskMarginsList.size()>0){
			return tyRiskMarginsList.get(0);
		}else{
			return null;
		}
	}
	
	
	/*
	 * 根据年月查询风险保证金log
	 */
	public TyRiskMarginSpecific getRiskMarginSpecific(int year,int month,String customerId){
		String sql ="select * from ty_risk_margin_specific where risk_id in (select id from ty_risk_margin where customer_id='"+customerId+"') and year='"+year+"' and month='"+month+"'";
		List<TyRiskMarginSpecific> tyRiskMarginSpecifics = commonDao.queryBySql(TyRiskMarginSpecific.class, sql, null);
		if(tyRiskMarginSpecifics.size()>0){
			return tyRiskMarginSpecifics.get(0);
		}else{
			return null;
		}
	}
}
	
	

	
