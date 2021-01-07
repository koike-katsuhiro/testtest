package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.util.DbSource;

public class ArticleDao {

	private static final String SELECT = "select "
										+ 		"art.article_id, "
										+ 		"art.title, "
										+ 		"art.text, "
										+ 		"art.create_date, "
										+ 		"art.name, "
										+ 		"art.email, "
										+ 		"c_ms.color_code "
										+ "from "
										+ 		"article art, "
										+ 		"color_master c_ms "
										+ "where "
										+ 		"art.color_id = c_ms.color_id "
										+ "order by "
										+ 		"art.create_date desc; ";

	private static final String SQL = "insert into "
												+ "article "
												+ 		"(create_date, "
												+ 		"name, "
												+ 		"email, "
												+ 		"title, "
												+ 		"text, "
												+ 		"color_id,"
												+ 		"del_flg) "
												+ "values "
												+ 		"(now(), "
												+ 		"?, "
												+ 		"?, "
												+ 		"?, "
												+ 		"?, "
												+ 		"?,"
												+ 		"0); ";
	/**
	 * Articleテーブルの操作し、過去記事情報を取得するメソッド
	 * @return pastArticleList  過去記事の情報が入ったArrayList
	 * @throws SQLException     SQL文実行時投げられる可能性アリ
	 * @throws NamingException  データベースを参照するときに投げられる可能性アリ
	 */
	public ArrayList<ArticleBean> getArticleList() throws SQLException, NamingException {

		Connection connection = null;
		ArrayList<ArticleBean> pastArticleList = new ArrayList<ArticleBean>();//
		//sourceメソッドで取得したDBのURLでDBに接続
		try {
			DataSource source = DbSource.getSource();
			connection = source.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				ArticleBean article = new ArticleBean();
				article.setArticle_id(result.getString("article_id"));
				article.setText(result.getString("text"));
				article.setCreate_date(result.getTimestamp("create_date"));
				article.setEmail(result.getString("email"));
				article.setColor_code(result.getString("color_code"));
				article.setTitle(result.getString("title"));
				article.setName(result.getString("name"));
				pastArticleList.add(article);
			}
			statement.close();
		}catch(SQLException e) {
			throw e;
		}catch(NamingException e) {
			throw e;
		}finally{
			if(connection != null) {//接続があったらメモリのために接続を切る
				connection.close();
			}
		}
		return pastArticleList;
	}

	/**
	 * ユーザーが一覧画面で入力した投稿をDBに保存するメソッド
	 * @param name	                         ユーザーが入力した名前
	 * @param email            ユーザーが入力したE-mail
	 * @param title            ユーザーが入力したタイトル
	 * @param text             ユーザーが入力した本文
	 * @param colorId          ユーザーが選択したラジオボタンのcolorID
	 * @throws SQLException    SQL文実行時投げられる可能性アリ
	 * @throws NamingException データベースを参照するときに投げられる可能性アリ
	 */
	public void contributor(ArticleBean article)throws SQLException, NamingException {
		Connection connection = null;
		try {
			DataSource source = DbSource.getSource();
			connection = source.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL);
			statement.setString(1, article.getName());
			statement.setString(2, article.getEmail());
			statement.setString(3, article.getTitle());
			statement.setString(4, article.getText());
			statement.setString(5, article.getColor_id());
			statement.executeUpdate();
			statement.close();
		}catch(SQLException e) {
			throw e;
		}catch(NamingException e) {
			throw e;
		}finally{
			if(connection != null) {
				connection.close();
			}
		}
	}

}
