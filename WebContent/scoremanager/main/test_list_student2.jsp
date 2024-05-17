<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		成績参照２
	</c:param>

	<c:param name="scripts"></c:param>


	<c:param name="content">

	<%-- 科目検索と学生検索に二通りの検索ボックスを作る（StudentExecuteAction or SubjectExecuteAction） --%>

	<%-- 背景に薄いグレーをいれる --%>
		<%-- daiによって、科目か学生かの表示を変える --%>
		<h2 class="p-3 mb-2 bg-light text-dark">成績一覧</h2>

		<%-- divで囲むことによって、検索欄の枠を作る --%>
		<div class="border border-3 rounded">

		<%-- マージンなどを使用して余白を入れる（位置調整） --%>
			<div class="p-3 ms-5">科目情報</div>


			    <%-- フォームで検索条件をexecuteファイルに渡す --%>
				<form action = "TestListSubjectExecute2.action" method="post" class="ms-5">

				    <%-- 入学年度のリストを受け取り一覧をセレクトボックスで表示 --%>
					<label>入学年度 </label>
					<select name="f1">
						<option value="0">--------</option>

						<%-- 入学年度の一覧から選択する --%>
						<c:forEach var="year" items="${ent_year_set}">
							<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
							<option value="${year}"<c:if test="${year==f1}">selected</c:if> >${year}</option>
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
							<option value="${sub.getSubject_cd()}" <c:if test="${sub.subject_cd==f3}">selected</c:if>>${sub.getSubject_name()}</option>
						</c:forEach>
					</select>

					<input type="submit" value="検索" class="btn btn-secondary ms-5">
				</form>

				<%-- bootstrapで線を入れる --%>
			    <hr width=”30%” align=”center”>

				<%-- 学番検索 --%>

				<form action = "TestListStudentExecute2.action" method="post">
					<div class="p-3 ms-5">学生情報</div>
					<div class="col-4 ms-5 mb-2">学生番号</div>

					<%-- 文字列（学番）を入力 --%>
					<input type="text" name="stu_num" placeholder="学生番号を入力してください" maxlength="10" autocomplete="off" style="ime-mode:disabled" value="${stu_num }" class="col-4 ms-5 mb-3" required>

					<input type="submit" value="検索" class="btn btn-secondary ms-5">
				</form>
			</div>

	<%-- どちらかのExecuteAction.javaから受け取ったリクエストパラを受け取り表示する --%>


	    <c:choose>

	    <%-- エラーがあった場合、マップにあるエラーメッセージを表示 --%>
	        <c:when test="${errors.size() > 0 }">
	        <br>
	            <div><font color="orange">${errors.get("select")}</font></div>
	            <div>${errors.get("nullpo")}</div>

	        </c:when>
	        <%-- 科目検索での成績情報があった場合 --%>
			<c:when test="${test_list_subs.size() > 0 }">
			<div>平均点数："${avg}"</div>

				<%-- テーブルを作る、カーソルを持って行ったところに色が付く --%>
				<table class="table table-hover">

				<%-- 見やすさのための改行 --%>
		        	<br>

				<%-- 検索された科目 --%>
				    <%-- 科目名の表示、<u>で下線を引いてわかりやすく（オリジナル）--%>
					<div class="h5 ms-3"><u>科目 : ${subjectName }</u></div>

					<%-- テーブルの一行目のみ、背景に色 --%>
					<tr class="bg-light">
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th class ="text-center">1回目</th>
						<th class ="text-center">2回目</th>
					</tr>
					<c:forEach var="sub_test" items="${test_list_subs}">
					<tr>
							<td>${sub_test.getEntYear()}</td>
							<td>${sub_test.getClassNum()}</td>
							<td>${sub_test.getStudentNo()}</td>
							<td>${sub_test.getStudentName()}</td>
							<td>${sub_test.getPoint(1)}</td>
							<%-- TestListSubjectDaoで一、二回目の点数を一列で表示できる --%>
				            <td>${sub_test.getPoint(2)}</td>
					</tr>
					</c:forEach>
				</table>
		    </c:when>

		    <%-- 学番検索での成績情報があった場合 --%>
		    <c:when test="${test_list_student.size() > 0}" >
			    <div>平均点数"${avg}"</div><br>

		        <%-- テーブルを作る、カーソルを持って行ったところに色が付く --%>
		        <table class="table table-hover">

		            <%-- 見やすさのための改行 --%>
		        	<br>

		        	<%-- 学生名と学生番号の表示、<u>で下線を引いてわかりやすく（オリジナル） --%>
		        	<div class="h5 ms-3"><u>氏名 : ${stu_name } (${stu_num })</u></div>

					<%-- テーブルの一行目のみ、背景に色 --%>
					<tr class="bg-light">
						<th>科目名</th>
						<th>科目コード</th>
						<th class ="text-center">回数</th>
						<th class ="text-center">点数</th>
					</tr>
					<c:forEach var="stu_test" items="${test_list_student}">
						<tr>
							<td>${stu_test.getSubjectName()}</td>
							<td>${stu_test.getSubjectCD()}</td>
							<td>${stu_test.getNum()}</td>
							<td>${stu_test.getPoint()}</td>
						</tr>
					</c:forEach>
				</table>
		    </c:when>
			<c:otherwise>
		        <%-- 見やすさのための改行 --%>
		   		<br>

				<div class="h5 ms-3"><u>氏名 : ${stu_name } (${stu_num })</u></div>
				<div>成績情報が存在しません。</div>

			</c:otherwise>
		</c:choose>

	</c:param>

</c:import>