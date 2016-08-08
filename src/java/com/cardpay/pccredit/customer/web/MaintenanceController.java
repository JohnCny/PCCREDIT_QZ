/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.MaintenanceConstant;
import com.cardpay.pccredit.customer.constant.MaintenanceEndResultEnum;
import com.cardpay.pccredit.customer.constant.MarketingCreateWayEnum;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.model.MaintenanceAction;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEnum;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
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
 * @author shaoming
 *
 * 2014年11月11日   下午2:27:27
 */
@Controller
@RequestMapping("/customer/maintenance/*")
@JRadModule("customer.maintenance")
public class MaintenanceController extends BaseController{
	@Autowired
	private MaintenanceService maintenanceService;

	@Autowired
	private ManagerBelongMapService managerBelongMapService;

	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 浏览维护计划信息页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView browse(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		filter.setCustomerManagerId(userId);
		QueryResult<MaintenanceForm> result = maintenanceService.findMaintenancePlansByFilter(filter);
		JRadPagedQueryResult<MaintenanceForm> pagedResult = new JRadPagedQueryResult<MaintenanceForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}

	/**
	 * 增加维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	
	public AbstractModelAndView create(HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_create", request);
		mv.addObject("userId",userId);
		return mv;
	}

	/**
	 * 修改维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_change", request);
		String maintenanceId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(maintenanceId)) {
			MaintenanceForm maintenance = maintenanceService.findMaintenanceById(maintenanceId);
			mv.addObject("maintenance", maintenance);
		}
		return mv;
	}

	/**
	 * 显示维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_display", request);
		String maintenanceId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(maintenanceId)) {
			MaintenanceForm maintenance = maintenanceService.findMaintenanceById(maintenanceId);
			List<MaintenanceWeb> maintenanceActions = maintenanceService.findMaintenanceActionByMaintenanceId(maintenanceId);
			mv.addObject("maintenance", maintenance);
			mv.addObject("maintenanceActions",maintenanceActions);
		}

		return mv;
	}
	/**
	 * 添加催收计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	
	public JRadReturnMap insert(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		boolean flag = maintenanceService.checkRepeat(form.getCustomerId(),MaintenanceEndResultEnum.maintaining);
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if(!flag){
			if (returnMap.isSuccess()) {
				try {
					Maintenance maintenance = form.createModel(Maintenance.class);
					IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
					String createdBy = user.getId();
					maintenance.setCreatedBy(createdBy);
					String customerManagerId = maintenance.getCustomerManagerId();

					if(customerManagerId!=null && customerManagerId.equals(createdBy)){
						maintenance.setCreateWay(MarketingCreateWayEnum.myself.toString());;
					}else{
						maintenance.setCreateWay(MarketingCreateWayEnum.manager.toString());;
					}

					String id = maintenanceService.insertMaintenance(maintenance);
					returnMap.put(RECORD_ID, id);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
				}catch (Exception e) {
					returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
					returnMap.put(JRadConstants.SUCCESS, false);
					return WebRequestHelper.processException(e);
				}
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(MaintenanceConstant.alreadyExists);
		}
		return returnMap;
	}
	/**
	 * 修改维护计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	
	public JRadReturnMap update(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				String createWay = maintenanceService.findMaintenanceById(form.getId()).getCreateWay();
				if(createWay!=null && createWay.equals(RiskCustomerCollectionEnum.system.toString())){
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
					return returnMap;
				}
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				Maintenance maintenance = form.createModel(Maintenance.class);
				maintenance.setModifiedBy(modifiedBy);
				boolean flag=maintenanceService.updateMaintenance(maintenance);
				if(flag){
					returnMap.put(RECORD_ID, maintenance.getId());
					returnMap.addGlobalMessage(CHANGE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 增加催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createAction.page")
	
	public AbstractModelAndView createAction(HttpServletRequest request) {
		String maintenanceId = RequestHelper.getStringValue(request, ID);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_action_create", request);
		mv.addObject("maintenanceId",maintenanceId);
		return mv;
	}
	/**
	 * 添加维护计划实施信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertAction.json")
	
	public JRadReturnMap insertAction(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				Maintenance maintenance = new Maintenance();
				String endResult= form.getEndResult();
				String maintenanceId = form.getMaintenancePlanId();
				maintenance.setId(maintenanceId);
				maintenance.setEndResult(endResult);
				/*修改催收计划最终结果*/
				boolean flag = maintenanceService.updateMaintenancePassive(maintenance);
				if(flag){
					/*在当前计划下添加一条新的实施信息记录*/
					MaintenanceAction maintenanceAction = new MaintenanceAction();
					IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
					String createdBy = user.getId();
					String maintenanceEndTime = form.getMaintenanceEndTime();
					String maintenanceStartTime = form.getMaintenanceStartTime();
					maintenanceAction.setCreatedBy(createdBy);
					if(maintenanceEndTime!=null && !maintenanceEndTime.equals("")){
						maintenanceAction.setMaintenanceEndTime(DateHelper.getDateFormat(maintenanceEndTime, "yyyy-MM-dd HH:mm:ss"));
					}
					if(maintenanceStartTime!=null && !maintenanceStartTime.equals("")){
						maintenanceAction.setMaintenanceStartTime(DateHelper.getDateFormat(maintenanceStartTime,"yyyy-MM-dd HH:mm:ss"));
					}
					maintenanceAction.setMaintenancePlanId(form.getMaintenancePlanId());
					maintenanceAction.setMaintenanceResult(form.getMaintenanceResult());
					maintenanceAction.setMaintenanceWay(form.getMaintenanceWay());
					String id = maintenanceService.insertMaintenanceAction(maintenanceAction);
					/*若最终结果为继续维护*/
					String maintenancePlanId = "";
					if(endResult.equals(MaintenanceEndResultEnum.nextmaintain.toString())){
						/*新增一条新的计划，计划内容与当前计划相同,最终结果为维护中*/
						maintenancePlanId = maintenanceService.copyMaintenancePlan(maintenanceId, MaintenanceEndResultEnum.maintaining, createdBy);
						/*返回最初维护计划页面*/
						returnMap.put(RECORD_ID,maintenancePlanId);
					}else{
						/*返回维护计划详细信息页面*/
						returnMap.put(ID, maintenanceId);
						returnMap.put(RECORD_ID, id);
					}
					returnMap.put(JRadConstants.MESSAGE, endResult);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 修改催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeAction.page")
	
	public AbstractModelAndView changeAction(HttpServletRequest request) {
		String id = RequestHelper.getStringValue(request, ID);
		MaintenanceAction maintenanceAction = maintenanceService.findMaintenanceActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_action_change", request);
		mv.addObject("maintenanceAction",maintenanceAction);
		return mv;
	}
	/**
	 * 修改催收计划实施信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAction.json")
	
	public JRadReturnMap updateAction(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				String maintenanceStartTime = form.getMaintenanceStartTime();
				String maintenanceEndTime = form.getMaintenanceEndTime();
				MaintenanceAction maintenanceAction = new MaintenanceAction();
				maintenanceAction.setModifiedBy(modifiedBy);
				maintenanceAction.setId(form.getId());
				maintenanceAction.setMaintenancePlanId(form.getMaintenancePlanId());
				maintenanceAction.setMaintenanceWay(form.getMaintenanceWay());
				maintenanceAction.setMaintenanceResult(form.getMaintenanceResult());
				maintenanceAction.setMaintenanceStartTime(DateHelper.getDateFormat(maintenanceStartTime, "yyyy-MM-dd HH:mm:ss"));
				maintenanceAction.setMaintenanceEndTime(DateHelper.getDateFormat(maintenanceEndTime, "yyyy-MM-dd HH:mm:ss"));
				boolean flag=maintenanceService.updateMaintenanceAction(maintenanceAction);
				if(flag){
					returnMap.put(ID, maintenanceAction.getMaintenancePlanId());
					returnMap.put(RECORD_ID, maintenanceAction.getId());
					returnMap.addGlobalMessage(CHANGE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	@RequestMapping(value = "getManager.json",method = RequestMethod.GET)
	public void getManager(HttpServletRequest request,PrintWriter printWriter){
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			List<AccountManagerParameterForm> accountManagerParameterForms = managerBelongMapService.findSubManagerBelongMapByManagerId(userId);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(accountManagerParameterForms);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "getCustomer.json",method = RequestMethod.GET)
	public void getCustomer(HttpServletRequest request,PrintWriter printWriter){
		try {
			String userId = RequestHelper.getStringValue(request, ID);
			List<CustomerInfor> customers = customerInforService.findCustomerByManagerId(userId);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(customers);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
