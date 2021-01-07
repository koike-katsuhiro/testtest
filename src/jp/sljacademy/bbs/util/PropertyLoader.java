package jp.sljacademy.bbs.util;

import java.util.ResourceBundle;

public class PropertyLoader {

	public static String getProperty(String name) {//プロパティファイルから対応するURLを取得
		ResourceBundle resource = ResourceBundle.getBundle("application");
		return resource.getString(name);
	}

}
