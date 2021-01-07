package jp.sljacademy.bbs;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.dao.ArticleDao;
import jp.sljacademy.bbs.dao.ColorMasterDao;
import jp.sljacademy.bbs.util.CommonFunction;
import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class ComfirmServlet
 */
//@WebServlet("/ComfirmServlet")
public class ComfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");
		
		HttpSession session = request.getSession(false);  //URL直書きの場合、session = null

		if(session == null ||session.getAttribute("account") == null) {
			resultPage = PropertyLoader.getProperty("url.java.IndexServlet");
			response.sendRedirect(resultPage);
			return;
		}
		//ログイン画面で確認ボタンを押した場合、"inputSubmit"の文字列をsessionに保持
		if(session.getAttribute("list") != "inputSubmit") {
			session.setAttribute("list", "comfirmOut");
			resultPage = PropertyLoader.getProperty("url.java.InputServlet");
			response.sendRedirect(resultPage);
			return;
		}
		ArticleBean article = (ArticleBean)session.getAttribute("article");//ユーザーが選択したラジオボタンのIDを取得のため
		try {
			ColorMasterDao colorDao = new ColorMasterDao();
			String colorCode = colorDao.getColorCode(article.getColor_id());
			request.setAttribute("colorCode", colorCode);
		} catch (SQLException e) {
			session.setAttribute("list", "comfirmException");
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		} catch (NamingException e) {
			session.setAttribute("list", "comfirmException");
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
			dispatcher.forward(request, response);
			return;
		}
		session.setAttribute("list", "comfirmIn");
		resultPage = PropertyLoader.getProperty("url.jsp.confirm");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
																	throws ServletException, IOException {

		String resultPage = PropertyLoader.getProperty("url.jsp.error");
		HttpSession session = request.getSession(false);
		ArticleBean article = (ArticleBean) session.getAttribute("article");

		if(request.getParameter("Back") != null) {//戻るボタン押下時
			session.setAttribute("list", "comfirmBack");
			resultPage = PropertyLoader.getProperty("url.java.InputServlet");
			response.sendRedirect(resultPage);
		}else if(request.getParameter("Submit") != null) {//投稿ボタン押下時
			if(!check(article)) {//入力項目エラーあり
				session.setAttribute("list", "comfirmError");
				resultPage = PropertyLoader.getProperty("url.java.InputServlet");
				response.sendRedirect(resultPage);
				return;
			}else {//入力項目エラーなし
				try {
					ArticleDao dao = new ArticleDao();
					dao.contributor(article);//DBへ投稿内容を保存するメソッドを呼ぶ
				} catch (SQLException e) {
					session.setAttribute("list", "comfirmException");
					request.setAttribute("errorMessage", e.getMessage());
					RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
					dispatcher.forward(request, response);
					return;
				} catch (NamingException e) {
					session.setAttribute("list", "comfirmException");
					request.setAttribute("errorMessage", e.getMessage());
					RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
					dispatcher.forward(request, response);
					return;
				}
				//記事投稿が終了したので、再度一覧画面に表示する値をセットしなおす
				ArticleBean initialValue = new ArticleBean();//コンストラクタを呼び出す
				initialValue.setName(article.getName());//投稿したユーザーの名前をセット
				initialValue.setEmail(article.getEmail());//投稿したユーザーのE-mailをセット
				session.setAttribute("article", initialValue);
				session.setAttribute("list", "comfirmSubmit");
				resultPage = PropertyLoader.getProperty("url.java.CompleteServlet");
				response.sendRedirect(resultPage);
			}
		}
	}

	/**
	 * ユーザーが一覧画面で入力したタイトル、テキスト、E-mailをチェックするメソッド
	 * @param article 一覧画面でユーザーが入力した情報が入ったsessionの値
	 * @return
	 *         true:入力された値にエラーがなかった時
	 *         false:入力された値の中でどれか一つでもエラーあった場合
	 * 判定のみで、エラーメッセージは必要なし
	 */
	private boolean check(ArticleBean article) {


		if(!CommonFunction.checkLen(article.getTitle(),50) &&
		    CommonFunction.isBlank(article.getText()) &&
		   !CommonFunction.checkLen(article.getText(),100) &&
		   !CommonFunction.checkEmail(article.getEmail())) {
			return true;
		}else {
			return false;
		}

	}

}
