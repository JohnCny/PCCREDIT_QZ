package com.cardpay.pccredit.system.web;

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
import com.cardpay.pccredit.system.filter.SystemUserFilter;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.SystemUserService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
/**
 * 
 * @author 季东晓
 *
 * 2014-12-5 上午10:21:31
 */
@Controller
@RequestMapping("/system/approvaluser/*")
@JRadModule("system.approvaluser")
public class SystemUserController extends BaseController{
	
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private  PccOrganizationService  pccOrganizationService;
	
	
	/**
	 * 用户显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView browse(@ModelAttribute SystemUserFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String selectval = request.getParameter("selectval");
		String selectvaltmp = "";
		List<String> selectlist = new  ArrayList<String>();
		String [] selectvalarry =null;
		if(StringUtils.isNotEmpty(selectval)){
		 selectvalarry= selectval.split(",");
		 for(int i=0;i<selectvalarry.length;i++){ 
			 selectlist.add(selectvalarry[i]);
		 }
		 selectvaltmp = "'" + selectval.replaceAll(",", "','") + "'";
		 filter.setSelectvaltmp(selectvaltmp);
	    }
		filter.setLimit(5);
		QueryResult<SystemUser> result = systemUserService.findSystemUserByFilter(filter);	
		JRadPagedQueryResult<SystemUser> pagedResult = new JRadPagedQueryResult<SystemUser>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/system/nodeaudit/system_user_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("selectvalList", selectlist);
		return mv;
	}
	
	/**
	 * 获得单位
	 * 
	 *
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectOrgId.json")
	
	public JRadReturnMap selectOrgId(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				 List<FlatTreeNode> results= pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
				returnMap.put("results", results);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	

}
