<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="jp.sljacademy.bbs.bean.ArticleBean"
    import="jp.sljacademy.bbs.bean.ColorMasterBean"%>
<%
    ArticleBean article = (ArticleBean) session.getAttribute("article");
    String colorCode = (String)request.getAttribute("colorCode");
%>

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
        <p>以下の内容で投稿します。</p>
        <form action="./ComfirmServlet" method="post">
            <div>

                <table class="inputArticle">
                    <tr>
                        <td class="itemName"><div>名前</div></td>
                        <td style="color:#<%=colorCode%>"><div><%=article.getReplaceName()%></div></td>
                    </tr>
                    <tr>
                        <td class="itemName"><div>E-mail</div></td>
                        <td style="color:#<%=colorCode%>"><div><%=article.getEmail()%></div></td>
                    </tr>
                    <tr>
                        <td class="itemName"><div>タイトル</div></td>
                        <td style="color:#<%=colorCode%>"><div><%=article.getReplaceTitle()%></div></td>
                    </tr>
                    <tr>
                        <td class="itemName"><div>本文</div></td>
                        <td style="color:#<%=colorCode%>"><div><%=article.getReplaceText()%></div></td>
                    </tr>
                </table>

            </div>
            <div>
                <input class="button" type="submit" name="Back" value="戻る">
                <input class="button" type="submit" name="Submit" value="投稿">
            </div>
        </form>
    </div>
    </main>
</body>
</html>