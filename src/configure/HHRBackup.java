package configure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;

import hhr.HHRUtils;

public class HHRBackup {
	
	private HHRConfig config;
	
	private static HHRBackup instance = new HHRBackup();
	
	public static HHRBackup getInstance(){return instance;}
	
	public void init(HHRConfig config){this.config = config;} 

	private HHRBackup(){}
	
	private boolean hasTimeStamp(File f){
		String fileName = f.getName();
		int index = fileName.lastIndexOf("_");
		String timestamp = fileName.substring(index);
		try {
			Date fileTime = HHRUtils.parseTimeStamp(timestamp);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	private boolean isMediaType(File f){
		String fileName = f.getName();
		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		if(!extension.equals("mp3") && !extension.equals("mp4"))
			return false;
		else
			return true;
	}
	
	private boolean isLatest(File f) throws ParseException{
		String fileName = f.getName();
		int index = fileName.lastIndexOf("_");
		String timestamp = fileName.substring(index);

		Date fileTime = HHRUtils.parseTimeStamp(timestamp);
		Date lastRunTime = HHRUtils.parseTimeStamp(config.getLastRunTime());

		if(fileTime.before(lastRunTime))
		    return false;
		else
			return true;
	}
	
	private void renameBackup(File f){
		String oldName = f.getAbsolutePath();
		String newName = oldName.substring(0,oldName.lastIndexOf("."));
		File nf = new File(newName);
		f.renameTo(nf);
	}
	
	private void backUpSource() throws IOException{
		HHRUtils.copyFile(config.getSource() + "HHR_googlemaps_languages_metadata.kml", config.getSource() + "HHR_googlemaps_languages_metadata.kml.bak");

		File root = new File(config.getSource());
		File[] subDirs = root.listFiles();
		for(File dir : subDirs){	
			if(dir.isDirectory()){			
				String language = HHRUtils.getLanguageFromPath(dir.getAbsolutePath());
				String target = config.getTarget();
				String dependency = config.getDependency();
				File tVoiceFolder = new File(target + language.toUpperCase() + "_Voice1\\");
				File tPrimeFolder = new File(target + language.toUpperCase() + "_Primers\\");
				File tVideoFolder = new File(target + language.toUpperCase() + "_Signs\\");
				
				if(!tVoiceFolder.exists()){
					tVoiceFolder.mkdir();
				}
				
				if(!tPrimeFolder.exists()){
					tPrimeFolder.mkdir();
				}	
				
				if(!tVideoFolder.exists()){
					tVideoFolder.mkdir();
				}	
				
				File acache = new File(tVoiceFolder.getAbsolutePath() + "\\cache.csv");
				if(!acache.exists()){
					try {
						HHRUtils.copyFile(dependency + "\\cache.csv",tVoiceFolder.getAbsolutePath() + "\\cache.csv");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				File phistory = new File(tPrimeFolder.getAbsolutePath() + "\\history.csv");
				if(!phistory.exists()){
					try {
						phistory.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				File vcache = new File(tVideoFolder.getAbsolutePath() + "\\cache.csv");	
				if(!vcache.exists()){
					try {
						HHRUtils.copyFile(dependency + "\\cache.csv",tVideoFolder.getAbsolutePath() + "\\cache.csv");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				
				File vhistory = new File(tVideoFolder.getAbsolutePath() + "\\history.csv");
				if(!vhistory.exists()){
					try {
						vhistory.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}
		}
	}
	
	private void backUpTarget() throws IOException{
		File root = new File(config.getTarget());
		File[] subDirs = root.listFiles();
		for(File dir : subDirs){
			Boolean dbFlag = false;
			if(dir.isDirectory()){		
				File[] files = dir.listFiles();
				for(File f : files){
					String fileName = f.getName();
					String extension = fileName.substring(fileName.lastIndexOf(".")+1);
							
					if(extension.equals("csv")){
						try {
							HHRUtils.copyFile(f.getAbsolutePath(), f.getAbsolutePath() + ".bak");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(extension.equals("mdb")){
						dbFlag = true;
						try {
							HHRUtils.copyFile(f.getAbsolutePath(), f.getAbsolutePath() + ".bak");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(dir.getName().substring(4).equals("Primers") && !dbFlag){
					HHRUtils.copyFile(config.getDependency() + "godanddeaf.mdb", dir.getAbsolutePath() + "\\" +dir.getName().substring(0,3) + "_godanddeaf.mdb");
					HHRUtils.copyFile(dir.getAbsolutePath() + "\\" +dir.getName().substring(0,3)+ "_godanddeaf.mdb", dir.getAbsolutePath() + "\\" + dir.getName().substring(0,3)+ "_godanddeaf.mdb" + ".bak");
					dbFlag = true;
				}
			}
		}
	}
	
	public void rollBack(File dir) throws ParseException, IOException{
		
		File[] files = dir.listFiles();
		for(File f : files){
			String fileName = f.getName();
			String bkName = f.getAbsolutePath() + ".bak";
			File bkFile = new File(bkName);
			String extension = fileName.substring(fileName.lastIndexOf(".")+1);
					
			if((extension.equals("mdb") || extension.equals("csv")) && bkFile.exists()){
				Path path = Paths.get(f.getAbsolutePath());
				try {
					Files.delete(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for(File f : files){
			String fileName = f.getName();
			String extension = fileName.substring(fileName.lastIndexOf(".")+1);

			if(extension.equals("bak")){
				renameBackup(f);
			}
			
			if(isMediaType(f) && hasTimeStamp(f) && isLatest(f)){
				Path path = Paths.get(f.getAbsolutePath());
				try {
					Files.delete(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(isMediaType(f) && !hasTimeStamp(f)){
				Path path = Paths.get(f.getAbsolutePath());
				try {
					Files.delete(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void backUp(){	
		try {
			config.readConfig();
			config.setLastRunTime(HHRUtils.makeTimeStamp());
			config.writeConfig();
			
			backUpSource();
			backUpTarget();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
