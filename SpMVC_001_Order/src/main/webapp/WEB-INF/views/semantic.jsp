<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 홈페이지</title>
</head>
<body>
	<%
	// jsp 코드가 들어가는 영영
	// scriptlet, java 문법의 스크립트를 HTML 파일에 끼워넣는곳
	String name= "홍길동";
	%> 
	
	<%@include file="/WEB-INF/views/include/include-header.jspf" %>
	<%@include file="/WEB-INF/views/include/include-nav.jspf" %>
	
	
	<%
	/*
	semantics tag
	HTML5에서 div tag를 사용한 layout을 대체하는 새로운 규격의 tag 들 
	보통 <div id= "header"></div> 와 같은 element로 Layout을 만들었는데
	tag들이 여러번 반복적으로 퐇마되는 복잡한 Layout 구조에서는
	가독성을 심하게 헤치게 되어, 유지보수, 기능추가, 변경 등을 수행하는데
	많은 에로사항이 있다.
	
	이러한 여러가지 불편한 상황을 대체 하고자 HTML5에 새롭게 도입된 tga
	
	*/
	
	
	
	
	
	
	%>
	<section>
		<h3>반갑습니다</h3>
		<p>나는 ${name} 입니다</p>
	</section>
	
</body>
</html>