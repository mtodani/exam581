<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		成績参照2
	</c:param>

	<c:param name="scripts"></c:param>


	<c:param name="content">

		<h2>成績参照２</h2>

	    <%-- フォームで検索条件をexecuteファイルに渡す --%>
		<form action = "TestListSubjectExecute2.action" method="post">

		    <%-- 入学年度のリストを受け取り一覧をセレクトボックスで表示 --%>
			<label>入学年度 </label>
			<select name="f1">
				<option value="0">--------</option>

				<%-- 入学年度の一覧から選択する --%>
				<c:forEach var="year" items="${ent_year_set}">
					<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
					<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
				</c:forEach>
			</select>

			<%-- クラスのリストを受け取り一覧をセレクトボックスで表示 --%>
			<label>クラス</label>
			<select name="f2">
				<option value="0">--------</option>
				<c:forEach var="num" items="${clist}">
					<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
					<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
				</c:forEach>
			</select>

			<%-- 科目のリストを受け取り一覧をセレクトボックスで表示 --%>
			<label>科目</label>
			<select name="f3">
				<option value="0">--------</option>
				<c:forEach var="sub" items="${slist}">
					<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
					<option value="${sub.getSubject_cd()}" <c:if test="${sub==f3}">selected</c:if>>${sub.getSubject_name()}</option>
				</c:forEach>
			</select>

			<input type="submit" value="科目参照">
		</form>

		<%-- 学番検索 --%>
		<form action = "TestListStudentExecute2.action" method="post">
			<label>学生番号 </label>
			<%-- 入力必須、自動変換off --%>
			<input type="text"  name="stu_num" autocomplete="off" style="ime-mode:disabled"  required>

			<%-- 送信ボタン --%>
			<input type="submit" value="学生参照">
		</form>


	</c:param>

</c:import>