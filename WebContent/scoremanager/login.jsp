<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

	    <h2>ログイン画面</h2>

	    <!-- エラーメッセージ表示部分  -->
		<c:if test="${not empty errorMessage}">
		    <ul>
		        <li>${errorMessage}</li>
		    </ul>
		</c:if>

	    <form action="LoginExecute.action" method="post">
	        <label for="id">ID</label>
	        <input type="text" id="id" name="id" autocomplete="off" style="ime-mode:disabled" value="teacher2" required><br>

	        <label for="password">パスワード</label>
	        <input type="password" id="password" name="password" value="password2" required><br>

	        <input type="checkbox" id="showPassword" name="showPassword">
	        <label for="showPassword">パスワードを表示（まだできません）</label><br>

	        <input type="submit" value="ログイン">
	    </form>

	</c:param>


</c:import>
