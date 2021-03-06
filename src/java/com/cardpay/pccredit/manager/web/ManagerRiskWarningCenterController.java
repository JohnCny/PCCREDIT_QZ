package com.cardpay.pccredit.manager.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.manager.filter.ManagerRiskWarningFilter;
import com.cardpay.pccredit.manager.model.ManagerRiskWarning;
import com.cardpay.pccredit.manager.service.AccountManagerRetrainingService;
import com.cardpay.pccredit.manager.service.ManagerRiskWarningService;
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
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;


/**
 * ManagerRiskWarningController类的描述
 *
 * @author 王海东
 * @created on 2014-11-12
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/manager/managerriskcenter/*")
@JRadModule("manager.managerriskcenter")
public class ManagerRiskWarningCenterController extends BaseController{
	
	@Autowired
	private ManagerRiskWarningService managerRiskWarningService;
	
	@Autowired
	private PccOrganizationService pccOrganizationService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountManagerRetrainingService accountManagerRetrainingService;
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView browse(@ModelAttribute ManagerRiskWarningFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setOriginator(loginId);
		QueryResult<ManagerRiskWarningForm> result = managerRiskWarningService.findCreateManagerRiskWarningByFilter(filter);
		JRadPagedQueryResult<ManagerRiskWarningForm> pagedResult = new JRadPagedQueryResult<ManagerRiskWarningForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("manager/manager_risk_warning/manager_risk_browse", request);
		mv.addObject("loginId", loginId);
		mv.addObject(PAGED_RESULT, pagedResult);

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
	
	public AbstractModelAndView create(@ModelAttribute ManagerRiskWarningFilter filter,HttpServletRequest request) {

		JRadModelAndView mv = new JRadModelAndView("manager/manager_risk_warning/manager_risk_create_centre", request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setOriginator(loginId);
		List<ManagerRiskWarningForm> result = managerRiskWarningService.findCreateManagerRiskWarningListByFilter(filter);
		List<User> userList = new ArrayList<User>();
		for (ManagerRiskWarningForm managerRiskWarningForm : result) {
			String userId = managerRiskWarningForm.getCustomerManagerId();
			User user1 = userService.getUserById(userId);
			userList.add(user1);
		}
		List<FlatTreeNode> orgList = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		mv.addObject("orgList", orgList);
		mv.addObject("managerList", new ArrayList<User>());
		mv.addObject("result", result);
		mv.addObject("userList", userList);
		return mv;
	}
	
	/**
	 * 通过机构id查询客户经理
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findManagersByorgId.json")
	public JRadReturnMap findManagersByorgId(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String orgId = RequestHelper.getStringValue(request, "orgId");
				List<User> users = accountManagerRetrainingService.findUserListByOrgId(orgId);
				returnMap.put("datas", users);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}

	/**
	 * 执行添加
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	
	public JRadReturnMap insert(@ModelAttribute ManagerRiskWarningForm managerRiskWarningForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		returnMap.setSuccess(true);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String newAddManagerIds = request.getParameter("newAddManagerIds");
				// 判断是否新增了客户经理
				if(StringUtils.isNotEmpty(newAddManagerIds)){
					String[] ids = null;
					ids = newAddManagerIds.split(",");
					for(int i = 0; i< ids.length; i++){
						if(StringUtils.isNotEmpty(ids[i])){
							ManagerRiskWarning managerRiskWarning = managerRiskWarningForm.createModel(ManagerRiskWarning.class);
							managerRiskWarning.setOriginator(loginId);
							managerRiskWarning.setCreatedBy(loginId);
							managerRiskWarning.setCustomerManagerId(ids[i]);
							String id = managerRiskWarningService.insertManagerRiskWarning(managerRiskWarning);
						}
					}
				}
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	
	public AbstractModelAndView change(@ModelAttribute ManagerRiskWarningFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/manager_risk_warning/manager_risk_change", request);

		String riskId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(riskId)) {
			ManagerRiskWarning managerRiskWarning = managerRiskWarningService.findManagerRiskWarningById(riskId);
			mv.addObject("managerRiskWarning", managerRiskWarning);
		}
		return mv;
	}

	/**
	 * 执行修改
	 * 
	 * @param managerRiskWarningForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	
	public JRadReturnMap update(@ModelAttribute ManagerRiskWarningForm managerRiskWarningForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), managerRiskWarningForm);
		if (returnMap.isSuccess()) {
			try {
				ManagerRiskWarning managerRiskWarning = managerRiskWarningForm.createModel(ManagerRiskWarning.class);
				managerRiskWarningService.updateManagerRiskWarning(managerRiskWarning);
				returnMap.put(RECORD_ID, managerRiskWarning.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}

	/**
	 * 执行反馈的修改
	 * 
	 * @param managerRiskWarningForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatefeedback.json")
	
	public JRadReturnMap updatefeedback(@ModelAttribute ManagerRiskWarningForm managerRiskWarningForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), managerRiskWarningForm);
		if (returnMap.isSuccess()) {
			try {
				String riskId = request.getParameter("id");
				String feedback = request.getParameter("feedback");
				ManagerRiskWarning managerRiskWarning = managerRiskWarningForm.createModel(ManagerRiskWarning.class);
				managerRiskWarning.setFeedback(feedback);
				managerRiskWarning.setId(riskId);
				managerRiskWarningService.updateFeedBackById(riskId, feedback);
				returnMap.put(RECORD_ID, managerRiskWarning.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}

	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	
	public AbstractModelAndView display(@ModelAttribute ManagerRiskWarningFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/manager_risk_warning/manager_risk_display", request);

		String riskId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(riskId)) {
			ManagerRiskWarning managerRiskWarning = managerRiskWarningService.findManagerRiskWarningById(riskId);
			mv.addObject("managerRiskWarning", managerRiskWarning);
			mv.addObject("id", riskId);
		}
		return mv;
	}

	/**
	 * 执行删除
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json")
	
	public JRadReturnMap delete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String riskId = RequestHelper.getStringValue(request, ID);
			managerRiskWarningService.deleteManagerRiskWarning(riskId);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}

}
