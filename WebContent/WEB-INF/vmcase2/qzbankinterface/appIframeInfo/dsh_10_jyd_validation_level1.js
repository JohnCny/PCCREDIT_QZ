var validator = $($formName).validate({
	rules:
    { 
		amt:{required:true,number:true},
		rate:{required:true,number:true,min:1,max:99},
		deadline:{required:true,number:true,min:1,max:60},
		focusContent:{required:true}
     },
messages:
    {
		amt:{required:"金额不能为空",number:"金额只能为数字"},
		rate:{required:"利率不能为空",number:"利率只能为数字",min:"利率不能小于1",max:"利率不能大于99"},
		deadline:{required:"授信期限不能为空",number:"授信期限只能为数字",min:"授信期限不能小于1",max:"授信期限不能大于60"},
		focusContent:{required:"重点监控内容不能为空"}
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});