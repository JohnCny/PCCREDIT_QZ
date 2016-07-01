package com.cardpay.pccredit.intopieces.web;

import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.cardpay.pccredit.QZBankInterface.service.CircleService;
import com.cardpay.pccredit.QZBankInterface.service.ECIFService;
import com.cardpay.pccredit.QZBankInterface.web.IESBForCircleForm;
import com.cardpay.pccredit.QZBankInterface.web.IESBForECIFReturnMap;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalExcelForm;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxx;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxDkjl;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxFc;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxJdc;
import com.cardpay.pccredit.intopieces.model.QzApplnDshJyd;
import com.cardpay.pccredit.intopieces.model.QzApplnJyxx;
import com.cardpay.pccredit.intopieces.model.QzApplnJydBzdb;
import com.cardpay.pccredit.intopieces.model.QzApplnJydDydb;
import com.cardpay.pccredit.intopieces.model.QzApplnJydGtjkr;
import com.cardpay.pccredit.intopieces.model.QzApplnNbscyjb;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqb;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqbJkjl;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqbZygys;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqbZykh;
import com.cardpay.pccredit.intopieces.model.QzApplnZa;
import com.cardpay.pccredit.intopieces.model.QzApplnZaReturnMap;
import com.cardpay.pccredit.intopieces.model.QzAppln_Za_Ywsqb_R;
import com.cardpay.pccredit.intopieces.service.AttachmentListService;
import com.cardpay.pccredit.intopieces.model.QzApplnJyd;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.DbrxxService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.service.JyxxService;
import com.cardpay.pccredit.intopieces.service.NbscyjbService;
import com.cardpay.pccredit.intopieces.service.SundsHelper;
import com.cardpay.pccredit.intopieces.service.YwsqbService;
import com.cardpay.pccredit.intopieces.service.ZA_YWSQB_R_Service;
import com.cardpay.pccredit.ipad.util.SundsException;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.model.ProductStuf;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.report.model.AccLoanInfo;
import com.cardpay.pccredit.report.service.AferAccLoanService;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.zainformation.service.ZAService;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.sunyard.TransEngine.exception.SunTransEngineException;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**    
* @Title: IntoPiecesApproveControl.java  
* @Package com.cardpay.pccredit.intopieces.web  
* @Description: TODO(用一句话描述该文件做什么)  
* @author tanwh    
* @date 2016年6月21日 上午11:27:01  
* @version V1.0    
*/
/**    
* @Title: IntoPiecesApproveControl.java  
* @Package com.cardpay.pccredit.intopieces.web  
* @Description: TODO(用一句话描述该文件做什么)  
* @author tanwh    
* @date 2016年6月21日 上午11:27:03  
* @version V1.0    
*/
@Controller
@RequestMapping("/intopieces/intopiecesapprove/*")
@JRadModule("intopieces.intopiecesapprove")
public class IntoPiecesApproveControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private DataAccessSqlService dataAccessSqlService;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private AferAccLoanService aferAccLoanService;
	
	@Autowired
	private CustomerInforCommDao customerinforcommDao;
	
	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	
	@Autowired
	private CircleService circleService;
	
	@Autowired
	private ECIFService eCIFService;
	
	@Autowired
	private YwsqbService ywsqbService;
	
	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	
	@Autowired
	private JyxxService jyxxService;
	
	@Autowired
	private DbrxxService dbrxxService;
	
	@Autowired
	private AttachmentListService attachmentListService;
	
	@Autowired
	private NbscyjbService nbscyjbService;
	
	@Autowired
	private ZAService zaService;
	
	@Autowired
	private ZA_YWSQB_R_Service za_ywsqb_r_service;
	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;
	
	public static final Logger logger = Logger.getLogger(IntoPiecesApproveControl.class);
	
	/**
	 * 申请页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "approve.page", method = { RequestMethod.GET })
	public AbstractModelAndView browse(@ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<IntoPieces> result = customerInforService.findCustomerInforWithEcifByFilter(filter);
		List<IntoPieces> intoPieces = result.getItems();
		for(IntoPieces pieces : intoPieces){
			if(pieces.getStatus().equals(Constant.SAVE_INTOPICES)){
				pieces.setNodeName("未提交申请");
			} else if(pieces.getStatus().equals(Constant.APPROVE_INTOPICES)||pieces.getStatus().equals(Constant.TRTURN_INTOPICES)){
				String nodeName = intoPiecesComdao.findAprroveProgress(pieces.getId());
				if(StringUtils.isNotEmpty(nodeName)){
					pieces.setNodeName(nodeName);
				} else {
					pieces.setNodeName("不在审批中");
				}
			} else if(pieces.getStatus().equals(Constant.RETURN_INTOPICES)){
				pieces.setNodeName("退回");
			} else {
				pieces.setNodeName("审批结束");
			}
		}		
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_approve",
                                                    request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 添加申请进件信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "changewh.page")
	public AbstractModelAndView changewh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/qz_customerinfor_base", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}
		return mv;
		
	}
	
	/**
	 * 执行申请
	 * @param customerInforFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save_apply.json")
	public JRadReturnMap save_apply(@ModelAttribute CustomerInforFilter customerInforFilter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("id");
				String appId = request.getParameter("appId");
				//全部修改为appId识别
				//检查相关的表是否填写
				//添加产品类型appId
				QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = za_ywsqb_r_service.findByAppId(appId);
				if(qzappln_za_ywsqb_r==null){
					returnMap.put(JRadConstants.MESSAGE, "请先\"选择产品\"");
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}
				
				//添加业务申请表appId
				QzApplnYwsqb qzApplnYwsqb = ywsqbService.findYwsqbByAppId(appId);
				if(qzApplnYwsqb==null){
					returnMap.put(JRadConstants.MESSAGE, "请先填写\"贷款申请表\"");
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}
				
				//添加担保人appId
				/*List<QzApplnDbrxx> dbrxx_ls = dbrxxService.findDbrxx(customerId, null);
				if(dbrxx_ls == null || dbrxx_ls.size() == 0){
					returnMap.put(JRadConstants.MESSAGE, "请先填写\"担保人信息表\"");
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}*/
				/**
				 *判断，如果是安居贷,贷生活10万以下，以下页签不用填写 
				 */
				String productType = qzappln_za_ywsqb_r.getProductType();//产品类型（3:安居贷）
				if(!"3".equals(productType) && !"5".equals(productType)){
					//添加附件appId
					QzApplnAttachmentList qzApplnAttachmentList = attachmentListService.findAttachmentListByAppId(appId);
					if(qzApplnAttachmentList == null){
						returnMap.put(JRadConstants.MESSAGE, "请先填写\"待决策资料清单\"");
						returnMap.put(JRadConstants.SUCCESS, false);
						return returnMap;
					}
				
					//添加内部审查appId
					QzApplnNbscyjb qzApplnNbscyjb = nbscyjbService.findNbscyjbByAppId(appId);
					if(qzApplnNbscyjb == null){
						returnMap.put(JRadConstants.MESSAGE, "请先填写\"内部审查意见表\"");
						returnMap.put(JRadConstants.SUCCESS, false);
						return returnMap;
					}
				
					QzApplnJyd jyd = intoPiecesService.getSdhjydFormAfter(appId);
					if(jyd==null){
						returnMap.put(JRadConstants.MESSAGE, "请先填写\"审贷会决议单\"");
						returnMap.put(JRadConstants.SUCCESS, false);
						return returnMap;
					}
				}
				
				if("5".equals(productType)){
					QzApplnDshJyd dshJyd = intoPiecesService.findDsh10JydByAppId(appId);
					if(dshJyd == null){
						returnMap.put(JRadConstants.MESSAGE, "请先填写\"决议单\"");
						returnMap.put(JRadConstants.SUCCESS, false);
						return returnMap;
					}
				}
				
				Circle circle = circleService.findCircleByAppId(appId);
				if(circle == null){
					returnMap.put(JRadConstants.MESSAGE, "请先填写\"贷款信息\"");
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}
				
				//设置流程开始
				saveApply(customerId,appId,qzappln_za_ywsqb_r.getProductType());
				
				returnMap.put(RECORD_ID, customerId);
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

	/**
	 * 提交申请，开始流程
	 * @param customer_id
	 */
	public void saveApply(String customer_id, String application_id, String type){
		//查找默认产品
		ProductFilter filter = new ProductFilter();
		filter.setDefault_type(type);
		ProductAttribute productAttribute = productService.findProductsByFilter(filter).getItems().get(0);
		//先判断是否为其他岗位退件的客户，如果是，只需改变状态不需再次新增申请件
		CustomerApplicationInfo info = intoPiecesService.ifReturnToApproveByappId(application_id);
		String appId = "";
		if(info!=null && info.getProductId().equalsIgnoreCase(productAttribute.getId())){//未修改产品 走原来审批 流程 
			info.setStatus(Constant.APPROVE_INTOPICES);
			
			commonDao.updateObject(info);
			appId = info.getId();
			//新增修改不是初审退下来或者更换了 产品类型 的进件，流程信息要改为初审，不然进件新增一个申请
		}else{
			//新申请没有开始流程状态为暂保存
			CustomerApplicationInfo cusInfo = intoPiecesService.ifReturnToApproveByappIdForNew(application_id);
			if(cusInfo!=null){
				cusInfo.setStatus(Constant.APPROVE_INTOPICES);
				
				commonDao.updateObject(cusInfo);
				appId = cusInfo.getId();
			}
//			这一步由创建申请做新增申请记录
////			//设置申请
//			CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
//			//customerApplicationInfo.setStatus(status);
//			customerApplicationInfo.setId(IDGenerator.generateID());
//			customerApplicationInfo.setApplyQuota("0");//设置额度
//			customerApplicationInfo.setCustomerId(customer_id);
//			if(customerApplicationInfo.getApplyQuota()!=null){
//				customerApplicationInfo.setApplyQuota((Integer.valueOf(customerApplicationInfo.getApplyQuota())*100)+"");
//			}
//			customerApplicationInfo.setCreatedTime(new Date());
//			customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
//			customerApplicationInfo.setProductId(productAttribute.getId());
//			
//			commonDao.insertObject(customerApplicationInfo);
//			
			
			//添加申请件流程
			WfProcessInfo wf=new WfProcessInfo();
			wf.setProcessType(WfProcessInfoType.process_type);
			wf.setVersion("1");
			commonDao.insertObject(wf);
			List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(),productAttribute.getId());
			boolean startBool=false;
			boolean endBool=false;
			//节点id和WfStatusInfo id的映射
			Map<String, String> nodeWfStatusMap = new HashMap<String, String>();
			for(NodeAudit nodeAudit:list){
				if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
					startBool=true;
				}
				
				if(startBool&&!endBool){
					WfStatusInfo wfStatusInfo=new WfStatusInfo();
					wfStatusInfo.setIsStart(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())?"1":"0");
					wfStatusInfo.setIsClosed(nodeAudit.getIsend().equals(YesNoEnum.YES.name())?"1":"0");
					wfStatusInfo.setRelationedProcess(wf.getId());
					wfStatusInfo.setStatusName(nodeAudit.getNodeName());
					wfStatusInfo.setStatusCode(nodeAudit.getId());
					commonDao.insertObject(wfStatusInfo);
					
					nodeWfStatusMap.put(nodeAudit.getId(), wfStatusInfo.getId());
					
					if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
						//添加初始审核
						CustomerApplicationProcess customerApplicationProcess=new CustomerApplicationProcess();
						String serialNumber = processService.start(wf.getId());
						customerApplicationProcess.setSerialNumber(serialNumber);
						customerApplicationProcess.setNextNodeId(nodeAudit.getId()); 
						customerApplicationProcess.setApplicationId(appId);
						commonDao.insertObject(customerApplicationProcess);
						
						CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
						applicationInfo.setSerialNumber(serialNumber);
						commonDao.updateObject(applicationInfo);
					}
				}
				
				if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
					endBool=true;
				}
			}
			//节点关系
			List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(), productAttribute.getId());
			for(NodeControl control : nodeControls){
				WfStatusResult wfStatusResult = new WfStatusResult();
				wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
				wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
				wfStatusResult.setExamineResult(control.getCurrentStatus());
				commonDao.insertObject(wfStatusResult);
			}
			
		}
		//对之前无appId的表添加id(尤其是调查内容记录表添加appId)。修改后不用添加（要删除）页签什么时候增加，什么时候匹配
//		intoPiecesService.addAppId(customer_id,appId);
	}
	
	//iframe
	@ResponseBody
	@RequestMapping(value = "iframe.page")
	public AbstractModelAndView iframe(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		//判断该进件申请是什么产品
		CustomerApplicationInfo appInfo = intoPiecesComdao.findCustomerApplicationInfoById(appId);
		//根据产品类型调整不同的的页签（产品类型3表示安居贷）
		ProductAttribute productAttribute = commonDao.findObjectById(ProductAttribute.class, appInfo.getProductId());
		if("3".equals(productAttribute.getDefaultType())){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_create", request);
		}
		if("5".equals(productAttribute.getDefaultType())){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_create_dsh_10", request);
		}
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}
		mv.addObject("appId",appId);
		return mv;
	}
	
	//page0
	@ResponseBody
	@RequestMapping(value = "page0.page")
	public AbstractModelAndView page0(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page0", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		//String appId = request.getParameter("appId");
		//修改为由applicationId唯一识别一笔进件
		QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = za_ywsqb_r_service.findByAppId(appId);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}
		mv.addObject("qzappln_za_ywsqb_r", qzappln_za_ywsqb_r);
		//查找专案信息
		List<QzApplnZaReturnMap> za_ls = zaService.findZasApproved();
		mv.addObject("za_ls", za_ls);
		mv.addObject("za_ls_json", JSONArray.fromObject(za_ls).toString());
		//查找已配置的专案信息
		if(qzappln_za_ywsqb_r != null && qzappln_za_ywsqb_r.getProductType() != null && 
				(qzappln_za_ywsqb_r.getProductType().equals("2") || qzappln_za_ywsqb_r.getProductType().equals("5"))){
			QzApplnZa qzApplnZa = zaService.findZaById(qzappln_za_ywsqb_r.getZaId());
			mv.addObject("qzApplnZa", qzApplnZa);
		}
		return mv;
	}
	
	//insert_page0
	@ResponseBody
	@RequestMapping(value = "insert_page0.json")
	public JRadReturnMap insert_page0(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				//String customerId = request.getParameter("customerId");
				String productType = request.getParameter("productType");
				String zaId = request.getParameter("zaId");
				
				//修改为新增一跳客户申请记录，产品表关联到该记录
				//查找默认产品
				ProductFilter filter = new ProductFilter();
				filter.setDefault_type(productType);
				ProductAttribute productAttribute = productService.findProductsByFilter(filter).getItems().get(0);
				//设置申请
				CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
				//customerApplicationInfo.setStatus(status);
				customerApplicationInfo.setId(IDGenerator.generateID());
				customerApplicationInfo.setApplyQuota("0");//设置额度
				//customerApplicationInfo.setCustomerId(customerId);
				if(customerApplicationInfo.getApplyQuota()!=null){
					customerApplicationInfo.setApplyQuota((Integer.valueOf(customerApplicationInfo.getApplyQuota())*100)+"");
				}
				customerApplicationInfo.setCreatedTime(new Date());
				customerApplicationInfo.setStatus(Constant.SAVE_INTOPICES);
				customerApplicationInfo.setProductId(productAttribute.getId());
				
				commonDao.insertObject(customerApplicationInfo);
				//新增产品专案
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = za_ywsqb_r_service.findByCustomerId(customerId);
				//if(qzappln_za_ywsqb_r == null){
				QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = new QzAppln_Za_Ywsqb_R();
					//未填申请时 关联客户id
					//qzappln_za_ywsqb_r.setCustomerId(customerId);
					qzappln_za_ywsqb_r.setProductType(productType);
					qzappln_za_ywsqb_r.setZaId(zaId);
					qzappln_za_ywsqb_r.setApplicationId(customerApplicationInfo.getId());
					ywsqbService.insert_page0(qzappln_za_ywsqb_r);
				//}else{
				//	qzappln_za_ywsqb_r.setProductType(productType);
				//	qzappln_za_ywsqb_r.setZaId(zaId);
				//	ywsqbService.update_page0(qzappln_za_ywsqb_r);
				//}
				
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
				//returnMap.put("customerid", customerId);
				returnMap.put("appId", customerApplicationInfo.getId());
				returnMap.put("productId", productAttribute.getId());
				returnMap.put("zaId", zaId);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//page1
	@ResponseBody
	@RequestMapping(value = "page1.page")
	public AbstractModelAndView page1(HttpServletRequest request) {
		
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		String ifHideUser = RequestHelper.getStringValue(request, "ifHideUser");
		//查询产品信息
		QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = null;
		if(appId != null && !appId.equals("")){
			qzappln_za_ywsqb_r = za_ywsqb_r_service.findByAppId(appId);
		}else{
			qzappln_za_ywsqb_r = za_ywsqb_r_service.findByCustomerId(customerInforId);
		}
		//查找page1信息
		QzApplnYwsqb qzApplnYwsqb = null;
		if(appId != null && !appId.equals("")){
			qzApplnYwsqb = ywsqbService.findYwsqbByAppId(appId);
		}
		else{
			qzApplnYwsqb = ywsqbService.findYwsqb(customerInforId, null);
		}
		
		QzApplnJyxx qzApplnJyxx = jyxxService.findJyxx(customerInforId, null);
		
		JRadModelAndView mv = null;
		if(qzApplnYwsqb != null){
			//获取产品类型，是贷生活或者安居贷就调整至page1ForLife_change
			if(qzappln_za_ywsqb_r != null){
				if("2".equals(qzappln_za_ywsqb_r.getProductType()) || "3".equals(qzappln_za_ywsqb_r.getProductType()) || "5".equals(qzappln_za_ywsqb_r.getProductType())){
					//获取专案信息
					QzApplnZa qzApplnZa = zaService.findZaById(qzappln_za_ywsqb_r.getZaId());
					mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1ForLife_change", request);
					mv.addObject("qzApplnZa", qzApplnZa);
				}else{
					mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1_change", request);
				}
			}else{
				mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1_change", request);
			}
			List<QzApplnYwsqbZygys> zygys_ls = ywsqbService.findYwsqbZygys(qzApplnYwsqb.getId());
			List<QzApplnYwsqbZykh> zykh_ls = ywsqbService.findYwsqbZykh(qzApplnYwsqb.getId());
			List<QzApplnYwsqbJkjl> jkjl_ls = ywsqbService.findYwsqbJkjl(qzApplnYwsqb.getId());
			mv.addObject("qzApplnYwsqb", qzApplnYwsqb);
			mv.addObject("zygys_ls", zygys_ls);
			mv.addObject("zykh_ls", zykh_ls);
			mv.addObject("jkjl_ls", jkjl_ls);
			mv.addObject("type", type);
		}
		else{
			//获取产品类型，是贷生活就调整至page1ForLife
			if(qzappln_za_ywsqb_r != null){
				if("2".equals(qzappln_za_ywsqb_r.getProductType()) || "3".equals(qzappln_za_ywsqb_r.getProductType()) || "5".equals(qzappln_za_ywsqb_r.getProductType())){
					//获取专案信息
					QzApplnZa qzApplnZa = zaService.findZaById(qzappln_za_ywsqb_r.getZaId());
					mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1ForLife", request);
					mv.addObject("qzApplnZa", qzApplnZa);
				}else{
					mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1", request);
				}
			}else{
				mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1", request);
			}
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			mv.addObject("orgName",user.getOrganization().getName());
			mv.addObject("orgId",user.getOrganization().getId());
			String externalId = user.getLogin();//工号
			mv.addObject("externalId",externalId);
			mv.addObject("userName",user.getDisplayName());
			
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("customerId", customerInfor.getId());
			}
			//查找开户信息 自动填充
			ECIF ecif = eCIFService.findEcifByCustomerId(customerInforId);
			mv.addObject("ecif", ecif);
			mv.addObject("type", type);
		}
		mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		mv.addObject("ifHideUser", ifHideUser);
		mv.addObject("qzApplnJyxx", qzApplnJyxx);
		if(qzappln_za_ywsqb_r != null){
			mv.addObject("productType", qzappln_za_ywsqb_r.getProductType());
		}
		mv.addObject("appId", appId);
		return mv;
	}
	
	//insert_page1
	@ResponseBody
	@RequestMapping(value = "insert_page1.json")
	public JRadReturnMap insert_page1(@ModelAttribute QzApplnYwsqbForm qzApplnYwsqbForm, HttpServletRequest request) throws Exception {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				QzApplnYwsqb qzApplnYwsqb = qzApplnYwsqbForm.createModel(QzApplnYwsqb.class);
				QzApplnJyxx qzApplnJyxx = qzApplnYwsqbForm.createModelJyxx();
				ywsqbService.dealWithNullValueJyxx(qzApplnJyxx);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnYwsqb.setCreatedBy(user.getId());
				qzApplnYwsqb.setCreatedTime(new Date());
				//未填申请时 关联客户id
				qzApplnYwsqb.setCustomerId(customerId);
				qzApplnYwsqb.setApplicationId(appId);
				
				ywsqbService.insert_page1(qzApplnYwsqb, qzApplnJyxx,request);
				//returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//update_page1
	@ResponseBody
	@RequestMapping(value = "update_page1.json")
	public JRadReturnMap update_page1(@ModelAttribute QzApplnYwsqbForm qzApplnYwsqbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String ywsqbId = request.getParameter("id");
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				QzApplnYwsqb qzApplnYwsqb = qzApplnYwsqbForm.createModel(QzApplnYwsqb.class);
				qzApplnYwsqb.setCustomerId(customerId);
				ywsqbService.dealWithNullValue(qzApplnYwsqb);
				QzApplnJyxx qzApplnJyxx = qzApplnYwsqbForm.createModelJyxx();
				qzApplnJyxx.setApplicationId(appId);
				ywsqbService.dealWithNullValueJyxx(qzApplnJyxx);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//未填申请时 关联客户id
				qzApplnYwsqb.setId(ywsqbId);
				ywsqbService.update_page1(qzApplnYwsqb,qzApplnJyxx,request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	//insert_page1ForLife
	@ResponseBody
	@RequestMapping(value = "insert_page1ForLife.json")
	public JRadReturnMap insert_page1ForLife(@ModelAttribute QzApplnYwsqbForm qzApplnYwsqbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				//String bussAddDedail = qzApplnYwsqbForm.getBussdisAddDetail();
				String bussDistrictAdd = qzApplnYwsqbForm.getBussdistrictAddress();
				qzApplnYwsqbForm.setBussdistrictAddress(bussDistrictAdd);
				QzApplnYwsqb qzApplnYwsqb = qzApplnYwsqbForm.createModel(QzApplnYwsqb.class);
//				QzApplnJyxx qzApplnJyxx = qzApplnYwsqbForm.createModelJyxx();
//				ywsqbService.dealWithNullValueJyxx(qzApplnJyxx);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnYwsqb.setCreatedBy(user.getId());
				qzApplnYwsqb.setCreatedTime(new Date());
				//关联申请id
				qzApplnYwsqb.setApplicationId(appId);
				//未填申请时 关联客户id
				qzApplnYwsqb.setCustomerId(customerId);
					
//				qzApplnJyxx.setCreatedBy(user.getId());
//				qzApplnJyxx.setCreatedTime(new Date());
				//未填申请时 关联客户id
//				qzApplnJyxx.setCustomerId(customerId);
					
				ywsqbService.insert_page1ForLife(qzApplnYwsqb,request);
				//returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
		}
		//update_page1ForLife("贷生活")
		@ResponseBody
		@RequestMapping(value = "update_page1ForLife.json")
		public JRadReturnMap update_page1ForLife(@ModelAttribute QzApplnYwsqbForm qzApplnYwsqbForm, HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			if (returnMap.isSuccess()) {
				try {
					String ywsqbId = request.getParameter("id");
					String customerId = request.getParameter("customerId");
					QzApplnYwsqb qzApplnYwsqb = qzApplnYwsqbForm.createModel(QzApplnYwsqb.class);
					qzApplnYwsqb.setCustomerId(customerId);
					//ywsqbService.dealWithNullValue(qzApplnYwsqb);
					
					//判断空值
					//证件类型
					if(!qzApplnYwsqb.getGlobalType().equals("026")){
						qzApplnYwsqb.setGlobalTypeOther("");
					}
					//教育水平
					if(!qzApplnYwsqb.getEducationLevel().equals("000")){
						qzApplnYwsqb.setEducationLevelOther("");
					}
					//婚姻状况
					if(!qzApplnYwsqb.getMaritalStatus().equals("20")){
						qzApplnYwsqb.setMaritalName("");
						qzApplnYwsqb.setMaritalGlobalType("100");
						qzApplnYwsqb.setMaritalGlobalTypeOther("");
						qzApplnYwsqb.setMaritalGlobalId("");
						qzApplnYwsqb.setMaritalWorkunit("");
						qzApplnYwsqb.setMaritalPhone("");
					}
					if(!qzApplnYwsqb.getMaritalStatus().equals("90")){
						qzApplnYwsqb.setMaritalStatusOther("");
					}
					//配偶证件类型
					if(qzApplnYwsqb.getMaritalGlobalType() == null || !qzApplnYwsqb.getMaritalGlobalType().equals("026")){
						qzApplnYwsqb.setMaritalGlobalTypeOther("");
					}
					//家庭住址类型
					if(!qzApplnYwsqb.getAddressType().equals("5")){
						qzApplnYwsqb.setAddressTypeOther("");
					}
					//是否曾在泉州银行申请过贷款
					if(!qzApplnYwsqb.getHaveApplyLoan().equals("1")){
						qzApplnYwsqb.setHaveLoanTime(null);
					}
					//是否曾使用泉州银行电子银行产品
					if(!qzApplnYwsqb.getHaveElePro().equals("1")){
						qzApplnYwsqb.setHaveEleProType("");
					}
					//有关联的个人或实体是否获得过泉州银行贷款
					if(!qzApplnYwsqb.getHaveGotLoan().equals("1")){
						qzApplnYwsqb.setHaveGotLoanName("");
						qzApplnYwsqb.setHaveGotLoanRelation("");
					}
					//信息渠道
					if(!qzApplnYwsqb.getInfoType().equals("8")){
						qzApplnYwsqb.setInfoTypeOther("");
					}
//					QzApplnJyxx qzApplnJyxx = qzApplnYwsqbForm.createModelJyxx();
//					ywsqbService.dealWithNullValueJyxx(qzApplnJyxx);
					User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
					//未填申请时 关联客户id
					qzApplnYwsqb.setId(ywsqbId);
					ywsqbService.update_page1ForLife(qzApplnYwsqb,request);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
					returnMap.setSuccess(true);
				}catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
			}else{
				returnMap.setSuccess(false);
				returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
			}
			return returnMap;
		}
	//page4_list
	@ResponseBody
	@RequestMapping(value = "page4_list.page")
	public AbstractModelAndView page4_list(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page4_list", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		
		List<QzApplnDbrxx> dbrxx_ls = null;
		if(appId != null && !appId.equals("")){
			dbrxx_ls = dbrxxService.findDbrxxByAppId(appId);
		}else{
			dbrxx_ls = dbrxxService.findDbrxx(customerInforId, null);
		}
		
		mv.addObject("dbrxx_ls", dbrxx_ls);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		mv.addObject("appId",appId);
		return mv;
	}
	
	//page4
	@ResponseBody
	@RequestMapping(value = "page4.page")
	public AbstractModelAndView page4(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page4", request);
		String customerInforId = RequestHelper.getStringValue(request, "customerId");
		String appId = RequestHelper.getStringValue(request, "appId");
		
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			mv.addObject("appId", appId);
		}
		return mv;
	}
		
	//insert_page4
	@ResponseBody
	@RequestMapping(value = "insert_page4.json")
	public JRadReturnMap insert_page4(@ModelAttribute QzApplnDbrxxForm qzApplnDbrxxForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				QzApplnDbrxx qzApplnDbrxx = qzApplnDbrxxForm.createModel(QzApplnDbrxx.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnDbrxx.setCreatedBy(user.getId());
				qzApplnDbrxx.setCreatedTime(new Date());
				//未填申请时 关联客户id
				qzApplnDbrxx.setCustomerId(customerId);
				qzApplnDbrxx.setApplicationId(appId);
				dbrxxService.insert_page4(qzApplnDbrxx, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//update_page4
	@ResponseBody
	@RequestMapping(value = "update_page4.page")
	public AbstractModelAndView update_page4(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page4_change", request);
		String id = RequestHelper.getStringValue(request, ID);

		String type = RequestHelper.getStringValue(request, "type");
		//查找page4信息
		QzApplnDbrxx qzApplnDbrxx = dbrxxService.findDbrxxById(id);
		List<QzApplnDbrxxDkjl> dkjl_ls = dbrxxService.findDbrxxDkjl(qzApplnDbrxx.getId());
		List<QzApplnDbrxxFc> fc_ls = dbrxxService.findDbrxxFc(qzApplnDbrxx.getId());
		List<QzApplnDbrxxJdc> jdc_ls = dbrxxService.findDbrxxJdc(qzApplnDbrxx.getId());
		mv.addObject("qzApplnDbrxx", qzApplnDbrxx);
		mv.addObject("dkjl_ls", dkjl_ls);
		mv.addObject("fc_ls", fc_ls);
		mv.addObject("jdc_ls", jdc_ls);

		mv.addObject("type", type);
		return mv;
	}
		
	//update_page4
	@ResponseBody
	@RequestMapping(value = "update_page4.json")
	public JRadReturnMap update_page4(@ModelAttribute QzApplnDbrxxForm qzApplnDbrxxForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String dbrxxId = request.getParameter("id");
				QzApplnDbrxx qzApplnDbrxx = qzApplnDbrxxForm.createModel(QzApplnDbrxx.class);
				dbrxxService.dealWithNullValue(qzApplnDbrxx);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//未填申请时 关联客户id
				qzApplnDbrxx.setId(dbrxxId);
				dbrxxService.update_page4(qzApplnDbrxx, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	//del_page4
	@ResponseBody
	@RequestMapping(value = "del_page4.json")
	public JRadReturnMap del_page4(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String dbrxxId = request.getParameter("id");
				dbrxxService.deleteDbrxx(dbrxxId);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	/*可配置的资料清单	
	//page5
	@ResponseBody
	@RequestMapping(value = "page5.page")
	public ModelAndView page5(HttpServletRequest request) {
		JRadModelAndView mv = null;
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		if(appId==null){
			appId="";
		}
		//查找page5信息
		QzApplnAttachmentList qzApplnAttachmentList = null;
		if(appId != null && !appId.equals("")){
			qzApplnAttachmentList = attachmentListService.findAttachmentListByAppId(appId);
		}
		else{
			qzApplnAttachmentList = attachmentListService.findAttachmentList(customerInforId, null);
		}
		
		if(qzApplnAttachmentList == null){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page5", request);
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("appId", appId);
				mv.addObject("type", type);
				mv.addObject("customerId", customerInfor.getId());
			}
			
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String externalId = user.getLogin();//工号
			mv.addObject("externalId",externalId);
			mv.addObject("userName",user.getDisplayName());
		}
		else{
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page5_change", request);
			mv.addObject("qzApplnAttachmentList", qzApplnAttachmentList);
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		
		//查找客户信息和经营信息
		CustomerInfor customerInfo = customerInforService.findCustomerInforById(customerInforId);
		mv.addObject("customerInfo", customerInfo);
		QzApplnJyxx qzApplnJyxx = jyxxService.findJyxx(customerInforId, null);
		mv.addObject("qzApplnJyxx", qzApplnJyxx);
		mv.addObject("appId", appId);
		
		//新的附件清单页面
		if(qzApplnAttachmentList == null){//如果新申请，未勾选
			CustomerApplicationInfo app = customerApplicationInfoService.findById(appId);
			List<ProductStuf> stufs = productService.findStufByProductId(app.getProductId());
			if(stufs != null && stufs.size()>0){//并且已配置新的附件清单
				//置chk_value_new
				BigInteger chk_value_new = new BigInteger("0");
				for(ProductStuf stuf : stufs){
					chk_value_new = chk_value_new.add(new BigInteger(stuf.getStufCode()));
				}
				//默认生成att
				QzApplnAttachmentList tmp = new QzApplnAttachmentList();
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				tmp.setCreatedBy(user.getId());
				tmp.setCreatedTime(new Date());
				tmp.setDocid(DateHelper.getDateFormat(new Date(), "yyyyMMddHHmmssSSS"));
				//未填申请时 关联客户id
				tmp.setCustomerId(app.getCustomerId());
				tmp.setApplicationId(appId);
				tmp.setChkValueNew(chk_value_new.toString());
				
				attachmentListService.insertAtt(tmp);
				//重定向到上传界面
				return new ModelAndView("redirect:/intopieces/intopiecesapprove/sunds_ocx.page?appId="+appId);
			}
		}
		else{
			if(qzApplnAttachmentList.getChkValue() == null){
				qzApplnAttachmentList = attachmentListService.findAttachmentListByAppId(appId);
				
				CustomerApplicationInfo app = customerApplicationInfoService.findById(appId);
				List<ProductStuf> stufs = productService.findStufByProductId(app.getProductId());
				if(stufs != null && stufs.size()>0){//并且已配置新的附件清单
					//置chk_value_new
					BigInteger chk_value_new = new BigInteger("0");
					for(ProductStuf stuf : stufs){
						chk_value_new = chk_value_new.add(new BigInteger(stuf.getStufCode()));
					}
					qzApplnAttachmentList.setChkValueNew(chk_value_new.toString());//更新新的配置清单值
					attachmentListService.updateAtt(qzApplnAttachmentList);
				}
			}
		}
		
		return mv;
	}
	*/
	//page5
	@ResponseBody
	@RequestMapping(value = "page5.page")
	public AbstractModelAndView page5(HttpServletRequest request) {
		JRadModelAndView mv = null;
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		if(appId==null){
			appId="";
		}
		//查找page5信息
		QzApplnAttachmentList qzApplnAttachmentList = null;
		if(appId != null && !appId.equals("")){
			qzApplnAttachmentList = attachmentListService.findAttachmentListByAppId(appId);
		}
		else{
			qzApplnAttachmentList = attachmentListService.findAttachmentList(customerInforId, null);
		}
		
		if(qzApplnAttachmentList == null){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page5", request);
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("appId", appId);
				mv.addObject("type", type);
				mv.addObject("customerId", customerInfor.getId());
			}
			
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String externalId = user.getLogin();//工号
			mv.addObject("externalId",externalId);
			mv.addObject("userName",user.getDisplayName());
		}
		else{
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page5_change", request);
			mv.addObject("qzApplnAttachmentList", qzApplnAttachmentList);
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		
		//查找客户信息和经营信息
		CustomerInfor customerInfo = customerInforService.findCustomerInforById(customerInforId);
		mv.addObject("customerInfo", customerInfo);
		QzApplnJyxx qzApplnJyxx = jyxxService.findJyxx(customerInforId, null);
		mv.addObject("qzApplnJyxx", qzApplnJyxx);
		mv.addObject("appId", appId);
		return mv;
	}
		
	//insert_page5
	@ResponseBody
	@RequestMapping(value = "insert_page5.json")
	public JRadReturnMap insert_page5(@ModelAttribute QzApplnAttachmentListForm qzApplnAttachmentListForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				QzApplnAttachmentList qzApplnAttachmentList = qzApplnAttachmentListForm.createModel(QzApplnAttachmentList.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnAttachmentList.setCreatedBy(user.getId());
				qzApplnAttachmentList.setCreatedTime(new Date());
				qzApplnAttachmentList.setDocid(DateHelper.getDateFormat(qzApplnAttachmentList.getCreatedTime(), "yyyyMMddHHmmssSSS"));
				qzApplnAttachmentList.setUploadValue("0");
				if(qzApplnAttachmentList.getBussType().equals("4")){//新版
					qzApplnAttachmentList.setShopId(qzApplnAttachmentListForm.getShopIdNew());
					qzApplnAttachmentList.setShopName(qzApplnAttachmentListForm.getShopNameNew());
				}
				//未填申请时 关联客户id
				qzApplnAttachmentList.setCustomerId(customerId);
				qzApplnAttachmentList.setApplicationId(appId);
				attachmentListService.insert_page5(qzApplnAttachmentList, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	//update_page5
	@ResponseBody
	@RequestMapping(value = "update_page5.json")
	public JRadReturnMap update_page5(@ModelAttribute QzApplnAttachmentListForm qzApplnAttachmentListForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String id = request.getParameter("id");
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				QzApplnAttachmentList qzApplnAttachmentList = qzApplnAttachmentListForm.createModel(QzApplnAttachmentList.class);
				//未填申请时 关联客户id
				qzApplnAttachmentList.setId(id);
				qzApplnAttachmentList.setCustomerId(customerId);
				qzApplnAttachmentList.setApplicationId(appId);
				attachmentListService.update_page5(qzApplnAttachmentList, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//page7
	@ResponseBody
	@RequestMapping(value = "page7.page")
	public AbstractModelAndView page7(HttpServletRequest request) {
		JRadModelAndView mv = null;
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		if(appId==null){
			appId="";
		}
		QzApplnNbscyjb qzApplnNbscyjb = null;
		if(appId != null && !appId.equals("")){
			qzApplnNbscyjb = nbscyjbService.findNbscyjbByAppId(appId);
		}else{
			qzApplnNbscyjb = nbscyjbService.findNbscyjb(customerInforId, null);
		}
		
		if(qzApplnNbscyjb == null){
			 mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page7", request);
			
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("customerId", customerInfor.getId());
				mv.addObject("appId", appId);
				mv.addObject("type", type);
			}
		}
		else{
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page7_change", request);
			mv.addObject("qzApplnNbscyjb", qzApplnNbscyjb);
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		
		//查找客户信息和经营信息
		CustomerInfor customerInfo = customerInforService.findCustomerInforById(customerInforId);
		mv.addObject("customerInfo", customerInfo);
		QzApplnJyxx qzApplnJyxx = jyxxService.findJyxx(customerInforId, null);
		mv.addObject("qzApplnJyxx", qzApplnJyxx);
		mv.addObject("appId", appId);		
		return mv;
	}
	
	//insert_page7
	@ResponseBody
	@RequestMapping(value = "insert_page7.json")
	public JRadReturnMap insert_page5(@ModelAttribute QzApplnNbscyjbForm qzApplnNbscyjbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				String appId = RequestHelper.getStringValue(request, "appId");
				QzApplnNbscyjb qzApplnNbscyjb = qzApplnNbscyjbForm.createModel(QzApplnNbscyjb.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnNbscyjb.setCreatedBy(user.getId());
				qzApplnNbscyjb.setCreatedTime(new Date());
				String shopName = qzApplnNbscyjb.getShopName();
				//未填申请时 关联客户id
				qzApplnNbscyjb.setCustomerId(customerId);
				qzApplnNbscyjb.setApplicationId(appId);
				nbscyjbService.insert_page7(qzApplnNbscyjb, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//update_page7
	@ResponseBody
	@RequestMapping(value = "update_page7.json")
	public JRadReturnMap update_page7(@ModelAttribute QzApplnNbscyjbForm qzApplnNbscyjbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String id = request.getParameter("id");
				QzApplnNbscyjb qzApplnNbscyjb = qzApplnNbscyjbForm.createModel(QzApplnNbscyjb.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnNbscyjb.setId(id);
				String shopName = qzApplnNbscyjb.getShopName();
				nbscyjbService.update_page7(qzApplnNbscyjb, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//page8
	@ResponseBody
	@RequestMapping(value = "page8.page")
	public AbstractModelAndView page8(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page8", request);
		String customerId = RequestHelper.getStringValue(request, ID);
		
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		String flag = RequestHelper.getStringValue(request, "flag");
		//作为审批后修改标志
		if(appId==null){
			appId="";
		}
		
		//获取内部审查意见表信息
		QzApplnNbscyjb qzApplnNbscyjb = null;
		if(appId != null && !appId.equals("")){
			qzApplnNbscyjb = nbscyjbService.findNbscyjbByAppId(appId);
		}else{
			qzApplnNbscyjb = nbscyjbService.findNbscyjb(customerId, null);
		}
		
		QzApplnJyd qzSdhjyd = new QzApplnJyd();
		List<QzApplnJydGtjkr> gtjkrs = new ArrayList<QzApplnJydGtjkr>();
		List<QzApplnJydBzdb> bzdbs = new ArrayList<QzApplnJydBzdb>();
		List<QzApplnJydDydb> dydbs = new ArrayList<QzApplnJydDydb>();
		if (StringUtils.isNotEmpty(appId)) {
			qzSdhjyd = intoPiecesService.getSdhjydFormAfter(appId);
		}else{
			qzSdhjyd = intoPiecesService.getSdhjydForm(customerId);
		}
		if(qzSdhjyd!=null){
			//获取共同借款人list
			gtjkrs = intoPiecesService.getJkrList(qzSdhjyd.getId());
			//获取保证担保list
			bzdbs = intoPiecesService.getBzdbList(qzSdhjyd.getId());
			//获取抵押担保list
			dydbs = intoPiecesService.getDydbList(qzSdhjyd.getId());
		}
		mv.addObject("customerId", customerId);
		mv.addObject("appId", appId);
		mv.addObject("type", type);
		mv.addObject("result", qzSdhjyd);
		mv.addObject("qzApplnNbscyjb", qzApplnNbscyjb);
		//查找开户信息 自动填充
		ECIF ecif = eCIFService.findEcifByCustomerId(customerId);
		mv.addObject("ecif", ecif);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		mv.addObject("orgName",user.getOrganization().getName());
		mv.addObject("userName",user.getDisplayName());
		mv.addObject("gtjkrs", gtjkrs);
		mv.addObject("bzdbs", bzdbs);
		mv.addObject("dydbs", dydbs);
		mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		mv.addObject("operate",operate);
		mv.addObject("flag",flag);
		return mv;
	}
	
	/**
	 * 审贷会决议单保存(申请前)
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "page8insert.json")
	public JRadReturnMap insert(@ModelAttribute QzSdhjydForm qzSdhjydForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = RequestHelper.getStringValue(request, ID);
				String appId = RequestHelper.getStringValue(request, "appId");
				String flag = RequestHelper.getStringValue(request, "flag");
				QzApplnJyd qzSdhjyd = qzSdhjydForm.createModel(QzApplnJyd.class);
				qzSdhjyd.setCustomerId(customerId);
				qzSdhjyd.setCreatedTime(new Date());
				qzSdhjyd.setApplicationId(appId);
				if(StringUtils.isNotEmpty(appId) && !flag.equals("1")){
					intoPiecesService.insertSdhjydFormAfter(qzSdhjyd);
				}else{
					intoPiecesService.insertSdhjydForm(qzSdhjyd,request);
				}
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
	
	/**
	 * 进入补充上会记录页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add_information.page", method = { RequestMethod.GET })
	public AbstractModelAndView reject(@ModelAttribute CustomerApplicationProcessFilter filter, HttpServletRequest request) throws SQLException {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.shouxinAddInforForm(filter);
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/intopieces_wait/intopiecesApprove_shouxin_add_infor", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 客户经理补充上会提交
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pass.json")
	public JRadReturnMap pass(HttpServletRequest request) throws SQLException {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String customerId = request.getParameter("customerId");
			//更新客户信息状态
			CustomerInfor infor = commonDao.findObjectById(CustomerInfor.class, customerId);
			infor.setProcessId("");
			commonDao.updateObject(infor);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	
	//iframe_approve(申请后)
	@ResponseBody
	@RequestMapping(value = "iframe_approve.page")
	public AbstractModelAndView iframeApprove(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_approve", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			mv.addObject("appId", appId);
			mv.addObject("operate", Constant.status_buchong);
		}
		return mv;
	}
	/*可配置的资料清单
	//影像
	@ResponseBody
	@RequestMapping(value = "sunds_ocx.page")
	public ModelAndView sunds_ocx(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_ocx", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		mv.addObject("appId", appId);
		//查找sunds_ocx信息
		List<QzApplnAttachmentBatch> batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
				
		//如果batch_ls为空 说明这是以前录得件 根据chk_value增加batch记录
		if(batch_ls == null || batch_ls.size() == 0){
			CustomerApplicationInfo app = customerApplicationInfoService.findById(appId);
			attachmentListService.addBatchInfo(appId);
			batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
		}
		CustomerApplicationInfo appInfo = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
		ECIF ecif = eCIFService.findEcifByCustomerId(appInfo.getCustomerId());
		mv.addObject("batch_ls", batch_ls);
		mv.addObject("ecif", ecif);
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "sunds_ocx_display.page")
	public AbstractModelAndView sunds_ocx_display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_ocx_display", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		mv.addObject("appId", appId);
		//查找sunds_ocx信息
		List<QzApplnAttachmentBatch> batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
		//如果batch_ls为空 说明这是以前录得件 根据chk_value增加batch记录
		if(batch_ls == null || batch_ls.size() == 0){
			CustomerApplicationInfo app = customerApplicationInfoService.findById(appId);
			attachmentListService.addBatchInfo(appId);
			batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
		}
		Circle circle = circleService.findCircleByAppId(appId);
		ECIF ecif = eCIFService.findEcifByCustomerId(circle.getCustomerId());
		mv.addObject("batch_ls", batch_ls);
		mv.addObject("ecif", ecif);
		return mv;
	}
	*/
	
	//影像
	@ResponseBody
	@RequestMapping(value = "sunds_ocx.page")
	public AbstractModelAndView sunds_ocx(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_ocx", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		mv.addObject("appId", appId);
		//查找sunds_ocx信息
		List<QzApplnAttachmentBatch> batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
		//如果batch_ls为空 说明这是以前录得件 根据chk_value增加batch记录
		if(batch_ls == null || batch_ls.size() == 0){
			attachmentListService.addBatchInfo(appId);
			batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
		}
		CustomerApplicationInfo appInfo = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
		ECIF ecif = eCIFService.findEcifByCustomerId(appInfo.getCustomerId());
		mv.addObject("batch_ls", batch_ls);
		mv.addObject("ecif", ecif);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "sunds_ocx_display.page")
	public AbstractModelAndView sunds_ocx_display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_ocx_display", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		mv.addObject("appId", appId);
		//查找sunds_ocx信息
		List<QzApplnAttachmentBatch> batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
		//如果batch_ls为空 说明这是以前录得件 根据chk_value增加batch记录
		if(batch_ls == null || batch_ls.size() == 0){
			attachmentListService.addBatchInfo(appId);
			batch_ls = attachmentListService.findAttachmentBatchByAppId(appId);
		}
		Circle circle = circleService.findCircleByAppId(appId);
		ECIF ecif = eCIFService.findEcifByCustomerId(circle.getCustomerId());
		mv.addObject("batch_ls", batch_ls);
		mv.addObject("ecif", ecif);
		return mv;
	}	
		
	//跳转到选择图片页面
	@ResponseBody
	@RequestMapping(value = "browse_folder.page")
	public AbstractModelAndView browse_folder_page(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_browse_folder", request);
		String batch_id = RequestHelper.getStringValue(request, "batch_id");
		mv.addObject("batch_id", batch_id);
		
		String sql = "select * from QZ_APPLN_ATTACHMENT_LIST where id in (select att_id from QZ_APPLN_ATTACHMENT_BATCH where id ='"+batch_id+"')";
		String appId = commonDao.queryBySql(QzApplnAttachmentList.class, sql, null).get(0).getApplicationId();
		mv.addObject("appId", appId);
		
		return mv;
	}	
		
	//浏览图片
	@ResponseBody
	@RequestMapping(value = "browse_folder.json")
	public void browse_folder_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			String batch_id = RequestHelper.getStringValue(request, "batch_id");
			//更新batch
			attachmentListService.browse_folder(file,batch_id);
			response.getWriter().write("true");
		}
		catch(Exception e){
			e.printStackTrace();
			logger.info("error", e);
		}
		
	}
		
	//跳转到选择更新图片页面
	@ResponseBody
	@RequestMapping(value = "browse_update_folder.page")
	public AbstractModelAndView browse_update_folder_page(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_browse_update_folder", request);
		String detail_id = RequestHelper.getStringValue(request, "detail_id");
		String file_no = RequestHelper.getStringValue(request, "file_no");
		
		String sql = "select * from QZ_APPLN_ATTACHMENT_DETAIL where id = '"+detail_id+"'";
		QzApplnAttachmentDetail detail = commonDao.queryBySql(QzApplnAttachmentDetail.class, sql, null).get(0);
		mv.addObject("detail_id", detail_id);
		mv.addObject("file_no", file_no);
		mv.addObject("batch_id", detail.getBatchId());
		
		return mv;
	}	
		
	//浏览图片
	@ResponseBody
	@RequestMapping(value = "browse_update_folder.json")
	public void browse_update_folder_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String detail_id = RequestHelper.getStringValue(request, "detail_id");
		String file_no = RequestHelper.getStringValue(request, "file_no");
		//更新batch
		attachmentListService.browse_update_folder(file,detail_id,file_no,request);
		response.getWriter().write("true");
	}
		
	//浏览图片完毕  开始通知后台上传影像平台
	@ResponseBody
	@RequestMapping(value = "browse_folder_complete.json")
	public JRadReturnMap browse_folder_complete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String batch_id = RequestHelper.getStringValue(request, "batch_id");
			
			attachmentListService.browse_folder_complete(batch_id,request);
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			returnMap.put(JRadConstants.SUCCESS, false);
			e.printStackTrace();
		}
		return returnMap;
		
	}	
	
	//查看缓存的图片列表
	@ResponseBody
	@RequestMapping(value = "display_detail.page")
	public AbstractModelAndView diaplsy_detail(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_display_detail", request);
		QueryResult<QzApplnAttachmentDetail> result = attachmentListService.display_detail(filter);
		JRadPagedQueryResult<QzApplnAttachmentDetail> pagedResult = new JRadPagedQueryResult<QzApplnAttachmentDetail>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		
		return mv;
	}	
		
	//查看已上传的图片列表
	@ResponseBody
	@RequestMapping(value = "display_server.page")
	public AbstractModelAndView display_server(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		filter.setIsUpload("1");
		
		QueryResult<PicPojo> result = new QueryResult<PicPojo>(0, null);;
		try {
			result = attachmentListService.display_server(filter,request);
			List<PicPojo> pic_ls = result.getItems();
			for(PicPojo pojo : pic_ls){
				String sql = "select * from QZ_APPLN_ATTACHMENT_DETAIL where id = '"+pojo.getFile_name().split("\\.")[0]+"'";
				QzApplnAttachmentDetail details = commonDao.queryBySql(QzApplnAttachmentDetail.class, sql, null).get(0);
				pojo.setDetail_id(pojo.getFile_name().split("\\.")[0]);
				pojo.setFile_name(details.getOriginalName().split("\\.")[0] + "." + pojo.getFile_name().split("\\.")[1]);
				pojo.setFile_no_local(details.getFileNo());
				pojo.setLsh(details.getLsh());
			}

			Collections.sort(pic_ls, new MyComparator());
			result.setItems(pic_ls);
		} catch (SunTransEngineException e) {
			logger.info("批次["+filter.getBatchId()+"]查询文件异常:" ,e);
		} catch (DocumentException e) {
			logger.info("批次["+filter.getBatchId()+"]解析影像平台xml异常:" ,e);
		} catch (SundsException e) {
			logger.info("批次["+filter.getBatchId()+"]解析影像平台xml异常:" ,e);
		}catch (Exception e) {
			logger.info("批次["+filter.getBatchId()+"]解析影像平台xml异常:" ,e);
		}
		
		JRadModelAndView mv = null;
		if(StringUtils.isNotEmpty(filter.getViewType()) && filter.getViewType().equals("1")){//按页查看
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_display_server_page", request);
		}
		else{//单张查看
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/sunds_display_server", request);
		}
		 
		JRadPagedQueryResult<PicPojo> pagedResult = new JRadPagedQueryResult<PicPojo>(filter, result);
		pagedResult.removeQueryParam("first_flag");
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("type",filter.getType());
		return mv;
	}	
		
	//删除影像平台上的文件
	@ResponseBody
	@RequestMapping(value = "delete_server_file.json")
	public JRadReturnMap delete_server_file(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String detail_id = RequestHelper.getStringValue(request, "detail_id");
			String file_no = RequestHelper.getStringValue(request, "file_no");
			String doc_id = RequestHelper.getStringValue(request, "doc_id");
			
			attachmentListService.delete_server_file(detail_id,file_no,doc_id,request);
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("删除失败");
			returnMap.put(JRadConstants.SUCCESS, false);
			e.printStackTrace();
		}
		return returnMap;
		
	}	
	
	//删除影像平台上的文件
	@ResponseBody
	@RequestMapping(value = "clear_batch.json")
	public JRadReturnMap clear_batch(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String batchId = RequestHelper.getStringValue(request, "batchId");
			
			attachmentListService.clear_batch(batchId,request);
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("删除失败");
			returnMap.put(JRadConstants.SUCCESS, false);
			e.printStackTrace();
		}
		return returnMap;
		
	}	
		
	//客户号检测
	@ResponseBody
	@RequestMapping(value = "detect_clientNo.json")
	public JRadReturnMap detect_clientNo(@ModelAttribute IESBForCircleForm iesbForCircleForm, HttpServletRequest request){
		JRadReturnMap returnMap = new JRadReturnMap();
		try{
			String circleId = RequestHelper.getStringValue(request, "id");
			String customerId = RequestHelper.getStringValue(request, "customerId");
			String clientNo = iesbForCircleForm.getClientNo();
			String clientType = iesbForCircleForm.getClientType();
			String globalId = iesbForCircleForm.getGlobalId();
			String globalType = iesbForCircleForm.getGlobalType();
			
			logger.info("circleId=" + circleId + "customerId= " +customerId+ "clientType= " +clientType
					+ "globalId=" +globalId+ "globalType=" + globalType);
			Map ECIFResp = eCIFService.detectECIF(globalId, clientType,globalType);
			
			String retCode = (String)ECIFResp.get("RET_CODE");
			if("000000".equals(retCode)){
				String clientName = (String)ECIFResp.get("CLIENT_NAME");
				String golbalType = (String)ECIFResp.get("GLOBAL_TYPE");
				String golbalId = (String)ECIFResp.get("GLOBAL_ID");
				String resClientNo = (String)ECIFResp.get("CLIENT_NO");
				
				//把获取的客户号存在库中
				ECIF ecif = eCIFService.findEcifByCustomerId(customerId);
				ecif.setClientNo(resClientNo);
				
				Circle circle = commonDao.findObjectById(Circle.class, circleId);
				//modified by nihc 201050814 先获取客户号人工去判断客户号是否与开户 一致 
//				if(clientName.equals(iesbForCircleForm.getClientName()) && golbalType.equals(iesbForCircleForm.getGlobalType())
//						&& golbalId.equals(iesbForCircleForm.getGlobalId())){
					if(circle != null){
						circle.setaClientNo(resClientNo);
						circle.setClientNo(resClientNo);
						circleService.updateCustomerInforCircle(circle);
					}
					returnMap.put(JRadConstants.SUCCESS, true);
					returnMap.addGlobalMessage("客户号获取成功,请核实客户号是否与开户一致，如果不一致请检查证件类型或 证件号码是否有误！");
					returnMap.put("resClientNo", resClientNo);
					return returnMap;
//				}
//				returnMap.put(JRadConstants.SUCCESS, false);
//				returnMap.put("resClientNo", resClientNo);
//				returnMap.put("message", "请检查客户身份信息和名称是否一致~!");
//				return returnMap;
			}
			returnMap.put("message", "客户信息不存在 ~！");
			returnMap.put(JRadConstants.SUCCESS, false);
		}catch(Exception e){
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("message", "检测失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	
	//查找该客户经理下的所有客户
	@ResponseBody
	@RequestMapping(value = "queryCustomerInfo.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView queryCustomerInfo(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/customerinfoByManager", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		//获取新生成的appid
		//String appId = request.getParameter("appId");
		//String productId = request.getParameter("productId");
		//String zaId = request.getParameter("zaId");
		String productType = request.getParameter("productType");
		String zaId = request.getParameter("zaId");
		//String isContinue = request.getParameter("isContinue");//是否续授信，记录到app表中
		//filter.setIsContinue(isContinue);
		
		//根据appid查找产品信息
		//QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = za_ywsqb_r_service.findByAppId(appId);
//			
		//判断产品类型获取满足该产品进件的客户
		//String productType = qzappln_za_ywsqb_r.getProductType();
		if("3".equals(productType)){
			QueryResult<CustomerInfor> result = customerInforService.findCustomerInfoWithLoanByFilter(filter);
			JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
			mv.addObject(PAGED_RESULT, pagedResult);
		}else{
			QueryResult<CustomerInfor> result = customerInforService.findCustomerInfoWithNotByFilter(filter);
			JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
			mv.addObject(PAGED_RESULT, pagedResult);
		}
//			mv.addObject("qzappln_za_ywsqb_r", qzappln_za_ywsqb_r);
////			//查找专案信息
//			List<QzApplnZaReturnMap> za_ls = zaService.findZas();
//			mv.addObject("za_ls", za_ls);
//			mv.addObject("za_ls_json", JSONArray.fromObject(za_ls).toString());
//			CustomerInforFilter filter = new CustomerInforFilter();
		
		
		//mv.addObject("customerId", customerInfor.getId());
		//mv.addObject("appId", appId);
		mv.addObject("productType", productType);
		mv.addObject("zaId", zaId);
		//mv.addObject("isContinue", isContinue);
		mv.addObject("filter",filter);
		return mv;
	}
	//客户经理选择产品
	@ResponseBody
	@RequestMapping(value = "selectProduct.page")
	public AbstractModelAndView selectProduct(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page0_for_create", request);
		String isContinue = request.getParameter("isContinue");//是否续授信
		//查找专案信息
		List<QzApplnZaReturnMap> za_ls = zaService.findZasApproved();
		mv.addObject("za_ls", za_ls);
		mv.addObject("za_ls_json", JSONArray.fromObject(za_ls).toString());
		if(StringUtils.isNotEmpty("isContinue")){
			mv.addObject("isContinue", isContinue);
		}
		//mv.addObject("customerId",customerId);
		return mv;
	}
	
	//显示创建的iframe_create
	@ResponseBody
	@RequestMapping(value = "iframe_create.page")
	public AbstractModelAndView iframeCreate(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe", request);
		String customerId= request.getParameter("id");//客户id
		//String appId = request.getParameter("appId");//进件申请id
		String productType = request.getParameter("productType");//选择类型
		String zaId = request.getParameter("zaId");//专案id
		String isContinue = request.getParameter("isContinue");//续授信
		
		//修改为新增一跳客户申请记录，产品表关联到该记录
		//查找默认产品
		ProductFilter filter = new ProductFilter();
		filter.setDefault_type(productType);
		ProductAttribute productAttribute = productService.findProductsByFilter(filter).getItems().get(0);
		//设置申请
		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		//customerApplicationInfo.setStatus(status);
		customerApplicationInfo.setId(IDGenerator.generateID());
		customerApplicationInfo.setApplyQuota("0");//设置额度
		customerApplicationInfo.setCustomerId(customerId);
		if(customerApplicationInfo.getApplyQuota()!=null){
			customerApplicationInfo.setApplyQuota((Integer.valueOf(customerApplicationInfo.getApplyQuota())*100)+"");
		}
		customerApplicationInfo.setCreatedTime(new Date());
		customerApplicationInfo.setStatus(Constant.SAVE_INTOPICES);
		customerApplicationInfo.setProductId(productAttribute.getId());
		customerApplicationInfo.setIsContinue(isContinue);
		
		commonDao.insertObject(customerApplicationInfo);
		//新增产品专案
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		//QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = za_ywsqb_r_service.findByCustomerId(customerId);
		//if(qzappln_za_ywsqb_r == null){
		QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = new QzAppln_Za_Ywsqb_R();
		//未填申请时 关联客户id
		qzappln_za_ywsqb_r.setCustomerId(customerId);
		qzappln_za_ywsqb_r.setProductType(productType);
		qzappln_za_ywsqb_r.setZaId(zaId);
		qzappln_za_ywsqb_r.setApplicationId(customerApplicationInfo.getId());
		ywsqbService.insert_page0(qzappln_za_ywsqb_r);
			
		//选择产品记录表关联客户，
		//QzAppln_Za_Ywsqb_R qzappln_za_ywsqb_r = za_ywsqb_r_service.findByAppId(appId);
		//根据产品类型调整不同的的页签（产品类型3表示安居贷）
		//ProductAttribute productAttribute = commonDao.findObjectById(ProductAttribute.class, productId);
		if("3".equals(productAttribute.getDefaultType())){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_create", request);
		}
		//根据产品类型调整不同的的页签（产品类型5表示10万以下的贷生活）
		if("5".equals(productAttribute.getDefaultType())){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_create_dsh_10", request);
		}
		String appId = customerApplicationInfo.getId();
	    mv.addObject("customerId",customerId);
		mv.addObject("appId",appId);
		return mv;
	}
	/**
	 * 安居贷客户获取住房按揭贷款台账信息
	 * delete_apply.json
	 */
	@ResponseBody
	@RequestMapping(value = "accLoan_info.page")
	public AbstractModelAndView accLoanInformation(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/accloan_information", request);
		String customerId = request.getParameter("id");
		String appId = request.getParameter("appId");
		String operate = request.getParameter("operate");
		//查询台账信息
		List<AccLoanInfo> loan = aferAccLoanService.getAfterAccLoanByCustomerId(customerId);
		mv.addObject("loan",loan);
		return mv;
	}
	
	
	/** 
	* @Title: dsh_jyd 
	* @Description: 贷生活决议单
	* @param @param request
	* @param @return    设定文件 
	* @return AbstractModelAndView    返回类型 
	* @throws 
	*/ 
	@ResponseBody
	@RequestMapping(value = "dsh_10_jyd.page")
	public AbstractModelAndView dsh_jyd(HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		JRadModelAndView mv;
		String customerId = request.getParameter("id");
		String appId = request.getParameter("appId");
		String operate = request.getParameter("operate");
		//查询是新增还是修改
		QzApplnDshJyd dshJyd = intoPiecesService.findDsh10JydByAppId(appId);
		if(dshJyd != null){//修改
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/dsh_10_jyd_update", request);
			mv.addObject("dshJyd", dshJyd);
		}else{//新增
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/dsh_10_jyd", request);
			//查询客户经理工号及对应的机构号
			mv.addObject("managerId", user.getLogin());
			mv.addObject("managerBrId", user.getOrganization().getId());
			mv.addObject("managerName", user.getDisplayName());
			//查询客户信息
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerId);
			mv.addObject("customerInfor", customerInfor);
		}
		mv.addObject("appId", appId);
		mv.addObject("customerId", customerId);
		mv.addObject("operate", operate);
		return mv;
	}
	
	/** 
	* @Title: insert_dsh_10_jyd 
	* @Description: 新增贷生活10万及以下决议单
	* @param @param qzApplnDshJydForm
	* @param @param request
	* @param @return
	* @param @throws Exception    设定文件 
	* @return JRadReturnMap    返回类型 
	* @throws 
	*/ 
	@ResponseBody
	@RequestMapping(value = "insert_dsh_10_jyd.json")
	public JRadReturnMap insert_dsh_10_jyd(@ModelAttribute QzApplnDshJydForm qzApplnDshJydForm, HttpServletRequest request) throws Exception {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				
				QzApplnDshJyd qzApplnDshJyd = qzApplnDshJydForm.createModel(QzApplnDshJyd.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//未填申请时 关联客户id
				qzApplnDshJyd.setCustomerId(customerId);
				qzApplnDshJyd.setApplicationId(appId);
				
				intoPiecesService.insert_dsh_10_jyd(qzApplnDshJyd,request);
				//returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	/** 
	* @Title: update_dsh_10_jyd 
	* @Description: 修改贷生活10万及以下决议单
	* @param @param qzApplnDshJydForm
	* @param @param request
	* @param @return
	* @param @throws Exception    设定文件 
	* @return JRadReturnMap    返回类型 
	* @throws 
	*/ 
	@ResponseBody
	@RequestMapping(value = "update_dsh_10_jyd.json")
	public JRadReturnMap update_dsh_10_jyd(@ModelAttribute QzApplnDshJydForm qzApplnDshJydForm, HttpServletRequest request) throws Exception {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("appId");
				String id = request.getParameter("id");
				
				QzApplnDshJyd qzApplnDshJyd = qzApplnDshJydForm.createModel(QzApplnDshJyd.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//未填申请时 关联客户id
				qzApplnDshJyd.setCustomerId(customerId);
				qzApplnDshJyd.setApplicationId(appId);
				qzApplnDshJyd.setId(id);
				
				intoPiecesService.update_dsh_10_jyd(qzApplnDshJyd,request);
				//returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	/**
	 * 删除进件信息
	 * delete_apply.json
	 */
	@ResponseBody
	@RequestMapping(value = "delete_apply.json")
	public JRadReturnMap deleteApply(@ModelAttribute IESBForCircleForm iesbForCircleForm, HttpServletRequest request){
		JRadReturnMap returnMap = new JRadReturnMap();
		try{
			String customerId = request.getParameter("id");
			String appId = request.getParameter("appId");
			
			intoPiecesService.deleteApply(appId);
			
			returnMap.put(RECORD_ID, customerId);
			returnMap.put(JRadConstants.MESSAGE, "删除成功");
			returnMap.put(JRadConstants.SUCCESS, true);
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put(JRadConstants.MESSAGE, "删除失败");
			returnMap.put(JRadConstants.SUCCESS, false);
		}
		return returnMap;
	}
	
	//================================================================//	
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
			
			
		//导入调查报告
		@ResponseBody
		@RequestMapping(value = "reportImport.json")
		public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if(file==null||file.isEmpty()){
					map.put(JRadConstants.SUCCESS, false);
					map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
					return map;
				}
				String productId = request.getParameter("productId");
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("applicationId");
				intoPiecesService.importExcel(file,productId,customerId,appId);
				map.put(JRadConstants.SUCCESS, true);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}
			return null;
		}
		
		
		
		

	    //显示维护信息--建议
		@ResponseBody
		@RequestMapping(value = "report_khxx.page")
		public AbstractModelAndView report_jy(HttpServletRequest request) throws UnsupportedEncodingException {
			JRadModelAndView mv = new JRadModelAndView("/intopieces/report_khxx", request);
			String appId = RequestHelper.getStringValue(request, "appId");
			String bm = RequestHelper.getStringValue(request, "bm");
			//String urlType = RequestHelper.getStringValue(request, "urlType");
			if (StringUtils.isNotEmpty(appId)) {
				LocalExcel localExcel = intoPiecesService.findLocalEXcelByApplication(appId);
				String encodeType = localExcel.getEncodeType();//获取编码
				String decodeType = localExcel.getDecodeType();//获取编码
				String tableContent = getFromBASE64(localExcel.getSheetKhxx(),encodeType,decodeType).replaceAll("\n", "<br>").replace("><br><", "><");
				mv.addObject("tableContent", tableContent);
				mv.addObject("appId", appId);
				/*
				mv.addObject("urlType", urlType);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);*/
			}
			return mv;
		}
		
		//显示维护信息--基本状况
		@ResponseBody
		@RequestMapping(value = "report_zcfz.page")
		public AbstractModelAndView report_jbzk(HttpServletRequest request) throws UnsupportedEncodingException {
			JRadModelAndView mv = new JRadModelAndView("/intopieces/report_zcfz", request);
			String appId = RequestHelper.getStringValue(request, "appId");
			//String urlType = RequestHelper.getStringValue(request, "urlType");
			if (StringUtils.isNotEmpty(appId)) {
				LocalExcel localExcel = intoPiecesService.findLocalEXcelByApplication(appId);
				String encodeType = localExcel.getEncodeType();//获取编码
				String decodeType = localExcel.getDecodeType();//获取编码
				String tableContent = getFromBASE64(localExcel.getSheet_zcfz(),encodeType,decodeType).replaceAll("\n", "<br>").replace("><br><", "><");
				mv.addObject("tableContent", tableContent);
				mv.addObject("appId", appId);
				/*mv.addObject("urlType", urlType);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);*/
			}
			return mv;
		}
		
		
		//显示维护信息--经营状态
		@ResponseBody
		@RequestMapping(value = "report_sy.page")
		public AbstractModelAndView report_jyzt(HttpServletRequest request) throws UnsupportedEncodingException {
			JRadModelAndView mv = new JRadModelAndView("/intopieces/report_sy", request);
			String appId = RequestHelper.getStringValue(request, "appId");
			//String urlType = RequestHelper.getStringValue(request, "urlType");
			if (StringUtils.isNotEmpty(appId)) {
				LocalExcel localExcel = intoPiecesService.findLocalEXcelByApplication(appId);
				String encodeType = localExcel.getEncodeType();//获取编码
				String decodeType = localExcel.getDecodeType();//获取编码
				String tableContent = getFromBASE64(localExcel.getSheetSy(),encodeType,decodeType).replaceAll("\n", "<br>").replace("><br><", "><");
				mv.addObject("tableContent", tableContent);
				mv.addObject("appId", appId);
				/*mv.addObject("urlType", urlType);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);*/
			}
			return mv;
		}

		//base64解码
		public static String getFromBASE64(String s,String encodeType,String decodeType) { 
			if (s == null) return null; 
			BASE64Decoder decoder = new BASE64Decoder(); 
			logger.info("【数据库返回base64】"+s);
			try { 
			byte[] b = decoder.decodeBuffer(s);
			//return new String(b);
			if(!StringUtils.isEmpty(decodeType)){
				return new String(b,decodeType);
			}else{
				return new String(b);
			}
			} catch (Exception e) { 
			return null; 
			}  
		} 
		
		
		@ResponseBody
		@RequestMapping(value = "isInBlacklist.json")
		public JRadReturnMap isInBlacklist(HttpServletRequest request) {
			String id = request.getParameter(ID);
			JRadReturnMap returnMap = new JRadReturnMap();
			if (returnMap.isSuccess()) {
				try {
					LocalExcel localExcel = intoPiecesService.findLocalEXcelByApplication(id);
					if(localExcel == null){
						returnMap.put("isInList", true);
					}
					
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
		
		class MyComparator implements Comparator{
			 @Override
			 public int compare(Object o1, Object o2) {
				 PicPojo po1 = (PicPojo)o1;
				 PicPojo po2 = (PicPojo)o2;
				 //首先比较出现次数，如果相同，则比较名字
				 int flag = Integer.valueOf(po1.getLsh()).compareTo(Integer.valueOf(po2.getLsh()));
				 if(flag == 0){
					 return (po1.getFile_name()).compareTo(po2.getFile_name());
				 }else{
					 return flag;
				 }  
			}
		}
		
}
