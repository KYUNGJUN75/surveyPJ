var regexpObj = {"lowercase":/^[a-z]+$/, "uppercase":/^[A-Z]+$/, "char+num":/^[a-zA-Z0-9]+$/, "userId":/^[a-z]+[a-z0-9]+$/};

if(String.prototype.startsWith == undefined) {
	String.prototype.startsWith = function (val, pos) {
	    if(pos) {
	    	// 
	    } else {
	    	pos = 0;
	    }
	    
	    try {
	    	var tmpVal = this.substr(pos);
	    	return tmpVal.indexOf(val) == 0 ? true : false;
	    } catch(e) {
	    	return false;
	    }
	};
}

$(document).ready(function() {
	// sbux-input check for validation
	$("span.sbux-input-normal").each(function(i){
		var reqetc = $(this).attr("reqetc");
		var classList = $(this).attr("class").split(/\s+/);
		for(var i = 0; i < classList.length; i++) {
			if(classList[i].indexOf("validate") != -1) {
				$(this).removeClass(classList[i]);
				$(this).find(".sbux-inb-input").addClass(classList[i]);
				if(reqetc != undefined) {
					$(this).removeAttr("reqetc");
					$(this).find(".sbux-inb-input").attr("reqetc", reqetc);
				}
			}
		}
	});
	
	$("p.sbux-pik-group").each(function(i){
		var reqetc = $(this).attr("reqetc");
		var classList = $(this).attr("class").split(/\s+/);
		for(var i = 0; i < classList.length; i++) {
			if(classList[i].indexOf("validate") != -1) {
				$(this).removeClass(classList[i]);
				$(this).find(".sbux-pik-input").addClass(classList[i]);
				if(reqetc != undefined) {
					$(this).removeAttr("reqetc");
					$(this).find(".sbux-pik-input").attr("reqetc", reqetc);
				}
				
				var compareCls = classList[i].split(/\[|,|\]/);
				for(var j = 0; j < compareCls.length; j++) {
					if(compareCls[j] == "dateRange" || compareCls[j] == "dateRange2") {
						$(this).find(".sbux-pik-input").addClass(classList[j + 1]);
					}
				}
			}
		} 
	});
	
	// 달력 icon 변경
	$(".glyphicon.glyphicon-calendar").each(function(i) {
		$(this).removeClass("glyphicon").removeClass("glyphicon-calendar");
		$(this).addClass("fa").addClass("fa-calendar");
	});
});

/***************************************************************************
* Function Name : fn_showHide
* Desc          : tag를 보이기 / 숨기기
* @param p1 (String or Object) : showObjId
* @param p2 (String or Object) : hideObjId
* @returns 
***************************************************************************/
function fn_showHide(showObjId, hideObjId) {
	fn_show(showObjId);
	fn_hide(hideObjId);
}

/***************************************************************************
* Function Name : fn_show
* Desc          : tag를 보이기
* @param p1 (String or Object) : showObjId
* @returns 
***************************************************************************/
function fn_show(showObjId) {
	if(showObjId && showObjId != null) {
		if(typeof showObjId == "string") {
			$("#" + showObjId).css("display", "");
		} else if(typeof showObjId == "object") {
			for(var i = 0; i < showObjId.length; i++) {
				$("#" + showObjId[i]).css("display", "");
			}
		}
	}
}

/***************************************************************************
* Function Name : fn_hide
* Desc          : tag를 보이기
* @param p1 (String or Object) : hideObjId
* @returns 
***************************************************************************/
function fn_hide(hideObjId) {
	if(hideObjId && hideObjId != null) {
		if(typeof hideObjId == "string") {
			$("#" + hideObjId).css("display", "none");
		} else if(typeof hideObjId == "object") {
			for(var i = 0; i < hideObjId.length; i++) {
				$("#" + hideObjId[i]).css("display", "none");
			}
		}
	}
}

/***************************************************************************
* Function Name : fn_lpad
* Desc          : 지정된 길이만큼 왼쪽에 String 값을 채운다.
* @param p1 (String) : str1
* @param p2 (Number) : len
* @param p3 (String) : pad
* @returns String : lpad 된 스트링정보
***************************************************************************/
function fn_lpad(str1, len, pad) {
	str1 = str1.toString();
	if(str1 == null || str1 == undefined) {
		return "";
	}
	
	if(str1.length >= len || pad == null || pad == undefined) {
		return str1;
	}
	
	var leftBuf = "";
	for(var i = 0; i < len - str1.length; i++) {
		leftBuf += pad;
	}
	leftBuf += str1;
	
	
	return leftBuf.toString();
}

/***************************************************************************
* Function Name : fn_onlyNumber
* Desc          : 숫자만 입력받기
* @param p1 (Object) : event
* @returns
***************************************************************************/
function fn_onlyNumber(event){
	event = event || window.event;
	var keyId = (event.which) ? event.which : event.keyCode;
	if ( (keyId >= 48 && keyId <= 57) || (keyId >= 96 && keyId <= 105) || keyId == 8 || keyId == 9 || keyId == 46 || keyId == 37 || keyId == 39 ) { 
		return;
	} else {
		return false;
	}
}

/***************************************************************************
* Function Name : fn_removeChar
* Desc          : 숫자가 아닌 문자는 제거한다.
* @param p1 (Object) : event
* @returns
***************************************************************************/
function fn_removeChar(event) {
	event = event || window.event;
	var keyId = (event.which) ? event.which : event.keyCode;
	if ( keyId == 8 || keyId == 46 || keyId == 37 || keyId == 39 ) { 
		return;
	} else {
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
	}
}

/***************************************************************************
* Function Name : fn_openPopup
* Desc          : 팝업창을 호출한다.
* @param p1 (String) : form ID
* @param p2 (String) : URI
* @param p3 (String) : 팝업화면ID
* @param p4 (Number) : 가로길이
* @param p5 (Number) : 세로길이
* @param p6 (String) : 팝업속성
* @param p7 (Boolean) : 중앙여부
* @param p8 (String) : 콜백함수명
* @returns
***************************************************************************/
function fn_openPopup(formNm, uri, popId, popWidth, popHeight, popAttr, isCenter, callback) {
	if(callback) {
		var fCallback = $("#com_callback", "#" + formNm);
		if(fCallback.length == 0) {
			$("#" + formNm).append("<input type='hidden' id='com_callback' name='com_callback' value='" + callback + "'>");
		} else {
			$("#com_callback", "#" + formNm).val(callback);
		}
	}
	
	var popBaseAttr = "width=" + popWidth + "px, height=" + popHeight + "px";
	if(isCenter && isCenter == true) {
		var centerPosInfo = fn_getOpenPopPos(popWidth, popHeight);
		popBaseAttr += ",left=" + centerPosInfo["popLeft"] + ",top=" + centerPosInfo["popTop"];
	}
	popBaseAttr += (popAttr != undefined && popAttr != "") ? ("," + popAttr) : "";
	
	var popWindow = window.open("", popId, popBaseAttr);
	var beforeTarget = $("#" + formNm).attr("target");
	$("#" + formNm).attr("action", uri);
	$("#" + formNm).attr("target", popId);
	$("#" + formNm).submit();
	$("#" + formNm).attr("target", beforeTarget);
}

function fn_getOpenPopPos(popWidth, popHeight) {
	var screenWidth = screen.availWidth;
	var screenHeight = screen.availHeight;
	var popLeft = (screenWidth - popWidth) / 2;
	var popTop = (screenHeight - popHeight) / 2;
	
	return {"popLeft":popLeft, "popTop":popTop};
}

/***************************************************************************
* Function Name : fn_locReplace
* Desc          : 화면을 전환한다.
* @param p1 (String) : URI
* @param p2 (Object) : 파라미터 (ex: {param1:"param1", param2:"param2"}
* @returns
***************************************************************************/
function fn_locReplace(uri, paramObj) {
	var actionUri = uri;
	if(paramObj != undefined && typeof paramObj == "object") {
		actionUri += "?" + $.param(paramObj);
	}
	
	window.location.href = actionUri;
}

/***************************************************************************
* Function Name : fn_setCellMode
* Desc          : 그리드 cell 의 input, select 표시
* @param p1 (String) : 그리드ID
* @param p2 (Number) : 로우
* @param p3 (Number) : 컬럼
* @param p4 (String) : 모드 (select or input)
* @param p5 (Boolean) : 적용여부 (true/false)
* @param p6 (Boolean) : 고정컬럼여부 (true/false/undefined)
* @returns
***************************************************************************/
function fn_setCellMode(strGridId, nRow, nCol, strMode, bApply, bCol) {
	var bFrozenCol = (typeof bCol == "undefined" || bCol == true) ? true : false;
	var gridTableId = bFrozenCol ? "SBHE_DT_" + strGridId : "SBHE_FA_" + strGridId;

	var cellClass = "";
	if(strMode == "select") {
		cellClass = "sb_combo";
	} else if(strMode == "input") {
		cellClass = "sb_edit";
	}
	
	$("#" + gridTableId).find("tbody tr[data-rowindex=" + nRow + "] td[data-colindex=" + nCol + "]").removeClass("sb_combo").removeClass("sb_edit");
	
	if(bApply == true) {
		$("#" + gridTableId).find("tbody tr[data-rowindex=" + nRow + "] td[data-colindex=" + nCol + "]").addClass(cellClass);
	} else {
		$("#" + gridTableId).find("tbody tr[data-rowindex=" + nRow + "] td[data-colindex=" + nCol + "]").removeClass(cellClass);
	}
}

/***************************************************************************
* Function Name : fn_setColMode
* Desc          : 그리드 column 의 input, select 표시
* @param p1 (String) : 그리드ID
* @param p2 (Number) : 컬럼
* @param p3 (String) : 모드 (select or input)
* @param p4 (Boolean) : 적용여부 (true/false)
* @param p5 (Boolean) : 고정컬럼여부 (true/false/undefined)
* @returns
***************************************************************************/
function fn_setColMode(strGridId, nCol, strMode, bApply, bCol) {
	var bFrozenCol = (typeof bCol == "undefined" || bCol == true) ? true : false;
	var gridTableId = bFrozenCol ? "SBHE_DT_" + strGridId : "SBHE_FA_" + strGridId;
	
	var cellClass = "";
	if(strMode == "select") {
		cellClass = "sb_combo";
	} else if(strMode == "input") {
		cellClass = "sb_edit";
	}
	
	$("#" + gridTableId).find("tbody td[data-colindex=" + nCol + "]").removeClass("sb_combo").removeClass("sb_edit");
	
	if(bApply == true) {
		$("#" + gridTableId).find("tbody td[data-colindex=" + nCol + "]").addClass(cellClass);
	} else {
		$("#" + gridTableId).find("tbody td[data-colindex=" + nCol + "]").removeClass(cellClass);
	}
}

/***************************************************************************
* Function Name : fn_numberTo3Commna
* Desc          : 3자리 콤마, 소수점 자리수 유지
* @param p1 (String or Number) : 숫자유형 값
* @returns String 
* ex) fn_numberTo3Commna(1234567890.56)                   -> 1,234,567,890.56
* ex) fn_numberTo3Commna("1234567890.56")                 -> 1,234,567,890.56
*     추가 : fn_round 와 함께 사용
*     ex) fn_numberTo3Commna(fn_round(1234567890.56, 1))  -> 1,234,567,890.6
**************************************************************************/
function fn_numberTo3Commna(this$) {
	var str = String(this$);
	if(typeof str != typeof undefined && !isNaN(str)) {
		var chkVal = str.split(".");
		var intVal = chkVal[0];

		var len = intVal.length, idx = len - 1;
		var rtnVal = "";
		var comma = "";
		
		while(idx >= 0) {
			if(rtnVal.replace(/,/g, "").length % 3 == 0 && idx != len - 1) {
				comma = ",";
			} else {
				comma = "";
			}
			rtnVal = str.charAt(idx) + comma + rtnVal;
			--idx;
		}
		
		if(chkVal.length == 2) {
			rtnVal += "." + chkVal[1];
		}

		return rtnVal;
	} else {
		return str;
	}
}

/***************************************************************************
* Function Name : fn_round
* Desc          : 소수점 처리 함수(num = 계산할 수, len = 소수점 이하 자릿수)
* @param p1 Number : 소수점
* @param p2 Number : 반올림 자리수
* @returns Number 
**************************************************************************/
function fn_round(num, len) {
	len = Math.pow(10, len);
	return Math.round(num * len) / len;
}

/***************************************************************************
* Function Name : fn_dateFormat
* Desc          : 20160701 형식의 날짜값을 2016-07-01 형식으로 리턴한다.
* @param p1 String : yyyymmdd 형식의 날짜값
* @param p2 String : 구분자
* @returns Number 
**************************************************************************/
function fn_dateFormat(dateStr, sep) {
	if(dateStr && dateStr.length == 8) {
		var yyyy = dateStr.substr(0, 4);
		var mm = dateStr.substr(4, 2);
		var dd = dateStr.substr(6, 2);
		
		return yyyy + sep + mm + sep + dd;
	} else {
		return dateStr;
	}
}

/***************************************************************************
* Function Name : fn_getDaysBetween
* Desc          : 두날짜의 차이를 리턴한다.
* @param p1 String : yyyy-mm-dd (ex:"2015-07-27")
* @param p2 String : yyyy-mm-dd (ex:"2015-08-01")
* @returns Number 
**************************************************************************/
function fn_getDaysBetween(fromDate, toDate) {
	var stDate = new Date(fromDate) ;
    var endDate = new Date(toDate) ;
 
    var btMs = endDate.getTime() - stDate.getTime() ;
    var btDay = btMs / (1000*60*60*24) ;
    
    return btDay;
}

/***************************************************************************
* Function Name : fn_selectComCodeList
* Desc          : 공통코드 조회
* @param p1 (String) : lclsCd (ex: KDIHR00007 or KDIHR00007,KDIHR00008)
* @returns JSONObject (ex: {"KDIHR00007":[], "KDIHR00008":[]})
***************************************************************************/
function fn_selectComCodeList(lclsCd) {
	var resultCodeObj = {};
	if(lclsCd && lclsCd != "") {
		$.ajax({
			type		: "POST",
			url			: "/com/cod/selectComCodeList.do",
			data		: {"lclsCd" : lclsCd},
			dataType    : 'json',
			async		: false,
			success		: function(response, status) {
				resultCodeObj = response.resultCodeObj;
			},
			error	    : function(data, status, error) {
				alert("조회에 실패하였습니다.");
			}
		});
	}
	
	return resultCodeObj;
}

/***************************************************************************
* Function Name : fn_getFileSize
* Desc          : 파일크기를 MB or KB 단위로 리턴
* @param p1 (Number) : size
* @returns String (ex: "23 MB" or "1024 KB")
***************************************************************************/
function fn_getFileSize(size) {
	var sizeStr = "";
    var sizeKB = size / 1024;
    if(parseInt(sizeKB) > 1024){
        var sizeMB = sizeKB/1024;
        sizeStr = fn_numberTo3Commna(sizeMB.toFixed(2)) + " MB";
    }else{
        sizeStr = fn_numberTo3Commna(sizeKB.toFixed(2)) + " KB";
    }

    return sizeStr;
}