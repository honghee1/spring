<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
.container{
	width:300px;
	margin:0px auto;
	height: 600px;
	padding-top:200px;
	box-sizing: border-box;
}
label{
	width:100px;
	display:inline-block;
	text-align: right;
	padding-right:10px;
}
p{
	text-align: center;
}
</style>
</head>
<body>
	<jsp:include page="template/header.jsp"></jsp:include>
	
	<div class="container">
	<%
		boolean flag = false;
		if(session.getAttribute("login") != null)
			flag = (boolean)session.getAttribute("login");
		if(flag){
			%>
				<%=session.getAttribute("name") %> 님이 로그인 하셨습니다.<br>
				<a href="logout.jsp">로그아웃</a> | <a href="member_update_view.jsp">정보수정</a>
			<%
		}else{
	%>
		<form method="post" action="login.do">
			<label for="id">아이디 : </label><input type="text" name="id" id="id"><br>
			<label for="pass">암호 : </label><input type="password" name="pass" id="pass"><br> 
			<p>
			<button type="submit">로그인</button>
			<button id="register" type="button">회원가입</button>
			</p>
		</form>
		<%
		}
		%>
	</div>
		
	<jsp:include page="template/footer.jsp"></jsp:include>
</body>
</html>




