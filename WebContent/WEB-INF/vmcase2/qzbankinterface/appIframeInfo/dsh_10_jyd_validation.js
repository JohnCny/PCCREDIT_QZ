var validator = $($formName).validate({
	rules:
    { 
		discussItems:{required:true},
		sugAmt:{required:true,number:true,max:100000},
		sugRate:{required:true,number:true,min:1,max:99}
     },
messages:
    {
		discussItems:{required:"审议事项不能为空"},
		sugAmt:{required:"建议金额不能为空",number:"建议金额只能为数字",max:"建议金额不能大于100000"},
		sugRate:{required:"建议利率不能为空",number:"建议利率只能为数字",min:"建议利率不能小于1",max:"建议利率不能大于99"}
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});