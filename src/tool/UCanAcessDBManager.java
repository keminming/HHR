package tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UCanAcessDBManager {
	   private String dbUrl;
	   private static final String JDBC_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";  
	   private static final String DB_URL_ROOT = "jdbc:ucanaccess://";
	   private static Connection connection;
	   //  Database credentials
	   static final String USER = "";
	   static final String PASS = "";
	   
	   public UCanAcessDBManager(String name){
		this.dbUrl = DB_URL_ROOT + name;
		Properties p = new Properties();
		p.put("user", USER);
		p.put("password", PASS);
		p.put("ingleconnection", true);
		// System.out.println(dbUrl);
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(this.dbUrl, p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	   
	   public Connection getConnection() throws SQLException{
		   return connection; 
	   }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
