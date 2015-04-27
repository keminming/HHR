package hhr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HHRUtils {
	public static String makeTimeStamp(){
		String timeStamp = new SimpleDateFormat("_MM-dd-yyyy-HH-mm-ss").format(new Date());
		return timeStamp;
	}
	
	public static Date parseTimeStamp(String dateStr) throws ParseException{
		DateFormat format = new SimpleDateFormat("_MM-dd-yyyy-HH-mm-ss");
		Date date = format.parse(dateStr);
		return date;
	}
	
	public static String makeChangeSummary(String source, List<String> audioList,List<List<String>> textList,List<String> videoList){
		StringBuilder sb = new StringBuilder();
		
		sb.append("Summary of Modification on Folder: " + source + "\r\n");
		sb.append("\r\nText changed [" + textList.size() + "]\r\n");
		sb.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++++\r\n");
		for(List<String> s : textList)
		{
			sb.append(s.get(0) + ": {" + s.get(1) + "} => {" + s.get(2) + "}\r\n");
		}

		sb.append("\r\nAudio changed [" + audioList.size() + "]\r\n");
		sb.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++++\r\n");
		for(String s : audioList)
		{
			sb.append(s + "\r\n");
		}

		sb.append("\r\nVideo changed [" + videoList.size() + "]\r\n");
		sb.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++++\r\n");
		for(String s : videoList)
		{
			sb.append(s + "\r\n");
		}
		
		sb.append("\r\n");
		
		return sb.toString();
	}
	
	public static String getLanguageFromPath(String path){
		File f = new File(path);
		return f.getName().substring(0,f.getName().indexOf("-"));
	}
	
	public static String getKeyHead(String wordInfo){
		String[] infos = wordInfo.split("_");//a0003_about1_gr	
		String keyHead = infos[1].substring(0,infos[1].length()-1);
		return keyHead;
	}
	
	public static String getKeyIndex(String wordInfo){
		String[] infos = wordInfo.split("_");//a0003_about1_gr
		String keyHeadIndex = infos[1].substring(infos[1].length()-1);
		return keyHeadIndex;
	}
	
	public static String getAudioFormat(String audioName){
		return audioName.substring(audioName.indexOf(".") + 1);
	}
	
	public static void copyFile(String src, String dest) throws IOException{
		InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest); 

        byte[] buffer = new byte[1024];

        int length;
        //copy the file content in bytes 
        while ((length = in.read(buffer)) > 0){
    	   out.write(buffer, 0, length);
        }

        in.close();
        out.close();
        System.gc();
        //System.out.println("File copied from " + src + " to " + dest);
	}
}
