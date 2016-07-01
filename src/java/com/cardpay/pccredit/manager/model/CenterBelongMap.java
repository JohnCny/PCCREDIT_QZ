/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：客户经理从属关系
 * @author 张石树
 *
 * 2014-11-10 下午2:07:01
 */
@ModelParam(table = "center_belong_map")
public class CenterBelongMap extends BusinessModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6563789115532739407L;
	private String name;
	private String parentId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
