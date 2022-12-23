<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.13.2/themes/base/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.13.2/jquery-ui.min.js"></script>
<script>
	$(function(){
		$("#qna_list").accordion();
		$(".qna_form form").submit(function(){
			var title = $("input[name=title]").val();
			var content = $("textarea[name=content]").val();
			console.log(title,content);
			var result = true;
			if(title.length == 0){
				alert("문의 제목을 입력하세요");
				result = false;
			}else if(title.length > 33){
				alert("문의 제목은 33글자까지만 입력하세요");
				result = false;
			}else if(content.length == 0){
				alert("문의 내용을 입력하세요");
				result = false;
			}else if(content.length > 1333){
				alert("문의 내용은 1333글자까지만 입력하세요");
				result = false;
			}
			return result;
		});
		var page = 1;//페이지 번호
		$("#btn_more").click(function(){
			page++;
			$.ajax({
				url:"nextQnaList.do",
				data : "page="+page,
				dataType : "json",
				success:function(r){
					if(r.nextPage == 0){
						$("#btn_more").off("click").text("더 이상 불러올 내용이 없습니다");
					}
					var arr = r.list;
					var tag = "";
					for(i=0;i<arr.length;i++){
						tag += "<h3 class='qna_title'><ul>";
						tag += "<li>제목 : "+arr[i].title+"</ul>";
						tag += "<li>작성자 : "+arr[i].writer+"</ul>";
						tag += "<li>작성일 : "+arr[i].wdate+"</ul>";
						switch(arr[i].status){
						case 0:
							tag += "<li>안읽음</li>";
							break;
						case 1:
							tag += "<li>읽음</li>";
							break;
						case 2:
							tag += "<li>답변완료</li>";
							break;
						}
						tag += "</ul></h3><div>";
						tag += "<p class='qna_content'>문의내용<br>"+arr[i].content+"</p><hr>";
						tag += "<p class='qna_response'>답변내용<br>"+arr[i].response+"</p></div>";
					}
					$("#qna_list").append(tag);
					$("#qna_list").accordion("refresh");
				}
			});
		});
	});
</script>
<style>
.container {
	width: 1200px;
	margin: 20px auto;
}
.qna_form {
	width: 650px;
	margin: 0 auto;
}
.qna_form table {
	border-collapse: collapse;
	box-sizing: border-box;
	width: 100%;
}
.qna_form td {
	padding: 5px;
}
.qna_form table tr td:first-child {
	width: 500px;
	height: 40px;
}
.qna_form table tr:nth-child(2) {
	height: 100px;
}
.qna_form input, .qna_form textarea {
	width: 100%;
	height: 100%;
	border-radius: 5px;
	box-sizing: border-box;
}
.qna_form textarea {
	resize: none;
}
.qna_form button {
	width: 100%;
	height: 140px;
}
.myButton {
	box-shadow: inset 0px 1px 0px 0px #bbdaf7;
	background: linear-gradient(to bottom, #79bbff 5%, #378de5 100%);
	background-color: #79bbff;
	border-radius: 6px;
	border: 1px solid #84bbf3;
	display: inline-block;
	cursor: pointer;
	color: #ffffff;
	font-family: Arial;
	font-size: 15px;
	font-weight: bold;
	padding: 6px 24px;
	text-decoration: none;
	text-shadow: 0px 1px 0px #528ecc;
}
.myButton:hover {
	background: linear-gradient(to bottom, #378de5 5%, #79bbff 100%);
	background-color: #378de5;
}
.myButton:active {
	position: relative;
	top: 1px;
}
.qna_title ul{
	list-style-type: none;
	font-size:0px;
	padding:0;
}
.qna_title li{
	display: inline-block;
	font-size: 16px;
	margin-right: 50px;
}
#qna_list{
	width: 800px;
	margin:0 auto;
}
#btn_more{
	width: 800px;
	border : none;
	border-radius:5px;
	height: 50px;
	font-size: 18px;
	display: block;
	margin:0 auto;
}
</style>
</head>
<body>
	<jsp:include page="template/header.jsp"></jsp:include>
	<div class="container">
		<div class="qna_form">
			<form action="sendQnA.do">
				<table>
					<tr>
						<td><input type="text" name="title"
							placeholder="문의글 제목 입력하세요"></td>
						<td rowspan="2"><button class="myButton">문의하기</button></td>
					</tr>
					<tr>
						<td><textarea name="content" placeholder="문의내용을 입력하세요"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<hr>
		<div id="qna_list">
			<!-- 문의 답변 목록 -->
			<c:forEach var="dto" items="${requestScope.list }">
				<h3 class="qna_title">
					<ul>
						<li>제목 : ${dto.title }</li>
						<li>작성자 : ${dto.writer }</li>
						<li>작성일 : ${dto.wdate }</li>
						<c:choose>
							<c:when test="${dto.status == 0 }"><li>안읽음</li></c:when>
							<c:when test="${dto.status == 1 }"><li>읽음<li></c:when>
							<c:otherwise><li>답변완료</li></c:otherwise>
						</c:choose>
					</ul>
				</h3>
				<div>
					<p class="qna_content">문의내용<br>${dto.content }</p>
					<hr>
					<p class="qna_response">답변내용<br>${dto.response }</p>
				</div>
			</c:forEach>
		</div>
		<button id="btn_more">더보기</button>
	</div>
	<jsp:include page="template/footer.jsp"></jsp:include>
</body>
</html>
