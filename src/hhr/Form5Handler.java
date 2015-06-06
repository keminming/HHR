package hhr;

import java.io.IOException;
import org.w3c.dom.Node;
import tool.CSVArchive;

public class Form5Handler extends FormHandler{
	
	public Form5Handler(HHREnvironment env) {
		super(env);
		
		String partent = target + "_Signs\\";
		
		csv = new CSVArchive(partent + "cache.csv");
		csv.loadCSV();
		
		history = new CSVArchive(partent + "history.csv");
		history.openCSV();
		
		placemark = new HHRForm5Placemark();
		
		emailInfos.add(new Email("about_videographer_group","videographerernameemail"));
		emailInfos.add(new Email("about_checker_group","checkeremail"));
		emailInfos.add(new Email("about_voiceorsign1_group","voicesignername01email"));
		emailInfos.add(new Email("about_voiceorsign2_group","voicesignname02email"));
		
		pns = new PrefixSurfix("_","_Signs\\",".mp4");
	}
	
	@Override
	public void action(Node word) throws IOException, InterruptedException {
		//copy audio to archive folder, convert format to mp3, record the change to csv file
		WordInfo info = getWordInfo(word);
		copyMediaFile(info);	
	}	
	
	@Override
	public void cleanUp() throws IOException{	
		csv.dumpCSV();
		history.closeCSV();
	}
}
