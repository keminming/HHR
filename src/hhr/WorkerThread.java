package hhr;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import org.xml.sax.SAXException;

import configure.HHRBackup;

public class WorkerThread implements Runnable{
	private HHREnvironment env;
	private Map<String,String> handlers = new HashMap<String,String>();
	
	public WorkerThread(HHREnvironment env){
		this.env = env;
		
		addHandler("4", "hhr.Form4Handler");
		addHandler("5", "hhr.Form5Handler");
	}
	
	public void addHandler(String name, String className){
		handlers.put(name, className);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		//create target folder and copy db and cache files

		File sfolder = new File(env.source);
	
		String folderName = sfolder.getName();
		System.out.println(">Starting on folder [" + folderName + "]");
		File[] files = sfolder.listFiles();
		FormHandler handler = null;
		
		try {
			for(File file : files){
				String fileName = file.getName();
				Pattern p = Pattern.compile("([\\d]) HHR.+\\.xml");			
				Matcher m = p.matcher(fileName);
				
				if(m.find()){
					String type = m.group(1);
					Class<?> c = Class.forName(handlers.get(type));
				    Constructor<?> constructor = c.getConstructor(HHREnvironment.class);
					handler = (FormHandler) constructor.newInstance(env);

					handler.init(file);
					handler.handle();
					handler.cleanUp();
				}
			}
			
			Statistic.getInstance().incFolder();
			System.out.println(">Finished on folder [" + folderName + "]");
			
		} catch(RuntimeException | SAXException | IOException | MessagingException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
			System.out.println(">Abort on folder [" + folderName + "]");
			try {
				handler.cleanUp();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			String target1 = env.target + "_Primers";
			String target2 = env.target + "_Voice1";
			String target3 = env.target + "_Signs";
			
			System.out.println("Automatically rolling back folder [" + target1 + "]...");
			System.out.println("Automatically rolling back folder [" + target2 + "]...");
			System.out.println("Automatically rolling back folder [" + target3 + "]...");
			
			try {
				HHRBackup.getInstance().rollBack(new File(target1));
				HHRBackup.getInstance().rollBack(new File(target2));
				HHRBackup.getInstance().rollBack(new File(target3));
			} catch (IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
