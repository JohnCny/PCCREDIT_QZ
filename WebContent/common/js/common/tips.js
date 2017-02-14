/**
 * <pre>
 * 美化提示信息
 * </pre>
 * @author ps
 * @createTime 2012年12月19日 10:25:15
 */
var Tip = function ($){
	/**
	 * 美化提示方式
	 * @param selector jquery选择器
	 * @param title 提示信息
	 * @return
	 */
	function prettyTip(selector,title){
		var _jqobj = null;
		if(typeof selector == 'string'){
			_jqobj = $("#"+selector);
		}else{
			_jqobj = selector;
		}
		_jqobj.poshytip("update",title);
		_jqobj.poshytip("show");
		_jqobj.focus();
	}

	/**
	 * 为每个输入框增加提示信息
	 * @param selector
	 * @return
	 */
	function doTip(selector){
		var _jqobj = null;
		if(typeof selector == 'string'){
			_jqobj = $("#"+selector);
		}else{
			_jqobj = selector;
		}
		_jqobj.poshytip({
			className: 'tip-yellowsimple',
			showOn: 'none',
			alignTo: 'target',
			alignX: 'inner-left',
			timeOnScreen: 3000,
			offsetX: 0,
			offsetY: 5
		});
	}
	
	function alert(msg){
		$("#_showMsgDiv_").empty().remove();
		var showMsgDiv = $("<div id='_showMsgDiv_' align='center'>");
		var h = window.screen.availHeight/4;
		var w = window.screen.availWidth/3;
		var table = $("<table align='center' style='height:100%'>");
		var tr = "<tr><td style='text-align: center;font-size:12px;font-family:Consolas;'></td></tr>";
		table.append(tr);
		showMsgDiv.css({
			width:"250px",
			maxWidth:"300px",
			height:"100px",
			maxHeight:"300px",
			zIndex:10000,
			border:"1px solid #c7bf93",
			borderRadius:"4px",
			textAlign:"center",
			top:parseFloat(h),
			left:parseFloat(w),
			backgroundColor:"#fff9c9",
			color:"#000",
			display:"none",
			position:"absolute",
			opacity:0.9
		});
		msg = msg.replace(/\n/g,"</br>");
		table.find("td").html(msg);

		showMsgDiv.append(table);
		$("body").append(showMsgDiv);
		showMsgDiv.fadeIn("slow",function(){
			setTimeout(function(){showMsgDiv.fadeOut("slow");},3000);
		});
	}
	
	return {
		prettyTip : prettyTip,
		doTip : doTip,
		alert : alert
	};
}(jQuery);