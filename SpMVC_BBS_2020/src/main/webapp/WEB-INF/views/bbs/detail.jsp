  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />        
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1'>


<section id="bbs-detail-header">
	<article><img src="${rootPath}/upload"${BBSVO.b_file}>
		<article>
			<div class="title">제목</div><div class="content">${BBSVO.b_subject}</div>
			<div class="title">작성일시</div><div class="content">${BBSVO.b_date}, ${BBSVO.b_tiem}</div>
			<div class="title">작성자</div><div class="content">${BBSVO.b_write}</div>
		</article>
	</article>
</section>
<section id="bbs-detail-body">

</section>

</html>