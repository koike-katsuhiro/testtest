package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.AccountBean;
import jp.sljacademy.bbs.util.DbSource;

public class AccountDao {


	private static final String SELECT = "select "
										+ 		"user_name, "
										+ 		"email "
										+ "from "
										+ 		"account "
										+ "where "
										+ 		"user_id = ? "
										+ "and "
										+ 		"user_pass = ?";


	public AccountBean getAccount(String userId,String userPass) throws SQLException, NamingException {

		AccountBean account = null;////sourceメソッドで取得したDBのURLでDBに接続
		Connection connection = null;
		try {
			DataSource source = DbSource.getSource();
			connection = source.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT);
			statement.setString(1, userId);//sql文に検索したい記述をセット
			statement.setString(2, userPass);//sql文に検索したい記述をセット
			ResultSet result = statement.executeQuery();//表を取得
			while(result.next()) {
				account = new AccountBean();//データベースに値がヒットしたときのみインスタンス化する
				account.setName(result.getString("user_name"));//user_idも取ってくるプライマリキーだから
				account.setEmail(result.getString("email"));
			}
			statement.close();
		}catch(SQLException e) {
			throw e;
		}catch(NamingException e) {
			throw e;
		}
		finally{
			if(connection != null) {//接続があったらメモリのために接続を切る
				connection.close();
			}
		}
		return account;
	}


}
