package jp.sljacademy.bbs.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DbSource {
	private static DataSource source;

	private DbSource() {

	}

	public static synchronized DataSource getSource() throws NamingException{
		if( source == null) {
			InitialContext context = new InitialContext();
			source = (DataSource)context.lookup("java:comp/env/jdbc/datasource");

		}
		return source;
	}

}