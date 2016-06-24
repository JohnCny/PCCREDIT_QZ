/**
 * 禁用页面组件
 */
function disableForm(){
	$("input[type='text']").attr("disabled",true);
	$("input[type='radio']").attr("disabled",true);
	$("input[type='checkbox']").attr("disabled",true);
	$("select").attr("disabled",true);
}
