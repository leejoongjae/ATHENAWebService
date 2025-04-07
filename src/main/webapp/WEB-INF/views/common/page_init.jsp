<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@	taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	pageContext.setAttribute("crlf", "\n");

	
%>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="Content-Script-Type" content="text/javascript" />
		<meta http-equiv="Content-Style-Type" content="text/css" />
		<meta name="author" content="영어 퀴즈 풀어 볼래" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<title>영어 퀴즈 풀어 볼래</title>

		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="/css/reset.css" /> 
		<link rel="stylesheet" type="text/css" href="/css/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="/css/jquery-ui-slider-pips.css" />
		<link rel="stylesheet" type="text/css" href="/css/element-ui.css" />
		<link rel="stylesheet" type="text/css" href="/css/footable.standalone.css" />
		<link rel="stylesheet" type="text/css" href="/css/jquery.mCustomScrollbar.min.css" />
		<link rel="stylesheet" type="text/css" href="/css/ionicons.css" />
		<link rel="stylesheet" type="text/css" href="/css/semantic.css" />
		<link rel="stylesheet" type="text/css" href="/css/element-ui-dark.css" />
		
		<!-- Stylesheets -->
	    <link rel="stylesheet" type="text/css" href="https://unpkg.com/aos@next/dist/aos.css" />
	    <link rel="stylesheet" type="text/css" href="/css/intro_reset.css" />
	    <link rel="stylesheet" type="text/css" href="/css/intro_main.css">
		
		
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/js/jquery-ui-slider-pips.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/js/modernizr.custom.js"></script>
		<script type="text/javascript" src="/js/classie.js"></script>
		<script type="text/javascript" src="/js/gnmenu.js"></script>
		<script type="text/javascript" src="/js/semantic.min.js"></script>
		<script type="text/javascript" src="/js/semantic-ui-calendar.min.js"></script>
		<script type="text/javascript" src="/js/footable.min.js"></script>
		<script type="text/javascript" src="/js/jquery.ui.touch-punch.min.js"></script>
		<script type="text/javascript" src="/js/iframe.js"></script>
		<script type="text/javascript" src="/js/slick.min.js"></script>
		<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="/js/modal.js"></script>
		<script type="text/javascript" src="/js/moment.min.js"></script>
		<script type="text/javascript" src="/js/timeline.js"></script>
		<script type="text/javascript" src="/js/jquery.dynamiclist.js"></script>
		<script type="text/javascript" src="/js/jquery.star-rating-svg.js"></script>
		<script type="text/javascript" src="/js/jquery.knob.min.js"></script>		
		<script type="text/javascript" src="/js/common_function.js"></script>
		<script type="text/javascript" src="/js/intro_common.js"></script>
		
		<link rel="stylesheet" type="text/css" href="/css/home-default.css" />
		
		<style type="text/css">
		#wrap {
			background: url(${SYSTEM_MAIN_BG_IMG}) no-repeat;
			background-position: center;
			background-size: cover;
		}
		</style>
	</head>
	<h1 class="blind"></h1>
	
	
	
	<div id="loading_page">
		<p><i class="notched circle loading icon"></i></p>
	</div>
	
</html>