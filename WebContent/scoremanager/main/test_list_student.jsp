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

	<h2>成績参照</h2>
	<div>科目情報</div>

	<form action="TestListSubjectExecute.action" method="post">

		<label>入学年度 </label>
		<select name="f1">
			<option value="0">--------</option>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>

		<label>クラス</label>
		<select name="f2">
			<option value="0">--------</option>
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<label>科目</label>
		<select name="f3">
			<option value="0">--------</option>
			<c:forEach var="sub" items="${sublist}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${sub.getSubject_cd()}" <c:if test="${sub==f3}">selected</c:if>>${sub.getSubject_name()}</option>
			</c:forEach>
		</select>

		<input type="submit" value="検索">
	</form>

	<form action = "TestListStudentExecute.action" method="post">

		<p>学生情報</p>
		<div>学生番号</div>
		<input type="text" placeholder="学生番号を入力してください" >

		<input type="submit" value="検索">

	</form>

<%-- どちらかのExecuteAction.javaから受け取ったリクエストパラを受け取り表示する --%>


    <c:choose>
        <c:when test="${error.size != 0}">
            <div>${errors.get("nullpo")}</div>
        </c:when>
		<c:when test="${test_list_subs != null }">

			<table>
				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>
					<th class ="text-center">1回目</th>
					<th class ="text-center">2回目</th>
				</tr>
				<c:forEach var="sub_test" items="${test_list_subs}">
					<tr>
						<td>${sub_test.getEntYear()}</td>
						<td>${sub_test.getClassNum()}</td>
						<td>${sub_test.getStudentNo()}</td>
						<td>${sub_test.getStudentName()}</td>
						<td>${sub_test.getPoint(1)}</td>
						<td>${sub_test.getPoint(2)}</td>

					</tr>
				</c:forEach>
			</table>
	    </c:when>
	    <c:when test="${test_list_student != null}" >
	        <table>
				<tr>
					<th>科目名</th>
					<th>科目コード</th>
					<th class ="text-center">回数</th>
					<th class ="text-center">点数</th>
				</tr>
				<c:forEach var="stu_test" items="${test_list_student}">
					<tr>
						<td>${stu_test.getSubjectName()}</td>
						<td>${stu_test.getSubjectCD()}</td>
						<td>${stu_test.getNum()}</td>
						<td>${stu_test.getPoint()}</td>
					</tr>
				</c:forEach>
			</table>
	    </c:when>
		<c:otherwise>
			<div>成績情報が存在しません。</div>
		</c:otherwise>
	</c:choose>

</body>
</html>