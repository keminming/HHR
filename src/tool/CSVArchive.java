package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVArchive {

	private DBManager dbm;
	
	private String path;
	
	private Map<String,List<String>> index;
	
	private List<List<String>> entries;
	
	private PrintWriter pw;
	
	public CSVArchive(String path){
		this.path = path;
	}
	
	public void createCacheTemplate(){
		try {
			openCSV();
			dbm = new DBManager("test\\bin\\godanddeaf.mdb");
			Connection conn = dbm.getConnection();
			String sql = "SELECT key_headword, key_headword_id FROM hands_gestmeaning";
			
			System.out.println(sql);
			
			Statement stmt = conn.createStatement();
		    stmt.execute(sql);
		    ResultSet rs = stmt.getResultSet();
		    
		    String keyHeadWord;
		    String keyHeadWordID;
			
		    while(rs.next()){
		         //Retrieve by column name
		    	 keyHeadWord = rs.getString("key_headword");
		    	 keyHeadWordID =rs.getString("key_headword_id");
		    	 appendToCSV(keyHeadWord + "," + keyHeadWordID + ",NA,NA");
		     }
		     closeCSV();
		     rs.close();
		     stmt.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openCSV(){
		try {
			pw = new PrintWriter(new FileOutputStream(new File(path), true /* append = true */));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void appendToCSV(String line){
		pw.append(line + "\r\n");
	}
	
	public void closeCSV(){
		pw.close();
		System.gc();
	}
	
	public void loadCSV(){
		index = new HashMap<String,List<String>>();
		entries = new ArrayList<List<String>>();		
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line = "";
		try {
			while((line = bf.readLine()) != null){
				String[] tokens = line.split(",");
				List<String> entry = new ArrayList<String>();
				
				for(String token : tokens){
					entry.add(token);
				}
				
				entries.add(entry);
				index.put(tokens[0] + tokens[1], entry);
			}
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dumpCSV() throws IOException{
		@SuppressWarnings("resource")
		FileOutputStream fos = new FileOutputStream(path);
		Writer out = new OutputStreamWriter(fos, "UTF8");

		StringBuilder sb = new StringBuilder();
		
		for(List<String> entry : entries){
			for(String token : entry){
				sb.append(token);
				sb.append(",");
			}
			
			sb.append("\n");
		}
		
		out.write(sb.toString());
		out.close();
		System.gc();
	}
	
	public String getHash(String key){
		return index.get(key).get(2);
	}

	public void setHash(String key, String hash){
		index.get(key).set(2, hash);
	}
	
	public String getTimestamp(String key){
		return index.get(key).get(3);
	}
	
	public void setTimestamp(String key, String timestamp) {
		// TODO Auto-generated method stub
		index.get(key).set(3,timestamp);
	}
	
	public List<List<String>> getEntries() {
		return entries;
	}

	public void setEntries(List<List<String>> entries) {
		this.entries = entries;
	}
	
	public static void main(String[] args){
		CSVArchive csv = new CSVArchive("C:\\Users\\ke\\Google Drive\\workspace\\hhr\\test\\bin\\cache.csv");
		csv.createCacheTemplate();
	}
}
