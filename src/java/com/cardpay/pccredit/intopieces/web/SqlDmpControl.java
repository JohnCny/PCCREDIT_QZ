package com.cardpay.pccredit.intopieces.web;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.cardpay.pccredit.intopieces.model.SqlInputPojo;
import com.cardpay.pccredit.intopieces.service.BackupDBService;
import com.cardpay.pccredit.intopieces.service.SqlInputService;
import com.cardpay.pccredit.intopieces.service.ZipUtils;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/intopieces/sqldmp/*")
@JRadModule("intopieces.sqldmp")
public class SqlDmpControl extends BaseController {

	Logger logger = Logger.getLogger(SqlDmpControl.class);
	@Autowired
	private SqlInputService sqlInputService;
	@Autowired
	private BackupDBService backupDBService;

	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "create.page", method = { RequestMethod.GET })

	public AbstractModelAndView browse(HttpServletRequest request) throws Exception {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/sqldmp", request);
		List<SqlInputPojo> ls = sqlInputService.getTableNames();
		for(SqlInputPojo obj : ls){
			obj.setTablespaceName(obj.getRowindex() + "." + obj.getTableName());
			if(obj.getTableName().startsWith("O_")){
				obj.setChecked(false);
			}
		}
		JSONArray obj = JSONArray.fromObject(ls);
		mv.addObject("znodes", obj.toString());
		mv.addObject("dmpFiles", showAllFiles(new File(Constant.FILE_DMP_PATH)));
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

	public JRadReturnMap change(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				// 获取页面传输的值
				String nodesJsonStr = request.getParameter("nodesJsonStr");
				JSONArray jsonarr = JSONArray.fromObject(nodesJsonStr);
				String tablenames = "";
				Object[] obj = new Object[jsonarr.size()];
				for (int i = 0; i < jsonarr.size(); i++) {
					JSONObject jsonObject = jsonarr.getJSONObject(i);
					tablenames += jsonObject.get("tableName") + ",";
				}
				tablenames = tablenames.substring(0, tablenames.length() - 1);
				logger.info("备份的表名为：" + tablenames);
				// 执行sql操作
				String backupCmd = "exp pccredit_qz/pccredit_qz@192.168.0.252/orcl " + "file="+Constant.FILE_DMP_PATH
						+ new java.text.SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())
						+ ".dmp compress=y tables=(" + tablenames + ")";
				boolean reSuccess = backupDBService.backupORreductionOracleDB(backupCmd);
				returnMap.put(JRadConstants.SUCCESS, reSuccess);
				returnMap.addGlobalMessage(CREATE_SUCCESS);

			} catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		} else {
			returnMap.setSuccess(false);

		}
		return returnMap;
	}

	/**
	 * 执行exe文件下载
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "download.json", method = { RequestMethod.GET })
	public void dowload(HttpServletRequest request, HttpServletResponse response) {
		try {
			String dmpFile = request.getParameter("dmpFile");
			if(dmpFile.indexOf(".zip")>=0){
				UploadFileTool.downLoadFile(response, Constant.FILE_DMP_PATH+dmpFile,dmpFile);
			}
			else{
				ZipUtils.compress(new File(Constant.FILE_DMP_PATH+dmpFile));
				UploadFileTool.downLoadFile(response, Constant.FILE_DMP_PATH+dmpFile+".zip",dmpFile+".zip");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public List<String> showAllFiles(File dir) throws Exception {
		List<String> ls = new ArrayList<String>();
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			/*System.out.println(fs[i].getAbsolutePath());
			if (fs[i].isDirectory()) {
				try {
					showAllFiles(fs[i]);
				} catch (Exception e) {
				}
			}*/
			if(fs[i].getName().indexOf(".dmp")>=0){
				ls.add(fs[i].getName()+"_"+fs[i].length());
			}
		}
		return ls;
	}
}
