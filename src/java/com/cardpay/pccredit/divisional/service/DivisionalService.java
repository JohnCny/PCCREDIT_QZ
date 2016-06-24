/**
 * 
 */
package com.cardpay.pccredit.divisional.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.QZBankInterface.client.Client;
import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.service.CircleService;
import com.cardpay.pccredit.QZBankInterface.service.IESBForSXInfo;
import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.service.CardInfomationService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.cardpay.pccredit.divisional.constant.DivisionalConstant;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.dao.DivisionalDao;
import com.cardpay.pccredit.divisional.dao.comdao.DivisionalCommDao;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.Divisional;
import com.cardpay.pccredit.divisional.model.DivisionalTransfer;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.cardpay.pccredit.ipad.util.IpadException;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.service.NotificationService;
import com.cardpay.pccredit.riskControl.constant.RiskType;
import com.cardpay.pccredit.riskControl.service.AccountabilityService;
import com.cardpay.pccredit.system.model.Dict;
import com.dc.eai.data.CompositeData;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;
import com.wicresoft.jrad.modules.privilege.service.impl.OrganizationServiceImpl;
import com.wicresoft.util.spring.Beans;

/**
 * 移交客户
 * 
 * @author Evans zhang
 * 
 *         2014-10-15 上午11:23:08
 */
@Service
public class DivisionalService {

	@Autowired
	private DivisionalDao divisionaldao;
	@Autowired
	private CircleService circleService;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private CustomerInforDao customerInforDao;
	@Autowired
	private DivisionalCommDao divisionalcommDao;
	@Autowired
	private CustomerInforDao customerinforDao;
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private CardInfomationService cardInfomationService;
	
	@Autowired
	private AccountabilityService accountabilityService;
	
	@Autowired
	private DivisionalReceiveService divisionalReceiveService;

	@Autowired
	private IESBForSXInfo iESBForSXInfo;
	
	@Autowired
	private Client client;
	
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * 移交方法
	 * 
	 * @param string
	 *            customerId
	 * 
	 * @return boolean
	 */

	public boolean insertDivisionalCustomer(String customerId,
			DivisionalTypeEnum divisionalEnum,
			DivisionalProgressEnum divisionalProgressEnum) {
		// 先通过customerId 找到经理Id
		String userId = customerInforDao.findCustomerManagerIdById(customerId);
		if (StringUtils.isNotEmpty(userId)) {
			// 先通过customerId 找到机构Id
			Organization organization = Beans
					.get(OrganizationService.class).findOrgByUserId(userId);

			Divisional divisional = new Divisional();
			divisional.setOriginalManagerOld(userId);
			divisional.setOriginalOrganizationOld(organization.getId());
			divisional.setCurrentOrganizationId(organization.getId());
			divisional.setDivisionalProgress(divisionalProgressEnum.toString());
			divisional.setDivisionalType(divisionalEnum.toString());
			divisional.setCustomerId(customerId);
			divisional.setCreatedTime(new Date());
			divisional.setCreatedBy(userId);
			int i = commonDao.insertObject(divisional);
			if (i > 0) {
				if(customerInforService.updateCustomerInforDivisionalStatus(customerId, CustomerInforDStatusEnum.turn)){
					//调用问责接口  查询客户下的产品
					List<CardInfomationFrom> results = cardInfomationService.findCardsByCustomerId(customerId);	
					for(CardInfomationFrom cardInfomationFrom:results){
						accountabilityService.insertAccountAbility(userId, customerId, cardInfomationFrom.getProductId(), RiskType.turn, "主动移交产生的问责", userId);
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			//移交到卡中心
			Divisional divisional = new Divisional();
			divisional.setOriginalManagerOld(userId);
			divisional.setDivisionalProgress(DivisionalProgressEnum.cfcc.toString());
			divisional.setDivisionalType(divisionalEnum.toString());
			divisional.setCustomerId(customerId);
			divisional.setCreatedTime(new Date());
			divisional.setCreatedBy(userId);
			int i = commonDao.insertObject(divisional);
			if (i > 0) {
				customerInforService.updateCustomerInforDivisionalStatus(customerId, CustomerInforDStatusEnum.turn);
				return true;
			} else {
				return false;
			}
		}
	}

	//申请移交客户-泉州
	public void insertDivisionalCustomer_qz(String customerId,
			DivisionalTypeEnum divisionalEnum,
			DivisionalProgressEnum divisionalProgressEnum) {
		String userId = customerInforDao.findCustomerManagerIdById(customerId);
		Divisional divisional = new Divisional();
		divisional.setOriginalManagerOld(userId);
		divisional.setOriginalOrganizationOld(null);//泉州移交跟原机构无关
		divisional.setCurrentOrganizationId(null);
		divisional.setDivisionalProgress(divisionalProgressEnum.toString());
		divisional.setDivisionalType(divisionalEnum.toString());
		divisional.setCustomerId(customerId);
		divisional.setCreatedTime(new Date());
		divisional.setCreatedBy(userId);
		commonDao.insertObject(divisional);
		
		customerInforService.updateCustomerInforDivisionalStatus(customerId, CustomerInforDStatusEnum.turn);
	}
	
	//申请移交客户-信贷
	public void insertDivisionalCustomer_xd_qz(String customerId,
			DivisionalTypeEnum divisionalEnum,
			DivisionalProgressEnum divisionalProgressEnum,String orgId,String manegerUserId,String changeBelong) {
		String userId = customerInforDao.findCustomerManagerIdById(customerId);
		Divisional divisional = new Divisional();
		divisional.setOriginalManagerOld(userId);
		divisional.setOriginalOrganizationOld(null);//泉州移交跟原机构无关
		divisional.setCurrentOrganizationId(orgId);
		divisional.setCustomerManagerId(manegerUserId);
		divisional.setDivisionalProgress(divisionalProgressEnum.toString());
		divisional.setDivisionalType(divisionalEnum.toString());
		divisional.setCustomerId(customerId);
		divisional.setCreatedTime(new Date());
		divisional.setCreatedBy(userId);
		divisional.setChangeBelong(changeBelong);
		commonDao.insertObject(divisional);
		
		customerInforService.updateCustomerInforDivisionalStatus(customerId, CustomerInforDStatusEnum.turn);
	}
	
	/**
	 * 获得分案申请表信息
	 * 
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalWeb> findDivisional(DivisionalFilter filter) {
		return divisionalcommDao.findDivisional(filter);
	}
	
	//团队长分案-泉州（不做任何限制  所有的分案  经由团队长处理 因为有的存量客户所属客户经理不是微贷中心的成员）
	public QueryResult<DivisionalWeb> findDivisional_qz(DivisionalFilter filter) {
		List<DivisionalWeb> pList = divisionaldao.findDivisional_qz(filter);
		int size = divisionaldao.findDivisional_qz_count(filter);
		QueryResult<DivisionalWeb> queryResult = new QueryResult<DivisionalWeb>(size, pList);
		
		return queryResult;
	}
	/**
	 * 获得移交客户页面所需信息
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalTransfer> findDivisionalTransfer(DivisionalFilter filter) {
		List<DivisionalTransfer> pList = divisionaldao.findDivisionalTransfer(filter);
		int size = divisionaldao.findDivisionalTransferCount(filter);
		QueryResult<DivisionalTransfer> queryResult = new QueryResult<DivisionalTransfer>(size, pList);
		
		return queryResult;
	}
	/**
	 * 获得客户经理信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Dict> findCustomerManagers(String id) {
		return divisionaldao.findCustomerManagers(id);
	}

	/**
	 * 获得客户信息吧ById
	 * 
	 * @param divisionalId
	 * @return
	 */
	public String findCustomerIdById(String divisionalId) {
		return divisionaldao.findCustomerIdById(divisionalId);
	}

	/**
	 * 更新分案申请信息
	 * 
	 * @param id
	 * @param customerManagerId
	 * @param orgId
	 * @return
	 * @throws Exception 
	 * @throws IpadException 
	 */
	public void updateDivisional(String id, String customerManagerId,String orgId, String result) throws Exception {
		//判断是微贷移交还是信贷移交
		Divisional divisional = commonDao.findObjectById(Divisional.class, id);
		if(divisional.getDivisionalType().equals(DivisionalTypeEnum.compulsive.toString())){//信贷移交
			
			//调用接口到信贷
			Circle circle = circleService.findCircleApproved(divisional.getCustomerId()).get(0);
			CompositeData req = iESBForSXInfo.createRequest("01", circle, null,divisional);
			CompositeData resp = client.sendMess(req);
			String ret_code = iESBForSXInfo.parseResponse(resp);
			if(!ret_code.equals(IpadConstant.RET_CODE_SUCCESS)){
				throw new Exception(ret_code);
			}
			else{
				customerinforDao.updateCustomerInfor(divisional.getCustomerId(), divisional.getCustomerManagerId(),CustomerInforDStatusEnum.complete.toString());
				divisionaldao.updateDivisionalResultAndProcess(id, DivisionalConstant.RECEIVED, DivisionalConstant.MANAGER);
				
				String oldManagerName = this.findUserNameByUserId(divisional.getOriginalManagerOld());
			    String ManagerName = this.findUserNameByUserId(divisional.getCustomerManagerId());
				notificationService.insertNotification(NotificationEnum.qita, divisional.getOriginalManagerOld(), DivisionalConstant.RECEIVESUCCESS, oldManagerName+"向"+ManagerName+DivisionalConstant.RECEIVESUCCESS, divisional.getCustomerManagerId());
				
				User oldUser = commonDao.findObjectById(User.class, divisional.getOriginalManagerOld());
				User newUser = commonDao.findObjectById(User.class, divisional.getCustomerManagerId());
				String sql = "select * from qz_iesb_for_circle where customer_id = '"+divisional.getCustomerId()+"'";
				List<Circle> circle_ls = commonDao.queryBySql(Circle.class, sql, null);
				if(divisional.getChangeBelong() != null && divisional.getChangeBelong().equals("1")){
					//更新用户相关的表
					//1.basic_customer_infomation -- user_id字段
					//2.qz_iesb_for_ecif -- user_id字段
					//3.qz_iesb_for_circle -- user_id字段
					//4.psp_check_task -- manager_id字段
					sql = "update basic_customer_information set user_id = '"+newUser.getId()+"' where id = '"+divisional.getCustomerId()+"'";
					commonDao.queryBySql(sql, null);
					sql = "update qz_iesb_for_ecif set user_id = '"+newUser.getId()+"' where customer_id = '"+divisional.getCustomerId()+"'";
					commonDao.queryBySql(sql, null);
					sql = "update qz_iesb_for_circle set user_id = '"+newUser.getId()+"' where customer_id = '"+divisional.getCustomerId()+"'";
					commonDao.queryBySql(sql, null);
					if(circle_ls != null && circle_ls.size() > 0){
						sql = "update psp_check_task set manager_id = '"+newUser.getLogin()+"' where cus_id = '"+circle_ls.get(0).getClientNo()+"'";
						commonDao.queryBySql(sql, null);
					}
				}
			}
			
		}
		else{//微贷
			divisionaldao.updateDivisional(id, customerManagerId, orgId,result);
		}
		
	}

	//团队长退回进件
	public void returnDivisional(String id,String customerId) {
		divisionaldao.returnDivisional(id);
		divisionaldao.returnDivisional_bci(customerId);
	}
	
	/**
	 * 通过id得到分案结果
	 * 
	 * @param id
	 * @return
	 */
	public String findDivisionalResultById(String id) {
		return divisionaldao.findDivisionalResultById(id);
	}
	/**
	 * 通过id得到分案进度
	 * 
	 * @param id
	 * @return
	 */
	public String findDivisionalProcessById(String id){
		return divisionaldao.findDivisionalProcessById(id);
	}
	/**
	 * 将分案申请上传到 卡中心
	 * 
	 * @param id
	 * @return
	 */
	public boolean uploadToCardCenter(String id, String process) {
		int i=divisionaldao.updateDivisionalProcessToCardCenter(id, process);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Divisional findDivisinalById(String id){
		return commonDao.findObjectById(Divisional.class, id);
	}
	/**
	 * 统计今日分案申请数量 ()
	 * @return
	 */
	public int findDivisionalCounsToday(String customerManagerId,String result,String process){
		return divisionaldao.findDivisionalCounsToday(customerManagerId, result, process);
	}

	public List<UserIpad> getAllUsers() {
		// TODO Auto-generated method stub
		return divisionaldao.getAllUsers();
	}
	
	public String findUserNameByUserId(String id){
		return divisionaldao.getUserNameByUserId(id);
	}
}
