package jp.sljacademy.bbs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.AccountBean;
import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.dao.AccountDao;
import jp.sljacademy.bbs.util.CommonFunction;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class IndexServlet
 */
//@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//sessionの処理

		String resultPage = PropertyLoader.getProperty("url.jsp.index");

		HttpSession session = request.getSession(false);

		//Sessionが存在するかどうか
		if(session != null && session.getAttribute("account") != null) {//Sessionがあるので一覧画面へ
			resultPage = PropertyLoader.getProperty("url.java.InputServlet");
			response.sendRedirect(resultPage);
			return;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");
		ArrayList<String> errorMessagesList = new ArrayList<String>();
		errorMessagesList = check(request.getParameter("user_id"), request.getParameter("user_pass"));
		AccountBean account = null;

		if(errorMessagesList.size() != 0) {//入力されたId、Passが空だったとき
			request.setAttribute("errorMessages",errorMessagesList);
			resultPage = PropertyLoader.getProperty("url.jsp.index");
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request,response);
			return;
	    }else {//入力されたId、Passが空ではなかったとき
	    	try {//DBに登録されているId、Passと一致するか確かめる
	    		AccountDao dao = new AccountDao();
	    		account = dao.getAccount(request.getParameter("user_id"),request.getParameter("user_pass"));
	    	}catch(NamingException e) {
	    		request.setAttribute("errorMessage", e.getMessage());
	    		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	    		dispatcher.forward(request, response);
	    	}catch(SQLException e) {
	    		request.setAttribute("errorMessage", e.getMessage());
	    		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	    		dispatcher.forward(request, response);
	    	}
		    if(account == null) {//ユーザーのId、Passが正しくなかったとき
		    	errorMessagesList.add("IDかパスワードが間違っています。");//エラーメッセージの表示
				request.setAttribute("errorMessages",errorMessagesList);
				resultPage = PropertyLoader.getProperty("url.jsp.index");
				RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
				dispatcher.forward(request, response);
				return;
		    }else {//ユーザーのId、Passが正しかったとき
				HttpSession session = request.getSession(false);
				ArticleBean article = new ArticleBean();//コンストラクタを一覧画面の初期値としてセット
				article.setName(account.getName());//入力された名前をセット
				article.setEmail(account.getEmail());//入力されたパスワードをセット
				session.setAttribute("account", account);//ログインのsession、各画面のログイン判定に使用
				session.setAttribute("article", article);//一覧画面のsessionを保存、各画面で値セットのために使用
				resultPage = PropertyLoader.getProperty("url.java.InputServlet");
				response.sendRedirect(resultPage);
		    }
	    }
	}

	/**
	 * ログイン画面で入力欄にIdとPassの入力があるかどうかをチェックするメソッド
	 * @param userId              ユーザーが入力したId
	 * @param userPass            ユーザーが入力したパスワード
	 * @return errorMessagesList  ログイン画面に表示させるエラーメッセージ
	 */
	private ArrayList<String> check(String userId, String userPass) {

		ArrayList<String> errorMessagesList = new ArrayList<String>();

	    if(CommonFunction.isBlank(userId)) {
	    	errorMessagesList.add("IDが入力されていません。");
	    }
	    if(CommonFunction.isBlank(userPass)) {
	    	errorMessagesList.add("パスワードが入力されていません。");
	    }

	    return errorMessagesList;
	}

}



