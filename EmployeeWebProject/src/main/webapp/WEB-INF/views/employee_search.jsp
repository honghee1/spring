<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url: "all.do",
			dataType: "json",
			success:function(r){
				var tag = "";
				if(r.code == 200) {
					var arr = r.result;
					for(i=0;i<arr.length;i++) {
						tag += "<p>";
						tag += "<span>"+arr[i].eno+"</span>";
						tag += "<span>"+arr[i].name+"</span>";
						tag += "<span>"+arr[i].department+"</span>";
						tag += "<span>"+arr[i].position+"</span>";
						tag += "</p>";
					}
				}else {
					tag = r.message;
				}
				$(".container").html(tag);
			}
		});
		//--------------------------------------------------------------------
		$(".btn").click(function() {
			var d = $("#frm").serialize();
			$.ajax({
				url: "search.do",
				data: d,
				dataType: "json",
				success:function(r){
					var tag = "";
					if(r.code == 200) {
						var arr = r.result;
						for(i=0;i<arr.length;i++) {
							tag += "<p>";
							tag += "<span>"+arr[i].eno+"</span>";
							tag += "<span>"+arr[i].name+"</span>";
							tag += "<span>"+arr[i].department+"</span>";
							tag += "<span>"+arr[i].position+"</span>";
							tag += "</p>";
						}
					}else {
						tag = r.message;
					}
					$(".container").html(tag);
				}
			});
		});
	});
</script>
</head>
<body>
	<form id="frm">
		<select name="kind">
			<option value="name">이름</option>
			<option value="department">부서</option>
			<option value="position">직급</option>
		</select>
		<input type="text" name="search"><button type="button" class="btn">검색</button>
	</form>
	<hr>
	<div class="container"></div>
</body>
</html>