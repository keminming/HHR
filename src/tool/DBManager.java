package tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
	   private String dbUrl;
	   private static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";  
	   private static final String DB_URL_ROOT = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
	   private static Connection connection;
	   //  Database credentials
	   static final String USER = "";
	   static final String PASS = "";
	   
	   public DBManager(String name) throws ClassNotFoundException, SQLException{
		   this.dbUrl = DB_URL_ROOT + name;
		   Properties p = new Properties();
           p.put("charSet", "utf8");
           p.put("user", USER);
           p.put("password", PASS);
		   //System.out.println(dbUrl);
		   Class.forName(JDBC_DRIVER);
		   connection = DriverManager.getConnection(this.dbUrl,p); 
	   }
	   
	   public Connection getConnection() throws SQLException{
		   return connection; 
	   }
	   
	   public static void main(String[] args) throws ClassNotFoundException, SQLException{
		   System.out.println("C:\\Users\\ke\\Google Drive\\workspace\\hhr\\test\\copy\\amh-language\\amh");
		   DBManager dbm = new DBManager("C:\\Users\\ke\\Google Drive\\workspace\\hhr\\test\\copy\\amh-language\\amh");
		   dbm.getConnection();
	   }
}
