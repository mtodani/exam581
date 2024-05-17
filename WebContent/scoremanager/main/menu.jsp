<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

<%-- bootstrap(css)の適用は、森谷が担当 --%>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<%-- 背景に薄いグレーをいれる --%>
		<h2 class="p-3 mb-2 bg-light text-dark">メニュー</h2>

		<%-- 改行 --%>
		<br>

		<%-- 大枠 --%>
		<div class="container">

			<%-- カラム分けのエリアを作成、枠の間にスペースをいれる、左側の余白を調整 --%>
			<div class="row grid gap-3 ms-5">

				<%-- 影を付ける、背景に色、文字の大きさ、分割、枠のサイズ、テキストを中央 --%>
				<div class="shadow alert alert-danger h4 col-4 w-25 text-center">

					<%-- 余白を入れて、見やすくする --%>
					<div class="mt-4"></div>

					<a href="StudentList.action">学生管理</a>
				</div>

				<%-- 影を付ける、背景に色、文字の大きさ、分割、枠のサイズ、テキストを中央 --%>
				<div class="shadow alert alert-success h4 col-4 w-25 text-center font-monospace">

					<%-- 余白を入れて、見やすくする --%>
					<div class="mt-4"></div>成績管理

					<%-- 改行を入れることによって、縦一列にすることが可能 --%>
					<br>
					<a href="TestRegist.action">成績登録</a>
					<br>
					<a href="TestList.action">成績参照1</a>
					<br>
					<a href="TestList2.action">成績参照2</a>
				</div>

				<%-- 影を付ける、背景に色、文字の大きさ、分割、枠のサイズ、テキストを中央 --%>
				<div class="shadow alert alert-warning h4 col-4 w-25 text-center">

					<%-- 余白を入れて、見やすくする --%>
					<div class="mt-4"></div>

					<a href="SubjectList.action">科目管理</a>
				</div>

			</div>
		</div>

	</c:param>


</c:import>

