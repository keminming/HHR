package tool;

import hhr.HHRUtils;
import hhr.Statistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaConverter{ 

	private ExecutorService executor; 
	private String ffmpeg;
	private int processes;
	
	public int getProcesses() {
		return processes;
	}

	public void setProcesses(int processes) {
		this.processes = processes;
		executor = Executors.newFixedThreadPool(processes);
	}

	private static MediaConverter instance = new MediaConverter();
	
	public static void setInstance(MediaConverter instance) {
		MediaConverter.instance = instance;
	}

	public static MediaConverter getInstance(){
		return instance;
	}
	
	private MediaConverter(){
	}
	
	public void setFfmpeg(String ffmpeg)
	{
		this.ffmpeg = ffmpeg;
	}
	
	public void convert(final String sourcePath, final String targetPath, final String currentPath) throws IOException, InterruptedException{
		Statistic.getInstance().incMedia();
		executor.execute(new Runnable()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					final ProcessBuilder builder = new ProcessBuilder(ffmpeg,"-i",sourcePath,targetPath);		
					builder.redirectErrorStream(true);
					Process process = builder.start();
					
					String s = null;
					BufferedReader stdInput = new BufferedReader(new 
						     InputStreamReader(process.getInputStream()));
					while ((s = stdInput.readLine()) != null) {
					    //System.out.println(s);
					}
					
					process.waitFor();
					Statistic.getInstance().incCompleteMedia();
					try {
						File f = new File(targetPath);
						if(f.exists()){
							HHRUtils.copyFile(targetPath, currentPath);
						}
						else
						{
							System.out.println("Fail to copy file " + targetPath);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		});
	}
}
