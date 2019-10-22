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

	var filePath = $('#filePath').val();

	if($('#filePath').val() == ''){
		alert("FILE PATH 를 입력하세요.");
		$('#filePath').focus();
		return;
	}

	$.ajax({
		type		: "POST",
		url			: "/runGenerateHtml.do",
		data		: {filePath : filePath},
		dataType    : "html",
		async		: false,
		success		: function(response, status) {

			$("#form_genHtml").html(response);

		},
		error	    : function(data, status, error) {
			alert(error);
			alert('생성에 실패하였습니다.');
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

		<form id="form_genHtml" name="form_genHtml" method="post" onsubmit="return false;">
			<table>
			    <tr>
                    <th style="text-align:left">File Path</th>
                    <td>
                        <input type="text" id="filePath" name="filePath" size="200" value="${filePath}"/> <button id="btn_submit" onclick="fn_submit()">변환</button>
                    </td>
			    </tr>
			</table>
		</form>

	</div> <!-- END Wrapper -->

</body>

</html>