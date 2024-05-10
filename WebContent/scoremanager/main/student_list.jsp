<%-- 学生一覧JSP --%>
<%-- EL記法 リクエストやセッションとかに入っている変数を宣言なしで取り出すことができる --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<h2>学生管理</h2>
		<a href="StudentCreate.action">新規登録</a>

		<form method="get">
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

			<label>在学中
				<%-- パラメーターf3が存在している場合checkedを追記 --%>
				<input type="checkbox" name="f3" value="t"
				<c:if test="${!empty f3}">checked</c:if> />
			</label>

			<button>絞込み</button>

			<div>${errors.get("f1")}</div>
		</form>

		<c:choose>
			<c:when test="${students.size()>0}">

				<div>検索結果：${students.size()}件</div>

				<table class="table table-hover">
					<tr>
						<th>入学年度</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>クラス</th>
						<th class="text-center">在学中</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach var="student" items="${students}">
						<tr>
							<td>${student.entYear}</td>
							<td>${student.student_no}</td>
							<td>${student.student_name}</td>
							<td>${student.class_num}</td>
							<td class="text-center">
								<%-- 在学フラグがたっている場合「○」それ以外は「×」を表示 --%>
								<c:choose>
									<c:when test="${student.isAttend()}">
										○
									</c:when>
									<c:otherwise>
										×
									</c:otherwise>
								</c:choose>
							</td>
							<td><a href="StudentUpdate.action?no=${student.student_no}">変更</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>学生情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>

	</c:param>
</c:import>