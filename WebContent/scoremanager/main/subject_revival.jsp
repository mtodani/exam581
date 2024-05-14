<%-- 学生一覧JSP --%>
<%-- EL記法 リクエストやセッションとかに入っている変数を宣言なしで取り出すことができる --%>
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

	<h2>消去科目管理</h2>
	<a href="SubjectList.action">科目一覧</a>

	<form action="SubjectRevivalExecute.action" method="post">
    <c:forEach var="subject" items="${subjects}" >
        <input type="checkbox" name="s_Items" value="${subject.subject_cd}"> ${subject.subject_cd} ${subject.getSubject_name()}<br>
    </c:forEach>
    <input type="submit" value="復元">
	</form>

</body>
</html>