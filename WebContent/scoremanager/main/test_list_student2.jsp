<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%-- 科目検索と学生検索に二通りの検索ボックスを作る（StudentExecuteAction or SubjectExecuteAction） --%>

	<h2>科目成績参照２</h2>

    <%-- フォームで検索条件をexecuteファイルに渡す --%>
	<form action = "TestListSubjectExecute2.action" method="post">
		<label>入学年度 </label>
		<select name="f1">
			<option value="0">--------</option>

			<%-- 入学年度の一覧から選択する --%>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${year}"<c:if test="${year==f1}">selected</c:if> >${year}</option>
			</c:forEach>
		</select>

		<label>クラス</label>
		<select name="f2">
			<option value="0">--------</option>
			<c:forEach var="num" items="${clist}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<label>科目</label>
		<select name="f3">
			<option value="0">--------</option>
				<c:forEach var="sub" items="${slist}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${sub}" <c:if test="${sub==f3}">selected</c:if>>${sub}</option>
			</c:forEach>
		</select>

		<input type="submit" value="科目参照">
	</form>

<%-- どちらかのExecuteAction.javaから受け取ったリクエストパラを受け取り表示する --%>

<%--
    <c:choose>
		<c:when test="${test_list_subs > 0}">
--%>
			<div></div>

			<table>
				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>
					<th class ="text-center">1回目</th>
					<th class ="text-center">2回目</th>
				</tr>
				<c:forEach var="stu_test" items="${test_list_subs}">
				<h5>"${stu_test.getPoints().keySet()}"</h5>
					<tr>
						<td>${stu_test.getEntYear()}</td>
						<td>${stu_test.getClassNum()}</td>
						<td>${stu_test.getStudentNo()}</td>
						<td>${stu_test.getStudentName()}</td>

						<%-- keyが1か2 --%>

							<c:choose>
								<c:when test="${stu_test.getPoints().keySet().equals(1)">
									<td class="text-center">${stu_test.getPoint(0)}</td>
									<td class="text-center">✕</td>
								</c:when>
								<c:otherwise>
									<td class="text-center">✕</td>
									<td class="text-center"><%-- ${stu_test.getPoint(1)}--%></td>
								</c:otherwise>
							</c:choose>

					</tr>
				</c:forEach>
			</table>
<%-- 	</c:when>
		<c:otherwise>
			<div>0件</div>
		</c:otherwise>
	</c:choose>
--%>


</body>
</html>