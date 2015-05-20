package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.web.form.BaseForm;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年5月7日 上午10:41:20 
 * 审议纪要
 */
@ModelParam(table = "qz_appln_ywsqb_sdhjy")
public class QzSyjy extends BusinessModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993484474057696853L;
	private String customerId;
	private String jkrxm;
	private String sqje;
	private String sqqx;
	private String sqrq;
	private String sqll;
	private String dbfs;
	private String jkrys;
	private String jkrls;
	private String sfwz;
	private String zzjyjg;
	private String hswt;
	private String hsqk;
	private String dscyqz;
	private Date rq;
	private String zbkhjlqz;
	private String xzdcryqz;
	private Date rq1;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getJkrxm() {
		return jkrxm;
	}
	public void setJkrxm(String jkrxm) {
		this.jkrxm = jkrxm;
	}
	public String getSqje() {
		return sqje;
	}
	public void setSqje(String sqje) {
		this.sqje = sqje;
	}
	public String getSqqx() {
		return sqqx;
	}
	public void setSqqx(String sqqx) {
		this.sqqx = sqqx;
	}
	public String getSqrq() {
		return sqrq;
	}
	public void setSqrq(String sqrq) {
		this.sqrq = sqrq;
	}
	public String getSqll() {
		return sqll;
	}
	public void setSqll(String sqll) {
		this.sqll = sqll;
	}
	public String getDbfs() {
		return dbfs;
	}
	public void setDbfs(String dbfs) {
		this.dbfs = dbfs;
	}
	public String getJkrys() {
		return jkrys;
	}
	public void setJkrys(String jkrys) {
		this.jkrys = jkrys;
	}
	public String getJkrls() {
		return jkrls;
	}
	public void setJkrls(String jkrls) {
		this.jkrls = jkrls;
	}
	public String getSfwz() {
		return sfwz;
	}
	public void setSfwz(String sfwz) {
		this.sfwz = sfwz;
	}
	public String getZzjyjg() {
		return zzjyjg;
	}
	public void setZzjyjg(String zzjyjg) {
		this.zzjyjg = zzjyjg;
	}
	public String getHswt() {
		return hswt;
	}
	public void setHswt(String hswt) {
		this.hswt = hswt;
	}
	public String getHsqk() {
		return hsqk;
	}
	public void setHsqk(String hsqk) {
		this.hsqk = hsqk;
	}
	public String getDscyqz() {
		return dscyqz;
	}
	public void setDscyqz(String dscyqz) {
		this.dscyqz = dscyqz;
	}
	public Date getRq() {
		return rq;
	}
	public void setRq(Date rq) {
		this.rq = rq;
	}
	public String getZbkhjlqz() {
		return zbkhjlqz;
	}
	public void setZbkhjlqz(String zbkhjlqz) {
		this.zbkhjlqz = zbkhjlqz;
	}
	public String getXzdcryqz() {
		return xzdcryqz;
	}
	public void setXzdcryqz(String xzdcryqz) {
		this.xzdcryqz = xzdcryqz;
	}
	public Date getRq1() {
		return rq1;
	}
	public void setRq1(Date rq1) {
		this.rq1 = rq1;
	}
	
}
