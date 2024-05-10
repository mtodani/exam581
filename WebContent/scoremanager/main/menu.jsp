<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<%-- <header>
	 	<h1>得点管理システム</h1>
		<p>${user.school.school_name}　${user.teacher_name}　様</p>
		<a href="../Logout.action">ログアウト</a>
	</header> --%>


	<c:param name="content">
		<h2>メニュー</h2>

		<a href="StudentList.action">学生管理</a>
		<div>成績管理</div>
		<a href="TestRegist.action">成績登録</a>
		<a href="TestList.action">成績参照1</a>
		<a href="TestList2.action">成績参照2</a>
		<a href="SubjectList.action">科目管理</a>

	</c:param>


</c:import>

