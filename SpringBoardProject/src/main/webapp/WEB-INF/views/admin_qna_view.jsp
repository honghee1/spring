<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
	.container{
		width:1200px;
		margin:20px auto;
	}
	.qna_form{
		width:650px;
		margin:0 auto;
	}
	.qna_form table{
		border-collapse: collapse;
		box-sizing: border-box;
		width:100%;
	}
	.qna_form td{
		padding:5px;
	}
	.qna_form table tr td:first-child{
		width:500px;
		height: 40px;
	}
	.qna_form table tr:nth-child(2){
		height: 100px;
	}
	.qna_form input, .qna_form textarea{
		width:100%;
		height: 100%;
		border-radius: 5px;
		box-sizing: border-box;
	} 
	.qna_form textarea{
	 	padding:5px;
	 	resize: none;
	 }
	 
	 button{
	 	width: 100%;
	 	height: 140px;
	 }
	 
	 .qna_title ul{
	 	font-size: 0px;
	 	padding:0;
	 }
	 .qna_title li{
	 	display: inline-block;
	 	font-size: 16px;
	 	margin-right:50px;
	 	box-sizing: border-box;
	 }
</style>
</head>
<body>
	<jsp:include page="template/header.jsp"></jsp:include>
	<h3 class="qna_title">
		<ul>
			<li>제목 : ${requestScope.dto.title }</li>
			<li>작성자 : ${requestScope.dto.writer }</li>
			<li>작성일 : ${requestScope.dto.wdate }</li>
			<c:choose>
				<c:when test="${requestScope.dto.status == 0 }"><li>안읽음</li></c:when>
				<c:when test="${requestScope.dto.status == 1 }"><li>읽음</li></c:when>
				<c:otherwise><li>답변완료</li></c:otherwise>
			</c:choose>
		</ul>
	</h3>
	<div>
		<p>문의내용</p>
		<p class="qna_content">${requestScope.dto.content }<hr>
			답변내용<br>
			${requestScope.dto.response }
		</p>
	</div>
	<hr>
	<div class="qna_form">
		<form action="answer.do">
			<input type="hidden" name="qno" value="${requestScope.dto.qno }">
			<table>
				<tr>
					<td>
						<textarea name="response" placeholder="답변 내용을 입력해주세요">${requestScope.dto.response }</textarea>
					</td>
					<td>
						<button>답변하기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
	<jsp:include page="template/footer.jsp"></jsp:include>
</body>
</html>