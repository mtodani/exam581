<%-- 学生登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
	<h2>科目情報登録</h2>

<%--"StudentCreateExecute.action"が番号重複の場合もコントロールしている --%>
	<form action = "SubjectCreateExecute.action" method="post">
		<label>科目コード</label>

					<%--value name 見返す --%>

		<input type="text"
			name="subject_cd" placeholder="科目コードを入力してください" maxlength="3" value="${subject_cd}" required />
		<div>${errors.get("subject_cd")}</div>

		<label>科目名</label>

					<%--value name 見返す --%>

		<input type="text"
			name="subject_name" placeholder="科目名を入力してください" maxlength="10" value="${subject_name}" required />
		<div>${errors.get("subject_name")}</div>


		<input type="submit" value="登録して終了">
	</form>

	<a href="StudentList.action">戻る</a>

</body>
</html>