<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/lib/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<form action="update.do" method="post">
		<input type="text" name="id" value="${requestScope.dto.id }"><br>
		<input type="password" name="passwd" value="${requestScope.dto.passwd }"><br>
		<input type="text" name="name" value="${requestScope.dto.name }"><br>
		<input type="number" name="age" value="${requestScope.dto.age }"><br>
		<input type="radio" id="gender1" name="gender" value="M" <c:if test="${requestScope.dto.gender == 'M' }">checked</c:if>><label for="gender1">남</label>		
		<input type="radio" id="gender2" name="gender" value="F" <c:if test="${requestScope.dto.gender == 'F' }">checked</c:if>><label for="gender2">여</label><br>	
		<input type="text" name="address" value="${requestScope.dto.address }"><br>
		<button>수정</button>
	</form>
</body>
</html>