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
function fn_trans_submit(){
	var dicPath = $('#dicPath').val();
	var srcTxt = $('#srcTxt').val();
	
    if(dicPath == ''){
		alert("사전파일경로를 입력하세요.(엑셀파일 혹은 텍스트파일)");
		$('#dicPath').focus();
		return;
	}
    if($('#srcTxt').val() == ''){
		alert("한글을 입력하세요.");
		$('#srcTxt').focus();
		return;
	}
    
	$.ajax({
		type		: "POST",
		url			: "/getDic.do",
		data		: {dicPath : dicPath, srcTxt : srcTxt},	
		dataType    : "html", 		
		async		: false,
		success		: function(response, status) {
			
			$("#form_getDic").html(response);

		},
		error	    : function(data, status, error) {
			alert(error);
			alert('조회에 실패하였습니다.');
		}
	});
}
</script>

<body >

	<div id="wrapper">
		<form id="form_getDic" name="form_getDic" method="post" onsubmit="return false;">
		** 사전조회
			<table>
				<tr>
			        <th style="text-align:left">사전파일경로 *</th>
					<td ><input type="text" id="dicPath" name="dicPath" size="50" value="/Project/bin/dices/dic.xlsx" /> </td>
			    </tr>
			    <tr>
                    <td colspan="2">
                        <button id="btn_trans_submit" onClick="fn_trans_submit()">조회</button>
                    </td>
			    </tr>
				<tr>
			        <th style="text-align:left">한국어</th>
					<td ><textarea id="srcTxt" name="srcTxt" height="50%" width="100%" cols="200" rows="2" >${srcTxt}</textarea></td>
				</tr>
				<tr>
			        <th style="text-align:left">불어</th>
					<td ><textarea id="replacedText" name="replacedText" height="50%" width="100%" cols="200" rows="2" >${replacedText}</textarea></td>
				</tr>
			</table>
		</form>	
	</div> <!-- END Wrapper -->
	
</body>

</html>