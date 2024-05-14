<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<h2 class = "p-3 mb-2 bg-light text-dark">成績参照</h2>

		<%-- 入学年度、クラス、科目を選択させる --%>
		<form action="TestListSubjectExecute.action" method="post">

			<div>科目情報</div>
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
				<c:forEach var="num" items="${classlist}">
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

			<input type="submit" value="検索" class="btn btn-secondary">

		</form>

		<%-- 学生番号を入力 --%>
		<form action = "TestListStudentExecute.action" method="post">

			<p>学生情報</p>
			<div>学生番号</div>
			<input type="text" name="stu_num" placeholder="学生番号を入力してください" autocomplete="off" style="ime-mode:disabled" required>

			<input type="submit" value="検索" class="btn btn-secondary">

		</form>

		<p><font color="aqua">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</font></p>

	</c:param>

</c:import>