/**
 * <pre>
 * 分页
 * </pre>
 * @author ps
 * @createTime 2013年1月18日 16:49:33
 */

var Page2 = function(){
	var $this = this;
	this.id = "";//每个分页的唯一标识
	this.positionId = "";
	
	this.downloadAction = "/intopieces/sqlinputquery/dataListDownload.json";//下载的路径MVC的.do
	this.setDownloadAction = function(downloadAction){
		$this.downloadAction = downloadAction;
	};
	this.sql = null;//sql查询语句
	/**
	 * 设置分页对应的查询sql
	 */
	this.setSql = function(sql){
		$this.sql = sql;
	};
	//默认为5
	this.showPageSize = 5;
	/**
	 * 设置可以显示的页链接数
	 */
	this.setShowPageSize = function(showPageSize){
		$this.showPageSize = showPageSize;
	};
	/**
	 * <pre>
	 * 显示分页的Html
	 * </pre>
	 * @param totalCount 总记录数
	 * @param curPage 当前页
	 * @param pageCount 每页显示记录数
	 */
	this.showPageHtml = function (totalCount,curPage,pageCount){
		totalCount = totalCount * 1;
		curPage = curPage * 1;
		pageCount = pageCount * 1;
		
		//计算页数
		var pageNum = Math.floor((totalCount - 1)/pageCount) + 1;
		var firstHtml = "<font class='black'>共[" + totalCount + "]条记录,共[" + pageNum + "]页,第["+curPage+"]页</font>";
		var middleHtml = "";
		var nextHtml = "";
		var space = " ";
		firstHtml += space;
		
		//显示首页与上一页
		if(curPage == 1 || pageNum == 0){
			firstHtml += "<font class='black' title='首页'>首页</font>";
			firstHtml += space;
			firstHtml += "<font class='black' title='上一页'>上一页</font>";
		}else{
			firstHtml += "<a href='javascript:void(0);' title='首页' onclick='selectPage(1)' class='blue'>首页</a>";
			firstHtml += space;
			firstHtml += "<a href='javascript:void(0);' title='上一页' onclick='selectPage("+(curPage - 1)+")' class='blue'>上一页</a>";
		}
		
		firstHtml += space;
		
		//显示末页与下一页
		if(curPage == pageNum || pageNum == 0){
			nextHtml += "<font class='black' title='下一页'>下一页</font>";
			nextHtml += space;
			nextHtml += "<font class='black' title='末页'>末页</font>";
		}else{
			nextHtml += "<a href='javascript:void(0);' title='下一页' onclick='selectPage("+(curPage + 1)+")' class='blue'>下一页</a>";
			nextHtml += space;
			nextHtml += "<a href='javascript:void(0);' title='末页' onclick='selectPage("+(pageNum)+")' class='blue'>末页</a>";
		}
		nextHtml += space;
		
		var row = 1;
		var endRow = pageNum;
		if(pageNum > $this.showPageSize){
			if((curPage + 2) <= $this.showPageSize){
				row = 1;
				endRow = $this.showPageSize;
			}else if((curPage + 2) > $this.showPageSize && (curPage + 2) < pageNum){
				row = curPage - 2;
				endRow = curPage + 2;
			}else{
				row = pageNum - ($this.showPageSize - 1);
				endRow = pageNum;
			}
		}
		for(;row <= endRow;row ++){
			if(curPage == row){
				middleHtml += "<font class='gray' style='font-size:20px;' title='第"+(row)+"页'>"+(row)+"</font>";
			}else{
				middleHtml += "<a href='javascript:void(0);' title='第"+(row)+"页' onclick='selectPage("+(row)+")'>"+(row)+"</a>";
			}
			middleHtml += space;
		}
		
		//输入页数文本框
		var inputPage = jQuery("<input id='_page_num_"+$this.id+"' style='width:30px' title='请输入页数!'/>");
		inputPage.bind("keyup",function(){
			$this.enterPage(pageNum, event);
		});
		//GO按钮
		var goBtn = jQuery("<input type='button' class='btn_width11113' value='GO'/>");
		goBtn.bind("click",function(){
			$this.goPage(pageNum);
		});
		
		
		//下载XLS
		var downloadXls = jQuery("<a href='javascript:void(0);' title='同时按下shift和鼠标左键可以输入记录数'  class='blue'>导出XLS</a>");
		downloadXls.bind("click",function(){
			$this.downloadData("xls", event);
		});
		
		//下载TXT
		var downloadTxt = jQuery("<a href='javascript:void(0);' title='同时按下shift和鼠标左键可以输入记录数'  class='blue' >导出TXT</a>");
		downloadTxt.bind("click",function(){
			$this.downloadData("text", event);
		});
		
		var pageHtml = firstHtml + middleHtml + nextHtml;
		var pageLink = jQuery(pageHtml);
		
		var pagePosition = jQuery("#"+$this.positionId);
		pagePosition.append(pageLink);
		pagePosition.append(space);
		pagePosition.append(inputPage);
		pagePosition.append(goBtn);
		pagePosition.append(space);
		pagePosition.append(downloadXls);
		pagePosition.append(space);
		pagePosition.append(downloadTxt);
	};
	
	//Enter事件
	this.enterPage = function(pageNum,event){
		event = window.event || event;
		
		if(event.keyCode == 13){
			$this.goPage(pageNum);
		}
	};
	//GO按钮事件
	this.goPage = function (pageNum){
		var page = jQuery("#_page_num_"+$this.id).val();
		if(page == ""){
			alert("请输入页数!");
			return;
		}
		var regex = /\d+/ig;
		if(!regex.test(page)){
			alert("请输入数字!");
			return;
		}
		
		if(parseInt(page, 10) > parseInt(pageNum, 10)){
			alert("输入页数大于最大页数,请重新输入!");
			return;
		}
		
		if(parseInt(page, 10) <= 0){
			alert("请输入大于0的数字!");
			return;
		}
		selectPage(page);
	};
	//下载数据
	this.downloadData = function (type,event){
		event = window.event || event;
		var pageNum = 1000;
		if (event != null && event.shiftKey){
			pageNum = window.prompt("请输入下载的记录数","10000");
		}
		jQuery("#_myPageDataDownLoad"+$this.id).empty().remove();
		var form = jQuery("<form>");
		form.attr("method","POST");
		form.attr("id","_myPageDataDownLoad"+$this.id);
		form.attr("action",contextPath+$this.downloadAction);
		form.attr("method","get");
		form.attr("target","_self");
		
		var typeInput = jQuery("<input type='hidden' name='type'/>");
		var sqlInput = jQuery("<input type='hidden' name='sql'/>");
		var pageNumInput = jQuery("<input type='hidden' name='pageNum'/>");
		
		typeInput.val(type);
		sqlInput.val($this.sql);
		pageNumInput.val(pageNum);
		
		form.append(typeInput);
		form.append(sqlInput);
		form.append(pageNumInput);
		
		
		jQuery("body").append(form);
		form.submit();
	};
};
