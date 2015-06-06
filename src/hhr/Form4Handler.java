package hhr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.htmlparser.util.Translate;
import org.w3c.dom.Node;
import tool.CSVArchive;


public class Form4Handler extends FormHandler{

	private GestMeaningDAO gmDao;

	public Form4Handler(HHREnvironment env) {
		super(env);
		
		String parent = target + "_Primers\\";
		
		gmDao = new GestMeaningDAO();
		File db = new File(parent + language + "_godanddeaf.mdb");
		gmDao.useDB(db.getAbsolutePath());	
		
		csv = new CSVArchive(target + "_Voice1\\cache.csv");
		csv.loadCSV();
		
		history = new CSVArchive(parent + "\\history.csv");
		history.openCSV();
		
		placemark = new HHRForm4Placemark();
		
		emailInfos.add(new Email("about_trans_group","translatoremail"));
		emailInfos.add(new Email("about_checker_group","checkeremail"));
		emailInfos.add(new Email("about_voice1_group","voicename01email"));
		emailInfos.add(new Email("about_voice2_group","voicename02email"));
		
		pns = new PrefixSurfix("_voice1_","_Voice1\\",".mp3");
	}	
	
	private void updateDB(WordInfo info){
		String rawGestHeadWord = info.fields.item(0).getTextContent();
		String gestHeadWord = Translate.decode(rawGestHeadWord);
		gestHeadWord = gestHeadWord.trim().replaceAll("[\\s()]+", "_");
		gestHeadWord = gestHeadWord.substring(0,Math.min(gestHeadWord.length(), 25));
		//System.out.println("++"+gestHeadWord);
		if(!gestHeadWord.equals("")){
			GestMeaning gm = gmDao.findGest(info.keyHead, info.keyHeadIndex);
			gmDao.updateGest(language,info.keyHead, info.keyHeadIndex, gestHeadWord);
			history.appendToCSV(info.keyHead + "," + info.keyHeadIndex + "," + gestHeadWord + "," + info.timestamp + ",");
		
			//System.out.println("--"+gm.getGestrueHeadWord());
//			if(Math.random()<0.2)
//				throw new RuntimeException();
			if(!gm.getGestrueHeadWord().equals(gestHeadWord))
			{
				List<String> pair = new ArrayList<String>();
				pair.add(info.keyHead + info.keyHeadIndex);
				pair.add(gm.getGestrueHeadWord());
				pair.add(gestHeadWord);
				textList.add(pair);
			}
		}		
	}

	@Override
	public void action(Node word) throws IOException, InterruptedException {
		WordInfo info = getWordInfo(word);
		//update database, gestHeadWord word column
		updateDB(info);
		//copy audio to archive folder, convert format to mp3, record the change to csv file
		copyMediaFile(info);			
	}
	
	@Override
	public void cleanUp() throws IOException{
		gmDao.closeDB();	
		csv.dumpCSV();
		history.closeCSV();
	}
}
