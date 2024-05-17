<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<h2 class="p-3 mb-2 bg-light text-dark">科目情報登録</h2>

	<%--"StudentCreateExecute.action"が番号重複の場合もコントロールしている --%>
		<form action = "SubjectCreateExecute.action" method="post">
		<div class="mb-3">
			<label for="disabledTextInput" class="form-label">科目コード</label>

			<%--value subject_cd 見返す (style="ime-mode:disabled"はブラウザでは機能していない)--%>
			<input type="text" class="form-control"
				name="subject_cd" placeholder="科目コードを入力してください" maxlength="3" style="ime-mode:disabled" value="${subject_cd}" required />
			<div style="color: red;">${errors.get("subject_cd")}</div>
		</div>

			<label for="disabledTextInput" class="form-label">科目名</label>
			<%--value subject_name 見返す --%>
			<input type="text" class="form-control"
				name="subject_name" placeholder="科目名を入力してください" maxlength="20" value="${subject_name}" required />
			<div style="color: red;">${errors.get("subject_name")}</div>
			<br>
			<input type="submit" class="btn btn-primary" value="登録して終了">



		</form>

		<a href="SubjectList.action">戻る</a>

	</c:param>

</c:import>