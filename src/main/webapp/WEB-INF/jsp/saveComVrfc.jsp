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
	var bfhnVrfcPrgmId = $('#bfhnVrfcPrgmId').val();
	var docCd = $('#docCd').val();
	var docNm = $('#docNm').val();
	var checkTrgt = $('#checkTrgt').val();
	var vrfcTblId = $('#vrfcTblId').val();
	
	if($('#tblId').val() == ''){
		alert("TABLE ID 를 입력하세요.");
		$('#tblId').focus();
		return;
	}else if($('#docCd').val() == ''){
		alert("문서코드 를 입력하세요.");
		$('#docCd').focus();
		return;
	}else if($('#bfhnVrfcPrgmId').val() == ''){
		alert("사전검증 프로그램 ID 를 입력하세요.");
		$('#bfhnVrfcPrgmId').focus();
		return;
	}else if($('#docNm').val() == ''){
		alert("문서명을 입력하세요.");
		$('#docNm').focus();
		return;
	}else if($('#checkTrgt').val() == ''){
		alert("검증대상기준을 선택해주세요.");
		$('#checkTrgt').focus();
		return;
	}
	if($('#checkTrgt').val() == 'I'){
		if ( vrfcTblId == "") {
			alert("검증기준 대상 내부테이블ID를 입력해주세요.");
			$('#vrfcTblId').focus();
			return;
		}
	}
	
	$.ajax({
		type		: "POST",
		url			: "/saveComVrfc.do",
		data		: {tblId : tblId, bfhnVrfcPrgmId : bfhnVrfcPrgmId, docCd : docCd, docNm : docNm, checkTrgt:checkTrgt,vrfcTblId:vrfcTblId },	
		dataType    : "html", 		
		async		: false,
		timeout     : 20000,
		success		: function(response, status) {
			
			$("#form_makeVO").html(response);
			
			if(response.insCnt > 0){
				alert('저장 하였습니다.');
			}

		},
		error	    : function(data, status, error) {
			alert(error);
			alert('저장에 실패하였습니다.');
		}
	});
}

/***************************************************************************
* Function Name : fn_excelDown
* Desc          : 결과전송.
* @param
* @returns
***************************************************************************/
function fn_excelDown(){

    var docCd = $('#docCd').val();
    
    if($('#docCd').val() == ''){
        alert("문서코드 를 입력하세요.");
        $('#docCd').focus();
        return;
    }
    
    
    $.ajax({
        type        : "POST",
        url         : "/excelDown.do",
        data        : {docCd : docCd }, 
        dataType    : "html",       
        async       : false,
        timeout     : 20000,
        success     : function(response, status) {
            
            $("#form_makeVO").html(response);
            
            if(response.insCnt > 0){
                alert('저장 하였습니다.');
            }

        },
        error       : function(data, status, error) {
            alert(error);
            alert('저장에 실패하였습니다.');
        }
    });
}

/***************************************************************************
* Function Name : fn_excelUpload
* Desc          : 결과전송.
* @param
* @returns
***************************************************************************/
function fn_excelUpload(){

    var docCd = $('#docCd').val();
    
    if($('#docCd').val() == ''){
        alert("문서코드 를 입력하세요.");
        $('#docCd').focus();
        return;
    }
    
    
    $.ajax({
        type        : "POST",
        url         : "/excelUpload.do",
        data        : {docCd : docCd }, 
        dataType    : "html",       
        async       : false,
        timeout     : 20000,
        success     : function(response, status) {
            
            $("#form_makeVO").html(response);
            
            if(response.insCnt > 0){
                alert('저장 하였습니다.');
            }

        },
        error       : function(data, status, error) {
            alert(error);
            alert('저장에 실패하였습니다.');
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
					<th>검증대상 기준</th>
					<td>
						<select name="checkTrgt" id="checkTrgt">
							<option value="O" <c:if test="${checkTrgt ne 'I'}">selected="selected"</c:if>>외부</option>
							<option value="I" <c:if test="${checkTrgt eq 'I'}">selected="selected"</c:if>>내부</option>
						</select>
					</td>
					<th>검증기준 TABLE ID</th>
                    <td>
                        <input type="text" id="vrfcTblId" name="vrfcTblId" size="50" value="${vrfcTblId}"/>
                    </td>
				</tr>
			    <tr>
                    <th>TABLE ID</th>
                    <td>
                        <input type="text" id="tblId" name="tblId" size="50" value="${tblId}"/> 
                    </td>
                    <th>사전검증 프로그램 ID</th>
                    <td>
                        <input type="text" id="bfhnVrfcPrgmId" name="bfhnVrfcPrgmId" size="50" value="${bfhnVrfcPrgmId}"/>
                        <button id="btn_submit" onclick="fn_submit()">생성</button>
                    </td>
                </tr>
                <tr>
                    <th>문서코드</th>
                    <td>
                        <input type="text" id="docCd" name="docCd" size="50" value="${docCd}"/> 
                        <button id="btn_submit" onclick="fn_excelDown()">ExcelDownload</button>
                        <button id="btn_submit" onclick="fn_excelUpload()">ExcelUpload</button>
                    </td>
                    <th>문서명</th>
                    <td>
                        <input type="text" id="docNm" name="docNm" size="50" value="${docNm}"/> 
                    </td>
			    </tr>
			</table>
		</form>	
		
	</div> <!-- END Wrapper -->
	
</body>

</html>