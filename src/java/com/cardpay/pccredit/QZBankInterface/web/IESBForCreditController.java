package com.cardpay.pccredit.QZBankInterface.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.QZBankInterface.model.Credit;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.cardpay.pccredit.QZBankInterface.service.CreditService;
import com.cardpay.pccredit.QZBankInterface.service.ECIFService;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年4月14日 下午5:44:14 
 * 程序的简单说明 
 */
@Controller
@RequestMapping("/intopieces/credit/*")
@JRadModule("intopieces.credit")
public class IESBForCreditController extends BaseController{
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private ECIFService eCIFService; 
	
	/*
	 * 跳转到增加客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "create.page")
	public AbstractModelAndView create(HttpServletRequest request) {  
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/iesbforcredit", request);
		//查找新开户
		List<IESBForECIFReturnMap> ls = eCIFService.findAllECIFByStatus(null);
		mv.addObject("ECIF_ls",ls);
		JSONArray json = new JSONArray();
		json = JSONArray.fromObject(ls);
		mv.addObject("ECIF_ls_json",json.toString());
				
		//查找登录用户信息
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String orgId = user.getOrganization().getId();//机构ID
		String parentOrgId = user.getOrganization().getParentId();//机构ID
		if(parentOrgId.equals("000000")){//替换为泉州总行id
			parentOrgId = Constant.QZ_ORG_ROOT_ID;
		}
		String externalId = user.getLogin();//工号
		mv.addObject("orgId",orgId);
		mv.addObject("parentOrgId",parentOrgId);
		mv.addObject("externalId",externalId);
				
		return mv;
	}
	
	/**
	 * 执行添加客户信息
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insert.json")
	public JRadReturnMap insert(@ModelAttribute IESBForCreditForm iesbForCreditForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				//设置级联选项
				iesbForCreditForm.setRegPermResidence(iesbForCreditForm.getRegPermResidence_3().split("_")[1]);
				
				//替换为总行id
				if(iesbForCreditForm.getHigherOrgNo().equals(Constant.QZ_ORG_ROOT_ID)){
					iesbForCreditForm.setHigherOrgNo("000000");
				}
				
				Credit credit = iesbForCreditForm.createModel(Credit.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				credit.setCreatedBy(user.getId());
				credit.setUserId(user.getId());
				creditService.insertCustomerInforCredit(credit);
//				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
}

