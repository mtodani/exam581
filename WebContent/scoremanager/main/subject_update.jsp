<%-- 科目更新JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<h2 class="p-3 mb-2 bg-light text-dark">科目情報変更</h2>
		<form action = "SubjectUpdateExecute.action" method="post">

			<label class="col-sm-2 col-form-label">科目コード</label>
			<br><input type= "hidden" class="form-control-plaintext" name="subject_cd" value="${subject_cd}">
			　${subject.getSubject_cd()}

			<br>

			<br><label class="col-sm-2 col-form-label">科目名</label>

			<br><input type="text" class="form-control" name="subject_name" placeholder="科目を入力してください"
				maxlength="10" value="${subject_name}" required />
			<div>${errors.get("subject_name")}</div>

			<c:if test="${not empty errors.subject_now}">
				<div style="color: red;">${errors.subject_now}</div>
			</c:if>
			<br>
			<input type="submit" class="btn btn-primary" value="変更">


		</form>

		<a href="SubjectList.action">戻る</a>

	</c:param>

</c:import>