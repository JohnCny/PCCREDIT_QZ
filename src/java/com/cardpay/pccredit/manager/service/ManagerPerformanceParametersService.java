package com.cardpay.pccredit.manager.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.ManagerPerformanceParamersComdao;
import com.cardpay.pccredit.manager.model.TyPerformanceCenter;
import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-17 下午4:38:43
 */
@Service
public class ManagerPerformanceParametersService {
	
	
	@Autowired
	private CommonDao commonDao;
	
	
	@Autowired
	private ManagerPerformanceParamersComdao managerPerformanceParamersComdao;
	
	/**
	 * 客户经理绩效参数配置
	 * @return
	 */
	public List<TyPerformanceParameters> getManagerPerformanceParamers(){
	
		return managerPerformanceParamersComdao.getManagerPerformanceParamers();
		
	}
	
	/**
	 * 客户经理绩效参数配置
	 * @param request
	 */
	public void updateManagerPerformanceParamers(HttpServletRequest request) {
		
		String[] levelCodes= request.getParameterValues("levelCode");
		String[] managerLevels= request.getParameterValues("managerLevel");
		String[] basicPerformances= request.getParameterValues("basicPerformance");
		String[] As= request.getParameterValues("A");
		String[] Bs= request.getParameterValues("B");
		String[] Cs= request.getParameterValues("C");
		//删除历史记录
		managerPerformanceParamersComdao.deleteList();
		for(int i=0;i<levelCodes.length;i++){
			TyPerformanceParameters parameters = new TyPerformanceParameters();
			String levelCode= levelCodes[i];
			String managerLevel=managerLevels[i];
			String basicPerformance=basicPerformances[i];
			String a=As[i];
			String b=Bs[i];
			String c=Cs[i];
			parameters.setId(IDGenerator.generateID());
			parameters.setLevelCode(levelCode);
			parameters.setManagerLevel(managerLevel);
			parameters.setBasicPerformance(basicPerformance);
			parameters.setA(a);
			parameters.setB(b);
			parameters.setC(c);
			commonDao.insertObject(parameters);		
					
			}
		
	}
	
	public void updateManagerPerformanceCenter(HttpServletRequest request) {
		String[] levelCodes= request.getParameterValues("levelCode");
		String[] regularPay= request.getParameterValues("regularPay");
		String[] managerLevel= request.getParameterValues("managerLevel");
		
		//删除 his
		managerPerformanceParamersComdao.deleteCenterList();
		for(int i=0;i<levelCodes.length;i++){
			TyPerformanceCenter center = new TyPerformanceCenter();
			center.setCenterType("3");
			center.setCenterLevel(levelCodes[i]);
			center.setRegularPay(regularPay[i]);
			center.setManagerLevel(managerLevel[i]);
			commonDao.insertObject(center);	
		}
	}
	
	/**
	 * 获取特定客户经理绩效参数
	 * @return
	 */
	public TyPerformanceParameters getParameterByLevel(String customerId){
		String sql = "select * from ty_performance_parameters where level_code in (select level_information from account_manager_parameter  where user_id='"+customerId+"')";
		List<TyPerformanceParameters>tyPerformanceParameters = commonDao.queryBySql(TyPerformanceParameters.class, sql, null);
		if(tyPerformanceParameters.size()>0){
			return tyPerformanceParameters.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 中心人员绩效参数配置
	 * @return
	 */
	public List<TyPerformanceCenter> getManagerPerformanceCenter(){
	
		return managerPerformanceParamersComdao.getManagerPerformanceCenter();
		
	}
}
