package com.cardpay.pccredit.intopieces.web;

import java.net.URL;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.service.SqlInputService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/intopieces/sqlinputquery/*")
@JRadModule("intopieces.sqlinputquery")
public class SqlQueryControl extends BaseController {
	
	Logger logger = Logger.getLogger(SqlQueryControl.class);
	@Autowired
	private SqlInputService sqlInputService;
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page", method = { RequestMethod.GET })
	
	public AbstractModelAndView browse(HttpServletRequest request){
		JRadModelAndView mv = new JRadModelAndView("/intopieces/sqlInputforQuery", request);
		return mv;
	}
	/**
	 * 执行sql操作
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	public void change(HttpServletRequest request,HttpServletResponse response) {
		try{
			//获取页面传输的值
			String sqlText = request.getParameter("sqltext");
			System.out.println("输入的sql语句为：" + sqlText);
			//执行sql操作
			sqlInputService.queryDatabase(sqlText,response);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("sqlinputquery", e);
		}
	}
}
