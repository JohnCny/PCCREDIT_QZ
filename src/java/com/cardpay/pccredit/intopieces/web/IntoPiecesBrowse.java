package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.intopieces.constant.CardStatus;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
import com.cardpay.pccredit.intopieces.model.ApprovedInfo;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContactVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantorVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecomVo;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcelForm;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.report.filter.OClpmAccLoanFilter;
import com.cardpay.pccredit.report.model.AccLoanInfo;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/intopieces/intopiecesqueryAll/*")
@JRadModule("intopieces.intopiecesqueryAll")
public class IntoPiecesBrowse extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;
	
	@Autowired
	private DivisionalService divisionalService;
	
	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private CommonDao commonDao;
	
	//贷成长/贷生活查询界面
	@ResponseBody
	@RequestMapping(value = "browseAll.page", method = { RequestMethod.GET })
	public AbstractModelAndView browseAll(@ModelAttribute IntoPiecesFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setUserId(userId);
		
		if(filter.getStartDate() ==null){
			filter.setStartDate(DateHelper.getDateFormat("2005-08-01","yyyy-MM-dd"));
		}
		if(filter.getEndDate()==null){
			filter.setEndDate(new Date());
		}
		
		if(StringUtils.isNotEmpty(filter.getProductId()) && filter.getProductId().equals("ALL")){//全部
			filter.setProductId(null);
		}
		//查看自己是否团队长
		List<Dict> blg_cus_ls = intoPiecesService.findBelogCusMgr(userId);
		if(blg_cus_ls != null && blg_cus_ls.size()>0){
			filter.setFilterTeamLeader("1");
		}
		else{//列出全部客户经理
			blg_cus_ls = divisionalService.findCustomerManagers(null);
		}
		QueryResult<IntoPieces> result = intoPiecesService.findintoPiecesAllByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(
				filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/intopieces_browseAll", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("blg_cus_ls", blg_cus_ls);
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		mv.addObject("url", url);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "exportApprovedInfo.page", method = { RequestMethod.GET })
	public void exportAll(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		List<ApprovedInfo> list = intoPiecesService.getApprovedInfo();
		create(list,response);
	}
	
	public void create(List<ApprovedInfo> list,HttpServletResponse response) throws ParseException{
		boolean flag = false;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("已贷款客户信息导出");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("客户经理");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("产品名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("户籍");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("身份证");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("年龄");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("手机");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("家庭住址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("营业地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("电话");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("配偶姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("借记卡账号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("流水");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("行业分类");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("贷款投向1级");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("贷款投向2级");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("经营年限");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("营业额(万)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("资产(万)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("权益");
		cell.setCellStyle(style);
		cell = row.createCell((short) 21);
		cell.setCellValue("负债率");
		cell.setCellStyle(style);
		cell = row.createCell((short) 22);
		cell.setCellValue("年可支配收入");
		cell.setCellStyle(style);
		cell = row.createCell((short) 23);
		cell.setCellValue("评审依据");
		cell.setCellStyle(style);
		cell = row.createCell((short) 24);
		cell.setCellValue("授信金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 25);
		cell.setCellValue("授信开始日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 26);
		cell.setCellValue("授信结束日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 27);
		cell.setCellValue("还款期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 28);
		cell.setCellValue("续授信");
		cell.setCellStyle(style);
		
		DecimalFormat df1 = new DecimalFormat("0.00");
		
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		Dictionary dictionary1 = dictMgr.getDictionaryByName("INDIV_BRT_PLACE_LEVEL_1");//FIELD_NAME[i]具体数据字典字段
		Dictionary dictionary2 = dictMgr.getDictionaryByName("INDIV_BRT_PLACE_LEVEL_2");//FIELD_NAME[i]具体数据字典字段
		Dictionary dictionary3 = dictMgr.getDictionaryByName("INDIV_BRT_PLACE_LEVEL_3");//FIELD_NAME[i]具体数据字典字段
		
		Dictionary dictionary_direction1 = dictMgr.getDictionaryByName("LOAN_DIRECTION_LEVEL_1");//FIELD_NAME[i]具体数据字典字段
		Dictionary dictionary_direction2 = dictMgr.getDictionaryByName("LOAN_DIRECTION_LEVEL_2");//FIELD_NAME[i]具体数据字典字段
		
		for(int i = 0; i < list.size(); i++){
			ApprovedInfo loan = list.get(i);
			row = sheet.createRow((int) i+1);
			row.createCell((short) 0).setCellValue(loan.getRowindex());
			row.createCell((short) 1).setCellValue(loan.getDisplayName());
			row.createCell((short) 2).setCellValue(loan.getProductName());
			row.createCell((short) 3).setCellValue(loan.getClientName());
			//籍贯
			String tmp = "";
			List<DictionaryItem> dictItems1 = dictionary1.getItems();
			for(DictionaryItem item : dictItems1){
				if(item.getName().equals(loan.getJiguan1())){
					tmp += item.getTitle();
					break;
				}
			}
			List<DictionaryItem> dictItems2 = dictionary2.getItems();
			for(DictionaryItem item : dictItems2){
				if(item.getName().equals(loan.getJiguan2())){
					tmp += item.getTitle();
					break;
				}
			}
			List<DictionaryItem> dictItems3 = dictionary3.getItems();
			for(DictionaryItem item : dictItems3){
				if(item.getName().equals(loan.getJiguan3())){
					tmp += item.getTitle();
					break;
				}
			}
			row.createCell((short) 4).setCellValue(tmp);
			row.createCell((short) 5).setCellValue(loan.getGlobalId());
			row.createCell((short) 6).setCellValue(loan.getAge());
			row.createCell((short) 7).setCellValue(loan.getMobile());
			row.createCell((short) 8).setCellValue(loan.getHomeAddr());
			row.createCell((short) 9).setCellValue(loan.getBussAddr());
			row.createCell((short) 10).setCellValue(loan.getBussPhone());
			row.createCell((short) 11).setCellValue(loan.getMaritalName());
			row.createCell((short) 12).setCellValue(loan.getCardNo());
			row.createCell((short) 13).setCellValue(loan.getLiushui());
			row.createCell((short) 14).setCellValue(loan.getBussType());
			
			//贷款投向
			tmp = "";
			List<DictionaryItem> dictItems_direction1 = dictionary_direction1.getItems();
			for(DictionaryItem item : dictItems_direction1){
				if(item.getName().equals(loan.getLoanDirection1())){
					tmp += item.getTitle();
					break;
				}
			}
			row.createCell((short) 15).setCellValue(tmp);
			tmp = "";
			List<DictionaryItem> dictItems_direction2 = dictionary_direction2.getItems();
			for(DictionaryItem item : dictItems_direction2){
				if(item.getName().equals(loan.getLoanDirection2())){
					tmp += item.getTitle();
					break;
				}
			}
			row.createCell((short) 16).setCellValue(tmp);
			
			row.createCell((short) 17).setCellValue(loan.getBussYear());
			row.createCell((short) 18).setCellValue(loan.getYearIncome());
			row.createCell((short) 19).setCellValue(loan.getTotalAssets());
			row.createCell((short) 20).setCellValue(loan.getQy());
			row.createCell((short) 21).setCellValue(loan.getFzbl());
			row.createCell((short) 22).setCellValue(loan.getNkzpsl());
			row.createCell((short) 23).setCellValue(loan.getPsyj());
			row.createCell((short) 24).setCellValue(loan.getLoanAmt());
			row.createCell((short) 25).setCellValue(loan.getLoanStartTerm());
			row.createCell((short) 26).setCellValue(loan.getLoanEndTerm());
			row.createCell((short) 27).setCellValue(loan.getLoanRepayTerm());
			row.createCell((short) 28).setCellValue(loan.getIsContinue()==null?"否":"是");
		}
		String fileName = "已贷款客户信息导出";
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
	
	//导入调查报告
			@ResponseBody
			@RequestMapping(value = "reportImport.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView reportImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
				filter.setRequest(request);
				QueryResult<LocalExcelForm> result = intoPiecesService.findLocalExcel(filter);
				JRadPagedQueryResult<LocalExcelForm> pagedResult = new JRadPagedQueryResult<LocalExcelForm>(filter, result);
				JRadModelAndView mv = new JRadModelAndView("/intopieces/report_import",request);
				mv.addObject(PAGED_RESULT, pagedResult);
				
				return mv;
			}
}
