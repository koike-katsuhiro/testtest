<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="jp.sljacademy.bbs.bean.ArticleBean"
    import="jp.sljacademy.bbs.bean.ColorMasterBean"
    import="jp.sljacademy.bbs.util.Cast"
    import="java.text.SimpleDateFormat" import="java.util.ArrayList"
    import="java.util.List" import="java.util.Iterator"%>
<%
    ArticleBean article = (ArticleBean)session.getAttribute("article");
	ArrayList<ArticleBean> pastArticle = Cast.castArticle(request.getAttribute("pastArticle"));
	ArrayList<ColorMasterBean> color = Cast.castColor(request.getAttribute("color"));
    ArrayList<String> errorMessages = Cast.castList(request.getAttribute("errorMessages"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/master.css" type="text/css">
<title>Insert title here</title>
</head>
<body>
    <header> 掲示板 </header>
    <main>
    <div>
    <%
      if(errorMessages != null){
    %>
        <ul>
        <%
        for(String errorList : errorMessages){
        %>
          <li><%=errorList%></li>
          <%
        }
        %>
        </ul>
        <%} %>
        <form action="./InputServlet" method="post">
            <div>
                <table class="inputArticle">
                    <tr>
                        <td class="itemName"><div>名前</div></td>
                        <td><div>
                                <input type="text" name="name" value="<%=article.getName()%>">
                            </div></td>
                    </tr>
                    <tr>
                        <td class="itemName"><div>E-mail</div></td>
                        <td><div>
                                <input type="text" name="email" value="<%=article.getEmail()%>">
                            </div></td>
                    </tr>
                    <tr>
                        <td class="itemName"><div>タイトル</div></td>
                        <td><div>
                                <input type="text" name="title" value="<%=article.getTitle()%>">
                            </div></td>
                    </tr>
                    <tr>
                        <td class="itemName"><div>本文</div></td>
                        <td><div>
                                <textarea name="text" cols="35" rows="5" wrap="hard"><%=article.getText()%></textarea>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="itemName"><div>文字色</div></td>

                        <td><div>
                        <%
                        for(ColorMasterBean colorList : color) {
                            String check;
                            if(article.getColor_id().equals(colorList.getColorId())){
                                check = "checked";
                            }else{
                                check = "";
                            }
                        %>
                                <input class="radio" type="radio" name="color"
                                    value="<%=colorList.getColorId()%>"
                                    id="<%=colorList.getColorId()%>" <%=check%>>
                                <label for="<%=colorList.getColorId()%>"
                                    style="color:#<%=colorList.getColorCode()%>"><%=colorList.getColorName()%></label>
                        <%
                        }
                        %>
                        </div></td>
                    </tr>
                </table>
            </div>
            <div>
                <input class="button" type="submit" name="clear" value="クリア">
                <input class="button" type="submit" name="Submit" value="確認">
            </div>
        </form>
        <hr>

        <div>
        <%
        for(ArticleBean pastArticleList : pastArticle) {
        %>

            <table class="postedArticle"
                style="color:#<%=pastArticleList.getColor_code()%>">

                <tr>
                    <td class="articleId"><div><%=pastArticleList.getArticle_id()%></div></td>
                    <td class="articleTitle"><div>
                            <%=pastArticleList.getReplaceTitle()%>
                        </div></td>
                </tr>
                <tr>
                    <td class="articleText" colspan="2"><div>
                            <%=pastArticleList.getReplaceText()%>
                        </div></td>
                </tr>
                <tr>
                    <td class="articleDate" colspan="2"><div>
                            <%=pastArticleList.getConversionDate()%>

                            <%
                        if(article.getEmail().length() != 0){
                        %>
                            <a href="mailto:<%=pastArticleList.getEmail()%>"><%=article.getReplaceName()%></a>
                        <%
                        }else{
                        %>
                            <%=pastArticleList.getReplaceName()%>
                        <%
                        }
                        %>
                        </div></td>
                </tr>
            </table>

        <%
          }
        %>
        </div>
    </div>
    </main>
</body>
</html>

