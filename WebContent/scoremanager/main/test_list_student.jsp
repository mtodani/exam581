<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<%-- 背景に薄いグレーをいれる --%>
		<%-- daiによって、科目か学生かの表示を変える --%>
		<h2 class="p-3 mb-2 bg-light text-dark">成績一覧(${dai })</h2>

		<%-- divで囲むことによって、検索欄の枠を作る --%>
		<div class="border border-3 rounded">

			<%-- マージンなどを使用して余白を入れる（位置調整） --%>
			<div class="p-3 ms-5">科目情報</div>

			<form action="TestListSubjectExecute.action" method="post" class="ms-5">

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
						<%-- 現在のsubと選択されていたf3が一致していた場合selectedを追記 --%>
						<option value="${sub.getSubject_cd()}" <c:if test="${sub.subject_cd==f3}">selected</c:if>>${sub.getSubject_name()}</option>
					</c:forEach>
				</select>

				<input type="submit" value="検索" class="btn btn-secondary ms-5">

			</form>

			<%-- bootstrapで線を入れる --%>
			<hr width=”30%” align=”center”>

			<form action = "TestListStudentExecute.action" method="post">

				<%-- マージンなどを使用して余白を入れる（位置調整） --%>
				<div class="p-3 ms-5">学生情報</div>
				<div class="col-4 ms-5 mb-2">学生番号</div>
				<input type="text" name="stu_num" placeholder="学生番号を入力してください" maxlength="10" autocomplete="off" style="ime-mode:disabled" value="${stu_num }" class="col-4 ms-5 mb-3" required>

				<input type="submit" value="検索" class="btn btn-secondary ms-5">

			</form>
		</div>

	<%-- どちらかのExecuteAction.javaから受け取ったリクエストパラを受け取り表示する --%>


	    <c:choose>

			<%-- エラーの表示 --%>
	        <c:when test="${errors.size() > 0 }">
	        <br>

	        	<%-- 入学年度、クラス、科目の選択が一つでも抜けていた際のエラー --%>
	            <div><font color="orange">${errors.get("select")}</font></div>

	            <%-- 学生番号が存在しなかった際のエラー --%>
	            ${errors.get("nullpo")}

	        </c:when>

	        <%-- 科目検索の際の成績表示 --%>
			<c:when test="${test_list_subs.size() > 0 }">

				<%-- テーブルを作る、カーソルを持って行ったところに色が付く --%>
				<table class="table table-hover">

					<%-- 見やすさのための改行 --%>
		        	<br>

					<%-- 科目名の表示、<u>で下線を引いてわかりやすく（オリジナル）--%>
					<div class="h5 ms-3"><u>科目 : ${subjectName }</u></div>

					<%-- テーブルの一行目のみ、背景に色 --%>
					<tr class="bg-light">
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th class ="text-center">1回</th>
						<th class ="text-center">2回</th>
					</tr>
					<c:forEach var="sub_test" items="${test_list_subs}">
						<tr>
							<%-- 値が入ってくる --%>
							<td>${sub_test.entYear}</td>
							<td>${sub_test.classNum}</td>
							<td>${sub_test.studentNo}</td>
							<td>${sub_test.studentName}</td>
							<td class="text-center">${sub_test.getPoint(1)}</td>
							<td class="text-center">${sub_test.getPoint(2)}</td>

						</tr>
					</c:forEach>

				</table>
		    </c:when>

		    <%-- 学生検索の際の成績表示 --%>
		    <c:when test="${test_list_student.size() > 0}" >

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
							<%-- 値が入ってくる --%>
							<td>${stu_test.subjectName}</td>
							<td>${stu_test.subjectCd}</td>
							<td class="text-center">${stu_test.num}</td>
							<td class="text-center">${stu_test.point}</td>
						</tr>
					</c:forEach>
				</table>
		    </c:when>

		    <c:otherwise>

		    	<c:choose>

		    		<%-- 学生情報はあるが、成績が入っていない場合 --%>
		    		<c:when test="${stu_num != null }">

		    			<%-- 見やすさのための改行 --%>
		    			<br>

				    	<div class="h5 ms-3"><u>氏名 : ${stu_name } (${stu_num })</u></div>
						<div>成績情報が存在しませんでした。</div>
				    </c:when>

					<%-- 科目検索の際に成績が入っていなかった場合 --%>
					<c:otherwise>
						<div>学生情報が存在しませんでした。</div>
					</c:otherwise>
		    	</c:choose>

		    </c:otherwise>

		</c:choose>

	</c:param>

</c:import>