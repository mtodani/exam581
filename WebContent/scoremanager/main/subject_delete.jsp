<%-- 学生更新JSP --%>
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
	<h2>学生情報削除</h2>
	<form action = "SubjectDeleteExecute.action" method="post">



		<input type= "hidden" name="subject_name" value="${subject_name}">
		<input type= "hidden" name="subject_cd" value="${subject_cd}">
		「${subject.getSubject_name()}(${subject.getSubject_cd()})を削除しますか？」






		<br><input type="submit" value="削除">

	</form>

	<a href="SubjectList.action">戻る</a>

</body>
</html>