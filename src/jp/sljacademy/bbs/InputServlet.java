package jp.sljacademy.bbs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.bean.ColorMasterBean;
import jp.sljacademy.bbs.dao.ArticleDao;
import jp.sljacademy.bbs.dao.ColorMasterDao;
import jp.sljacademy.bbs.util.CommonFunction;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class InputServlet
 */
//@WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InputServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");
		HttpSession session = request.getSession(false);

		if(session == null || session.getAttribute("account") == null) {
			resultPage = PropertyLoader.getProperty("url.java.IndexServlet");
			response.sendRedirect(resultPage);
			return;
		}
		try {
			ArticleDao dao = new ArticleDao();
			ArrayList<ArticleBean> pastArticleList = dao.getArticleList();
			request.setAttribute("pastArticle", pastArticleList);
			ColorMasterDao colorDao = new ColorMasterDao();
			ArrayList<ColorMasterBean> colorList = colorDao.getColorList();
			request.setAttribute("color", colorList);
		} catch (SQLException e) {
			session.setAttribute("list", "inputException");
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		} catch (NamingException e) {
			session.setAttribute("list", "inputException");
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		}
		session.setAttribute("list", "inputIn");
		resultPage = PropertyLoader.getProperty("url.jsp.input");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		String resultPage = PropertyLoader.getProperty("url.jsp.error");

		ArticleBean previousArticle = new ArticleBean();
		previousArticle.setName(request.getParameter("name"));
		previousArticle.setEmail(request.getParameter("email"));
		previousArticle.setTitle(request.getParameter("title"));
		previousArticle.setText(request.getParameter("text"));
		previousArticle.setColor_id(request.getParameter("color"));
		session.setAttribute("article", previousArticle);

		ArrayList<String> errorMessagesList = new ArrayList<String>();
		errorMessagesList = check(previousArticle);//タイトル、本文、メールアドレスのチェック処理

		if(request.getParameter("clear") != null) {//クリアボタン押下時
			ArticleBean initialValue = new ArticleBean();
			session.setAttribute("article", initialValue);
			session.setAttribute("list", "inputClear");
		}else if(request.getParameter("Submit") != null){//確認ボタン押下時
			if(errorMessagesList.size() != 0) {//エラーメッセージが入っていたとき
				request.setAttribute("errorMessages",errorMessagesList);
				session.setAttribute("list", "inputError");
			}else {
				session.setAttribute("list", "inputSubmit");
				resultPage = PropertyLoader.getProperty("url.java.ComfirmServlet");
				response.sendRedirect(resultPage);
				return;
			}
		}


		try {//必要な条件だけ処理を通らせたい
			ArticleDao dao = new ArticleDao();
			List<ArticleBean> pastArticle = dao.getArticleList();
			request.setAttribute("pastArticle", pastArticle);
			ColorMasterDao colorDao = new ColorMasterDao();
			List<ColorMasterBean> colorList = colorDao.getColorList();
			request.setAttribute("color", colorList);
		} catch (SQLException e) {
			session.setAttribute("list", "inputException");
			request.setAttribute("errorMessages", e.getMessage());
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		} catch (NamingException e) {
			session.setAttribute("list", "inputException");
			request.setAttribute("errorMessages", e.getMessage());
			resultPage = PropertyLoader.getProperty("url.jsp.error");
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		}
		resultPage = PropertyLoader.getProperty("url.jsp.input");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request,response);
	}

	/**
	 *一覧画面でユーザーが入力した値を引数として受け取り、入力項目制限をチェックするメソッド
	 * @param title ユーザーが入力したタイトル
	 * @param text  ユーザーが入力した本文
	 * @param email ユーザーが入力したE-mail
	 * @return 判定した結果に対応したエラーメッセージ
	 */
	private ArrayList<String> check(ArticleBean previousArticle) {//エラーの種類を判定

		ArrayList<String> errorMessagesList = new ArrayList<String>();

		if(!CommonFunction.checkLen(previousArticle.getTitle(),50)) {
			errorMessagesList.add("タイトルの文字数が50文字を超えています。");
		}
		if(CommonFunction.isBlank(previousArticle.getText())) {
			errorMessagesList.add("本文が未入力です。");
		}
		if(!CommonFunction.checkLen(previousArticle.getText(),100)) {
			errorMessagesList.add("本文の文字数が100文字を超えています。");
		}
		if(!CommonFunction.checkEmail(previousArticle.getEmail())) {
			errorMessagesList.add("E-mailが正しいフォーマット(半角英数字@test.co.jp)ではありません。");
		}
		return errorMessagesList;
	}
}


