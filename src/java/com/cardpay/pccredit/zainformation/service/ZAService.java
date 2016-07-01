package com.cardpay.pccredit.zainformation.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.web.IESBForCircleForm;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.dao.ZADao;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.QuotaFreezeOrThawFilter;
import com.cardpay.pccredit.intopieces.filter.ZAFilter;
import com.cardpay.pccredit.intopieces.model.QuotaFreezeInfo;
import com.cardpay.pccredit.intopieces.model.QuotaProcessSx;
import com.cardpay.pccredit.intopieces.model.QuotaProcessZa;
import com.cardpay.pccredit.intopieces.model.QzApplnZa;
import com.cardpay.pccredit.intopieces.model.QzApplnZaReturnMap;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

@Service
public class ZAService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	
	@Autowired
	private ZADao zaDao;
	
	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;
	
	public void insert(QzApplnZa qzApplnZa){
		commonDao.insertObject(qzApplnZa);
	}
	
	public void update(QzApplnZa qzApplnZa){
		commonDao.updateObject(qzApplnZa);
	}
	
	public QzApplnZa findZaById(String id){
		return zaDao.findZaById(id);
	}
	
	public QueryResult<QzApplnZa> findAllZa(ZAFilter filter){
		List<QzApplnZa> ls = zaDao.findAllZaByFilter(filter);
		int size = zaDao.findAllZaCountByFilter(filter);
		QueryResult<QzApplnZa> qs = new QueryResult<QzApplnZa>(size, ls);
		
		List<QzApplnZa> tmp = qs.getItems();
		for(QzApplnZa obj : tmp){
			if(obj.getProcessStatus() == null){
				obj.setNodeName("无");
			}
			else if(obj.getProcessStatus().equals(Constant.APPROVE_INTOPICES)||obj.getProcessStatus().equals(Constant.TRTURN_INTOPICES)){
				String nodeName = intoPiecesComdao.findQuotaProgressZa(obj.getId());
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
		
		return qs;
	}
	
	public List<QzApplnZaReturnMap> findZas(){
		List<QzApplnZaReturnMap> ls = zaDao.findZas();
		return ls;
	}
	
	public List<QzApplnZaReturnMap> findZasApproved(){
		List<QzApplnZaReturnMap> ls = zaDao.findZasApproved();
		return ls;
	}
	
	public void deleteZAById(String id){
		QzApplnZa za = commonDao.findObjectById(QzApplnZa.class, id);
		String sql = "delete from QUOTA_PROCESS_ZA where SERIAL_NUMBER = '"+za.getSerialnumber()+"' and za_id = '"+id+"'";
		commonDao.queryBySql(sql, null);
		
		zaDao.deleteZAById(id);
	}
	
	//发起流程
	//添加申请件流程
	public void startQuoatProcess(String zaId){
		QzApplnZa za = commonDao.findObjectById(QzApplnZa.class, zaId);
		//退回到客户经理的，修改状态即可
		if(za.getProcessStatus()!=null && za.getProcessStatus().equals(Constant.RETURN_INTOPICES)){
			za.setProcessStatus(Constant.APPROVE_INTOPICES);
			commonDao.updateObject(za);
		}
		//新的申请
		else{  
			//拒绝的 和审批成功，删除之前流程相关信息
			if(za.getProcessStatus()!=null &&(za.getProcessStatus().equals(Constant.REFUSE_INTOPICES) || za.getProcessStatus().equals(Constant.APPROVED_INTOPICES))){
				//删除WF_PROCESS_RECORD表和WF_STATUS_QUEUE_RECORD
				String sql = "delete from WF_STATUS_QUEUE_RECORD where CURRENT_PROCESS = '"+za.getSerialnumber()+"'";
				commonDao.queryBySql(sql, null);
				sql = "delete from WF_PROCESS_RECORD where Id = '"+za.getSerialnumber()+"'";
				commonDao.queryBySql(sql, null);
				//删除quota_process
				sql = "delete from QUOTA_PROCESS_ZA where SERIAL_NUMBER = '"+za.getSerialnumber()+"'";
				commonDao.queryBySql(sql, null);
				//circle的SERIALNUMBER_QUOTA和PROCESS_STATUS 置空
				sql = "update qz_appln_za set SERIALNUMBER= NULL,PROCESS_STATUS = NULL where id= '"+za.getId()+"'";
				commonDao.queryBySql(sql, null);
			}
			
			//发起流程
			za.setProcessStatus(Constant.APPROVE_INTOPICES);
			commonDao.updateObject(za);
			
			WfProcessInfo wf=new WfProcessInfo();
			wf.setProcessType(WfProcessInfoType.za_info_type);
			wf.setVersion("1");
			commonDao.insertObject(wf);
			List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.ZAInfo.name(),Constant.ZA_INFO);
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
						
						QuotaProcessZa quotaProcessza = new QuotaProcessZa();
						String serialNumber = processService.start(wf.getId());
						quotaProcessza.setSerialNumber(serialNumber);
						quotaProcessza.setNextNodeId(nodeAudit.getId()); 
						quotaProcessza.setZaId(zaId);
						quotaProcessza.setApplyReason(null);
						commonDao.insertObject(quotaProcessza);
						
						QzApplnZa za_ = commonDao.findObjectById(QzApplnZa.class, zaId);
						za_.setSerialnumber(serialNumber);
						commonDao.updateObject(za_);
					}
				}
				
				if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
					endBool=true;
				}
			}
			
			//节点关系
			List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.ZAInfo.name(), Constant.ZA_INFO);
			for(NodeControl control : nodeControls){
				WfStatusResult wfStatusResult = new WfStatusResult();
				wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
				wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
				wfStatusResult.setExamineResult(control.getCurrentStatus());
				commonDao.insertObject(wfStatusResult);
			}
		}
	}
	
	//按流程获取数据
	public QueryResult<QzApplnZa> getZaWFByFilter(QuotaFreezeOrThawFilter filter){
		List<QzApplnZa> ls = zaDao.getZaWFByFilter(filter);
		int size = zaDao.getZaCountWFByFilter(filter);
		QueryResult<QzApplnZa> qz = new QueryResult<QzApplnZa>(size,ls);
		
		List<QzApplnZa> tmp = qz.getItems();
		for(QzApplnZa obj : tmp){
			if(obj.getProcessStatus() == null){
				obj.setNodeName("无");
			}
			else if(obj.getProcessStatus().equals(Constant.APPROVE_INTOPICES)||obj.getProcessStatus().equals(Constant.TRTURN_INTOPICES)){
				String nodeName = intoPiecesComdao.findQuotaProgressZa(obj.getId());
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
	
	//更新审批流程
	public void quot_operate(HttpServletRequest request) throws Exception{
		QuotaProcessZa process =  commonDao.queryBySql(QuotaProcessZa.class, 
				"select * from Quota_Process_za where id = '"+request.getParameter("processId")+"'", null).get(0);
		request.setAttribute("serialNumber", process.getSerialNumber());
		request.setAttribute("zaId", process.getZaId());
		request.setAttribute("applicationStatus", request.getParameter("applicationStatus"));
		request.setAttribute("objection", "false");
		this.updateQuotaProcess(request,process);
	}
	
	//更新流程节点
	public void updateQuotaProcess(HttpServletRequest request,QuotaProcessZa process) throws Exception {
		QzApplnZa za = new QzApplnZa();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String serialNumber = request.getAttribute("serialNumber").toString();
		String applicationStatus = request.getAttribute("applicationStatus").toString();
		String zaId = request.getAttribute("zaId").toString();
		
		//applicationStatus 必须是ApproveOperationTypeEnum中的类型
		String examineResutl = processService.examineForQuota(serialNumber, loginId, applicationStatus, null);
		//更新单据状态
		//拒绝/结束/退回客户经理 时  examineResutl是ApproveOperationTypeEnum枚举
	    if (examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.RETURNTOFIRST.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())) {
			if(examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){//拒绝
				za.setProcessStatus(Constant.REFUSE_INTOPICES);
				
				//String refusalReason = request.getParameter("reason2");
				//process.setRefusalReason(refusalReason);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.RETURNTOFIRST.toString())){//退回客户经理
				za.setProcessStatus(Constant.RETURN_INTOPICES);
				
				//String fallbackReason = request.getParameter("reason1");
				//process.setFallbackReason(fallbackReason);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())){//结束
				za.setProcessStatus(Constant.APPROVED_INTOPICES);
			}
			za.setId(zaId);
			commonDao.updateObject(za);
			
			process.setNextNodeId(null);
		}
	    //通过/退回时  examineResutl是result的code
	    else {
	    	//退回
			if(applicationStatus.equalsIgnoreCase(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
				za.setProcessStatus(Constant.TRTURN_INTOPICES);
				
				//String fallbackReason = request.getParameter("reason");
				//process.setFallbackReason(fallbackReason);
			}
			//通过
			else{
				za.setProcessStatus(Constant.APPROVE_INTOPICES);
			}
			
			za.setId(zaId);
			commonDao.updateObject(za);
			
			process.setNextNodeId(examineResutl);
		}
		
		process.setProcessOpStatus(applicationStatus);
		process.setSerialNumber(serialNumber);
		process.setAuditUser(loginId);
		process.setCreatedTime(new Date());
//			customerApplicationProcess.setDelayAuditUser(user.getId());//清空字段值 
		customerApplicationIntopieceWaitDao.updateQuotaProcessZaBySerialNumber(process);

	}
}
