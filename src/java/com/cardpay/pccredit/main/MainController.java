/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerMarketingService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.afterloan.service.AfterloanCheckService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.dao.IntoPiecesDao;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.QzApplnApprovalMeeting;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.cardpay.pccredit.manager.dao.StatisticsManagerDao;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.manager.service.InformationMaintenanceService;
import com.cardpay.pccredit.manager.service.ManagerAssessmentScoreService;
import com.cardpay.pccredit.manager.service.ManagerSalaryService;
import com.cardpay.pccredit.manager.service.StatisticsScheduleService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.report.model.HomeTips;
import com.cardpay.pccredit.report.model.NameValueRecord;
import com.cardpay.pccredit.report.service.StatisticalCommonService;
import com.cardpay.pccredit.riskControl.dao.NplsInfomationDao;
import com.cardpay.pccredit.riskControl.service.CustomerOverdueService;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.service.SystemUserService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.enviroment.GlobalSetting;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.menu.UIMenuMgr;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 系统首页
 * 
 * @author Vincent
 * @created on Dec 27, 2013
 * 
 * @version $Id: MainController.java 1639 2014-10-09 05:15:35Z vincent $
 */
@Controller
public class MainController {
	@Autowired
	private IntoPiecesDao intoPiecesDao;
	@Autowired
	private GlobalSetting globalSetting;

	@Autowired
	private UIMenuMgr menuMgr;

	@Autowired
	private LoginManager loginManager;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	private ManagerAssessmentScoreService managerAssessmentScoreService;
	
	@Autowired
	private CustomerMarketingService customerMarketingService;
	
	@Autowired
	private DivisionalService divisionalService;
	
	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private ManagerSalaryService managerSalaryService;
	
	@Autowired
	private StatisticsScheduleService statisticsScheduleService;
	
	@Autowired
	private StatisticsManagerDao statisticsManagerDao;
	
	@Autowired
	private CustomerOverdueService customerOverdueService;
	
	@Autowired
	private NplsInfomationDao nplsInfomationDao;
	
	@Autowired
	private InformationMaintenanceService informationMaintenanceService;
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	private AfterloanCheckService afterLoanCheckService;
	
	@Autowired
	private StatisticalCommonService statisticalCommonService;
	
	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;
	
	@ResponseBody
	@RequestMapping(value = "/main.page", method = { RequestMethod.GET })
	public AbstractModelAndView mainPage(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/main", request);

		if (globalSetting.isSuperAdminMode(request)) {
			mv.addObject("menuList", menuMgr.getAllUiMenus());
		}
		else {
			// 存储菜单
			IUser user = loginManager.getLoggedInUser(request);
			request.getSession().setAttribute("menuList", menuMgr.getUiMenusByUser(user.getId()));
		}

		return mv;
	}

	@RequestMapping(value = "/home.page", method = { RequestMethod.GET })
	public AbstractModelAndView indexPage(HttpServletRequest request) {

		//JRadModelAndView mv = new JRadModelAndView("home/home", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String rolename = user.getRoles().get(0).getName();
		
		//calendar.add(Calendar.DATE, 3); 
		//int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		//calendar.add(Calendar.DATE, 1); 
		//int day2 = calendar.get(Calendar.DAY_OF_MONTH);
		//Date date = calendar.getTime();
		//Date day1 = DateHelper.normalizeDate(DateHelper.shiftDay(date, 3), "yyyy-MM-dd");
		//Date day2 = DateHelper.normalizeDate(DateHelper.shiftDay(date, 4), "yyyy-MM-dd");
		Organization organization = organizationService.findOrgByUserId(userId);
		AccountManagerParameterForm accountManagerParameter = accountManagerParameterService.findAccountManagerParameterByUserId(userId);
		//查询贷后检查任务数
		int loanCount = afterLoanCheckService.findAferLoanCheckCountByUserId(userId);
		//查询所检查提醒任务
		//获取贷后点击通知时限和截止时限
		List<Dict> dict = customerInforService.findDict("afterloan");
		String remindTime="";
		String endTime="";
		for(int i=0;i<dict.size();i++){
			Dict dictd = dict.get(i);
			if("reminddate".equals(dictd.getTypeCode())){
				remindTime=dictd.getTypeName();
			}else if("enddate".equals(dictd.getTypeCode())){
				endTime=dictd.getTypeName();
			}
		}
		//int remindCount = afterLoanCheckService.findAferLoanCheckRemindCount(enddate,userId);
		//客户经理层级
		String level = "";
		if(accountManagerParameter != null ){
		 level = accountManagerParameter.getLevelInformation();
		}
		/*String pageurl ="";
		if(level.equals("MANA005") || level.equals("MANA003") ){
			
			//pageurl = "home/managerhome";
			pageurl = "home/home";	
		}
		else{
			
			pageurl = "home/home";	
		
		}*/
		String pageurl = "home/home";
		JRadModelAndView mv = new JRadModelAndView(pageurl, request);
		/*统计客户已授信额度
		Double doubleApply=managerAssessmentScoreService.getManagerApplyQuota(userId);

		HashMap<String,Integer> homeData = mainService.getHomeData(userId,0);
		HashMap<String,Object> rightHomeData = getRightHomeData(userId);*/
		mv.addObject("accountManagerParameter",accountManagerParameter);
		mv.addObject("rolename",rolename);
		String organizationname = organization.getName();
		mv.addObject("organizationname",organizationname);
		/*mv.addObject("doubleApply",doubleApply);
		mv.addObject("day1",day1);
		mv.addObject("day2",day2);
		mv.addObject("loanCount",loanCount);
		mv.addObject("remindCount",remindCount);
		mv.addObject("remindCountTeam",remindCountTeam);
		mv.addObject("roleName",roleName);*/
		
		/*center
		mv.addObject("marketing",homeData.get("marketing"));
		mv.addObject("divisional",homeData.get("divisional"));
		mv.addObject("applicationReject",homeData.get("applicationReject"));
		mv.addObject("applicationInfo",homeData.get("applicationInfo"));
		mv.addObject("maintenance",homeData.get("maintenance"));
		mv.addObject("officerChannels",homeData.get("officerChannels"));
		mv.addObject("collection",homeData.get("collection"));
		mv.addObject("riskNumber",homeData.get("riskNumber"));
		mv.addObject("abilityNumber",homeData.get("abilityNumber"));
		mv.addObject("productNumber",homeData.get("productNumber"));*/
		
		/*right
		mv.addObject("not",rightHomeData.get("not"));
		mv.addObject("already",rightHomeData.get("already"));
		mv.addObject("wait",rightHomeData.get("wait"));*/
		
		/*进件状况
		mv.addObject("UserApplicationInfo",rightHomeData.get("UserApplicationInfo"));
		mv.addObject("UserApplicationSuccess",rightHomeData.get("UserApplicationSuccess"));
		mv.addObject("UserApplicationNopass",rightHomeData.get("UserApplicationNopass"));
		mv.addObject("UserApplicationRefuse",rightHomeData.get("UserApplicationRefuse"));*/
		
		/*奖励激励状况
		mv.addObject("reward",rightHomeData.get("reward"));
		mv.addObject("riskGuarantee",rightHomeData.get("riskGuarantee"));*/
		
		/*客户经营状况（余额）
		mv.addObject("credit",rightHomeData.get("credit"));*/
		
		/*客户经营状况（数量
		mv.addObject("customer",rightHomeData.get("customer"));
		mv.addObject("riskCustomer",rightHomeData.get("riskCustomer"));
		mv.addObject("verificationCustomer",rightHomeData.get("verificationCustomer"));*/
		
		/*
		if(level.equals("MANA005") || level.equals("MANA003") ){
			// 当前进件状况
			mv.addObject("applicationStatusJson",statisticalCommonService.getApplicationStatusJson());
			// 当前贷款状况
			mv.addObject("creditStatusJson",statisticalCommonService.getCreditStatusJson());
			
			// 当前卡片状况
			List<NameValueRecord> cardList = statisticalCommonService.statisticalCardStatus();
			mv.addObject("cardStatusCategoriesJson",statisticalCommonService.getCardStatusCategoriesJson(cardList));
			mv.addObject("cardStatusValuesJson",statisticalCommonService.getCardStatusValuesJson(cardList));
		}*/
		
		//新版首页
		List<String> userIds = null;
		CustomerApplicationProcessFilter filter = new CustomerApplicationProcessFilter();
		if(rolename.equals(Constant.COMMON_USER)){
			userIds = new ArrayList<String>();
			userIds.add(userId);
		}
		else if(rolename.equals(Constant.COMMON_USER_BRANCH) || rolename.equals(Constant.BRANCH_USER)){
			filter.setFilterOrgId("1");//支行权限
			//查询机构下的客户经理
			List<Dict> org_ls = intoPiecesDao.findBelongOrgs(user.getOrganization().getId());
			for(Dict obj : org_ls){
				List<Dict> users = intoPiecesDao.findOrgBelogCusMgr(obj.getTypeCode());
				if(users != null && users.size()>0 && userIds == null){
					userIds = new ArrayList<String>();
				}
				for(Dict obj2 : users){
					userIds.add(obj2.getTypeCode());
				}
			}
		}
		else if(rolename.equals(Constant.TEAM_LEADER)){
			filter.setFilterTeamLeader("1");//团队长
			//查找团队下的客户经理
			List<Dict> users = intoPiecesDao.findTeamBelongCusMgr(user.getId());
			if(users != null && users.size()>0){
				userIds = new ArrayList<String>();
			}
			for(Dict obj : users){
				userIds.add(obj.getTypeCode());
			}
		}

		filter.setUserIds(userIds);
		
		//用信预警提示，按客户经理维度
		List<HomeTips> tips1 = statisticalCommonService.getUsedCreditByUserId(filter);
		mv.addObject("tips1",tips1);
		//贷后提醒
		List<HomeTips> tips2 = afterLoanCheckService.findAferLoanCheckRemindCountTeam(endTime,remindTime,filter);
		mv.addObject("tips2",tips2);
		//21日还息提醒
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 21);
		List<HomeTips> tips3 = afterLoanCheckService.getPsNormIntAmtListForHome(filter,calendar.getTime());
		mv.addObject("tips3",tips3);
		//即将到期客户(借据)
		List<HomeTips> tips4 = statisticalCommonService.getExpiryClientNum(filter);
		mv.addObject("tips4",tips4);
		//已到期客户()
		List<HomeTips> tips5 = statisticalCommonService.getAlreadyExpiryClientNum(filter);
		mv.addObject("tips5",tips5);
		
		//查询该客户是否有排审
		//如果是审贷会成员
		List<QzApplnApprovalMeeting> meeting_pre_ls = customerApplicationIntopieceWaitDao.findMeetingByUserIdPre(userId);
		List<QzApplnApprovalMeeting> meeting_today_ls = customerApplicationIntopieceWaitDao.findMeetingByUserIdToday(userId);
		List<QzApplnApprovalMeeting> meeting_tomorrow_ls = customerApplicationIntopieceWaitDao.findMeetingByUserIdTomorrow(userId);
		mv.addObject("meeting_pre_ls",meeting_pre_ls);
		mv.addObject("meeting_today_ls",meeting_today_ls);
		mv.addObject("meeting_tomorrow_ls",meeting_tomorrow_ls);
		
		//如果是客户经理
		List<QzApplnApprovalMeeting> meeting_pre_manager_ls = customerApplicationIntopieceWaitDao.findMeetingByManagerIdPre(userId);
		List<QzApplnApprovalMeeting> meeting_today_manager_ls = customerApplicationIntopieceWaitDao.findMeetingByManagerIdToday(userId);
		List<QzApplnApprovalMeeting> meeting_tomorrow_manager_ls = customerApplicationIntopieceWaitDao.findMeetingByManagerIdTomorrow(userId);
		mv.addObject("meeting_pre_manager_ls",meeting_pre_manager_ls);
		mv.addObject("meeting_today_manager_ls",meeting_today_manager_ls);
		mv.addObject("meeting_tomorrow_manager_ls",meeting_tomorrow_manager_ls);
		
		return mv;
	}
	
	/*首页右边信息*/
	private HashMap<String,Object> getRightHomeData(String userId){
		HashMap<String,Object> rightHomeData = new HashMap<String,Object>();
		/*用户bar*/
		/*未*/
		int not_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId, Constant.SAVE_INTOPICES, Constant.NOPASS_INTOPICES);
		/*已*/
		int already_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId,Constant.SUCCESS_INTOPICES,Constant.APPROVED_INTOPICES);
		/*待*/
		int wait_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId,Constant.APPROVE_INTOPICES,null);
		rightHomeData.put("not", not_size);
		rightHomeData.put("already", already_size);
		rightHomeData.put("wait", wait_size);
		/*进件状况*/
		/*进件数量*/
		int application_info_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId, null, null);
		/*审批通过*/
		int application_success_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId, Constant.SUCCESS_INTOPICES, null);
		/*补充调查*/
		int application_nopass_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId, Constant.NOPASS_INTOPICES, null);
		/*拒绝*/
		int application_refuse_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId, Constant.REFUSE_INTOPICES, null);
		rightHomeData.put("UserApplicationInfo", application_info_size);
		rightHomeData.put("UserApplicationSuccess", application_success_size);
		rightHomeData.put("UserApplicationNopass", application_nopass_size);
		rightHomeData.put("UserApplicationRefuse", application_refuse_size);
		/*奖励激励状况*/
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		if(month == 0){
			year = year - 1;
			month = 12;
		}
		/*奖励激励****************************************************************************/
		String reward_size = managerSalaryService.getRewardIncentiveInformation(year, month, userId);
		/*风险保证*/
		String riskGuarantee_size = managerSalaryService.getReturnPrepareAmountById(c.get(Calendar.YEAR), c.get(Calendar.MONTH), userId);
		rightHomeData.put("reward", reward_size);
		rightHomeData.put("riskGuarantee", riskGuarantee_size);
		/*客户经营状况（数量）*/
		/*授信*/
		int credit_size = statisticsScheduleService.findCustomerApplyQuota(userId);
		rightHomeData.put("credit", credit_size);
		/*客户经营状况（余额）*/
		/*有效客户*/
		int customer_size = customerInforService.findCustomerInforCountByUserId(userId);
		rightHomeData.put("customer", customer_size);
		/*逾期客户*/ 
		int risk_customer_size = customerOverdueService.findCustomerOverdueCountById(userId);
		rightHomeData.put("riskCustomer", risk_customer_size);
		/*核销客户*/
		int verification_customer_size = nplsInfomationDao.findNplsInformationCountById(userId);
		rightHomeData.put("verificationCustomer", verification_customer_size);
		return rightHomeData;
	}
	@RequestMapping(value = "/homeData.json", method = { RequestMethod.GET })
	public void getHomeDataToWeb(HttpServletRequest request,PrintWriter printWriter){
		IUser user =Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		int day = RequestHelper.getIntValue(request, "day");
		HashMap<String,Integer> homeData = mainService.getHomeData(userId,day);
		JSONArray json = JSONArray.fromObject(homeData);
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
	}
}
