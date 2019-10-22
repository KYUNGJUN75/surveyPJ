<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style type="text/css">
iframe {
 overflow-y:auto;
 overflow-x:hidden;
}
</style>
</head>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/css/com.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.form.js"></script>

 <script type="text/javascript">

	$( function() {
	  $( "#tabs" ).tabs();
	} );

 </script>
 <BODY>
  <div id="tabs" >
      <ul class="check">
          <li class="show"><a href="#gen">GENERATE HTML</a></li>
          <li class="show"><a href="#vo">VO</a></li>
          <li class="hide"><a href="#sql">SQL</a></li>
          <li class="hide"><a href="#vrfc">공통검증</a></li>
          <li class="hide"><a href="#mapp">포탈/내부 매핑</a></li>
          <li class="hide"><a href="#selins">SELECT_INSERT</a></li>
          <li class="hide"><a href="#getlabel">라벨</a></li>
          <li class="hide"><a href="#getdic">다국어사전</a></li>
          <li class="hide"><a href="#gencmt">다국어주석(문자열)</a></li>
          <li class="hide"><a href="#gencmtdir">다국어주석(디렉터리)</a></li>
          <li class="hide"><a href="#dblabel">DB라벨조회(주석생성포함)</a></li>
      </ul>
      <!--  HTML GENERATE 탭 -->
      <div id="gen" class="Tab_container"> <!-- http://localhost:48080 -->
      <iframe src="/generateHtml.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

      <!--  VO 만들기 탭 -->
      <div id="vo" class="Tab_container"> <!-- http://localhost:48080 -->
      <iframe src="/makeVO.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

      <!--  SQL 만들기 탭 -->
      <div id="sql" class="Tab_container">
      <iframe src="/makeSQL.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=yes vspace=0></iframe>
      </div>

      <!--  공통검증 만들기 탭 -->
      <div id="vrfc" class="Tab_container">
      <iframe src="/saveComVrfc.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

      <!--  포탈/내부 매핑 만들기 탭 -->
      <div id="mapp" class="Tab_container">
      <iframe src="/selectTblMap.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

	  <!--  외부 select 내부 isert sql 만들기 탭 -->
      <div id="selins" class="Tab_container">
      <iframe src="/selectMakeSelectInsert.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

      <!--  라벨조회 탭 -->
      <div id="getlabel" class="Tab_container">
      <iframe src="/getLabel.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

      <!--  다국어사전(String 2 String) 만들기 탭 -->
      <div id="getdic" class="Tab_container">
      <iframe src="/getDic.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

      <!--  다국어주석(String 2 String) 만들기 탭 -->
      <div id="gencmt" class="Tab_container">
      <iframe src="/transComment.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>

      <!--  다국어주석(Dir 2 Dir) 만들기 탭 -->
      <div id="gencmtdir" class="Tab_container">
      <iframe src="/transCommentDir.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0></iframe>
      </div>
      <!--  다국어주석(Dir 2 Dir) 만들기 탭 -->
      <div id="dblabel" class="Tab_container">
      <iframe src="/dbToLabelCreate.do" width="100%" height="100%" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=yes vspace=0></iframe>
      </div>

  </div>

 </BODY>
</HTML>
