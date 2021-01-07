package jp.sljacademy.bbs;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.sljacademy.bbs.util.PropertyLoader;

/**
 * Servlet implementation class CompleteServlet
 */
//@WebServlet("/CompleteServlet")
public class CompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resultPage = PropertyLoader.getProperty("url.jsp.complete");
		HttpSession session = request.getSession(false);

		//Sessionが存在するかどうか
		if(session == null || session.getAttribute("account") == null) {
			resultPage = PropertyLoader.getProperty("url.java.IndexServlet");
			response.sendRedirect(resultPage);
			return;
		}
		session.setAttribute("list","completeIn");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
																			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.setAttribute("list", "completeBack");
		String resultPage = PropertyLoader.getProperty("url.java.InputServlet");
		response.sendRedirect(resultPage);
	}

}
