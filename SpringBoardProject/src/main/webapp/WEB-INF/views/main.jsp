<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
<style type="text/css">
	#container{
		/* background-color: lime; */
	}
	.board{
		margin:20px auto;
		border-collapse: collapse;
	}
	.board th, .board td{
		padding: 10px;
		text-align: center;
	}
	.board th{
		border-top : 2px solid black;
		border-bottom : 2px solid black;
	}
	.board td{
		border-top : 1px solid black;
		border-bottom : 1px solid black;
	}
	.board tr:last-child > td {
		border-bottom : 2px solid black;
	}
	.title{
		width:500px;	
	}
	.writer{
		width:150px;	
	}
	.date{
		width:200px;
	}
	.page_bar{
		position:relative;
		text-align: center;
	}
	.page_bar a:link,.page_bar a:visited {
		color:black;
		text-decoration: none;
		font-size : 18px;
		margin-left: 10px;
		margin-right: 10px;
	}
	.page_bar a:hover{
		font-weight: bold;
		color:red;
	}
	.btn_writer{
		position:absolute;
		right:0px;
	}
</style>
</head>
<body>
	<!-- header.jsp를 현재 문서에 포함 -->
	<jsp:include page="template/header.jsp" flush="false"></jsp:include>
	<div id="container">
		<table class="board">
			<tr>
				<th>글번호</th>
				<th class="title">제목</th>
				<th class="writer">작성자</th>
				<th class="date">작성일</th>
				<th>조회수</th>
				<th>좋아요</th>
				<th>싫어요</th>
			</tr>
	<!-- 게시판 기능 추가 기존 게시판에 있는 내용을 el과 jstl로 표현 -->
		<c:if test="${requestScope.list ==null}">
			<script>
				location.href="main.do?pageNo=1";
				
			</script>
		</c:if>
		<c:forEach var="dto" items="${requestScope.list }">
			<tr>
				<td>${dto.bno }</td>
				<td><a href="boardView.do?bno=${dto.bno }"> ${dto.title }</a></td>
				<td>${dto.writer}</td>
				<td>${dto.bDate }</td>
				<td>${dto.bCount }</td>
				<td>${dto.bLike }</td>
				<td>${dto.bHate }</td>
			</tr>
		</c:forEach>
			<tr>
			<td colspan="7">
					<div class="page_bar">
						<c:if test="${pagging.previousPageGroup }">
							<a href="main.do?pageNo=${pagging.startPageOfPageGroup - 1 }">◀</a>
						</c:if>
						<c:forEach var="i" begin="${pagging.startPageOfPageGroup}" 
						end="${pagging.endPageOfPageGroup}">
							<a href="main.do?pageNo=${i }">${ i}</a>
						</c:forEach>
					
						<c:if test="${pagging.nextPageGroup }">
							<a href="main.do?pageNo=${pagging.endPageOfPageGroup + 1 }">▶</a>
						</c:if>
						<a href="boardWriteView.do" class="btn_writer">글쓰기</a>
					</div>
			</tr>
		</table>
	</div>
	<jsp:include page="template/footer.jsp" flush="false"></jsp:include>
	
</body>
</html>







