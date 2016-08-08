var validator = $($formName).validate({
	rules:
    { 
		centerSug:{required:true}
     },
messages:
    {
		centerSug:{required:"中心审查岗意见不能为空"}
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});