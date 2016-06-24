package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxx;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxDkjl;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxFc;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxJdc;
import com.cardpay.pccredit.intopieces.model.QzApplnNbscyjb;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface NbscyjbDao {
	public QzApplnNbscyjb findNbscyjb(@Param("customerId")String customerId,@Param("applicationId")String applicationId);
	public QzApplnNbscyjb findNbscyjbByAppId(@Param("applicationId")String applicationId);
	public QzApplnNbscyjb findNbscyjbById(@Param("id")String id);
}
