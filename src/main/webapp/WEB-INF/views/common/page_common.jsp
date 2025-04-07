<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@	taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	pageContext.setAttribute("crlf", "\n");

	
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta name="author" content="영어 퀴즈 풀어 볼래" />
	<meta name="description" content="영어 퀴즈 풀어 볼래" />
	<meta name="keywords" content="영어 퀴즈 풀어 볼래" />

	<title>영어 퀴즈 풀어 볼래</title>

	<!--[if lt IE 9]> <script src="경로명/html5shiv.js"></script> <![endif]-->

	<!-- Favicon ie/ie외 브라우저/apple/android -->
	<link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico" />
	<link rel="icon" type="image/png" href="/img/favicon-32x32.png" />
	<link rel="apple-touch-icon" href="/img/apple-icon-180x180.png" />
	<link rel="icon" type="image/png" href="/img/android-icon-192x192.png" />

	<!-- Stylesheets -->
	<link rel="stylesheet" type="text/css" href="/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="/css/jquery-ui-slider-pips.css" />
	<link rel="stylesheet" type="text/css" href="/css/footable.standalone.css" />
	<link rel="stylesheet" type="text/css" href="/css/jquery.mCustomScrollbar.min.css" />
	<link rel="stylesheet" type="text/css" href="/css/semantic.css" />
	<link rel="stylesheet" type="text/css" href="/css/ionicons.css" />
	<link rel="stylesheet" type="text/css" href="/css/plyr.css" />
	<link rel="stylesheet" type="text/css" href="/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="/css/element-ui.css" />
	<link rel="stylesheet" type="text/css" href="/css/element-ui-dark.css" />
	<!-- Summernote Editor -->
	<link rel="stylesheet" type="text/css" href="/summernote/summernote-lite.css" />
	<link rel="stylesheet" type="text/css" href="/summernote/plugin/math/katex.min.css" />
	<link rel="stylesheet" type="text/css" href="/summernote/plugin/emoji/css/emoji.css" />

	<!-- Scripts -->
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui-slider-pips.js"></script>
	<script type="text/javascript" src="/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="/js/modernizr.custom.js"></script>
	<script type="text/javascript" src="/js/classie.js"></script>
	
	<script type="text/javascript" src="/js/gnmenu.js"></script>
	
	<script type="text/javascript" src="/js/semantic.min.js"></script>
	<script type="text/javascript" src="/js/semantic-ui-calendar.min.js"></script>
	<script type="text/javascript" src="/js/footable.min.js"></script>
	<script type="text/javascript" src="/js/iframe.js"></script>
	<script type="text/javascript" src="/js/chart.min.js"></script>
	<script type="text/javascript" src="/js/Chart.PieceLabel.min.js"></script>
	<script type="text/javascript" src="/js/slick.min.js"></script>
	<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
	<script type="text/javascript" src="/js/modal.js"></script>
	<script type="text/javascript" src="/js/moment.min.js"></script>
	<script type="text/javascript" src="/js/timeline.js"></script>
	<script type="text/javascript" src="/js/jquery.dynamiclist.js"></script>
	<script type="text/javascript" src="/js/jquery.star-rating-svg.js"></script>
	<script type="text/javascript" src="/js/jquery.knob.min.js"></script>
	<script type="text/javascript" src="/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="/js/jquery.video-layers.min.js"></script>
	<script type="text/javascript" src="/js/plyr.js"></script>
	<script type="text/javascript" src="/js/hls.min.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
	
</head>