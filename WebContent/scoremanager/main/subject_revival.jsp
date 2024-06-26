<%-- 科目復元管理一覧JSP --%>
<%-- EL記法 リクエストやセッションとかに入っている変数を宣言なしで取り出すことができる --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<h2>科目復元管理</h2>
		<a href="SubjectList.action">科目一覧</a>

		<%--論理削除された科目の一覧表示--%>
		<form action="SubjectRevivalExecute.action" method="post">
		<table class="table table-hover">
					<tr>
						<th>選択</th>
						<th>科目コード</th>
						<th>科目名</th>

						<th></th>
						<th></th>
					</tr>

	    <c:forEach var="subject" items="${subjects}" >
	    <%--科目をチェックリストで表示--%>
	    	<tr>
		        <td><input type="checkbox" name="s_Rvival" value="${subject.subject_cd}"></td>
		        <td> ${subject.subject_cd}</td>
		        <td>${subject.getSubject_name()}</td>
	        </tr>
	    </c:forEach>
	    </table>

	    <%--チェックリストが１つも選択されずに復元をした場合の表示--%>
	    <c:if test="${not empty errors.s_Rvival}">
			<div style="color: red;">${errors.s_Rvival}</div>
		</c:if>

	    <input type="submit" value="復元">
		</form>

	</c:param>

</c:import>