var validator = $($formName).validate({
	rules:
    { 
		subBranchManagerSug:{required:true}
     },
messages:
    {
		subBranchManagerSug:{required:"支行负责人审批意见不能为空"}
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});