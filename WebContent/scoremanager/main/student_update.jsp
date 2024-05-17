<%-- 学生更新JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<h2>科目情報変更</h2>
		<form action = "StudentUpdateExecute.action" method="post">
			<label>入学年度</label>

			<input type= "hidden" name="ent_year" value="${ent_year}">
			${ent_year}


			<label>学生番号</label>


			<input type= "hidden" name="no" value="${no}">
			${no}


			<label>氏名</label>
			<input type="text" name="name" placeholder="氏名を入力してください"
				maxlength="10" value="${name}" required />
			<div>${errors.get("name")}</div>

			<label>クラス</label>
			<select name="class_num">
				<c:forEach var="num" items="${class_num_set}">
					<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
					<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
				</c:forEach>
			</select>

			<label>在学中</label>
			<%-- is_attendがtrueの場合checkedを追記 --%>
			<input type="checkbox" name="is_attend"
			<c:if test="${is_attend}">checked</c:if>>

			<input type="submit" value="変更">

		</form>

		<a href="StudentList.action">戻る</a>

	</c:param>

</c:import>