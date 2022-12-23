<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.login != null }">
<style>
	nav {
		width:1200px;
		margin:0 auto;/* 가운데 정렬  */
		border:1px solid black;
		text-align: center;
		position: relative;
	}
	ul{
		list-style-type: none;
		height: auto;
		display: inline-block;
	}
	li{
		display: inline-block;
	}
	#menu_bar a:link,#menu_bar a:visited {
		color:black;
		text-decoration: none;
		font-size: 17px;
		text-align: center;
		padding: 20px 30px;
	}
	#login{
		position: absolute;
		right: 0px;
		top:0px;
		width:150px;
		padding:5px;
	}
</style>
<nav id="menu_bar">
	<ul>
		<li><a href="main.do">HOME</a></li>
		<li><a href="logout.do">로그아웃</a></li>
		<c:if test="${sessionScope.grade == 6 }">
			<li><a href="memberManageView.do">전체 회원 관리</a></li>
			<li><a href="qnaAdminView.do">회원문의내역</a></li>
		</c:if>			
		<c:if test="${sessionScope.grade < 6 }">
		<li><a href="qnaView.do">문의하기</a></li>
		<li><a href="updateView.do">회원정보수정</a></li>
		</c:if>
	</ul>
	<div id="login">
		<table>
			<tr>
				<c:choose>
					<c:when test="${sessionScope.grade==1}">
						<td><img src="/img/bronze.png" style="width:32px;"></td>
					</c:when>
					<c:when test="${sessionScope.grade==2}">
						<td><img src="/img/silver.png" style="width:32px;"></td>
					</c:when>
					<c:when test="${sessionScope.grade==3}">
						<td><img src="/img/gold.png" style="width:32px;"></td>
					</c:when>
					<c:when test="${sessionScope.grade==4}">
						<td><img src="/img/vip.png" style="width:32px;"></td>
					</c:when>
					<c:when test="${sessionScope.grade==5}">
						<td><img src="/img/vvip.png" style="width:32px;"></td>
					</c:when>
					<c:when test="${sessionScope.grade==6}">
						<td><img src="/img/master.png" style="width:32px;"></td>
					</c:when>
				</c:choose>
				<td>${sessionScope.name }님 로그인</td>
			</tr>
			
		</table>
		
	</div>
</nav>		
</c:if>
<c:if test="${sessionScope.login == null }">
<!-- 메뉴부분 -->
<style>
	nav {
		width:1200px;
		margin:0 auto;/* 가운데 정렬  */
		border:1px solid black;
		text-align: center;
	}
	ul{
		list-style-type: none;
		height: auto;
		display: inline-block;
	}
	li{
		display: inline-block;
	}
	#menu_bar a:link,#menu_bar a:visited {
		color:black;
		text-decoration: none;
		font-size: 17px;
		text-align: center;
		padding: 20px 30px;
	}
</style>
<nav id="menu_bar">
	<ul>
		<li><a href="main.do">HOME</a></li>
		<li><a href="registerView.do">회원가입</a></li>
		<li><a href="loginView.do">로그인</a></li>
		<li><a href="#">문의하기</a></li>
	</ul>
</nav>
</c:if>



