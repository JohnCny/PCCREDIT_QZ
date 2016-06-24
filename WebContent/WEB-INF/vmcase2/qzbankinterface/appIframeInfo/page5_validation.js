var validator = $($formName).validate({
	rules:
    { 
		//user_2:{required:true},
		//user_3:{required:true},
		//user_4:{required:true}
     },
messages:
    {
		//user_2:{required:"行政岗不能为空"},
		//user_3:{required:"协助调查人员不能为空"},
		//user_4:{required:"贷审会成员不能为空"}
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});