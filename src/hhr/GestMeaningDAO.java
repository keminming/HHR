package hhr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tool.UCanAcessDBManager;

public class GestMeaningDAO {
	private Connection conn;
	
	public void useDB(String name){
		try {
			conn = new UCanAcessDBManager(name).getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	
	public void closeDB(){
		try {
			conn.close();
			System.gc();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GestMeaning findGest(String word, String index){
		GestMeaning gestMeaning = null;
		try {
			
			String sql = "SELECT gest_headword FROM hands_gestmeaning WHERE key_headword = '" + word + "' AND key_headword_id = " + index;
			
			//System.out.println(sql);
			
			Statement stmt = conn.createStatement();
		    stmt.execute(sql);
		    ResultSet rs = stmt.getResultSet();
			
		    while(rs.next()){
		         //Retrieve by column name
		    	 gestMeaning = new GestMeaning();

		    	 String gestHeadWord;
				//gestHeadWord = new String(rs.getBytes("gest_headword"),"utf8");
				gestHeadWord = rs.getString("gest_headword");

				gestMeaning.setGestrueHeadWord(gestHeadWord);
				gestMeaning.setKeyHeadsWordId(index);
				gestMeaning.setKeyHeadWord(word);
		     }

		     rs.close();
		     stmt.close();
		     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error code: " + e.getErrorCode() + " sqlstate: " + e.getSQLState() + " message: " + e.getMessage());
			e.printStackTrace();
		}
			
		return gestMeaning;
	}
		
	public void updateGest(String language,String word, String index, String gestHeadWord){
		try {
			String sql = "UPDATE hands_gestmeaning SET langcode_ind = '" + language.toUpperCase() + "', gest_headword = '" + gestHeadWord + "' WHERE key_headword = '" + word + "' AND key_headword_id = " + index;
			//System.out.println(sql);
			Statement stmt = conn.createStatement();
			stmt.execute(sql);

		    stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		GestMeaningDAO dao = new GestMeaningDAO();
		dao.useDB("C:\\Users\\ke\\Google Drive\\workspace\\hhr\\test\\copy\\amh-language\\amh_godanddeaf.mdb");
		//dao.insertGest("xx", "1", "sdsdsdsdsd");
	}
}
