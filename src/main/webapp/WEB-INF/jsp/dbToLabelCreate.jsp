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
* Function Name : fn_submit
* Desc          : 결과전송.
* @param
* @returns
***************************************************************************/
function fn_label_submit(){

	var lblNm = $('#lblNm').val();
	var langCd = $('#langCd').val();
	
	if($('#lblNm').val() == ''){
		alert("라벨명을 입력하세요.");
		$('#lblNm').focus();
		return;
	}
	
	$.ajax({
		type		: "POST",
		url			: "/dbToLabelCreateData",
		data		: {lblNm : lblNm, langCd : langCd},	
		dataType    : "json", 		
		async		: false,
		success		: function(response, status) {
			$("tbody[id='data_list']").html("");
			$("#desc0").val("");
			$("#desc1").val("");
			$("#desc2").val("");
			//console.log( JSON.stringify(response.resultList) );
			///$("#comLabelVo").html(response);
			console.log(response.resultList.length);
			
			if( response.resultList == undefined || response.resultList.length == 0 ) {
				return;
			}
			$.each(response.resultList,function(index){
				var headers = $("<tr/>");
				var anchor1 = $("<a/>").text(response.resultList[index].lblId).click(function(event){
					var data1   = response.resultList[index].lblId;
					var data2   = response.resultList[index].prgComment;
					var data3   = response.resultList[index].htmlComment;
					$("#desc0").val(data1);
					$("#desc1").val(data2);
					$("#desc2").val(data3);
				});
				var th1 = $("<td/>");
				anchor1.appendTo(th1);
				
				var th2 = $("<td/>").text(response.resultList[index].lblNm);
				
				var type="라벨";
				
				if (response.resultList[index].lblTpcd == "B") {
					type = "버튼"; 
				}
				
				var th3 = $("<td/>").text(type);
				var th4 = $("<td/>").text(response.resultList[index].bsopClsfCd);
				th1.appendTo(headers);
				th2.appendTo(headers);
				th3.appendTo(headers);
				th4.appendTo(headers);
				headers.appendTo($("tbody[id='data_list']"));
			});
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
		<form id="comLabelVo" name="comLabelVo" method="post" onsubmit="return false;">
			<table>
			    <tr>
			        <th style="text-align:left">라벨</th>
					<td ><input type="text" id="lblNm" name="lblNm" size="50" value="저장" placeholder="검색어를 입력해주세요."/> ex) 저장 <button id="btn_submit" onclick="fn_label_submit();" type="button">변환</button></td>
					
			    </tr>
				<tr>
			        <th style="text-align:left">언어</th>
					<td ><input type="text" id="langCd" name="langCd" size="50" value="KO" /></td>
			    </tr>
			</table>
		</form>
		
		<h4>데이터 목록</h4>
		<table style="width:100%;border:1px #9BBED2 solid;">
			<colgroup>
				<col style="width:20%;"/>
				<col style="width:auto;"/>
				<col style="width:20%;"/>
				<col style="width:20%;"/>
			</colgroup>
			<thead>
			<tr>
				<th style="border-right:1px #9BBED2 solid;background-color:#ededed;">라벨ID</th>
				<th style="border-right:1px #9BBED2 solid;background-color:#ededed;">라벨명</th>
				<th style="background-color:#ededed;">구분</th>
				<th style="background-color:#ededed;">업무코드</th>
			</tr>
			</thead>
			<tbody id="data_list">
			</tbody>
		</table>
		
		<h4>데이터 상세</h4>		
		<table style="width:100%;border:1px #9BBED2 solid;margin-top:10px;">
			<colgroup>
				<col style="width:30%;"/>
				<col style="width:auto;"/>
			</colgroup>
			<tr>
				<th style="text-align:right;">라벨ID</th>
				<td><input type="text" name="desc0" id="desc0" value="" style="width:98%;"/></td>
			</tr>
			<tr>
				<th style="text-align:right;">Java/SQL/TypeScript주석</th>
				<td><input type="text" name="desc1" id="desc1" value="" style="width:98%;"/></td>
			</tr>
			<tr>
				<th style="text-align:right;">html주석</th>
				<td><input type="text" name="desc2" id="desc2" value="" style="width:98%;"/></td>
			</tr>
		</table>
	</div> <!-- END Wrapper -->
	
</body>
</html>