package com.cardpay.pccredit.intopieces.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationInfoDao;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.QzApplnApprovalMeeting;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusQueueRecord;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

/**
 * CustomerApplicationIntopieceWaitService类的描述
 * 
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
@Service
public class CustomerApplicationIntopieceWaitService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;

	@Autowired
	private CustomerApplicationInfoDao customerApplicationInfoDao;

	@Autowired
	private NodeAuditService nodeAuditService;

	@Autowired
	private ProcessService processService;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private IntoPiecesService intoPiecesService;
	

	// 查询所有的进件包括审核的及未审核的
	public QueryResult<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceWaitForm(CustomerApplicationProcessFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceWaitForm(filter);
		int size = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceWaitCountForm(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;

	}

	// 根据serialNumber查询CUSTOMER_APPLICATION_PROCESS表
	public CustomerApplicationIntopieceWaitForm findCustomerApplicationProcessBySerialNumber(String serialNumber) {
		return customerApplicationIntopieceWaitDao.findCustomerApplicationProcessBySerialNumber(serialNumber);
	}

	// 申请进件审核
	public int updateCustomerApplicationProcess(String userId) {
		// 判断自己是否有审核任务
		int i = 0;
		if (customerApplicationIntopieceWaitDao.getCustomerApplicationInfoByUserId(userId) != 0) {
			return 0;
		}
		// 是否有审核的进件
		List<CustomerApplicationProcess> listNodeId = customerApplicationIntopieceWaitDao.findCustomerApplicationInfoAll();
		for (CustomerApplicationProcess customerApplicationProcess : listNodeId) {
			List<NodeAudit> listNode = nodeAuditService.findByNodeAuditByUserId(NodeAuditTypeEnum.Product.toString(), userId, customerApplicationProcess.getProductId());
			boolean falg = false;
			for (NodeAudit nodeAudit : listNode) {
				if (customerApplicationProcess.getProductId().equals(nodeAudit.getProductId()) && customerApplicationProcess.getNextNodeId().equals(nodeAudit.getId())) {
					customerApplicationProcess.setDelayAuditUser(userId);
					customerApplicationProcess.setAuditTime(new Date());
					i = customerApplicationIntopieceWaitDao.updateCustomerApplicationProcess(customerApplicationProcess);
					
					CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, customerApplicationProcess.getApplicationId());
					applicationInfo.setModifiedBy(userId);
					applicationInfo.setModifiedTime(new Date());
					commonDao.updateObject(applicationInfo);
					
					falg = true;
					break;
				}
			}
			if(falg){
				break;
			}
		}
		return i;
		// 循环所有的节点
	}

	public void updateCustomerApplicationProcessBySerialNumberApplicationInfo(HttpServletRequest request) throws Exception {
		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		CustomerApplicationProcess customerApplicationProcess = new CustomerApplicationProcess();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String serialNumber = request.getParameter("serialNumber");
		String examineAmount = request.getParameter("examineAmount");
		String applicationStatus = request.getParameter("applicationStatus");
		String applicationId = request.getParameter("applicationId");
		String customerId = request.getParameter("customerId");
		String objection = request.getParameter("objection");
		if(objection.equals("true")){
			applicationStatus = ApproveOperationTypeEnum.OBJECTION.toString();
		}
		if(StringUtils.isNotEmpty(examineAmount)){
			examineAmount = (Double.parseDouble(examineAmount) * 100) + "";
		}
		//applicationStatus 必须是ApproveOperationTypeEnum中的通过，退回，拒绝三个类型
		String examineResutl = processService.examine(serialNumber, loginId, applicationStatus, examineAmount,null);
		//更新单据状态
	    if (examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())) {
			if(examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){
				customerApplicationInfo.setStatus(Constant.REFUSE_INTOPICES);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
				customerApplicationInfo.setStatus(Constant.NOPASS_INTOPICES);
				//退回时 删除提交申请备份的信息
				CustomerApplicationInfo returnApp = commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
				customerInforService.deleteCloneSubmitAppByReturn(returnApp.getCustomerId(), applicationId);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())){
				customerApplicationInfo.setFinalApproval(examineAmount);
				customerApplicationInfo.setStatus(Constant.APPROVED_INTOPICES);
			}
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			
			customerApplicationProcess.setNextNodeId(null);
		} else {
			customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			
			customerApplicationProcess.setNextNodeId(examineResutl);
		}
	    if(Constant.APPROVED_INTOPICES.equalsIgnoreCase(customerApplicationInfo.getStatus())){
	    	intoPiecesService.exportData(applicationId, customerId, null);
	    }
		if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString())) {
			String fallbackReason = request.getParameter("reason");
			customerApplicationProcess.setFallbackReason(fallbackReason);
		} else if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())) {
			String refusalReason = request.getParameter("reason");
			customerApplicationProcess.setRefusalReason(refusalReason);
		}
		customerApplicationProcess.setProcessOpStatus(applicationStatus);
		customerApplicationProcess.setSerialNumber(serialNumber);
		customerApplicationProcess.setExamineAmount(examineAmount);
		customerApplicationProcess.setAuditUser(loginId);
		customerApplicationProcess.setCreatedTime(new Date());
		customerApplicationProcess.setExamineAmount(examineAmount);
//		customerApplicationProcess.setDelayAuditUser(user.getId());//清空字段值 
		customerApplicationIntopieceWaitDao.updateCustomerApplicationProcessBySerialNumber(customerApplicationProcess);

	}

	/** 
	* @Title: recieveIntopieceWaitFormAllInOne 
	* @Description: all in one
	* @param @param filter
	* @param @return    设定文件 
	* @return QueryResult<CustomerApplicationIntopieceWaitForm>    返回类型 
	* @throws 
	*/ 
	public QueryResult<CustomerApplicationIntopieceWaitForm> recieveIntopieceWaitFormAllInOne(CustomerApplicationProcessFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.IntopieceWaitFormAllInOne(filter);
		int size = customerApplicationIntopieceWaitDao.CountIntopieceWaitFormAllInOne(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;

	}
	
	/** 
	* @Title: recieveIntopieceWaitForm 
	* @Description: 根据团队长获取能看到的件
	* @param @param filter
	* @param @return    设定文件 
	* @return QueryResult<CustomerApplicationIntopieceWaitForm>    返回类型 
	* @throws 
	*/ 
	public QueryResult<CustomerApplicationIntopieceWaitForm> recieveIntopieceWaitForm(CustomerApplicationProcessFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.IntopieceWaitForm(filter);
		int size = customerApplicationIntopieceWaitDao.CountIntopieceWaitForm(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;

	}
	
	/** 
	* @Title: intopieceWaitFormByOrgId 
	* @Description: 根据机构id获取所能看到的件(新贷生活,安居带) 
	* @param @param filter
	* @param @return    设定文件 
	* @return QueryResult<CustomerApplicationIntopieceWaitForm>    返回类型 
	* @throws 
	*/ 
	public QueryResult<CustomerApplicationIntopieceWaitForm> intopieceWaitFormByOrgId(CustomerApplicationProcessFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.intopieceWaitFormByOrgId(filter);
		int size = customerApplicationIntopieceWaitDao.CountIntopieceWaitFormByOrgId(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;
	}
		
	/** 
	* @Title: intopieceWaitFormByOrgIdSpecial 
	* @Description: 贷生活流程中客户经理这一步,因为需要客户经理参与,人数太多,不方便配置,所以直接特殊处理,自己看自己的件
	* @param @param filter
	* @param @return    设定文件 
	* @return QueryResult<CustomerApplicationIntopieceWaitForm>    返回类型 
	* @throws 
	*/ 
	public QueryResult<CustomerApplicationIntopieceWaitForm> intopieceWaitFormByOrgIdSpecial(CustomerApplicationProcessFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.intopieceWaitFormByOrgIdSpecial(filter);
		int size = customerApplicationIntopieceWaitDao.CountIntopieceWaitFormByOrgIdSpecial(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;
	}
	
	// 查询需要团队初审拒件的进件
	public QueryResult<CustomerApplicationIntopieceWaitForm> IntopieceChushenRejectForm() {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.IntopieceChushenRejectForm();
		int size = customerApplicationIntopieceWaitDao.CountIntopieceChushenRejectForm();
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;

	}
	// 查询需要补充上会的进件
	public QueryResult<CustomerApplicationIntopieceWaitForm> shouxinAddInforForm(CustomerApplicationProcessFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.shouxinAddInforForm(filter);
		int size = customerApplicationIntopieceWaitDao.CountshouxinAddInforForm(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;

	}
	
	public WfStatusInfo getNextIsEnd(HttpServletRequest request){
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String serialNumber = request.getAttribute("serialNumber").toString();
		return processService.getNextIsEnd(serialNumber,loginId,ApproveOperationTypeEnum.APPROVE.toString());
	}
	public void updateCustomerApplicationProcessBySerialNumberApplicationInfo1(HttpServletRequest request,Circle circle) throws Exception 
	{	
		WfStatusInfo tmp = this.getNextIsEnd(request);
		
		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		CustomerApplicationProcess customerApplicationProcess = new CustomerApplicationProcess();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String serialNumber = request.getAttribute("serialNumber").toString();
		String examineAmount = request.getAttribute("examineAmount").toString();
		String applicationStatus = request.getAttribute("applicationStatus").toString();
		String applicationId = request.getAttribute("applicationId").toString();
		String objection = request.getAttribute("objection").toString();
		if(objection.equals("true")){
			applicationStatus = ApproveOperationTypeEnum.OBJECTION.toString();
		}
		if(StringUtils.isNotEmpty(examineAmount)){
			examineAmount = (Double.parseDouble(examineAmount) * 100) + "";
		}
		//applicationStatus 必须是ApproveOperationTypeEnum中的通过，退回，拒绝三个类型
		String examineResutl = processService.examine(serialNumber, loginId, applicationStatus, examineAmount,request.getParameter("reason"));
		//更新单据状态
	    if (examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())) {
			if(examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){
				customerApplicationInfo.setStatus(Constant.REFUSE_INTOPICES);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
				customerApplicationInfo.setStatus(Constant.NOPASS_INTOPICES);
				//退回时 删除提交申请备份的信息
				CustomerApplicationInfo returnApp = commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
				customerInforService.deleteCloneSubmitAppByReturn(returnApp.getCustomerId(), applicationId);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())){
				customerApplicationInfo.setFinalApproval(examineAmount);
				customerApplicationInfo.setStatus(Constant.APPROVED_INTOPICES);
			}
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			
			customerApplicationProcess.setNextNodeId(null);
		} else {
			//这一步需要判断是否增加排审记录
			//根据当前审批结果 查找审批结果表 获取下一个审批状态
			if(tmp.getStatusName().equals(Constant.status_zhongxin)){
				QzApplnApprovalMeeting meeting = customerApplicationIntopieceWaitDao.findMeetingByAppId(applicationId);
				if(meeting != null){
					meeting.setStatus("1");
					commonDao.updateObject(meeting);
				}
			}
			if(tmp.getStatusName().equals(Constant.status_shouxin)){
				QzApplnApprovalMeeting meeting = customerApplicationIntopieceWaitDao.findMeetingByAppId(applicationId);
				if(meeting == null){
					meeting = new QzApplnApprovalMeeting();
					meeting.setStatus("0");
					meeting.setApplicationId(applicationId);
					
					CustomerApplicationInfo app = commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
					CustomerInfor customer = commonDao.findObjectById(CustomerInfor.class, app.getCustomerId());
					meeting.setCustomerId(customer.getId());
					meeting.setManagerId(customer.getUserId());

					Calendar c = Calendar.getInstance();  
					c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
					if(isWeekend(c)){
						c.set(Calendar.DATE, c.get(Calendar.DATE) + 2);//周末加两天
					}
					meeting.setMeetingTime(c.getTime());
					
					//查询该进件对应的客户以前是否有审贷会的排审记录，有的话还是同一个审贷员
					List<WfStatusQueueRecord> pre_wf_ls = customerApplicationIntopieceWaitDao.findPreWFByCustomerId(customer.getId());
					List<SystemUser> all_checker = customerApplicationIntopieceWaitDao.findAllChecker(tmp.getStatusCode());
					//查询该进件对应的客户以前是否有审贷会的排审记录，有的话还是同一个审贷员
					//List<QzApplnApprovalMeeting> pre_meeting_ls = customerApplicationIntopieceWaitDao.findPreMeetingByCustomerId(customer.getId());
					if(pre_wf_ls != null && pre_wf_ls.size() > 0){
						for(SystemUser su : all_checker){
							if(su.getId().equals(pre_wf_ls.get(0).getExamineUser())){
								meeting.setUserId(pre_wf_ls.get(0).getExamineUser());
								break;
							}
						}
						
					}

					if(StringUtils.isEmpty(meeting.getUserId())){
						//查询该岗位的人员，并筛选出一个作为审核人
						List<SystemUser> checker_ls = customerApplicationIntopieceWaitDao.findSomeOneAsChecker(tmp.getStatusCode(),meeting.getMeetingTime());
						int times = checker_ls.get(0).getAge();
						List<SystemUser> min_checker_ls = new ArrayList<SystemUser>();
						for(SystemUser obj : checker_ls){
							if(obj.getAge() <= times){
								min_checker_ls.add(obj);
							}
						}
						meeting.setUserId(min_checker_ls.get((int)(Math.random()*(min_checker_ls.size()))).getId());
					}
					
					meeting.setLsh(getMeetingLsh(meeting));
					commonDao.insertObject(meeting);
				}
			}
			
			customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			
			customerApplicationProcess.setNextNodeId(examineResutl);
		}
		if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString())) {
			String fallbackReason = request.getParameter("reason");
			customerApplicationProcess.setFallbackReason(fallbackReason);
		} else if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())) {
			String refusalReason = request.getParameter("reason");
			customerApplicationProcess.setRefusalReason(refusalReason);
		}else{
			String auditReason = request.getParameter("reason");
			customerApplicationProcess.setAuditOpinion(auditReason);//审批意见
			customerApplicationProcess.setRefusalReason("");
			customerApplicationProcess.setFallbackReason("");
		}
		customerApplicationProcess.setProcessOpStatus(applicationStatus);
		customerApplicationProcess.setSerialNumber(serialNumber);
		customerApplicationProcess.setExamineAmount(examineAmount);
		customerApplicationProcess.setAuditUser(loginId);
		customerApplicationProcess.setCreatedTime(new Date());
		customerApplicationProcess.setExamineAmount(examineAmount);
//		customerApplicationProcess.setDelayAuditUser(user.getId());//清空字段值 
		customerApplicationIntopieceWaitDao.updateCustomerApplicationProcessBySerialNumber(customerApplicationProcess);

		circle.setModifiedBy(user.getId());
		circle.setModifiedTime(new Date());
		commonDao.updateObject(circle);
		
	}
	
	/** 
	* @Title: getMeetingLsh 
	* @Description: 将会议安排在什么时间段
	* @param @param meeting
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws 
	*/ 
	public Integer getMeetingLsh(QzApplnApprovalMeeting meeting) {
		//查询该审贷员明天已有几个会议
		List<Integer> meeting_ls = customerApplicationIntopieceWaitDao.findLastMeetingByUserIdAndDate(meeting.getUserId(),meeting.getMeetingTime());
		//查询该客户经理明天已有几个会议
		List<Integer> meeting_ls2 = customerApplicationIntopieceWaitDao.findLastMeetingByManagerIdAndDate(meeting.getManagerId(),meeting.getMeetingTime());
		
		if(meeting_ls == null && meeting_ls2 == null){
			return 0;
		}
		if(meeting_ls != null && meeting_ls2 == null){
			meeting_ls2.add(Integer.valueOf(-1));
		}
		if(meeting_ls == null && meeting_ls2 != null){
			meeting_ls.add(Integer.valueOf(-1));
		}
		if(meeting_ls != null && meeting_ls2 != null){
			for(int i = 0;i<100;i++){
				if(!meeting_ls.contains(Integer.valueOf(i)) && !meeting_ls2.contains(Integer.valueOf(i))){
					return i;
				}
			}
		}
		return 0;
	}

	/** 
	 * 判断是否是周末 
	 * @return 
	 */  
	private boolean isWeekend(Calendar cal){  
	    int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
	    if(week ==6 || week==0){//0代表周日，6代表周六  
	        return true;  
	    }  
	    return false;  
	} 
	
}
