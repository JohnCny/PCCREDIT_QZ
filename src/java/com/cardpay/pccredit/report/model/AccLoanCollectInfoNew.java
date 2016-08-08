package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

/**    
* @Title: AccLoanCollectInfoNew.java  
* @Package com.cardpay.pccredit.report.model  
* @Description: 重做的汇总统计  
* @author tanwh    
* @date 2016年7月24日 下午10:34:23  
* @version V1.0    
*/
public class AccLoanCollectInfoNew extends BusinessModel{
	
	private static final long serialVersionUID = -7513388150092036959L;
	
	private String rowIndex;
	private Integer value_1_1_start;private Integer value_1_1_end;private Integer value_1_1;//计算
	private Double value_1_2_start;private Double value_1_2_end;private Double value_1_2;//计算
	private Integer value_2_1;
	private Double value_2_2;
	private Double value_3_1;
	private Double value_3_2;
	private Double value_4_1;//计算
	private Double value_4_2;
	private Double value_4_3;//计算
	private Double value_4_4;//计算
	private Integer value_5_1;
	private Integer value_5_2_start;private Integer value_5_2_end;private Integer value_5_2;//计算
	private Double value_5_3_start;private Double value_5_3_end;private Double value_5_3;//计算
	private Double value_6_3;
	private Integer value_7_1_start;private Integer value_7_1_end;private Integer value_7_1;//计算
	private Double value_7_2_start;private Double value_7_2_end;private Double value_7_2;//计算
	private Integer value_8_1;
	private Integer value_8_2;
	private Integer value_8_3;
	private Integer value_8_4;
	private Integer value_9_1;
	private Double value_9_2;
	private Double value_10_1_start;private Double value_10_1_end;private Double value_10_1;//计算
	private Double value_10_2_start;private Double value_10_2_end;private Double value_10_2;//计算
	private Double value_10_3;//计算
	private Double value_11_1;
	private Double value_11_2;
	private Integer value_11_3;
	
	public String getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
	public Integer getValue_1_1_start() {
		return value_1_1_start;
	}
	public void setValue_1_1_start(Integer value_1_1_start) {
		this.value_1_1_start = value_1_1_start;
	}
	public Integer getValue_1_1_end() {
		return value_1_1_end;
	}
	public void setValue_1_1_end(Integer value_1_1_end) {
		this.value_1_1_end = value_1_1_end;
	}
	public Integer getValue_1_1() {
		return value_1_1;
	}
	public void setValue_1_1(Integer value_1_1) {
		this.value_1_1 = value_1_1;
	}
	public Double getValue_1_2_start() {
		return value_1_2_start;
	}
	public void setValue_1_2_start(Double value_1_2_start) {
		this.value_1_2_start = value_1_2_start;
	}
	public Double getValue_1_2_end() {
		return value_1_2_end;
	}
	public void setValue_1_2_end(Double value_1_2_end) {
		this.value_1_2_end = value_1_2_end;
	}
	public Double getValue_1_2() {
		return value_1_2;
	}
	public void setValue_1_2(Double value_1_2) {
		this.value_1_2 = value_1_2;
	}
	public Integer getValue_2_1() {
		return value_2_1;
	}
	public void setValue_2_1(Integer value_2_1) {
		this.value_2_1 = value_2_1;
	}
	public Double getValue_2_2() {
		return value_2_2;
	}
	public void setValue_2_2(Double value_2_2) {
		this.value_2_2 = value_2_2;
	}
	public Double getValue_3_1() {
		return value_3_1;
	}
	public void setValue_3_1(Double value_3_1) {
		this.value_3_1 = value_3_1;
	}
	public Double getValue_3_2() {
		return value_3_2;
	}
	public void setValue_3_2(Double value_3_2) {
		this.value_3_2 = value_3_2;
	}
	public Double getValue_4_1() {
		return value_4_1;
	}
	public void setValue_4_1(Double value_4_1) {
		this.value_4_1 = value_4_1;
	}
	public Double getValue_4_2() {
		return value_4_2;
	}
	public void setValue_4_2(Double value_4_2) {
		this.value_4_2 = value_4_2;
	}
	public Double getValue_4_3() {
		return value_4_3;
	}
	public void setValue_4_3(Double value_4_3) {
		this.value_4_3 = value_4_3;
	}
	public Double getValue_4_4() {
		return value_4_4;
	}
	public void setValue_4_4(Double value_4_4) {
		this.value_4_4 = value_4_4;
	}
	public Integer getValue_5_1() {
		return value_5_1;
	}
	public void setValue_5_1(Integer value_5_1) {
		this.value_5_1 = value_5_1;
	}
	public Integer getValue_5_2_start() {
		return value_5_2_start;
	}
	public void setValue_5_2_start(Integer value_5_2_start) {
		this.value_5_2_start = value_5_2_start;
	}
	public Integer getValue_5_2_end() {
		return value_5_2_end;
	}
	public void setValue_5_2_end(Integer value_5_2_end) {
		this.value_5_2_end = value_5_2_end;
	}
	public Integer getValue_5_2() {
		return value_5_2;
	}
	public void setValue_5_2(Integer value_5_2) {
		this.value_5_2 = value_5_2;
	}
	public Double getValue_5_3_start() {
		return value_5_3_start;
	}
	public void setValue_5_3_start(Double value_5_3_start) {
		this.value_5_3_start = value_5_3_start;
	}
	public Double getValue_5_3_end() {
		return value_5_3_end;
	}
	public void setValue_5_3_end(Double value_5_3_end) {
		this.value_5_3_end = value_5_3_end;
	}
	public Double getValue_5_3() {
		return value_5_3;
	}
	public void setValue_5_3(Double value_5_3) {
		this.value_5_3 = value_5_3;
	}
	public Double getValue_6_3() {
		return value_6_3;
	}
	public void setValue_6_3(Double value_6_3) {
		this.value_6_3 = value_6_3;
	}
	public Integer getValue_7_1_start() {
		return value_7_1_start;
	}
	public void setValue_7_1_start(Integer value_7_1_start) {
		this.value_7_1_start = value_7_1_start;
	}
	public Integer getValue_7_1_end() {
		return value_7_1_end;
	}
	public void setValue_7_1_end(Integer value_7_1_end) {
		this.value_7_1_end = value_7_1_end;
	}
	public Integer getValue_7_1() {
		return value_7_1;
	}
	public void setValue_7_1(Integer value_7_1) {
		this.value_7_1 = value_7_1;
	}
	public Double getValue_7_2_start() {
		return value_7_2_start;
	}
	public void setValue_7_2_start(Double value_7_2_start) {
		this.value_7_2_start = value_7_2_start;
	}
	public Double getValue_7_2_end() {
		return value_7_2_end;
	}
	public void setValue_7_2_end(Double value_7_2_end) {
		this.value_7_2_end = value_7_2_end;
	}
	public Double getValue_7_2() {
		return value_7_2;
	}
	public void setValue_7_2(Double value_7_2) {
		this.value_7_2 = value_7_2;
	}
	public Integer getValue_8_1() {
		return value_8_1;
	}
	public void setValue_8_1(Integer value_8_1) {
		this.value_8_1 = value_8_1;
	}
	public Integer getValue_8_2() {
		return value_8_2;
	}
	public void setValue_8_2(Integer value_8_2) {
		this.value_8_2 = value_8_2;
	}
	public Integer getValue_8_3() {
		return value_8_3;
	}
	public void setValue_8_3(Integer value_8_3) {
		this.value_8_3 = value_8_3;
	}
	public Integer getValue_8_4() {
		return value_8_4;
	}
	public void setValue_8_4(Integer value_8_4) {
		this.value_8_4 = value_8_4;
	}
	public Integer getValue_9_1() {
		return value_9_1;
	}
	public void setValue_9_1(Integer value_9_1) {
		this.value_9_1 = value_9_1;
	}
	public Double getValue_9_2() {
		return value_9_2;
	}
	public void setValue_9_2(Double value_9_2) {
		this.value_9_2 = value_9_2;
	}
	public Double getValue_10_1_start() {
		return value_10_1_start;
	}
	public void setValue_10_1_start(Double value_10_1_start) {
		this.value_10_1_start = value_10_1_start;
	}
	public Double getValue_10_1_end() {
		return value_10_1_end;
	}
	public void setValue_10_1_end(Double value_10_1_end) {
		this.value_10_1_end = value_10_1_end;
	}
	public Double getValue_10_1() {
		return value_10_1;
	}
	public void setValue_10_1(Double value_10_1) {
		this.value_10_1 = value_10_1;
	}
	public Double getValue_10_2_start() {
		return value_10_2_start;
	}
	public void setValue_10_2_start(Double value_10_2_start) {
		this.value_10_2_start = value_10_2_start;
	}
	public Double getValue_10_2_end() {
		return value_10_2_end;
	}
	public void setValue_10_2_end(Double value_10_2_end) {
		this.value_10_2_end = value_10_2_end;
	}
	public Double getValue_10_2() {
		return value_10_2;
	}
	public void setValue_10_2(Double value_10_2) {
		this.value_10_2 = value_10_2;
	}
	public Double getValue_10_3() {
		return value_10_3;
	}
	public void setValue_10_3(Double value_10_3) {
		this.value_10_3 = value_10_3;
	}
	public Double getValue_11_1() {
		return value_11_1;
	}
	public void setValue_11_1(Double value_11_1) {
		this.value_11_1 = value_11_1;
	}
	public Double getValue_11_2() {
		return value_11_2;
	}
	public void setValue_11_2(Double value_11_2) {
		this.value_11_2 = value_11_2;
	}
	public Integer getValue_11_3() {
		return value_11_3;
	}
	public void setValue_11_3(Integer value_11_3) {
		this.value_11_3 = value_11_3;
	}
}
