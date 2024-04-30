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
						<c:when test="${stu_test.getPoints().keySet().equals(\"[1]}\")">
							<td class="text-center">${stu_test.getPoint(1)}</td>
							<td class="text-center">✕</td>
						</c:when>
						<c:otherwise>
							<td class="text-center">✕</td>
							<td class="text-center"><%-- ${stu_test.getPoint(2)}--%></td>
						</c:otherwise>
					</c:choose>

			</tr>
		</c:forEach>
	</table>

</body>
</html>