package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.afterloan.model.AfterLoaninfo;
import com.cardpay.pccredit.report.dao.AfterAccLoanDao;
import com.cardpay.pccredit.report.filter.AccLoanCollectFilter;
import com.cardpay.pccredit.report.filter.OClpmAccLoanFilter;
import com.cardpay.pccredit.report.model.AccLoanCollectInfo;
import com.cardpay.pccredit.report.model.AccLoanCollectInfoNew;
import com.cardpay.pccredit.report.model.AccLoanInfo;
import com.cardpay.pccredit.report.model.AccLoanOverdueInfo;
import com.cardpay.pccredit.report.model.PsNormIntAmt;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 贷款借据清单
 * @author chinh
 *
 * 2015-8-1上午17:03:57
 */
@Service
public class AferAccLoanService {
	@Autowired
	private AfterAccLoanDao afterAccLoanDao;
	/**
     * 贷款借据清单
     * @param filter
     * @return
     */
	public QueryResult<AccLoanInfo> getAfterAccLoan(OClpmAccLoanFilter filter){
		List<AccLoanInfo> pList = afterAccLoanDao.getAfterAccLoan(filter);
		int size = afterAccLoanDao.getAfterAccLoanCount(filter);
		QueryResult<AccLoanInfo> qs = new QueryResult<AccLoanInfo>(size, pList);
		return qs;
	}
	
	public List<AccLoanInfo> getAfterAccLoanList(OClpmAccLoanFilter filter){
		return afterAccLoanDao.getAfterAccLoanAll(filter);
	}
	
	/**
     * 客户逾期清单
     * @param filter
     * @return
     */
	public QueryResult<AccLoanOverdueInfo> getLoanOverdue(OClpmAccLoanFilter filter){
		List<AccLoanOverdueInfo> pList = afterAccLoanDao.getLoanOverdue(filter);
		int size = afterAccLoanDao.getLoanOverdueCount(filter);
		QueryResult<AccLoanOverdueInfo> qs = new QueryResult<AccLoanOverdueInfo>(size, pList);
		return qs;
	}
	
	/**
     * 汇总查询
     * @param filter
     * @return
     */
	public List<AccLoanCollectInfo> getAccLoanCollect(AccLoanCollectFilter filter){
		return afterAccLoanDao.getAccLoanCollect(filter);
	}
	
	public List<AccLoanCollectInfoNew> getAccLoanCollectNew(AccLoanCollectFilter filter){
		List<AccLoanCollectInfoNew> accloanList = afterAccLoanDao.getAccLoanCollectNew(filter);
		
		for(AccLoanCollectInfoNew obj : accloanList){
			obj.setValue_1_1((obj.getValue_1_1_end()==null?0:obj.getValue_1_1_end())-(obj.getValue_1_1_start()==null?0:obj.getValue_1_1_start()));
			obj.setValue_1_2((obj.getValue_1_2_end()==null?0:obj.getValue_1_2_end())-(obj.getValue_1_2_start()==null?0:obj.getValue_1_2_start()));
			obj.setValue_4_1((obj.getValue_2_2()==null?0:obj.getValue_2_2())+(obj.getValue_3_2()==null?0:obj.getValue_3_2()));
			obj.setValue_4_3((obj.getValue_4_2()==null?0:obj.getValue_4_2())*100d/(obj.getValue_4_1()==null?0:obj.getValue_4_1()));
			obj.setValue_4_4((obj.getValue_2_1()==null?0:obj.getValue_2_1())+(obj.getValue_3_1()==null?0:obj.getValue_3_1()));
			obj.setValue_5_2((obj.getValue_5_2_end()==null?0:obj.getValue_5_2_end())-(obj.getValue_5_2_start()==null?0:obj.getValue_5_2_start()));
			obj.setValue_5_3((obj.getValue_5_3_end()==null?0:obj.getValue_5_3_end())-(obj.getValue_5_3_start()==null?0:obj.getValue_5_3_start()));
			obj.setValue_7_1((obj.getValue_7_1_end()==null?0:obj.getValue_7_1_end())-(obj.getValue_7_1_start()==null?0:obj.getValue_7_1_start()));
			obj.setValue_7_2((obj.getValue_7_2_end()==null?0:obj.getValue_7_2_end())-(obj.getValue_7_2_start()==null?0:obj.getValue_7_2_start()));
			obj.setValue_10_1((obj.getValue_10_1_end()==null?0:obj.getValue_10_1_end())-(obj.getValue_10_1_start()==null?0:obj.getValue_10_1_start()));
			obj.setValue_10_2((obj.getValue_10_2_end()==null?0:obj.getValue_10_2_end())-(obj.getValue_10_2_start()==null?0:obj.getValue_10_2_start()));
			obj.setValue_10_3((obj.getValue_10_1()==null?0:obj.getValue_10_1())*100d/(obj.getValue_10_2()==null?0:obj.getValue_10_2()));
		}
		
		return accloanList;
	}
	
	/**
	 * 根据客户Id查询台账信息
	 * @param customerId
	 * @return
	 */
	public List<AccLoanInfo> getAfterAccLoanByCustomerId(String customerId){
		return afterAccLoanDao.getAfterAccLoanByCustomerId(customerId);
	}

	//查询借据对应当前日期的还款计划表
	public QueryResult<PsNormIntAmt> getPsNormIntAmt(OClpmAccLoanFilter filter) {
		// TODO Auto-generated method stub
		List<PsNormIntAmt> pList = afterAccLoanDao.getPsNormIntAmt(filter);
		int size = afterAccLoanDao.getPsNormIntAmtCount(filter);
		QueryResult<PsNormIntAmt> qs = new QueryResult<PsNormIntAmt>(size, pList);
		return qs;
		
	}

	public List<PsNormIntAmt> getPsNormIntAmtList(OClpmAccLoanFilter filter) {
		// TODO Auto-generated method stub
		return afterAccLoanDao.getPsNormIntAmtList(filter);
	}

	public List<AccLoanOverdueInfo> getLoanOverdueAll(OClpmAccLoanFilter filter) {
		return afterAccLoanDao.getLoanOverdueAll(filter);
	}
	
}
