<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>


	<c:param name="scripts">

		<%-- パスワード表示処理 --%>
		<script type="text/javascript">
            function togglePassword() {
                var passwordField = document.getElementById("password");
                if (passwordField.type === "password") {
                    passwordField.type = "text";
                } else {
                    passwordField.type = "password";
                }
            }
        </script>

	</c:param>

	<c:param name="content">


		<div class="container">

        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header text-center">
                        <h2>ログイン画面</h2>
                    </div>
                    <div class="card-body">

					    <%-- エラーメッセージ表示部分  --%>
						<c:if test="${not empty errorMessage}">
						    <ul>
						        <li>${errorMessage}</li>
						    </ul>
						</c:if>

					    <form action="LoginExecute.action" method="post">

					        <label for="id" class="form-label">ID</label>
					        <input type="text" class="form-control" id="id" name="id" autocomplete="off" style="ime-mode:disabled" placeholder="半角でご入力ください" maxlength="20" required><br>

					        <label for="password" class="form-label">パスワード</label>
					        <input type="password" class="form-control" id="password" name="password" placeholder="20文字以内の半角英数字でご入力ください"  maxlength="20" required><br>

							<div class="mb-3 form-check">
								<%-- クリックされたらtogglePassword()を呼び出し実行 --%>
						        <input type="checkbox" id="chk_d_ps" name="chk_d_ps" onclick="togglePassword()">
						        <label for="chk_d_ps">パスワードを表示</label><br>
							</div>

					        <input type="submit" class="btn btn-primary w-100" value="ログイン">
					    </form>

					</div>
                </div>
            </div>
        </div>
        </div>

	</c:param>


</c:import>
