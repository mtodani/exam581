<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>得点管理システム</title>
</head>
<body>
    <h1>得点管理システム</h1>
    <h2>ログイン</h2>


    <form action="LoginExecute.action" method="post">
        <label for="id">ID</label>
        <input type="text" id="id" name="id" value="ABC123XYZ1"><br>

        <label for="password">パスワード</label>
        <input type="password" id="password" name="password"><br>

        <input type="checkbox" id="showPassword" name="showPassword">
        <label for="showPassword">パスワードを表示（まだできません）</label><br>

        <input type="submit" value="ログイン">
    </form>

    <footer>
        &copy; 2024 TIC 大阪学園
    </footer>
</body>
</html>