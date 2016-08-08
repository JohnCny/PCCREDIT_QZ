function threeLevelMenu(jsonValue) {
	var areaJson = eval(jsonValue);
	var temp_html;
	var oProvince = $("#centerId");
	var oCity = $("#teamId");
	var oDistrict = $("#centerMgrId");
	// 初始化省
	var province = function() {
		temp_html += "<option value='ALL'>请选择中心</option>";
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
		temp_html += "<option value='ALL'>请选择团队</option>";
		var n = oProvince.get(0).selectedIndex;
		if(n != 0){
			$.each(areaJson[n-1].children, function(i, city) {
				temp_html += "<option value='" + city.typeCode + "'>"
						+ city.typeName + "</option>";
			});
		}
		oCity.html(temp_html);
		district();
	};
	// 赋值县
	var district = function() {
		temp_html = "";
		temp_html += "<option value='ALL'>请选择客户经理</option>";
		var m = oProvince.get(0).selectedIndex;
		var n = oCity.get(0).selectedIndex;
		if(m != 0 && n != 0){
			if (typeof (areaJson[m-1].children[n-1].children) == "undefined") {
				oDistrict.css("display", "none");
			} else {
				oDistrict.css("display", "inline");
				$.each(areaJson[m-1].children[n-1].children, function(i, district) {
					temp_html += "<option value='" + district.typeCode + "'>"
							+ district.typeName + "</option>";
				});
				oDistrict.html(temp_html);
			}
		}else{
			oDistrict.html(temp_html);
		}
	};
	// 选择省改变市
	oProvince.change(function() {
		city();
	});
	// 选择市改变县
	oCity.change(function() {
		district();
	});

	province();
}