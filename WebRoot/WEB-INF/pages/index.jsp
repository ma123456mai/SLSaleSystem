<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!--
		Charisma v1.0.0

		Copyright 2012 Muhammad Usman
		Licensed under the Apache License v2.0
		http://www.apache.org/licenses/LICENSE-2.0

		http://usman.it
		http://twitter.com/halalit_usman
	-->
<meta charset="utf-8">
<title>SL会员商城</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
<meta name="author" content="Muhammad Usman">

<!-- The styles -->
<link id="bs-css" href="statics/css/bootstrap-cerulean.css" rel="stylesheet">
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

.code
    {
            background:url(code_bg.jpg);
            font-family:Arial;
            font-style:italic;
            color:#AAAAAA;
            font-size:20px;
            border:0;
            padding:2px 3px;
            letter-spacing:3px;
            font-weight:bolder;             
            float:left; 
            cursor:pointer;
            width:65px;
            height:20px;
            line-height:20px;
            text-align:center;
            vertical-align:middle;

    }
    a
    {
        text-decoration:none;
        font-size:12px;
        color:#288bc4;
        }
    a:hover
    {
       text-decoration:underline;
</style>
<link href="statics/css/bootstrap-responsive.css" rel="stylesheet">
<link href="statics/css/charisma-app.css" rel="stylesheet">
<link href="statics/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='statics/css/fullcalendar.css' rel='stylesheet'>
<link href='statics/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<link href='statics/css/chosen.css' rel='stylesheet'>
<link href='statics/css/uniform.default.css' rel='stylesheet'>
<link href='statics/css/colorbox.css' rel='stylesheet'>
<link href='statics/css/jquery.cleditor.css' rel='stylesheet'>
<link href='statics/css/jquery.noty.css' rel='stylesheet'>
<link href='statics/css/noty_theme_default.css' rel='stylesheet'>
<link href='statics/css/elfinder.min.css' rel='stylesheet'>
<link href='statics/css/elfinder.theme.css' rel='stylesheet'>
<link href='statics/css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='statics/css/opa-icons.css' rel='stylesheet'>
<link href='statics/css/uploadify.css' rel='stylesheet'>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="statics/img/favicon.ico">

</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>欢迎来到SL会员商城</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info">请输入登陆账号和密码</div>
					<div class="form-horizontal">
						<fieldset>
							<div class="input-prepend" title="登陆账号" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i> </span><input
									autofocus class="input-large span10" name="loginCode"
									id="loginCode" type="text" value="" />
							</div>
							<div class="clearfix" ></div>

							<div class="input-prepend" title="登录密码" data-rel="tooltip" >
								<span class="add-on"><i class="icon-lock"></i> </span>
								<input class="input-large span10" name="password" id="password"
									type="password" value="" />
							</div>
							
							<div style=" margin:0 auto;width: 220px; height: 55px;left: 20px">
							   	<table>
							        <tr>
							            <td><input class="input-large span10" type="text" id="inputCode" title="验证码 "/></td>
							            <td><div class="code" style="color: #AAAAAA;border: solid 1px;" id="checkCode" onclick="createCode()" ></div></td>
							        	
							        </tr>
							        
							    </table>
							    <p style="color:#cc9900">请点击图片刷新(验证码不区分大小写)</p>
  							</div>

							<div class="clearfix"></div>
							<ul id="formtip"></ul>
							<p class="center span5">
								<button type="submit" class="btn btn-primary" id="loginBtn">登录</button>
							</p>
						</fieldset>
					</div>

				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->

	</div>
	<!--/.fluid-container-->

	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="statics/js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="statics/js/jquery-ui-1.8.21.custom.min.js"></script>
	<!-- transition / effect library -->
	<script src="statics/js/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script src="statics/js/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script src="statics/js/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script src="statics/js/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script src="statics/js/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script src="statics/js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="statics/js/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script src="statics/js/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script src="statics/js/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<script src="statics/js/bootstrap-collapse.js"></script>
	<!-- carousel slideshow library (optional, not used in demo) -->
	<script src="statics/js/bootstrap-carousel.js"></script>
	<!-- autocomplete library -->
	<script src="statics/js/bootstrap-typeahead.js"></script>
	<!-- tour library -->
	<script src="statics/js/bootstrap-tour.js"></script>
	<!-- library for cookie management -->
	<script src="statics/js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script src='statics/js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='statics/js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script src="statics/js/excanvas.js"></script>
	<script src="statics/js/jquery.flot.min.js"></script>
	<script src="statics/js/jquery.flot.pie.min.js"></script>
	<script src="statics/js/jquery.flot.stack.js"></script>
	<script src="statics/js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script src="statics/js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="statics/js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="statics/js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script src="statics/js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script src="statics/js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script src="statics/js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script src="statics/js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="statics/js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="statics/js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="statics/js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="statics/js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="statics/js/charisma.js"></script>
	<script src="statics/localjs/index.js"></script>
	


</body>
</html>
