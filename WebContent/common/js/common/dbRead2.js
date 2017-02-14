/**
 * <pre>
 * 数据库查看
 * </pre>
 * @author ps
 * @createTime 2013年1月18日 16:49:33
 */

var DbRead = function($){
	var pageList = null;
	var totalCount = 0;
	var curIndex = 0;
	var isPageClick = false;
	
	function doQueryMulti(){
		isPageClick = false;
		curIndex = 0;
		pageList = new Array();
		//var sqlList = new Array();
		var sql = DbRead.SQL;
		sqls = sql.split(";");
		var sqlList = "";
		for ( var i = 0; i < sqls.length; i++) {
			if($.trim(sqls[i]) == ""){
				continue;
			}
			sqlList += sqls[i] + ";";
		}
		var url = contextPath + "/intopieces/sqlinputquery/getDbDataMulti.json";
		var formjson = {"sqls":sqlList};
		$.ajax({
			url : url,
			type : "get",
			data : formjson,
			success : function(data) {
				if (data.success) {

					var result = data.list;
					$("#todo").val("查询");
					$("#todo").attr("disabled",false);
					
					$("div[id^=selectDiv]").empty().remove();
					$("#selectTable").empty();
					if(result.length == 0){
						return;
					}
					var selectTable = $("#selectTable");
					var selectRow = $("<tr>");
					selectTable.append(selectRow);
					//遍历所有查询结果
					for ( var k = 0; k < result.length; k++) {
						var selectTd = $("<td>");
						selectTd.attr("id","selectRow"+k);
						selectTd.attr("title",sqls[k]);
						selectTd.attr("width","10%");
						selectTd.append("<span>"+k+"</span>");
						selectTd.addClass("unselected");
						//点击事件时，显示点击的div
						selectTd.bind("click",function(){
							var rowId = $(this).attr("id");
							rowId = rowId.substr(rowId.length-1,1);
							curIndex = rowId;
							$("div[id^=selectDiv]").each(function(index){
								var divId = $(this).attr("id");
								divId = divId.substr(divId.length-1,1);
								if(divId == rowId){
									$(this).css("display","");
									$("#selectRow"+divId).addClass("selected");
									$("#selectRow"+divId).addClass("selected");
									toggleClass($("#selectRow"+divId), "selected", "unselected");
								}else{
									$(this).css("display","none");
									toggleClass($("#selectRow"+divId), "unselected", "selected");
								}
							});
						});
						selectRow.append(selectTd);
						
						
						if(result[k] != null){
							doPlainTable(result[k], 1, k);
							var page2 = new Page2();
							page2.sql = sqls[k];
							page2.id = ""+k;
							page2.positionId = "page"+k;
							pageList[k] = page2;
							page2.showPageHtml(totalCount, 1, 10);
						}else{
							var selectDiv = $("<div id='selectDiv"+k+"'>");
							selectDiv.css("display","none");
							selectDiv.html("查询失败");
							$("body").append(selectDiv);
						}
					}
					
					$("#selectDiv0").css("display","");
					toggleClass($("#selectRow0"), "selected", "unselected");
				}
				else{
					$("#todo").val("查询");
					$("#todo").attr("disabled", false);
					topWin.Dialog.message(data.message);
			    }
			}
		});
	}
	function toggleClass(obj,addClass,removeClass){
		if(typeof(obj) == "string"){
			$("#"+obj).removeClass(removeClass);
			$("#"+obj).addClass(addClass);
		}else{
			obj.removeClass(removeClass);
			obj.addClass(addClass);
		}
	}
	
	function doPlainTable(data,curPage,index){
		if(!isPageClick){
			$("#dataTable").empty();
		}
		
		var selectDiv = $("<div id='selectDiv"+index+"'>");
		selectDiv.css("display","none");
		var scrollDiv = $("<div class='scrollbar'>");
		var table = $("<table cellpadding='0' cellspacing='0' border=1 class=listcenter></table>");
		table.attr("id","dataTable"+index);
		//总条数
		totalCount = data[0];
		var th = $("<tr>");
		th.css("background-color","rgb(217, 233, 255)");
		th.css("color","#104184");
		th.css("background-image","url('../images/table/headerOverBg.gif')");
		//标题部分
		var meta = data[1];
		//i=1,是去掉trownum
		for(var i = 1;i < meta.length;i ++){
			var td = $("<th>");
			td.html(meta[i]);
			
			th.append(td);
		}
		
		table.append(th);
		
		//数据部分
		var odd = 1;
		for(var row = 2;row < data.length;row ++){
			var tr = $("<tr>");
			tr.css("background-color",odd%2==0?"rgb(243, 244, 243)":"rgb(255, 255, 255)");
			tr.attr("name",odd);
			//当鼠标悬挂在元素上时，背景颜色的切换
			tr.hover(
				function () {
					$(this).css("background-color","#3399FF");
				},
				function () {
					var _odd = $(this).attr("name");
					$(this).css("background-color",_odd%2==0?"rgb(243, 244, 243)":"rgb(255, 255, 255)");
			});
			odd ++;
			
			for(var j = 1;j < meta.length;j ++){
				var td = $("<td>");
				td.css("word-break","keep-all");
				td.css("word-wrap","normal");
				td.css("white-space","nowrap");
				td.attr("title",data[row][meta[j]] == null?" ":data[row][meta[j]]);
				td.html(data[row][meta[j]] == null?" ":data[row][meta[j]]);
				tr.append(td);
			}
			table.append(tr);
		}
		scrollDiv.append(table);
		selectDiv.append(scrollDiv);
		selectDiv.append("<br/><div id='page"+index+"' style='height:30px;text-align:center;margin-top: 15px;'>");
		$("body").append(selectDiv);
	}
	
	function doQuery(curPage){
		//获取网页选定文本
		
		var sql = pageList[curIndex].sql;
		isPageClick = true;
		
		var url = contextPath +"/intopieces/sqlinputquery/getDbData.json";
		var formjson = {"sql":sql,"curPage":curPage};
		$.ajax({
			url : url,
			type : "get",
			data : formjson,
			success : function(data) {
				if (data.success) {

					var result = data.list;
					$("#todo").val("查询");
					$("#todo").attr("disabled",false);
					
					$("#selectDiv"+curIndex).empty().remove();
					
					doPlainTable(result[0], curPage, curIndex);
					$("#selectDiv"+curIndex).css("display","");
					pageList[curIndex].showPageHtml(totalCount, curPage, 10);
				}else{
					$("#todo").val("查询");
					$("#todo").attr("disabled", false);

					topWin.Dialog.message(data.message);
				}
			}
		});
	}
	//操纵DML语句方法
	function update(){
		var sql = DbRead.SQL;
		
		if(window.confirm("你提交的DML语句如下:\n"+sql+"\n是否真的要提交?")){
			//var sqlList = new Array();
			var sqlList = "";
			sqls = sql.split(";");
			var sqlList = "";
			for ( var i = 0; i < sqls.length; i++) {
				if($.trim(sqls[i]) == ""){
					continue;
				}
				sqlList += sqls[i] + ";";
			}
			
			var url = contextPath +"/intopieces/sqlinputquery/updateList.json";
			var formjson = {"sqlList":sqlList};
			$.ajax({
				url : url,
				type : "get",
				data : formjson,
				success : function(data) {
					if (data.success) {
						Tip.prettyTip("toupdate", "Sql操作成功!");
					}else{
						Tip.prettyTip("toupdate", "Sql操作失败,请检查!");
					}
					
					$("#toupdate").val("更新");
					$("#toupdate").attr("disabled", false);
				}
			});
		}else{
			$("#toupdate").val("更新");
			$("#toupdate").attr("disabled", false);
		}
	}
	//执行按钮的事件方法
	function toDo(type){
		
		var text = "";
		if(document.all){
			text = document.selection.createRange().text;
		}else{
			text = window.getSelection().toString();
		}
		var sql = $("#sqlText").val();
		
		if(sql == "" || sql == null){
			Tip.prettyTip("sqlText","请输入SQL语句");
			return;
		}
		
		if(text != "" && text != null && text != undefined){
			sql = text;
		}

		DbRead.SQL = sql;
		
		if(type == 0){
			$("#toupdate").val("正在执行中...请稍候!");
			$("#toupdate").attr("disabled",true);
			update();
		}else if(type == 1){
			$("#todo").val("正在执行中...请稍候!");
			$("#todo").attr("disabled",true);
			doQueryMulti();
		}
	}
	
	function ctrlenterEvent(event){
		event = window.event || event;
		if(event.ctrlKey == true && event.keyCode == 13){
			toDo(1);
		}
	}
	
	return {
		doQuery : doQuery,
		update : update,
		toDo : toDo,
		ctrlenterEvent : ctrlenterEvent
	};
}(jQuery);

var jq = jQuery.noConflict();
jq(document).ready(function(){
	Tip.doTip("sqlText");
	Tip.doTip("todo");
	Tip.doTip("toupdate");
	
});
//选择页面
function selectPage(row){
	//让执行按钮disabled
	jq("#todo").val("正在执行中...请稍候!");
	jq("#todo").attr("disabled",true);
	
	DbRead.doQuery(row);
}
