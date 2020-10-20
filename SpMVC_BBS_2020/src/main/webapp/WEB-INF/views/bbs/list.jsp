<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />     
<script>
	document.addEventListener("DOMContentLoaded", function(){
		
		document.querySelector("#bbs-write").addEventListener("click", function(){
			
			document.location.href="${rootPath}/bbs/write"	
		})
		
		  document.querySelector("table").addEventListener("click",function (event){
            let tag_name = event.target.tagName;
            //alert(tag_name);
           if(tag_name === "TD"){
        	   let seq = event.target.dataset.seq
        	   if(seq){
        		   // alert(seq)
        		   document.location.href = "${rootPath}/bbs/detail/" + seq
        	   }
        	   
           }
            
            
        });
		
	})	
</script>
<style>
 td.bbs-subject{
 	coursor: pointer;
 }
 
 td.bbs-subject:hover{
  background-color: #ccc;
 }

</style>
<table class="table table-scriped table-bordered table-hover">
<thead>
	<tr>
			<th>NO</th>
			<th>작성일자</th>
			<th>작성시각</th>
			<th>작성자</th>
			<th>제목</th>
			<th>조회수</th>
		</tr>
</thead>
	
<tbody>

	<c:if test="${empty BBS_LIST}">
		<tr><td colspan="6">데이터가 없습니다.</td></tr>
	</c:if>
	<c:forEach items="${BBS_LIST}" var="vo" varStatus="i">
	<tr class="bbs_tr" data-seq="${vo.b_seq}" >		
		<td>${i.count}</td>
		<td>${vo.b_date}</td>
		<td>${vo.b_time}</td>
		<td>${vo.b_writer}</td>
		<td class="bbs-subject" data-seq="${vo.b_seq}">
		${vo.b_subject}
		<img src="${rootPath}/upload/${vo.b_file}" width="50px">
		</td>
		<td>${vo.b_count}</td>
	</tr>
	</c:forEach>
</tbody>
	
</table>
<button id="bbs-write">글쓰기</button>