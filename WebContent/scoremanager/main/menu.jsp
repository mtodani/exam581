<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>

	<header>
	 	<h1>得点管理システム</h1>
		<p>${user.school.school_name}　${user.teacher_name}　様</p>
		<a href="#">ログアウト</a>
	</header>

<h2>メニュー</h2>

<a href="StudentList.action">学生管理</a>
<a href="#">成績登録</a>
<a href="SubjectList.action">科目管理</a>
<a href="TestList.action">成績参照1</a>
<a href="TestList2.action">成績参照2</a>


<footer>
        &copy; 2024 TIC 大原学園
</footer>

</body>
</html>
