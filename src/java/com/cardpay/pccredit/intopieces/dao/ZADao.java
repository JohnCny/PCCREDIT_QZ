package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.QuotaFreezeOrThawFilter;
import com.cardpay.pccredit.intopieces.filter.ZAFilter;
import com.cardpay.pccredit.intopieces.model.QuotaFreezeInfo;
import com.cardpay.pccredit.intopieces.model.QzApplnZa;
import com.cardpay.pccredit.intopieces.model.QzApplnZaReturnMap;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface ZADao {
	public QzApplnZa findZaById(@Param("id")String id);
	public List<QzApplnZa> findAllZaByFilter(ZAFilter filter);
	public int findAllZaCountByFilter(ZAFilter filter);
	public void deleteZAById(@Param("id")String id);
	public List<QzApplnZaReturnMap> findZas();
	public List<QzApplnZaReturnMap> findZasApproved();
	public List<QzApplnZa> getZaWFByFilter(QuotaFreezeOrThawFilter filter);
	public int getZaCountWFByFilter(QuotaFreezeOrThawFilter filter);
}
