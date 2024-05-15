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

	<%-- 科目検索 --%>
		<h2>科目成績参照２</h2>

	    <%-- フォームで検索条件をexecuteファイルに渡す --%>
		<form action = "TestListSubjectExecute2.action" method="post">

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

			<input type="submit" value="科目参照">
		</form>

		<%-- 学番検索 --%>

		<form action = "TestListStudentExecute2.action" method="post">
			<label>学生番号 </label>

			<%-- 文字列（学番）を入力 --%>
			<input type="text"  name="stu_num" autocomplete="off" style="ime-mode:disabled"  required>

			<input type="submit" value="学生参照">
		</form>

	<%-- どちらかのExecuteAction.javaから受け取ったリクエストパラを受け取り表示する --%>


	    <c:choose>

	    <%-- エラーがあった場合、マップにあるエラーメッセージを表示 --%>
	        <c:when test="${errors.size() > 0 }">
	        <br>
	            ${errors.get("select")}
	            ${errors.get("nullpo")}

	        </c:when>
	        <%-- 科目検索での成績情報があった場合 --%>
			<c:when test="${test_list_subs.size() > 0 }">
			平均点数："${avg}"

				<table>
				<%-- 検索された科目 --%>
				    <div>科目 : ${subjectName }</div>
					<tr>
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
		        合計点数"${stu_name}"<br>
		        合計点数"${sum}"<br>
			    平均点数"${avg}"<br>
		        <table>
					<tr>
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
				<div>成績情報が存在しません。</div>
			</c:otherwise>
		</c:choose>

	</c:param>

</c:import>