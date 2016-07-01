package com.cardpay.pccredit.manager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.manager.model.KhjljxbcForm;
import com.cardpay.pccredit.manager.model.Khjltjzl;
import com.cardpay.pccredit.manager.service.ManagerAssessmentScoreService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.web.ProductAttributeForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：客户经理评估信息Controller
 * @author 张石树
 *
 * 2014-11-13 下午2:26:19
 */
@Controller
@RequestMapping("/manager/jxbc/*")
@JRadModule("manager.jxbc")
public class ManagerJxbcqzController extends BaseController{
	
	@Autowired
	private ManagerAssessmentScoreService managerAssessmentScoreService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;

	/**
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView displayOrganization(@ModelAttribute MaintenanceFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/assessmentscore/qz_kehujl_jxbc", request);
		
		//查询客户经理
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm> forms = managerBelongMapService.findSubListManagerByManagerId(user);
		String customerManagerId = filter.getCustomerManagerId();
		QueryResult<KhjljxbcForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			result = managerBelongMapService.findManJxList(filter);
		}else{
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = managerBelongMapService.findManJxList(filter);
			}else{
				//直接返回页面
				return mv;
			}
		}
		JRadPagedQueryResult<KhjljxbcForm> pagedResult = new JRadPagedQueryResult<KhjljxbcForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;
	}

	
	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	public AbstractModelAndView create(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/assessmentscore/qz_kehujl_jxbc_create", request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		MaintenanceFilter filter = new MaintenanceFilter();
		//查询客户经理
		List<AccountManagerParameterForm> forms = managerBelongMapService.findSubListManagerByManagerId(user);
		mv.addObject("forms", forms);
		return mv;
	}
	
	/**
	 * add
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert( @ModelAttribute KhjljxbcForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {

				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String year = RequestHelper.getStringValue(request, "year");
				String customerManagerId = RequestHelper.getStringValue(request, "customerManagerId");
				
				String years[] = year.split("-");
				int i = managerAssessmentScoreService.findJxCountByParam(years[0],Integer.valueOf(years[1]).toString(),customerManagerId);
				if(i > 0){
					returnMap.setSuccess(false);
					returnMap.addGlobalError("该年月客户经理绩效数据已经存在！");
					return returnMap;
				}
				Khjltjzl jx = form.createModel(Khjltjzl.class);
				jx.setYear(years[0]);
				jx.setMonth(Integer.valueOf(years[1]).toString());
				jx.setCustmanagerid(customerManagerId);
				jx.setBalanceDailyAverage(form.getBalanceDailyAverage()==""?"0":form.getBalanceDailyAverage());
				jx.setMonthAverageKpiScore(form.getMonthAverageKpiScore()==""?"0":form.getMonthAverageKpiScore());
				jx.setOverdueLoanJd(form.getOverdueLoanJd()==""?"0":form.getOverdueLoanJd());
				managerAssessmentScoreService.insertKhjxbczd(jx);
				returnMap.put(MESSAGE, "添加成功");
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "change.page")
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/assessmentscore/qz_kehujl_jxbc_update", request);
		String id = RequestHelper.getStringValue(request, ID);
		Khjltjzl jx = managerAssessmentScoreService.findKhjljxById(id);
		if(jx !=null){
			mv.addObject("jx", jx);
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "update.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute KhjljxbcForm form, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				Khjltjzl jx = managerAssessmentScoreService.findKhjljxById(form.getId());
				jx.setActiveCustomerNum(form.getActiveCustomerNum());
				jx.setActualActiveCustomerNum(form.getActualActiveCustomerNum());
				jx.setMonthActualReceiveIncome(form.getMonthActualReceiveIncome());
				jx.setOtherRetailProductAwards(form.getOtherRetailProductAwards());
				jx.setBalanceDailyAverage(form.getBalanceDailyAverage());
				jx.setOverdueLoan(form.getOverdueLoan());
				jx.setAccountabilityRiskMargin(form.getAccountabilityRiskMargin());
				jx.setMonthAverageKpiScore(form.getMonthAverageKpiScore());
				jx.setOverdueLoanJd(form.getOverdueLoanJd());
				managerAssessmentScoreService.updateJxbc(jx);
				returnMap.put(MESSAGE, "修改成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
}
