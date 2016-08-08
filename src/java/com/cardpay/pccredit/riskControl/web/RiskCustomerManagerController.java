package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.service.RiskCustomerService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * @author chenzhifang
 *
 * 2014-11-4上午10:17:57
 */
@Controller
@RequestMapping("/riskcontrol/riskcustomermanager/*")
@JRadModule("riskcontrol.riskcustomermanager")
public class RiskCustomerManagerController extends BaseController {
	
	@Autowired
	private RiskCustomerService riskCustomerService;
	
	/**
	 * 客户经理浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "managerbrowse.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView managerBrowse(@ModelAttribute RiskCustomerFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
        user.getRoles();
        
        // 客户经理Id
        filter.setReportedIdManager(user.getId());
        // 风险类型
        filter.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
        // 客户经理
        filter.setRole(RiskControlRole.manager.toString());
		QueryResult<RiskCustomer> result = riskCustomerService.findRiskCustomersByFilter(filter);
		JRadPagedQueryResult<RiskCustomer> pagedResult = new JRadPagedQueryResult<RiskCustomer>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/riskcustomer_manager_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
}
