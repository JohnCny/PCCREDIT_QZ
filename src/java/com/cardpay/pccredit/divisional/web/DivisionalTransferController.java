package com.cardpay.pccredit.divisional.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.divisional.constant.DivisionalConstant;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.DivisionalTransfer;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * @author 姚绍明
 *
 * 2014年10月24日 上午10:25:05
 */
@Controller
@RequestMapping("/divisional/customeraltransfer/*")
@JRadModule("divisional.customeraltransfer")
public class DivisionalTransferController extends BaseController{
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private DivisionalService divisionalservice;
	@Autowired
	private PccOrganizationService pccOrganizationService;
	@Autowired
	private UserService userService;
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute DivisionalFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setOriginalManagerOld(user.getId());
		QueryResult<DivisionalTransfer> result = divisionalservice.findDivisionalTransfer(filter);
		JRadPagedQueryResult<DivisionalTransfer> pagedResult = new JRadPagedQueryResult<DivisionalTransfer>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/divisional/customeraltransfer/divisional_transfer_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 移交客户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "transfer.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String,Object> transfer(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			String customerId = RequestHelper.getStringValue(request, ID);
			Boolean flag = divisionalservice.insertDivisionalCustomer(customerId,DivisionalTypeEnum.initiative,DivisionalProgressEnum.charge);
			if(flag){
				returnMap.put(JRadConstants.SUCCESS,true);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERSUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS,false);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERERROR);
			}
		}catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 移交客户-泉州
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "transfer_qz.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String,Object> transfer_qz(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String customerId = RequestHelper.getStringValue(request, ID);
			divisionalservice.insertDivisionalCustomer_qz(customerId,DivisionalTypeEnum.initiative,DivisionalProgressEnum.charge);
			returnMap.put(JRadConstants.SUCCESS,true);
			returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERSUCCESS);
			
		}catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	//信贷客户移交-界面
	@ResponseBody
	@RequestMapping(value = "transfer_xd_qz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView transfer_xd_qz(@ModelAttribute DivisionalFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setOriginalManagerOld(user.getId());
		String customerId = RequestHelper.getStringValue(request, ID);
		CustomerInfor customerInfor = customerInforservice.findCustomerInforByCustomerId(customerId);
		//查询所有机构
		List<FlatTreeNode> orgs= pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		//查询所有用户
		List<UserIpad> users = divisionalservice.getAllUsers();
		JRadModelAndView mv = new JRadModelAndView("/divisional/customeraltransfer/divisional_xd_transfer", request);
		mv.addObject("customerInfor", customerInfor);
		mv.addObject("orgs", orgs);
		mv.addObject("users", users);
		return mv;
	}

	//信贷客户移交-发起
	@ResponseBody
	@RequestMapping(value = "transfer_xd_qz.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String,Object> transfer_xd_qz_json(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String customerId = RequestHelper.getStringValue(request, ID);
			String orgId = RequestHelper.getStringValue(request, "orgId");
			String userId = RequestHelper.getStringValue(request, "userId");
			String changeBelong = RequestHelper.getStringValue(request, "changeBelong");
			
			divisionalservice.insertDivisionalCustomer_xd_qz(customerId,DivisionalTypeEnum.compulsive,DivisionalProgressEnum.charge,orgId,userId,changeBelong);
			returnMap.put(JRadConstants.SUCCESS,true);
			returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERSUCCESS);
			
		}catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
}
