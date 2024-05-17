<%-- 学生更新完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<h2>成績情報変更</h2>
			<p>削除が完了しました</p>
		<a href="TestRegist.action">戻る</a>
		<a href="TestList.action">成績参照1</a>
		<a href="TestList2.action">成績参照2</a>
	</c:param>

</c:import>

