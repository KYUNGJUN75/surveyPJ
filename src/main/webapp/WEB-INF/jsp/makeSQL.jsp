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

	var tblId = $('#tblId').val();
	var inparam = $('#inparam').val();
	var outparam = $('#outparam').val();
	var gubun = $('#gubun').val();
	var checkTrgt = $('#checkTrgt').val();
	
	if($('#tblId').val() == ''){
		alert("TABLE ID 를 입력하세요.");
		$('#tblId').focus();
		return;
	}
	
	$.ajax({
		type		: "POST",
		url			: "/selectMakeSQL.do",
		data		: {tblId : tblId, inparam : inparam, outparam : outparam, gubun : gubun,checkTrgt:checkTrgt},	
		dataType    : "html", 		
		async		: false,
		success		: function(response, status) {
			
			$("#form_makeVO").html(response);

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
		
		<form id="form_makeVO" name="form_makeVO" method="post" onsubmit="return false;">
			<table>
				<tr>
                    <th style="text-align:left">구분</th>
                    <td colspan="3">
						<select name="checkTrgt" id="checkTrgt">
							<option value="O" <c:if test="${checkTrgt ne 'I'}">selected="selected"</c:if>>외부DB</option>
							<option value="I" <c:if test="${checkTrgt eq 'I'}">selected="selected"</c:if>>내부DB</option>
						</select>
					</td>
			    </tr>
			    <tr>
                    <th style="text-align:left">TABLE ID</th>
                    <td>
                        <input type="text" id="tblId" name="tblId" size="50" value="${tblId}"/> <button id="btn_submit" onclick="fn_submit()">변환</button>
                    </td>
			    </tr>
			    <tr>
                    <th style="text-align:left">업무구분</th>
                    <td>
                         <input type="text" id="gubun" name="gubun" size="50" value="${gubun}"/> (ex:CareMnf) 
                    </td>
			    </tr>
			    <tr>
                    <th style="text-align:left">IN 파라미터</th>
                    <td>
                        <input type="text" id="inparam" name="inparam" size="50" value="${inparam}"/> (ex:dices.cari.mnf.abcVo) 
                    </td>
			    </tr>
			    <tr>
                    <th style="text-align:left">OUT 파라미터 </th>
                    <td >
                        <input type="text" id="outparam" name="outparam" size="50" value="${outparam}"/> (ex:dices.cari.mnf.abcVo) 
                    </td>
			    </tr>
			   
			    <tr>
                    <th colspan="2">SQL</th>
			    </tr>
				<tr>
					<td colspan="2"><textarea id="sqlStr" name="sqlStr" height="100%" width="100%" cols="150" rows="52" >${sqlStr}</textarea></td>
				</tr>
			</table>
		</form>	
		
	</div> <!-- END Wrapper -->
	
</body>

</html>