var validator = $($formName).validate({
	rules:
    { 
		address:{required:true},
		mobile:{required:true,number:true},
		applyReason:{required:true},
     },
messages:
    {
		address:{required:"地址不能为空"},
		mobile:{required:"电话不能为空",number:"电话只能为数字"},
		applyReason:{required:"申请原因不能为空"},
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});