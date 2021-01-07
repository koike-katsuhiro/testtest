package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.ColorMasterBean;
import jp.sljacademy.bbs.util.DbSource;

public class ColorMasterDao {

	private static final String SELECT = "select "
										+ 		"color_code, "
										+ 		"color_name, "
										+ 		"color_id "
										+ "from "
										+ 		"color_master; ";

	private static final String SQL = "select "
									+ 		"color_code "
									+ "from "
									+ 		"color_master "
									+ "where "
									+ 		"color_id = ?; ";


	/**
	 * 一覧画面でラジオボタンを表示するためにDBに接続し、color_code、color_id、color_nameを取得するメソッド
	 * @return colorList         ColorMasterBean型のArrayList
	 *                           ラジオボタンの表示に使用
	 *
	 * @throws SQLException      SQL文実行時投げられる可能性がアリ
	 * @throws NamingException   データベースを参照するときに投げられる可能性アリ
	 */
	public ArrayList<ColorMasterBean> getColorList() throws SQLException, NamingException {


		ArrayList<ColorMasterBean> colorList = new ArrayList<ColorMasterBean>();
		Connection connection = null;
		try {
			DataSource source = DbSource.getSource();
			connection = source.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT);
			ResultSet result = statement.executeQuery();//SQL実行
			while(result.next()) {
				ColorMasterBean colorParts = new ColorMasterBean();
				colorParts.setColorCode(result.getString("color_code"));
				colorParts.setColorId(result.getString("color_id"));
				colorParts.setColorName(result.getString("color_name"));
				colorList.add(colorParts);
			}
			statement.close();//SQL終了
		}catch(SQLException e) {
			throw e;
		}catch(NamingException e) {
			throw e;
		}finally{
			if(connection != null) {//DBの接続を切る
				connection.close();
			}
		}
		return colorList;
	}

	/**
	 * ユーザーが選択したラジオボタンのcolorIdをもとにDBに接続してcolorCodeを取得するメソッド
	 * @param colorId ユーザーが選択したラジオボタンのcolorId
	 * @return colorCodeByColorId ユーザーが選択したラジオボタンに対応したcolorCodeを取得
	 *                            確認画面に表示するデータの色付けに使用
	 *
	 * @throws SQLException       SQL文実行時投げられる可能性がアリ
	 * @throws NamingException    データベースを参照するときに投げられる可能性アリ
	 */
	public String getColorCode(String colorId) throws SQLException, NamingException {

		String colorCodeByColorId = "";
		Connection connection = null;
		try {
			DataSource source = DbSource.getSource();
			connection = source.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL);
			statement.setString(1, colorId);
			ResultSet result = statement.executeQuery();//SQL実行
			while(result.next()) {
				colorCodeByColorId = result.getString("color_code");
			}
			statement.close();//SQL終了
		}catch(SQLException e) {
			throw e;
		}catch(NamingException e) {
			throw e;
		}finally{
			if(connection != null) {//DBの接続を切る
				connection.close();
			}
		}
		return colorCodeByColorId;
	}




}
