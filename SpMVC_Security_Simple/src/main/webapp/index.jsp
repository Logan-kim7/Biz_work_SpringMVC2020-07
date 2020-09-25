<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 홈페이지</title>
</head>
<body>
<h3> 나의 홈페이지 방문을 환영 합니다.</h3>

<form method="POST" action="${rootPath}/logout">
	<button>Logout</button>
	<input name="${_csrt.parameterName}" value="${_csrf.token}">

</form>
</body>
</html>