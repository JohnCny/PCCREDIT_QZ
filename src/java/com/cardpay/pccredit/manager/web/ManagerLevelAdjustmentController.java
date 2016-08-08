package com.cardpay.pccredit.manager.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.filter.ManagerLevelAdjustmentFilter;
import com.cardpay.pccredit.manager.model.ManagerMonthTargetData;
import com.cardpay.pccredit.manager.service.ManagerLevelAdjustmentService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：客户经理级别调整controller
 * @author 张石树
 *
 * 2014-11-20 上午10:12:19
 */
@Controller
@RequestMapping("/manager/leveladjustment/*")
@JRadModule("manager.leveladjustment")
public class ManagerLevelAdjustmentController extends BaseController{
	
	@Autowired
	private ManagerLevelAdjustmentService managerLevelAdjustmentService;

	/**
	 * 查看客户经理下属的客户评估信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView browse(@ModelAttribute ManagerLevelAdjustmentFilter filter,
		HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<ManagerLevelAdjustmentForm> result = managerLevelAdjustmentService.findManagerLevelAdjustmentByFilter(filter);
		JRadPagedQueryResult<ManagerLevelAdjustmentForm> pagedResult = new JRadPagedQueryResult<ManagerLevelAdjustmentForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/level_adjustment/manager_leveladjustment_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 查看客户经理每月指标信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display_assessment_browse.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView displayOrganization(@ModelAttribute ManagerLevelAdjustmentFilter filter,
		HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<ManagerMonthTargetData> result = managerLevelAdjustmentService.findManagerMonthTargetDataByFilter(filter);
		JRadPagedQueryResult<ManagerMonthTargetData> pagedResult = new JRadPagedQueryResult<ManagerMonthTargetData>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/level_adjustment/manager_assessment_target_data_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 执行 确认
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "handleAdjustmentLevel.json")
	public JRadReturnMap handleAdjustmentLevel(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = RequestHelper.getStringValue(request, ID);
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			managerLevelAdjustmentService.handleAdjustmentLevel(id, user);
			returnMap.addGlobalMessage(ManagerLevelAdjustmentConstant.IF_HANDLE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
}
