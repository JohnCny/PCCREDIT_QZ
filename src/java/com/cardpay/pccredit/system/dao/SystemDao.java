package com.cardpay.pccredit.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.QZBankInterface.filter.EcifFilter;
import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.cardpay.pccredit.system.filter.SystemUserFilter;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface SystemDao {

	List<SystemUser> findSystemUserByFilter(SystemUserFilter filter);
	int findSystemUserCountByFilter(SystemUserFilter filter);
}
