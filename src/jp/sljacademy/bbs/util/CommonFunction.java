package jp.sljacademy.bbs.util;

public class CommonFunction {

	public static boolean checkEmail(String param1) {//メールフォーマットチェックメソッド
		String str1 = "^[A-Za-z0-9]+@test\\.co\\.jp$";

		if(param1 == null ||param1.length() == 0 || param1.matches(str1)) {
			return true;
		}else {
			return false;
		}
	}


	public static boolean checkLen(String param1,int param2) {//文字数チェックメソッド

		if(param1 == null || param1.length() <= param2) {

			return true;

		}else {
			return false;
		}
	}


	public static boolean isBlank(String param1) {//空文字チェックメソッド
		if(param1 == null || param1.length() == 0) {
			return true;
		}else {
			return false;
		}

	}

}
