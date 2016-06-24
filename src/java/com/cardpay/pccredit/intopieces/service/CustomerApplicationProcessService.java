package com.cardpay.pccredit.intopieces.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationInfoDao;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationProcessDao;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationInfoFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.system.model.NodeControl;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class CustomerApplicationProcessService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationProcessDao customerApplicationProcessDao;

	/**
	 * 根据appid获取
	 * @return
	 */
	public CustomerApplicationProcess findByAppId(String appId){
		return customerApplicationProcessDao.findByAppId(appId);
	}

	/**
	 * 根据id
	 * @return
	 */
	public CustomerApplicationProcess findById(String id){
		return customerApplicationProcessDao.findById(id);
	}
	
	/**
	 * 根据当前节点，获取上一节点
	 * @param currentStatus
	 * @param exUserID
	 * @param exResult
	 * @return
	 * @throws SQLException
	 */
	public NodeControl getLastStatus(String id) {
		String sql = "select * from node_control where next_node = #{id}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<NodeControl> list = commonDao.queryBySql(NodeControl.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
}
