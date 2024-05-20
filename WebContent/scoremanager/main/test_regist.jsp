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

	<c:if test ="${errors.size() > 0}">
	    ${errors.get("test_errors")}
	    ${errors.get("test_errors2")}
	</c:if>

	<c:choose>
		<c:when test="${tests.size()>0}">
			<div>検索結果：${tests.size()}件</div>

			<div style="display: flex;">
			<form action="TestRegistExecute.action" method="post">
			<table class="table table-hover">
				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>
					<th>点数</th>
				</tr>
				<c:forEach var="test" items="${tests}">
					<tr>
						<td>${test.student.getEntYear()}</td>
						<td>${test.student.getClass_num()}</td>
						<td>${test.student.getStudent_no()}</td>
						<td>${test.student.getStudent_name()}</td>

						<td>
						<c:choose>
						<c:when test="${test.point=='-1'}"><input type="text" name="point_${test.student.student_no}" value=""/></c:when>
						<c:otherwise><input type="text" name="point_${test.student.student_no}" value="${test.getPoint()}"/></c:otherwise>
						</c:choose>
						<div>${errors.get("test_error")}</div>
						</td>
						<td>
						<%--<a href="TestDeleteExecute.action?no=${test.student.student_no}?sub_cd=${test.subject.subject_cd}?num=${test.num}">削除</a></td>--%>
						<%-- <td><a href="StudentUpdate.action?no=${student.student_no}">変更</a></td>--%>
					</tr>
				</c:forEach>
				</table>
				<input type="hidden" name="f1" value="${f1}"/>
				<input type="hidden" name="f2" value="${f2}"/>
				<input type="hidden" name="f3" value="${f3}"/>
				<input type="hidden" name="f4" value="${f4}"/>
				<input type="submit" value="登録して終了">
			<%--<button type="submit">登録して終了</button>--%>
        </form>
        <form action="TestDeleteExecute.action" method="post">
			<table class="table table-hover"style="border-collapse:separate; border-spacing:3px; border:1px">
				<tr>
					<th>削除</th>
				</tr>
				<c:forEach var="test" items="${tests}">
					<tr>
						<%--<td>削除</td>--%>
						<input type="hidden" name="f1" value="${test.student.student_no}"/>
						<input type="hidden" name="f2" value="${test.subject.subject_cd}"/>
						<input type="hidden" name="f3" value="${test.no}"/>
						<td><input type="submit" value="削除" style="height:20px;width:50px"></td>
					</tr>
				</c:forEach>
				</table>

        </form>
        </div>
		</c:when>
	</c:choose>

</body>
</html>