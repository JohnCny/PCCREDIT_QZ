package com.cardpay.pccredit.intopieces.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.service.SqlInputService;
import com.cardpay.pccredit.intopieces.service.ZipUtils;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import sun.security.jca.GetInstance.Instance;

@Controller
@RequestMapping("/intopieces/sqlinputquery/*")
@JRadModule("intopieces.sqlinputquery")
public class SqlQueryControl extends BaseController {
	private static Logger logger = Logger.getLogger(SqlQueryControl.class);
	
	@Autowired
	private SqlInputService sqlInputService;
	
	@ResponseBody
	@RequestMapping(value = "create.page", method = { RequestMethod.GET })
	public AbstractModelAndView browse(HttpServletRequest request){
		JRadModelAndView mv = new JRadModelAndView("/intopieces/sqlInputforQuery", request);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "getDbDataMulti.json")
	public JRadReturnMap getDbDataMulti(HttpServletRequest request){
		JRadReturnMap returnMap = new JRadReturnMap();
		String sqls = request.getParameter("sqls");
		if(StringUtils.isNotEmpty(sqls)){
			String[] sqltmps = sqls.split(";");
			try {
				List result = new ArrayList();
				for (String sql : sqltmps) {
					if(StringUtils.isNotEmpty(sql)){
						List<?> list = this.getDbDataList(sql, 1);
						result.add(list);
					}
				}
				returnMap.setSuccess(true);
				returnMap.put("list", result);
			} catch (Exception e) {
				logger.error("查询异常，查询语句[" + sqls + "]", e);
				returnMap.setSuccess(false);
				returnMap.addGlobalMessage(e.getMessage());
			}
		}
		else{
			returnMap.setSuccess(false);
			returnMap.addGlobalMessage("查询语句为空!");
		}
		return returnMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "getDbData.json")
	public JRadReturnMap getDbData(HttpServletRequest request){
		JRadReturnMap returnMap = new JRadReturnMap();
		String sql = request.getParameter("sql");
		int curPage = StringUtils.isNotEmpty(request.getParameter("curPage"))?Integer.parseInt(request.getParameter("curPage")):1;
		try {
			List result = new ArrayList();
			List<?> list = this.getDbDataList(sql, curPage);
			result.add(list);
			returnMap.setSuccess(true);
			returnMap.put("list", result);
		} catch (Exception e) {
			logger.error("查询异常，查询语句[" + sql + "]", e);
			returnMap.setSuccess(false);
			returnMap.addGlobalMessage(e.getMessage());
		}
		return returnMap;
	}
	
	public List<?> getDbDataList(String sql,int curPage) throws SQLException{
		return this.getData(sql, curPage);
		
	}
	
	/**
	 * 封装SQL的分页
	 * @param sql SQL语句
	 * @param end
	 * @return
	 */
	public String packagingSql(String sql,int end){
		int start = end - 10;
		sql = "select * from( select rownum trownum,t.* from (" + sql + ") t where rownum <="+ end +") where trownum >"+start;
		
		logger.info(sql);
		return sql;
	}
	
	public String packagingSql2(String sql,int end){
		int start = 0;
		sql = "select * from( select rownum trownum,t.* from (" + sql + ") t where rownum <="+ end +") where trownum >"+start;
		
		logger.info(sql);
		return sql;
	}
	
	
	/**
	 * DML语句
	 * @param sql sql语句
	 * @return 操纵的sql数
	 */
	public int update(String sql){
		return sqlInputService.changeDatabase(sql);
	}
	
	/**
	 * DML语句
	 * @param sqlLists sql 列表
	 */

	@ResponseBody
	@RequestMapping(value = "updateList.json")
	public JRadReturnMap updateList(HttpServletRequest request){
		JRadReturnMap returnMap = new JRadReturnMap();
		String sqls = request.getParameter("sqlList");
		if(StringUtils.isNotEmpty(sqls)){
			String[] sqltmps = sqls.split(";");
			try{
				for (String sql : sqltmps) {
					if(sql == null || "".equals(sql)){
						continue;
					}
					
					if(sql.trim().intern() == "".intern()){
						continue;
					}
					this.update(sql);
				}
				returnMap.setSuccess(true);
			}catch(Exception e){
				logger.error("查询异常，查询语句[" + sqls + "]", e);
				returnMap.setSuccess(false);
				returnMap.addGlobalMessage(e.getMessage());
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalMessage("查询语句为空!");
		}
		
		return returnMap;
	}
	
	private List<?> getData(String sql,int curPage) throws SQLException{

		List result = new ArrayList();
		int total = sqlInputService.getTotalCount(sql);
		result.add(total);
		
		int end = curPage * 10;
		sql = packagingSql(sql, end);
		
		sqlInputService.executeQuery(sql,result);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "dataListDownload.json", method = { RequestMethod.GET })
	public void dataListDownload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String sql = request.getParameter("sql");
		String type = request.getParameter("type");
		int pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum"))?Integer.parseInt(request.getParameter("pageNum")):1;
		
		sql = packagingSql2(sql,pageNum);
		List result = new ArrayList();
		sqlInputService.executeQuery(sql,result);
        PrintStream ps= null;
		if(type.equals("text")){
			response.setContentType("text/plain");
			response.setHeader("Connection", "close");
	        // 防止中文文件名乱码
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strTime = format.format(new Date());
	        response.setHeader("Content-Disposition", "attachment;filename="+strTime+".txt");
	        OutputStream os;
			try {
				os = response.getOutputStream();
				ps = new PrintStream(os);
				List<String> titles = (List<String>)result.get(0);
				for(String str : titles){
					ps.printf("%-32s",str);  
				}
				ps.println();
				result.remove(0);
		        for(Object obj : result){
		        	Map map = (Map)obj;
		        	for(String str : titles){
						ps.printf("%-32s",map.get(str));  
					}

					ps.println();
		        }
		        //ps.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}finally{
				ps.close();
			}
	        
		}
		else if(type.equals("xls")){
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("data");
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFCell cell;
			List<String> titles = (List<String>)result.get(0);
			
			for(int i=0;i<titles.size();i++){
				cell = row.createCell((short) i);
				cell.setCellValue(titles.get(i));
				cell.setCellStyle(style);
			}
			result.remove(0);
			int rowcount=1;
	        for(Object obj : result){
	        	Map map = (Map)obj;
				row = sheet.createRow((int) rowcount);
				for(int i=0;i<titles.size();i++){
					cell = row.createCell((short) i);
					cell.setCellValue((String)map.get(titles.get(i)));
					cell.setCellStyle(style);
				}
				rowcount++;
	        }

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strTime = format.format(new Date());
			OutputStream os = null;
			try{
				response.setHeader("Content-Disposition", "attachment;fileName="+strTime+".xls");
				response.setHeader("Connection", "close");
				response.setHeader("Content-Type", "application/vnd.ms-excel");
				os = response.getOutputStream();
				wb.write(os);
				os.flush();
				os.close();

			}catch(IOException e){
				e.printStackTrace();
			}finally{
				os.close();
			}
		}
	}
	
}
