<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/master.css" type="text/css">
<title>掲示板</title>
</head>
<body>
    <header> 掲示板 </header>
    <main>
    <div>
        <p>投稿が完了しました。</p>
        <form action="./CompleteServlet" method="post">
            <p>
                <input class="button" type="submit" name="Back" value="一覧画面に戻る">
            </p>
        </form>
    </div>
    </main>
</body>
</html>