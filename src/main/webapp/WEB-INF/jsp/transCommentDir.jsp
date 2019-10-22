<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>



<link rel="stylesheet" href="/css/com.css">
<link rel="stylesheet" href="/css/button.css">
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.form.js"></script>

<script type="text/javascript">

/***************************************************************************
* Function Name : fn_transdir_submit
* Desc          : 결과전송.
* @param
* @returns
***************************************************************************/
function fn_transdir_submit(){
	var dicPath = $('#dicPath').val();
	var srcDir 	= $('#srcDir').val();
	var tgDir 	= $('#tgDir').val();
	
    if(dicPath == ''){
		alert("사전파일경로를 입력하세요.(엑셀파일 혹은 텍스트파일)");
		$('#dicPath').focus();
		return;
	}
    if(srcDir == ''){
		alert("변환대상경로(Source)를 입력하세요.");
		$('#srcDir').focus();
		return;
	}
    
	$.ajax({
		type		: "POST",
		url			: "/transCommentDirData.do",
		data		: {dicPath : dicPath, srcDir : srcDir, tgDir : tgDir },	
		dataType    : "html", 		
		async		: false,
		success		: function(response, status) {
			
			$("#form_transCommentDir").html(response);

		},
		error	    : function(data, status, error) {
			alert(error);
			alert('조회에 실패하였습니다.');
		}
	});
}

 
</script>
</head>
<body >
	<div id="wrapper">
		<form id="form_transCommentDir" name="form_transCommentDir" method="post" onsubmit="return false;">
			<table>
			    <tr>
			        <th style="text-align:left">사전파일경로 *</th>
					<td ><input type="text" id="dicPath" name="dicPath" size="50" value="${dicPath}" /> ex) <a href="#this" onClick="javascript:$('#dicPath').val('C:/Temp/dic.xlsx')">C:/Temp/dic.xlsx</a></td>
			    </tr>
				<tr>
			        <th style="text-align:left">변환대상경로(Source) *</th>
					<td ><input type="text" id="srcDir" name="srcDir" size="50" value="${srcDir}" /> ex) <a href="#this" onClick="javascript:$('#srcDir').val('C:/DICES_DEV/workspaces/dices-ept-client/src/app/care')">C:/DICES_DEV/workspaces/dices-ept-client/src/app/care</td>
			    </tr>
				<tr>
			        <th style="text-align:left">변환결과경로(Target)</th>
					<td ><input type="text" id="tgDir" name="tgDir" size="50" value="${tgDir}" /> ex) <a href="#this" onClick="javascript:$('#tgDir').val('C:/Temp/translated')">C:/Temp/translated</td>
				</tr>
			    <tr>
	                  <td colspan="2">
	                     <button id="btn_transdir_submit" onClick="fn_transdir_submit()">변환</button>
	                  </td>
			    </tr>
				<tr>
			        <th style="text-align:left">변환 결과</th>
					<td ><textarea id="reportText" name="reportText" height="50%" width="100%" cols="200" rows="50" >${reportText}</textarea></td>
				</tr>
			</table>
		</form>	
		
	</div> <!-- END Wrapper -->
	
</body>

</html>