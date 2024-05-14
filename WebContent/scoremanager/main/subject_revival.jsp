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

	<h2>科目復元管理</h2>
	<a href="SubjectList.action">科目一覧</a>

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
    	<tr>
	        <td><input type="checkbox" name="s_Items" value="${subject.subject_cd}"></td>
	        <td> ${subject.subject_cd}</td>
	        <td>${subject.getSubject_name()}</td>
        </tr>
    </c:forEach>
    </table>
    <input type="submit" value="復元">
	</form>

</body>
</html>