package com.cardpay.pccredit.intopieces.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.QZBankInterface.client.Client;
import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.dao.QuotaFreezeOrThawDao;
import com.cardpay.pccredit.intopieces.dao.SxInfoDao;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.QuotaFreezeOrThawFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.QuotaFreezeInfo;
import com.cardpay.pccredit.intopieces.model.QuotaProcess;
import com.cardpay.pccredit.intopieces.model.QuotaProcessSx;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.cardpay.pccredit.QZBankInterface.service.CircleService;
import com.cardpay.pccredit.QZBankInterface.service.IESBForED;
import com.cardpay.pccredit.QZBankInterface.service.IESBForSXInfo;
import com.cardpay.pccredit.QZBankInterface.web.IESBForCircleForm;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.dc.eai.data.CompositeData;

@Service
public class SxInfoService{
	
	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;
	
	@Autowired
	public SxInfoDao sxInfoDao;
	
	@Autowired
	public IESBForSXInfo iESBForSXInfo;
	
	@Autowired
	private NodeAuditService nodeAuditService;

	@Autowired
	private ProcessService processService;
	
	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	
	@Autowired
	private CircleService circleService;
	
	@Autowired
	public CommonDao commonDao;
	
	@Autowired
	private Client client;
	
	private static final Logger logger = Logger.getLogger(SxInfoService.class);
	
	/**
	 * 查询贷款成功的客户
	 * @author chinh
	 * @param filter
	 * @return QueryResult
	 */
	//按客户经理获取数据
	public QueryResult<QuotaFreezeInfo> getQzIesbForCircleByFilter(QuotaFreezeOrThawFilter filter){
		List<QuotaFreezeInfo> circle = sxInfoDao.getQzIesbForCircleByFilter(filter);
		int size = sxInfoDao.getQzIesbForCircleCountByFilter(filter);
		QueryResult<QuotaFreezeInfo> qz = new QueryResult<QuotaFreezeInfo>(size,circle);
		
		List<QuotaFreezeInfo> ls = qz.getItems();
		for(QuotaFreezeInfo obj : ls){
			if(obj.getProcessStatus() == null){
				obj.setNodeName("无");
			}
			else if(obj.getProcessStatus().equals(Constant.APPROVE_INTOPICES)||obj.getProcessStatus().equals(Constant.TRTURN_INTOPICES)){
				String nodeName = intoPiecesComdao.findQuotaProgressSx(obj.getCircleId());
				if(StringUtils.isNotEmpty(nodeName)){
					obj.setNodeName(nodeName);
				} else {
					obj.setNodeName("不在审批中");
				}
			} 
			else if(obj.getProcessStatus().equals(Constant.RETURN_INTOPICES)){
				obj.setNodeName("退回客户经理");
			} 
			else {
				obj.setNodeName("审批结束");
			}
		}
		return qz;
	}
	
	//按流程获取数据
	public QueryResult<QuotaFreezeInfo> getQzIesbForCircleWFByFilter(QuotaFreezeOrThawFilter filter){
		List<QuotaFreezeInfo> circle = sxInfoDao.getQzIesbForCircleWFByFilter(filter);
		int size = sxInfoDao.getQzIesbForCircleCountWFByFilter(filter);
		QueryResult<QuotaFreezeInfo> qz = new QueryResult<QuotaFreezeInfo>(size,circle);
		
		List<QuotaFreezeInfo> ls = qz.getItems();
		for(QuotaFreezeInfo obj : ls){
			if(obj.getProcessStatus() == null){
				obj.setNodeName("无");
			}
			else if(obj.getProcessStatus().equals(Constant.APPROVE_INTOPICES)||obj.getProcessStatus().equals(Constant.TRTURN_INTOPICES)){
				String nodeName = intoPiecesComdao.findQuotaProgressSx(obj.getCircleId());
				if(StringUtils.isNotEmpty(nodeName)){
					obj.setNodeName(nodeName);
				} else {
					obj.setNodeName("不在审批中");
				}
			} 
			else if(obj.getProcessStatus().equals(Constant.RETURN_INTOPICES)){
				obj.setNodeName("退回客户经理");
			} 
			else {
				obj.setNodeName("审批结束");
			}
		}
		return qz;
	}
	
	//发起流程
	//添加申请件流程
	public void startQuoatProcess(String circleId,IESBForCircleForm iesbForCircleForm,String applyReason){
		Circle circle = commonDao.findObjectById(Circle.class, circleId);
		//退回到客户经理的，修改状态即可
		if(circle.getProcessStatusSx()!=null && circle.getProcessStatusSx().equals(Constant.RETURN_INTOPICES)){
			circle.setProcessStatusSx(Constant.APPROVE_INTOPICES);
			commonDao.updateObject(circle);
			
			String sql = "update Quota_Process_SX set "
					+ "apply_reason = '"+applyReason+"', "
					+ "reg_perm_residence = '"+iesbForCircleForm.getRegPermResidence()+"', "//户籍所在地
					+ "reg_perm_residence1 = '"+iesbForCircleForm.getRegPermResidence_1()+"', "//户籍所在地
					+ "reg_perm_residence2 = '"+iesbForCircleForm.getRegPermResidence_2()+"', "//户籍所在地
					+ "reg_perm_residence3 = '"+iesbForCircleForm.getRegPermResidence_3()+"', "//户籍所在地
					+ "mobile = '"+iesbForCircleForm.getMobile()+"', "//手机号码
					+ "address = '"+iesbForCircleForm.getAddress()+"' "//地址
					+ "where SERIAL_NUMBER = '"+circle.getSerialnumberSx()+"'";
			commonDao.queryBySql(sql, null);
		}
		//新的申请
		else{  
			//拒绝的 和审批成功，删除之前流程相关信息
			if(circle.getProcessStatusSx()!=null &&(circle.getProcessStatusSx().equals(Constant.REFUSE_INTOPICES) || circle.getProcessStatusSx().equals(Constant.APPROVED_INTOPICES))){
				//删除WF_PROCESS_RECORD表和WF_STATUS_QUEUE_RECORD
				String sql = "delete from WF_STATUS_QUEUE_RECORD where CURRENT_PROCESS = '"+circle.getSerialnumberSx()+"'";
				commonDao.queryBySql(sql, null);
				sql = "delete from WF_PROCESS_RECORD where Id = '"+circle.getSerialnumberSx()+"'";
				commonDao.queryBySql(sql, null);
				//删除quota_process
				sql = "delete from QUOTA_PROCESS_SX where SERIAL_NUMBER = '"+circle.getSerialnumberSx()+"'";
				commonDao.queryBySql(sql, null);
				//circle的SERIALNUMBER_QUOTA和PROCESS_STATUS 置空
				sql = "update QZ_IESB_FOR_CIRCLE set SERIALNUMBER_SX= NULL,PROCESS_STATUS_SX = NULL where id= '"+circle.getId()+"'";
				commonDao.queryBySql(sql, null);
			}
			
			//发起流程
			circle.setProcessStatusSx(Constant.APPROVE_INTOPICES);
			commonDao.updateObject(circle);
			
			WfProcessInfo wf=new WfProcessInfo();
			wf.setProcessType(WfProcessInfoType.sx_info_type);
			wf.setVersion("1");
			commonDao.insertObject(wf);
			List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.SXInfo.name(),Constant.SX_INFO);
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
						iesbForCircleForm.setRegPermResidence(iesbForCircleForm.getRegPermResidence_3().split("_")[1]);
						
						QuotaProcessSx quotaProcessSx = new QuotaProcessSx();
						String serialNumber = processService.start(wf.getId());
						quotaProcessSx.setSerialNumber(serialNumber);
						quotaProcessSx.setNextNodeId(nodeAudit.getId()); 
						quotaProcessSx.setCircleId(circleId);
						quotaProcessSx.setApplyReason(applyReason);
						quotaProcessSx.setRegPermResidence(iesbForCircleForm.getRegPermResidence());
						quotaProcessSx.setRegPermResidence1(iesbForCircleForm.getRegPermResidence_1());
						quotaProcessSx.setRegPermResidence2(iesbForCircleForm.getRegPermResidence_2());
						quotaProcessSx.setRegPermResidence3(iesbForCircleForm.getRegPermResidence_3());
						quotaProcessSx.setAddress(iesbForCircleForm.getAddress());
						quotaProcessSx.setMobile(iesbForCircleForm.getMobile());
						commonDao.insertObject(quotaProcessSx);
						
						Circle circle_ = commonDao.findObjectById(Circle.class, circleId);
						circle_.setSerialnumberSx(serialNumber);
						commonDao.updateObject(circle_);
					}
				}
				
				if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
					endBool=true;
				}
			}
			
			//节点关系
			List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.SXInfo.name(), Constant.SX_INFO);
			for(NodeControl control : nodeControls){
				WfStatusResult wfStatusResult = new WfStatusResult();
				wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
				wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
				wfStatusResult.setExamineResult(control.getCurrentStatus());
				commonDao.insertObject(wfStatusResult);
			}
		}
	}
	
	//更新审批流程
	public void quot_operate(HttpServletRequest request) throws Exception{
		QuotaProcessSx process =  commonDao.queryBySql(QuotaProcessSx.class, 
				"select * from Quota_Process_SX where id = '"+request.getParameter("processId")+"'", null).get(0);
		request.setAttribute("serialNumber", process.getSerialNumber());
		request.setAttribute("circleId", process.getCircleId());
		request.setAttribute("applicationStatus", request.getParameter("applicationStatus"));
		request.setAttribute("objection", "false");
		this.updateQuotaProcess(request,process);
	}
	
	//更新流程节点
	public void updateQuotaProcess(HttpServletRequest request,QuotaProcessSx process) throws Exception {
		Circle circle = new Circle();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String serialNumber = request.getAttribute("serialNumber").toString();
		String applicationStatus = request.getAttribute("applicationStatus").toString();
		String circleId = request.getAttribute("circleId").toString();
		
		//applicationStatus 必须是ApproveOperationTypeEnum中的类型
		String examineResutl = processService.examineForQuota(serialNumber, loginId, applicationStatus, null);
		//更新单据状态
		//拒绝/结束/退回客户经理 时  examineResutl是ApproveOperationTypeEnum枚举
	    if (examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.RETURNTOFIRST.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())) {
			if(examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){//拒绝
				circle.setProcessStatusSx(Constant.REFUSE_INTOPICES);
				
				//String refusalReason = request.getParameter("reason2");
				//process.setRefusalReason(refusalReason);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.RETURNTOFIRST.toString())){//退回客户经理
				circle.setProcessStatusSx(Constant.RETURN_INTOPICES);
				
				//String fallbackReason = request.getParameter("reason1");
				//process.setFallbackReason(fallbackReason);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())){//结束
				circle.setProcessStatusSx(Constant.APPROVED_INTOPICES);
				
				//调用接口
				Circle circle_ = commonDao.findObjectById(Circle.class, circleId);
				String RET_CODE = this.getIesbForSX("02",circle_,process);
				if(RET_CODE.equals(IpadConstant.RET_CODE_SUCCESS)){
					//修改授信信息
					circle.setMobile(process.getMobile());
					circle.setAddress(process.getAddress());
					circle.setRegPermResidence(process.getRegPermResidence());
					circle.setRegPermResidence_1(process.getRegPermResidence1());
					circle.setRegPermResidence_2(process.getRegPermResidence2());
					circle.setRegPermResidence_3(process.getRegPermResidence3());
					
				}
				else{
					throw new Exception("修改授信信息失败，失败原因:"+RET_CODE);
				}
			}
			circle.setId(circleId);
			commonDao.updateObject(circle);
			
			process.setNextNodeId(null);
		}
	    //通过/退回时  examineResutl是result的code
	    else {
	    	//退回
			if(applicationStatus.equalsIgnoreCase(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
				circle.setProcessStatusSx(Constant.TRTURN_INTOPICES);
				
				//String fallbackReason = request.getParameter("reason");
				//process.setFallbackReason(fallbackReason);
			}
			//通过
			else{
				circle.setProcessStatusSx(Constant.APPROVE_INTOPICES);
			}
			
			circle.setId(circleId);
			commonDao.updateObject(circle);
			
			process.setNextNodeId(examineResutl);
		}
		
		process.setProcessOpStatus(applicationStatus);
		process.setSerialNumber(serialNumber);
		process.setAuditUser(loginId);
		process.setCreatedTime(new Date());
//		customerApplicationProcess.setDelayAuditUser(user.getId());//清空字段值 
		customerApplicationIntopieceWaitDao.updateQuotaProcessSxBySerialNumber(process);

	}
	
	/**
	 * @param HashMap
	 * @return retMsg
	 */
	public String getIesbForSX(String type,Circle cirlce,QuotaProcessSx process){
		CompositeData request = iESBForSXInfo.createRequest(type, cirlce, process,null);
		//发送请求数据
		CompositeData resp = client.sendMess(request);
		return iESBForSXInfo.parseResponse(resp);
	}

	//撤销授信信息修改
	public void quot_cancle(String circleId){
		//贷款信息
		Circle circle = commonDao.findObjectById(Circle.class, circleId);
		//冻结流程表
		if(circle != null){
			String sql = "delete from QUOTA_PROCESS_SX where SERIAL_NUMBER = '"+circle.getSerialnumberSx()+"' and circle_id = '"+circleId+"'";
			commonDao.queryBySql(sql, null);
			
			circle.setSerialnumberSx(null);
			circle.setProcessStatusSx(null);
			commonDao.deleteObject(Circle.class, circle.getId());
		}
		
	}
}
