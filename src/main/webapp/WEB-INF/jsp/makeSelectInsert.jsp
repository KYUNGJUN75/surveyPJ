<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

</head>

<link rel="stylesheet" href="/css/com.css">
<link rel="stylesheet" href="/css/button.css">
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.form.js"></script>

<script type="text/javascript">

/***************************************************************************
* Function Name : fn_submit
* Desc          : 결과전송.
* @param
* @returns
***************************************************************************/
function fn_submit(){

	var fromTblId = $('#fromTblId').val();
    var toTblId = $('#toTblId').val();
	
    if($('#fromTblId').val() == ''){
		alert("FROM TABLE ID 를 입력하세요.");
		$('#fromTblId').focus();
		return;
	}else if($('#toTblId').val() == ''){
		alert("TO TABLE ID 를 입력하세요.");
		$('#toTblId').focus();
		return;
	}
    
	$.ajax({
		type		: "POST",
		url			: "/selectMakeSelectInsert.do",
		data		: {fromTblId : fromTblId, toTblId : toTblId},	
		dataType    : "html", 		
		async		: false,
		success		: function(response, status) {
			
			$("#form_tblMap").html(response);

		},
		error	    : function(data, status, error) {
			alert(error);
			alert('조회에 실패하였습니다.');
		}
	});
}

//Search Form 엔터 입력시 조회
function press(event) {
    if (event.keyCode==13) {
    	fn_submit();
    }
}

</script>

<body onkeypress="press(event);">

	<div id="wrapper">
		
		<form id="form_tblMap" name="form_tblMap" method="post" onsubmit="return false;">
			<table>
			    <tr>
			        <th style="text-align:left">FROM TBL</th>
                    <td>
                        <input type="text" id="fromTblId" name="fromTblId" size="50" value="${fromTblId}" />
                    </td>
                    <th style="text-align:left">TO TBL</th>
                    <td>
                        <input type="text" id="toTblId" name="toTblId" size="50" value="${toTblId}"/> <button id="btn_submit" onclick="fn_submit()">변환</button>
                    </td>
			    </tr>
				<tr>
					<td colspan="4"><textarea id="tobeSql" name="tobeSql" height="100%" width="100%" cols="200" rows="52" >${tobeSql}</textarea></td>
				</tr>
			</table>
		</form>	
		
	</div> <!-- END Wrapper -->
	
</body>

</html>