function twoLevelMenu(jsonValue) {
	var areaJson = eval(jsonValue);
	var temp_html;
	var oProvince = $("#centerId");
	var oCity = $("#orgMgrId");
	// 初始化省
	var province = function() {
		temp_html += "<option value='ALL'>请选择机构</option>";
		$.each(areaJson, function(i, province) {
			temp_html += "<option value='" + province.typeCode + "'>"
					+ province.typeName + "</option>";
		});
		oProvince.html(temp_html);
		city();
	};
	// 赋值市
	var city = function() {
		temp_html = "";
		temp_html += "<option value='ALL'>请选择客户经理</option>";
		var n = oProvince.get(0).selectedIndex;
		if(n != 0){
			$.each(areaJson[n-1].children, function(i, city) {
				temp_html += "<option value='" + city.typeCode + "'>"
						+ city.typeName + "</option>";
			});
		}
		oCity.html(temp_html);
	};
	
	// 选择省改变市
	oProvince.change(function() {
		city();
	});

	province();
}