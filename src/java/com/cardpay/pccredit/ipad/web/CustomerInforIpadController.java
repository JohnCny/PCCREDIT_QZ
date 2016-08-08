/**
 * 
 */
package com.cardpay.pccredit.ipad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.web.CustomerInforForm;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.ipad.model.CustomerInforIpad;
import com.cardpay.pccredit.ipad.model.Result;
import com.cardpay.pccredit.ipad.service.CustomerInforForIpadService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.controller.BaseController;

/**
 * @author shaoming
 *
 * 2014年11月27日   下午4:14:28
 */
@Controller
public class CustomerInforIpadController extends BaseController{

	@Autowired
	private CustomerInforForIpadService customerInforService;

	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/insert.json")
	
	public String insert(@ModelAttribute CustomerInforForm customerinfoForm, HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		CustomerInfor customerInfor = customerinfoForm.createModel(CustomerInfor.class);
		//		System.out.println(request.getParameter("chineseName"));
		Result result = new Result();
		try{
			boolean flag = customerInforService.insertCustomerInfor(customerInfor);
			if(!flag){
				result.setStatus(IpadConstant.FAIL);
				result.setReason(IpadConstant.CREATEFAIL);
			}else{
				result.setStatus(IpadConstant.SUCCESS);
				result.setReason(IpadConstant.CREATESUCCESS);
			}
		}catch(Exception e){
			result.setStatus(IpadConstant.FAIL);
			result.setReason(IpadConstant.SYSTEMERROR);
		}
		
		map.put("result",result);		
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	/**
	 * 提供userId得到该客户经理名下所有客户接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/getCustomer.json")
	
	public String getCustomerInforByUserId(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String userId = request.getParameter("userId");
		if(StringUtils.isNotEmpty(userId)){
			String currentPage=request.getParameter("currentPage");
			String pageSize=request.getParameter("pageSize");
			int page = 1;
			int limit = 10;
			if(StringUtils.isNotEmpty(currentPage)){
				page = Integer.parseInt(currentPage);
			}
			if(StringUtils.isNotEmpty(pageSize)){
				limit = Integer.parseInt(pageSize);
			}
			try{
				List<CustomerInforIpad> customerInfors = customerInforService.getCustomerInforByUserId(userId,page,limit);
				int totalCount = customerInforService.getCustomerInforCountByUserId(userId);
				map.put("totalCount", totalCount);
				map.put("result",customerInfors);	
			}catch(Exception e){
				Result result = new Result();
				result.setReason(IpadConstant.SYSTEMERROR);
				result.setStatus(IpadConstant.FAIL);
				map.put("result", result);
			}
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();

	}
}
