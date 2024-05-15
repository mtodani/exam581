<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<h2>科目情報削除</h2>
	<form action = "SubjectDeleteExecute.action" method="post">

	<c:choose>
	    <c:when test="${not empty errors.subject_now}">
            <div style="color: red;">${errors.subject_now}</div>
        </c:when>

		<c:otherwise>
			<input type= "hidden" name="subject_name" value="${subject_name}">
			<input type= "hidden" name="subject_cd" value="${subject_cd}">
			「${subject.getSubject_name()}(${subject.getSubject_cd()})を削除しますか？」

			<br><input type="submit" value="削除">
		</c:otherwise>

	</c:choose>
	</form>
	<a href="SubjectList.action">科目一覧</a>
	</c:param>

</c:import>