var validator = $($formName).validate({
	rules : {
		year : {
			required : true
		},
		customerManagerId :{
			required : true
		},
		activeCustomerNum :{
			required : true
		},
		actualActiveCustomerNum :{
			required : true
		},
		monthActualReceiveIncome :{
			required : true
		},
		otherRetailProductAwards :{
			required : true
		},
		/*balanceDailyAverage :{
			required : true
		},*/
		overdueLoan :{
			required : true
		},
		accountabilityRiskMargin :{
			required : true
		}/*,
		monthAverageKpiScore :{
			required : true
		}*/
	},
	messages : {
		year : {
			required : "日期必输"
		},
		customerManagerId :{
			required : "客户经理必输"
		},
		activeCustomerNum :{
			required : "当月新增活跃客户数必输"
		},
		actualActiveCustomerNum :{
			required : "当月实际活跃客户数必输"
		},
		monthActualReceiveIncome :{
			required : "当月所管理的客户用信实收利息收入必输"
		},
		otherRetailProductAwards :{
			required : "行内其他零售产品奖励必输"
		},
		/*balanceDailyAverage :{
			required : "季度日均用信余额必输"
		},*/
		overdueLoan :{
			required : "逾期贷款率必输"
		},
		accountabilityRiskMargin :{
			required : "本月问责风险保证金必输"
		}/*,
		monthAverageKpiScore :{
			required : "月均KPI得分(季度)必输"
		}*/
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});

