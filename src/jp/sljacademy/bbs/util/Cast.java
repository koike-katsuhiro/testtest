package jp.sljacademy.bbs.util;

import java.util.ArrayList;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.bean.ColorMasterBean;



public class Cast {
	@SuppressWarnings("unchecked")
	public static ArrayList<String> castList(Object object){
		return (ArrayList<String>)object;

	}
	@SuppressWarnings("unchecked")
	public static ArrayList<ArticleBean> castArticle(Object object){
		return (ArrayList<ArticleBean>)object;

	}
	@SuppressWarnings("unchecked")
	public static ArrayList<ColorMasterBean> castColor(Object object){
		return (ArrayList<ColorMasterBean>)object;

	}
}
