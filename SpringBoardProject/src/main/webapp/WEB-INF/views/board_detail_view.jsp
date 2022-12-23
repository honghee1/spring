<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 페이지</title>
<style type="text/css">
	#container{
		/* background-color: lime; */
		width:1200px;
		margin:0 auto;
	}
	#container table{
		width:600px;
		border-collapse: collapse;
		margin:0 auto;
	}
	#container th{
		width:150px !important;
		text-align: right;
		padding:5px;
	}
	#container td{
		width: 500px;
		padding:5px;
		height: 40px;
	}
	#container input{
		width: 100%;
		height:40px;
		box-sizing:border-box;
		border-width : 1px;
		border-radius: 5px;
	}
	h2{
		text-align: center;
	}
	.btn{
		text-decoration: none;
		background-color: #e8e8e8;
		min-width: 80px;
		display: inline-block;
		padding:5px 0px;
		font-weight:normal;
		border : 1px solid #585858;
		text-align:center;
		color : black;
		font-size:14px;
		box-sizing: border-box;
	}
	.btn:hover{
		background-color: #282828;
		color:#FFFFFF
	}
	.hate{
		transform: rotate(0.5turn);
	}
	.text_center{
		text-align: center;
	}
	.btn_like{
		width:64px;
		display: inline-block;
		color:black;
		text-decoration: none;
	}
	.comment_form{
		width:100%;
		border :1px solid black;
	}
	.writer{
		width: 100%;
		display: inline-block;
		font-weight: bold;
		padding-left: 30px;
	}
	.comment_form textarea{
		width:100%;
		height: 80px;
		margin-top:10px;
		font-size: 18px;
		padding-left: 30px;
		padding-right: 30px;
		outline:none;
		border: none;
		resize: none;
		box-sizing: border-box;
		
	}
	.comment_form button{
		width: 100px;
		height: 40px;
		font-weight: bold;
		background-color: #ffff00;	
		outline:none;
		border: none;
	}
	.comment_form button:hover{
		background-color: #dfdf25;	
	}
	.length{
		text-align: right;
		padding:10px 30px;
	}
	hr{
	margin:0;
	}
	.comment_form p {
		margin:0;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(function(){
		$(".comment_form textarea").keyup(function() {
			$(this).next().text($(this).val().length+"/500");
		});
		$(".comment_form button").click(function(){
			var data = $("#comment").serialize();
			$.ajax({
				url : "insertComment.do",
				data : data,
				method:"get",
				success:function(d){
					if(d == 1){
						alert("댓글 등록 성공");
					}else{
						alert("댓글 등록 실패");
					}
					location.reload();	
				}
			});
		});
		$(".btn_like").click(function(){
			// 0 - like, 1 - hate
			// bno;
			var obj = $(this);
			var idx = $(this).index();
			d = "bno=${requestScope.board.bno}&mode="+idx;
			$.ajax({
				url : "plusLikeHate.do",
				data : d,
				method : "get",
				success:function(result){
					var msg = "이 게시물에다가";
					msg += idx == 0 ? "좋아요를 " : "싫어요를 ";
					msg += result == 1 ? "하셨습니다." : "취소하셨습니다.";
					alert(msg);
					location.reload();					
				}
			});
		});
	});
</script>
</head>
<body>
	<c:if test="${sessionScope.login == null || sessionScope.login == false  }">
		<c:set var="page" target="${sessionScope }" value="${pageContext.request.requestURI}${pageContext.request.queryString }" property="resultPage" scope="session"/>
		${pageContext.request.requestURI}${pageContext.request.queryString }
		<script>
			alert("로그인을 하셔야 이용할수 있습니다.");
			location.href="loginView.do";
		</script>
	</c:if>

	<jsp:include page="template/header.jsp" flush="false"></jsp:include>
	<div id="container">
		<h2>글조회 페이지</h2>
			<table>
				<tr>
					<th>제목</th>
					<td>
						<!-- 조회한 내용 -->
						${requestScope.board.title }
					</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>
						${requestScope.board.writer }			
					</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>
						${requestScope.board.bCount }
					</td>
				</tr>
				<tr>
					<th>작성일</th>
					<td>
						${requestScope.board.bDate }
					</td>
				</tr>
				<tr>
					<th style="vertical-align: top;">내용</th>
					<td>
						${requestScope.board.content }
					</td>
				</tr>
				<tr>
					<td colspan="2">
						첨부파일<br>
						<c:forEach var="f" items="${requestScope.flist }">
							<a href="fileDown.do?fno=${f.fno}&bno=${f.bno}">
							${f.fileName}</a><br>
							<!-- 해당 파일이 이미지인지? -->
							<c:if test="${f.type =='image' }">
								<img src="imageLoad.do?file=${f.fileName}&type=${f.type}">
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text_center">
						<a href="#" class="btn_like">
							<img src="${pageContext.request.contextPath }/img/like.png">
							<!-- 좋아요 개수 -->
							<span>${requestScope.board.bLike }</span>
						</a>
						<a href="#" class="btn_like">
							<img src="${pageContext.request.contextPath }/img/like.png" class="hate">
							<!-- 싫어요 개수 -->
							<span>${requestScope.board.bHate}</span>
						</a>
					</td>
				</tr>
				<c:if test="${sessionScope.login == true}">
				<tr>
					<td colspan="2">
						<div class="comment_form">
							<form id="comment">
							<input type="hidden" name="bno" value="${requestScope.board.bno }">
							<input type="hidden" name="writer" value="${sessionScope.id }">
							<span class="writer">${sessionScope.id }</span>
							<textarea name="content" maxlength="500"></textarea>
							<p class="length">0/500</p><hr>
							<p style="text-align: right;"><button type="button">등록</button></p>
							</form>							
						</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<th><a href="/main.do" class="btn">목록보기</a></t	h>
					<td style="text-align: right;">
					<c:if test="${sessionScope.id == requestScope.board.writer }">
						<a href="boardUpdateView.do" class="btn">수정</a>
						<a href="deleteBoard.do?bno=${requestScope.board.bno }" class="btn">삭제</a>
					</c:if>
					<c:choose>
						<c:when test="${requestScope.other.BEFORE != -1 }">
							<a href="boardView.do?bno=${requestScope.other.BEFORE }" class="btn">이전글</a>
						</c:when>
						<c:otherwise>
							<a class="btn">이전글이 없습니다.</a>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${requestScope.other.NEXT != -1 }">
							<a href="boardView.do?bno=${requestScope.other.NEXT }" class="btn">다음글</a>
						</c:when>
						<c:otherwise>
							<a class="btn">다음글이 없습니다.</a>
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
				
						<td colspan="2">
					
					<c:forEach var="comment" items="${requestScope.comment }">
						<p>${comment.writer }
						${comment.cdate }
						<a href="commentLike.do?cno=${comment.cno }&bno=${board.bno}">${comment.clike }</a>
						<a href="commentHate.do?cno=${comment.cno }&bno=${board.bno}">${comment.chate }</a>
						<!-- 댓글 삭제하는 링크 deleteComment.do 댓글 번호를 기준으로 삭제-->
						<c:if test="${sessionScope.id == comment.writer || sessionScope.grade == 6 }">
							<a href="deleteComment.do?cno=${comment.cno }&bno=${board.bno}">댓글 삭제</a>
						</c:if>
						</p>
						<p>
							${comment.content }
						</p>
					</c:forEach>
					
						</td>
				</tr>
			</table>
	</div>
	<jsp:include page="template/footer.jsp" flush="false"></jsp:include>
</body>
</html>











