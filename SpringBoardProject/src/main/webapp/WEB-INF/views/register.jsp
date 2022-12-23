<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style type="text/css">
	#container{
	width: 1200px;
	height:600px;
	margin:0 auto;
	text-align: center;
	}
	form{
		display: inline-block;
		margin-top:100px;
	}
</style>
</head>
<body>
	<jsp:include page="template/header.jsp"></jsp:include>
	<!-- 아이디 암호 이름 나이 입력 받는 회원가입 폼 -->
	<div id="container">
		<form action="register.do" method="post">
			<label>아이디 <input type="text" name="id"></label><br> 
			<label>암호<input type="password" name="passwd"></label><br>
			<label>이름 <input type="text" name="name"></label><br> 
			<label>닉네임 <input type="text" name="nick"></label><br>
			<input type="hidden" name="grade" value="0">
			<button>가입</button>
			<button type="button">뒤로가기</button>
		</form>
	</div>
	<jsp:include page="template/footer.jsp"></jsp:include>
</body>
</html>






















