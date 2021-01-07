<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList"
    import="jp.sljacademy.bbs.util.Cast"
    import="jp.sljacademy.bbs.bean.AccountBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/master.css" type="text/css">
<title>掲示板</title>
</head>
<body>
    <header> 掲示板 </header>
    <main>
    <div>
        <p>あなたのIDとパスワードを入力してログインしてください。</p>
        <%
          ArrayList<String> errorMessages = Cast.castList(request.getAttribute("errorMessages"));
          if(errorMessages != null){
        %>
        <ul>
            <%
            for(String errorList: errorMessages){
            %>
              <li><%=errorList%></li>
            <%

            }
            %>
        </ul>
        <%} %>



        <form method="post" action="/Bbs/IndexServlet">
            <p>
                <label class="itemName">ID:</label> <input type="text"
                    name="user_id" value="">
            </p>
            <p>
                <label class="itemName">パスワード:</label> <input type="password"
                    name="user_pass" value="">
            </p>
            <div>
                <input class="button" type="submit" name="Login" value="ログイン">
            </div>
        </form>
    </div>
    </main>
</body>
</html>