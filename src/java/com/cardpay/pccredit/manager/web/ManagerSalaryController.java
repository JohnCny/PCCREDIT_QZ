package com.cardpay.pccredit.manager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.service.ManagerSalaryService;
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
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:56:37
 */
@Controller
@RequestMapping("/manager/managersalary/*")
@JRadModule("manager.managersalary")
public class ManagerSalaryController extends BaseController {
	@Autowired
	private ManagerSalaryService managerSalaryService;
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView browse(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/managersalary/managersalary_browse", request);
		String chineseName = request.getParameter("userId");
		//查询客户经理
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm> forms = managerBelongMapService.findSubListManagerByManagerId(user);
		String customerManagerId = filter.getCustomerManagerId();
		
		QueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			forms =  managerBelongMapService.findAllManager();
			filter.setCustomerManagerIds(forms);
			result = managerSalaryService.findManagerSalaryByFilter(filter);
		}else{
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = managerSalaryService.findManagerSalaryByFilter(filter);
			}else{
				forms =  managerBelongMapService.findAllManager();
				filter.setCustomerManagerIds(forms);
				result = managerSalaryService.findManagerSalaryByFilter(filter);
			}
		}
		filter.setManagerName(chineseName);
		//QueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm> result = managerSalaryService.findManagerSalaryByFilter(filter);
		JRadPagedQueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm> pagedResult = new JRadPagedQueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;
	}
	
	/**
	 * 生成月度数据
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "generateData.json")
	
	public JRadReturnMap generateData(@ModelAttribute ManagerSalaryForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			managerSalaryService.docalculateMonthlySalaryTy(Integer.valueOf(form.getYear()), Integer.valueOf(form.getMonth()));
		}
		catch (Exception e) {
			returnMap.setSuccess(false);
			returnMap.addGlobalError(e.getMessage());
			return returnMap;
			
		}
		return null;
	}
}
