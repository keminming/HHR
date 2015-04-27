package tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1CheckSum {

	private MessageDigest md;
	
	private byte[] dataBytes;
	
	public SHA1CheckSum(){
		 try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 dataBytes = new byte[1024];
	}
	
	public String hash(String file) throws IOException{
		@SuppressWarnings("resource")
		FileInputStream fis = new FileInputStream(file);
		
		int n = 0;
		while((n = fis.read(dataBytes)) != -1){
			md.update(dataBytes,0,n);
		}
		fis.close();
		
		byte[] mdbytes = md.digest();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0;i < mdbytes.length;i++){
			sb.append(Integer.toString((mdbytes[i] &0xff) +0x100, 16).substring(1));
		}
		
		return sb.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SHA1CheckSum sha1 = new SHA1CheckSum();
		try {
			String h = sha1.hash("C:\\Users\\ke\\Google Drive\\workspace\\hhr\\test\\copy\\amh-language\\1425794201823.aac");
			System.out.println(h);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
