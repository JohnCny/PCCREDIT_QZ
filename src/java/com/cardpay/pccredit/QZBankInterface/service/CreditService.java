package com.cardpay.pccredit.QZBankInterface.service;

import com.cardpay.pccredit.QZBankInterface.client.Client;
import com.cardpay.pccredit.QZBankInterface.constant.Constant;
import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.Credit;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.dc.eai.data.CompositeData;

import java.util.Date;

import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by johhny on 15/4/22.
 */
@Service
public class CreditService {
    @Autowired
    private CommonDao commonDao;
    
    @Autowired
	private IESBForCredit iesbForCredit;
    
    @Autowired
	private Client client;
    
    @Autowired
	private ECIFService ecifService;
    
    /**
     * 插入数据
     * @param credit
     * @return
     */
	public void insertCustomerInforCredit(Credit credit) {
		
		//组包
		CompositeData req = iesbForCredit.createCommonCreditRequest(credit);
		//发送
		CompositeData resp = client.sendMess(req);
		//解析，存db
		boolean res = iesbForCredit.parseEcifResponse(resp);
		//如果成功，保存credit，并置ecif状态
		if(res){
			String id = IDGenerator.generateID();
			credit.setId(id);
			credit.setCreatedTime(new Date());
			commonDao.insertObject(credit);
			
			ECIF ecif = ecifService.findEcifByClientNo(credit.getClientNo());
			commonDao.updateObject(ecif);
		}
		
	}
}
