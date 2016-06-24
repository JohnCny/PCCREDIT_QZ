package com.cardpay.pccredit.intopieces.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.CustomerApplicationInfoFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerApplicationProcessDao {
    int deleteByPrimaryKey(String id);

    int insert(CustomerApplicationInfo record);

    int insertSelective(CustomerApplicationInfo record);

    CustomerApplicationInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerApplicationInfo record);

    int updateByPrimaryKey(CustomerApplicationInfo record);
    
  
    public CustomerApplicationProcess findByAppId(String appId);
    
    public CustomerApplicationProcess findById(String id);
}