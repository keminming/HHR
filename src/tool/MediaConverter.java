package tool;

import hhr.HHRUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MediaConverter {
	private String ffmpeg;
	
	public MediaConverter(String ffmpeg){
		this.ffmpeg = ffmpeg;
	}
	
	public void convert(String ffmpeg, String SourcePath, final String targetPah) throws IOException{
		//System.out.println(SourcePath);
		//System.out.println(targetPah);
		
		final Process process = new ProcessBuilder(ffmpeg,"-i",SourcePath,targetPah).start();		

		BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(process.getInputStream()));

		BufferedReader stdError = new BufferedReader(new 
		     InputStreamReader(process.getErrorStream()));
		

/*		// read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		String s = null;
		while ((s = stdInput.readLine()) != null) {
		    System.out.println(s);
		}*/

/*		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
		    System.out.println(s);
		}*/
	}
}
