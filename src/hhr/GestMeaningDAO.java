package hhr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tool.UCanAcessDBManager;

public class GestMeaningDAO {
	private Connection conn;
	private final String query = "SELECT gest_headword FROM hands_gestmeaning WHERE key_headword = ? AND key_headword_id = ?";
	private final String update = "UPDATE hands_gestmeaning SET langcode_ind = ?, gest_headword = ? WHERE key_headword = ? AND key_headword_id = ?";
	private PreparedStatement queryStatement;
	private PreparedStatement updateStatement;
	private int updates = 0;
	
	public void useDB(String name){
		try {
			conn = new UCanAcessDBManager(name).getConnection();
			conn.setAutoCommit(false);
			queryStatement = conn.prepareStatement(query);
			updateStatement = conn.prepareStatement(update);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	
	public void closeDB(){
		try {
			if(updates > 0)
				updateStatement.executeBatch();
			conn.commit();
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
	
			queryStatement.setString(1, word);
			queryStatement.setString(2, index);
		    ResultSet rs = queryStatement.executeQuery();
			
		    while(rs.next()){
				gestMeaning = new GestMeaning();
				String gestHeadWord;
				gestHeadWord = rs.getString("gest_headword");
				gestMeaning.setGestrueHeadWord(gestHeadWord);
				gestMeaning.setKeyHeadsWordId(index);
				gestMeaning.setKeyHeadWord(word);
		     }

		     rs.close();
		     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error code: " + e.getErrorCode() + " sqlstate: " + e.getSQLState() + " message: " + e.getMessage());
			e.printStackTrace();
		}
			
		return gestMeaning;
	}
		
	public void updateGest(String language,String word, String index, String gestHeadWord){
		try {
			updateStatement.setString(1, language.toUpperCase());
			updateStatement.setString(2, gestHeadWord);
			updateStatement.setString(3, word);
			updateStatement.setString(4, index);
			updateStatement.addBatch();
			updates++;
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
