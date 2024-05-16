
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

		<h2>科目管理</h2>
		<%--科目機能url--%>
		<a href="SubjectCreate.action">新規登録</a>
		<a href="SubjectRevival.action">科目復元</a>

		<%--科目一覧の表示--%>
		<c:choose>
			<c:when test="${subjects.size()>0}">

				<div>検索結果：${subjects.size()}件</div>

				<table class="table table-hover">
					<tr>
						<th>科目コード</th>
						<th>科目名</th>

						<th></th>
						<th></th>
					</tr>
					<c:forEach var="subject" items="${subjects}">
						<tr>
							<td>${subject.subject_cd}</td>
							<td>${subject.subject_name}</td>

							<td class="text-center">

							<%--科目の変更・削除に科目コードを渡す--%>
							</td>
							<td><a href="SubjectUpdate.action?subject_cd=${subject.subject_cd}">変更</a></td>
							<td><a href="SubjectDelete.action?subject_cd=${subject.subject_cd}">削除</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>

			<%--科目が存在しない場合--%>
			<c:otherwise>
				<div>科目情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>

	</c:param>

</c:import>