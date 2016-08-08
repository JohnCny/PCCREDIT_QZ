package com.cardpay.pccredit.intopieces.service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.filter.OClpmAccLoanFilter;
import com.cardpay.pccredit.report.model.AccLoanInfo;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.JRadModelAndView;

@Service
public class SqlInputService {
	Logger logger = Logger.getLogger(SqlInputService.class);
	@Autowired
	private CommonDao commonDao;

	/*
	 * /* 执行sql语句
	 */
	public boolean changeDatabase(String sql) {
		boolean flag = false;
		Connection conn = commonDao.getSqlSession().getConnection();
		Statement st = null;

		if (conn == null) {
			logger.error("获取数据库连接失败 ！");
			return false;
		}
		try {
			st = conn.createStatement();
			st.execute(sql);
			int num = st.getUpdateCount();
			flag = true;
			logger.info("更新记录数 ： " + num);
		} catch (SQLException e) {

			logger.error(e.getMessage());
		} finally {
			try {
				if (st != null) {
					st.close();
				}

			} catch (SQLException e1) {
				Log.error(e1.getMessage());
			}
		}
		return flag;
	}

	/** 
	* @Title: queryDatabase 
	* @Description: 查询数据库
	* @param @param sql
	* @param @return
	* @param @throws JSONException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public void queryDatabase(String sql,HttpServletResponse response) throws JSONException {
		boolean flag = false;
		Connection conn = commonDao.getSqlSession().getConnection();
		Statement st = null;

		if (conn == null) {
			logger.error("获取数据库连接失败 ！");
		}
		try {
			st = conn.createStatement();
			ResultSet resultSet = st.executeQuery(sql);
			create(resultSet,response);
			//String rs = resultSetToJson(resultSet,mv);
			//logger.info("rs ： " + rs);
			//return rs;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (st != null) {
					st.close();
				}

			} catch (SQLException e1) {
				Log.error(e1.getMessage());
			}
		}
	}

	/** 
	* @Title: resultSetToJson 
	* @Description: ResultSet转json
	* @param @param rs
	* @param @return
	* @param @throws SQLException
	* @param @throws JSONException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public String resultSetToJson(ResultSet rs,JRadModelAndView mv) throws SQLException,
			JSONException {
		if (rs == null) {
			return null;
		}
		// json数组
		JSONArray array = new JSONArray();

		// 获取列数
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		JSONObject jsonObjTitle = new JSONObject();
		// 添加列名
		String transform = "[{'<>': 'tr', 'children':[";
		for (int i = 1; i <= columnCount; i++) {
			String columnName = metaData.getColumnLabel(i);
			jsonObjTitle.put(columnName, columnName);
			transform += "{'<>': 'td', 'html': '${"+columnName+"}'},";
		}
		transform = transform.substring(0, transform.length()-1);
		transform += "]}]";
		array.put(jsonObjTitle);
		
		mv.addObject("transform", transform);
		// 遍历ResultSet中的每条数据
		while (rs.next()) {

			JSONObject jsonObj = new JSONObject();
			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);
			}
			array.put(jsonObj);
		}

		return array.toString();
	}
	
	public void create(ResultSet rs,HttpServletResponse response) throws SQLException{
		HSSFWorkbook wb = new HSSFWorkbook();
		int index = 1;
		fillData(rs,wb,index);
		String fileName = "sql数据查询";
		try{
			response.setHeader("Content-Disposition", "attachment;fileName="+new String(fileName.getBytes("gbk"),"iso8859-1")+".xls");
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void fillData(ResultSet rs,HSSFWorkbook wb,int index) throws SQLException{
		ResultSetMetaData rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount(); 
        String columnName = null; 
        
		
		HSSFSheet sheet = wb.createSheet("sql数据查询"+index);
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		// 添加标题 
        for (int i = 1; i <= columnCount; i++) { 
        	cell = row.createCell((short) i);
        	columnName = rsmd.getColumnName(i);
    		cell.setCellValue(columnName);
    		cell.setCellStyle(style);
        }
        int m=0;
        while (rs.next()) { 
        	row = sheet.createRow((int) m+1);
        	row.createCell((short) 0).setCellValue(m+1);
            for (int n = 1; n <= columnCount; n++) { 
    			row.createCell((short) n).setCellValue(rs.getString(n));
            } 
            m++;
            if(m>60000){
            	index ++;
            	fillData(rs,wb,index);
            }
        } 
		
	}
}
