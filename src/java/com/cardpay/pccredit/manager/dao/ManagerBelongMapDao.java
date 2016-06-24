package com.cardpay.pccredit.manager.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.manager.filter.ManagerBelongMapFilter;
import com.cardpay.pccredit.manager.model.CenterBelongMap;
import com.cardpay.pccredit.manager.model.KhjljxbcForm;
import com.cardpay.pccredit.manager.model.ManagerBelongMap;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 描述 ：客户从属关系Dao
 * @author 张石树
 *
 * 2014-11-10 下午4:39:24
 */
@Mapper
public interface ManagerBelongMapDao {

	/**
	 * 获取跟节点
	 */
	List<ManagerBelongMap> getManagerBelongRoot();

	/**
	 * 根据parentId查询子节点信息
	 * @param parentId
	 * @return
	 */
	List<ManagerBelongMapForm> findByParentId(@Param("parentId") String parentId);

	/**
	 * 根据childId查询节点信息
	 * @param childId
	 * @return
	 */
	ManagerBelongMapForm findByChildId(@Param("childId") String childId);

	/**
	 * 分页查询信息
	 * @param filter
	 * @return
	 */
	List<ManagerBelongMapForm> findManagerBelongMapByFilter(ManagerBelongMapFilter filter);

	/**
	 * 分页查询统计 
	 * @param filter
	 * @return
	 */
	int findManagerBelongMapCountByFilter(ManagerBelongMapFilter filter);

	/**
	 * 根据客户经理从属的客户经理参数id查询客户经理信息
	 * @param childIdInStr
	 * @return
	 */
	List<AccountManagerParameterForm> findAccountManagerParameterByChildIds(@Param("childIdInStr") String childIdInStr);
	List<AccountManagerParameterForm> findAllManager();
	/**
	 * 找子节点
	 */
	public List<ManagerBelongMapForm> findChildId(@Param("id") String userId);
	
	public List<KhjljxbcForm> findManJxList(MaintenanceFilter filter);
	public int findManJxCountList(MaintenanceFilter filter);

	//查找所有中心
	List<CenterBelongMap> findCenterByParentId(@Param("parentId") String parentId);
	
}
