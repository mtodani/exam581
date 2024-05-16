<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<h2>科目情報登録</h2>

	<%--"StudentCreateExecute.action"が番号重複の場合もコントロールしている --%>
		<form action = "SubjectCreateExecute.action" method="post">

			<label>科目コード</label>
			<%--value subject_cd 見返す --%>
			<input type="text"
				name="subject_cd" placeholder="科目コードを入力してください" maxlength="3" style="ime-mode:disabled" value="${subject_cd}" required />
			<div>${errors.get("subject_cd")}</div>

			<label>科目名</label>
			<%--value subject_name 見返す --%>
			<input type="text"
				name="subject_name" placeholder="科目名を入力してください" maxlength="10" value="${subject_name}" required />
			<div>${errors.get("subject_name")}</div>

			<input type="submit" value="登録して終了">
		</form>

		<a href="SubjectList.action">戻る</a>

	</c:param>

</c:import>