package com.cardpay.pccredit.intopieces.dao;

import java.util.List;
import com.cardpay.pccredit.intopieces.model.SqlInputPojo;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface SqlInputDao {
	public List<SqlInputPojo> getTableNames();
}
