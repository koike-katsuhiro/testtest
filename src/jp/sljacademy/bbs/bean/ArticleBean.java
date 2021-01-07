package jp.sljacademy.bbs.bean;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ArticleBean {

	private String name;
	private String email;
	private String title;
	private String text;
	private String article_id;
	private Timestamp create_date;//timestanp型
	private String color_id;
	private String color_code;


	public ArticleBean(){
		name =  "";
		email = "";
		title = "";
		text =  "";
		color_id = "3";
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReplaceName() {
		if(name.length() == 0) {
			return "nobody";
		}else {
			return name;
		}
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReplaceTitle() {
		if(title.length() == 0) {
			return "(no title)";
		}else {
			return title;
		}
	}


	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public String getReplaceText() {
		return text.replace("\r\n", "<br>");
	}


	public String getArticle_id() {
		return article_id;
	}
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}


	public Timestamp getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}
	public String getConversionDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH時mm分");
		return sdf.format(create_date);
	}


	public String getColor_id() {
		return color_id;
	}
	public void setColor_id(String color_id) {
		this.color_id = color_id;
	}


	public String getColor_code() {
		return color_code;
	}
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
}
