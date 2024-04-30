<%-- 成績一覧JSP --%>

<%-- なんとなく形だけの仮設計 --%>

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

	<h2>成績管理</h2>

	<form action="TestRegist.action" method="post">

		<label>入学年度 </label>
		<select name="f1">
			<option value="0">--------</option>
			<%--forEacでリストから一つずつ取り出しvarの変数に代入する--%>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>

		<label>クラス</label>
		<select name="f2">
			<option value="0">--------</option>
			<%--forEacでリストから一つずつ取り出しvarの変数に代入する--%>
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

<%--科目、回数の中身は仮 --%>

		<label>科目</label>
		<select name="f3">
			<option value="0">--------</option>
			<%--forEacでリストから一つずつ取り出しvarの変数に代入する--%>
			<c:forEach var="subject" items="${subject_set}">
				<%-- 現在のnumと選択されていたf3が一致していた場合selectedを追記 --%>
				<option value="${subject.getSubject_cd()}" <c:if test="${subject.getSubject_cd()==f3}">selected</c:if>>${subject.getSubject_name()}</option>
			</c:forEach>
		</select>

		<label>回数</label>
		<select name="f4">
			<option value="0">--------</option>
			<%--forEacでリストから一つずつ取り出しvarの変数に代入する--%>
			<c:forEach var="testnum" items="${numStr_set}">
				<%-- 現在のnumと選択されていたf4が一致していた場合selectedを追記 --%>
				<option value="${testnum}" <c:if test="${testnum==f4}">selected</c:if>>${testnum}</option>
			</c:forEach>
		</select>



		<button>検索</button>

		<div>${errors.get("f1")}</div>
	</form>
<%--
	<c:choose>
		<c:when test="${test.size()>0}">
			<div>検索結果：${students.size()}件</div>

			<table class="table table-hover">
				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>

					<th class="text-center">在学中</th>
					<th></th>
					<th></th>
				</tr>
				<c:forEach var="student" items="${students}">
					<tr>
						<td>${student.ent_Year}</td>
						<td>${student.class_Num}</td>
						<td>${student.student_no}</td>
						<td>${student.name}</td>

						<td><a href="StudentUpdate.action?no=${student.student_no}">変更</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<div>学生情報が存在しませんでした</div>
		</c:otherwise>
	</c:choose>
	--%>

</body>
</html>