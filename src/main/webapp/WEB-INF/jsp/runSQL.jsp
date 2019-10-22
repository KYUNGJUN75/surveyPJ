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

	var sqlStr = $('#sqlStr').val();
	
	if($('#sqlStr').val() == ''){
		return;
	}
	
	$.ajax({
		type		: "POST",
		url			: "/runSQL.do",
		data		: {sqlStr : sqlStr},	
		dataType    : "html", 		
		async		: false,
		success		: function(response, status) {
			
			$("#form_runSQL").html(response);

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
		
		<form id="form_runSQL" name="form_runSQL" method="post" onsubmit="return false;">
			<table>
				<tr>
                    <th colspan="2">SQL</th>
                </tr>
			    <tr>
                    <td colspan="2"><textarea id="sqlStr" name="sqlStr" height="100%" width="100%" cols="150" rows="52" >${sqlStr}</textarea></td>
                </tr>
			   
			    <tr>
                    <th colspan="2">RESULT</th>
			    </tr>
				<tr>
					<td colspan="2"><textarea id="sqlResult" name="sqlResult" height="100%" width="100%" cols="150" rows="52" >${sqlResult}</textarea></td>
				</tr>
			</table>
		</form>	
		
	</div> <!-- END Wrapper -->
	
</body>

</html>