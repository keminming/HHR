package hhr;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import tool.KMLGenerator;


public class HHRScanner implements Runnable{
	private String source;
	private String target;
	private String ffmpeg;
	private String dependency;
	private Statistic stat;
	private KMLGenerator kml;
	private ExecutorService executor;
	private static HHRScanner instance;
	public String getDependency() {
		return dependency;
	}

	public void setDependency(String dependency) {
		this.dependency = dependency;
	}	
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getFfmpeg() {
		return ffmpeg;
	}

	public void setFfmpeg(String ffmpeg) {
		this.ffmpeg = ffmpeg;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public static void setInstance(HHRScanner instance) {
		HHRScanner.instance = instance;
	}

	public static HHRScanner getInstance(){
		instance = new HHRScanner();
		return instance;
	}
	
	public void setStat(Statistic stat){
		this.stat = stat;
	}
	
	private HHRScanner(){
		stat = Statistic.getInstance();
		kml = KMLGenerator.getInstance();
		executor = Executors.newFixedThreadPool(500);
	}

	public KMLGenerator getKml() {
		return kml;
	}

	public void setKml(KMLGenerator kml) {
		this.kml = kml;
	}

	public Statistic getStat() {
		return stat;
	}

	public void start(){
		stat.reset();
		String kmlPath = source + "HHR_googlemaps_languages_metadata.kml";
		kml.load(kmlPath);
		File root = new File(source);
		File[] subDirs = root.listFiles();
		int languages = 0;
		for(File file : subDirs){
			
			if(file.isDirectory()){
				String srcLanguageFolder = file.getAbsolutePath() + "\\";
				String language = HHRUtils.getLanguageFromPath(file.getAbsolutePath());
				String dstLanguageFolder = target + language;
				
				WorkerThread worker = new WorkerThread(new HHREnvironment(language,srcLanguageFolder,dstLanguageFolder,ffmpeg));			
				
				executor.execute(worker);
				languages++;
			}
		}
		stat.setLanguages(languages);
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			kml.dump(kmlPath);
			stat.stop();
			System.out.println("Scanning Finished.");
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		start();
	}
}
