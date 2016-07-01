/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：客户经理绩效参数
 * @author 贺珈
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="ty_performance_parameters",generator=IDType.uuid32)
public class TyPerformanceParameters extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String levelCode;
	
	private String managerLevel;
	
	private String basicPerformance;
	
	private String A;
	
	private String B;
	
	private String C;

	public String getManagerLevel() {
		return managerLevel;
	}

	public void setManagerLevel(String managerLevel) {
		this.managerLevel = managerLevel;
	}

	public String getBasicPerformance() {
		return basicPerformance;
	}

	public void setBasicPerformance(String basicPerformance) {
		this.basicPerformance = basicPerformance;
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
}
