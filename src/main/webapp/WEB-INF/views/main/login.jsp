<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="/WEB-INF/views/common/page_init.jsp" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<script>
			$(document).ready(function () {
				
			});
			
			function saveUserInfo(){
				
				if($("#userName").val() == null || $("#userName").val() == ""){
					alert("별명을 입력해 주세요.");
					$("#userName").focus();
					return false;
				}
				
				$("#engForm").attr("action", "/saveUserInfo");
			    $("#engForm").submit();
			}
			
    		
    		
		</script>
	</head>
	<form class="ui form pt10" id="engForm" name="engForm" method="post">
		<input type="hidden" id="androidId" name="androidId" value="${vo.androidId}"/> 

		<div id="wrap" class="flex_bg">
	        <div class="flex_child">
	            <div class="ui stretched grid">
	                <div class="sixteen wide tablet eight wide computer column login-box">
	                    <div class="ui segment login_segment">
	                        <h1>
	                            <div class="logo_img">
	                            	<span>별명 입력</span>
	                            </div>
	                        </h1>
	                        
	                        <div id="tab1" class="tab_content" style="display: block;">
	                            <div class="field">
	                                
	                                <div class="ui left icon input">
	                                    <i class="user blue icon"></i>
	                                    <input type="text" id="userName" name="userName"  placeholder="별명을 입력하세요.">
	                                </div>
	                            </div>
	                            
	                            <div class="ui fluid blue button" OnClick="saveUserInfo()">로그인</div>
	                        </div>
	                    </div>
	                </div>
		        </div>
		    </div>
		</div>
    </form>

</html>
