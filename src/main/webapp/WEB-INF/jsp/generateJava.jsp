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
    var pkgPath = $('#pkgPath').val();
    var addItem = $('#addItem').val();
    var addItemComment = $('#addItemComment').val();
    var arrayItem = $('#arrayItem').val();
    var checkTrgt = $('#checkTrgt').val();
    
    if($('#tblId').val() == ''){
        alert("TABLE ID 를 입력하세요.");
        $('#tblId').focus();
        return;
    }else if($('#pkgPath').val() == ''){
        alert("PACKAGE PATH 를 입력하세요.");
        $('#pkgPath').focus();
        return;
    }
    
    $.ajax({
        type        : "POST",
        url         : "/generateJava.do",
        data        : {tblId : tblId, pkgPath : pkgPath, addItem : addItem, addItemComment : addItemComment, arrayItem : arrayItem, checkTrgt:checkTrgt},   
        dataType    : "html",       
        async       : false,
        success     : function(response, status) {
            
            $("#form_makeVO").html(response);

        },
        error       : function(data, status, error) {
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
                    <th style="text-align:left">PACKAGE PATH</th>
                    <td>
                        <input type="text" id="pkgPath" name="pkgPath" size="50" value="${pkgPath}"/> (ex:dices.cari.mnf) 
                    </td>
                </tr>
                <tr>
                    <th style="text-align:left">추가 항목 ID </th>
                    <td colspan="3">
                        <input type="text" id="addItem" name="addItem" size="120" value="${addItem}"/> (ex: AAA_BB_CC|STRING|AAA항목,BBB_CC_DDD|Boolean|BBB항목명,CCC_CC_DDD|BOOLEAN|CCC항목,...) 
                    </td>
                </tr>
                <tr>
                    <th style="text-align:left">체크박스 항목 ID </th>
                    <td colspan="3">
                        <input type="text" id="arrayItem" name="arrayItem" size="120" value="${arrayItem}"/> (ex: AAA_BB_CC,BBB_CC_DDD,CCC_CC_DDD,...) 
                    </td>
                </tr>
                <tr>
                    <th colspan="2">CONTROL</th>
                    <th colspan="2">SERVICE</th>
                </tr>
                <tr>
                    <td colspan="2"><textarea id="voStr" name="voStr" height="100%" width="100%" cols="130" rows="52" >${voStr}</textarea></td>
                    <td colspan="2"><textarea id="svVoStr" name="svVoStr" height="100%" width="100%" cols="130" rows="52" >${svVoStr}</textarea></td>
                </tr>
            </table>
        </form> 
        
    </div> <!-- END Wrapper -->
    
</body>

</html>