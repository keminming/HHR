package hhr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tool.CSVArchive;
import tool.EmailSender;
import tool.KMLGenerator;
import tool.MediaConverter;
import tool.SHA1CheckSum;

public abstract class FormHandler {
	
	class Email{
		public Email(String groupName, String fieldName) {
			// TODO Auto-generated constructor stub
			this.groupName = groupName;
			this.fieldName = fieldName;
		}
		public String groupName;
		public String fieldName;
	}
	
	class WordInfo{
		public WordInfo(String timestamp, String keyHead,
				String keyHeadIndex, NodeList fields) {
			this.timestamp = timestamp;
			this.keyHead = keyHead;
			this.keyHeadIndex = keyHeadIndex;
			this.fields = fields;
		}
		public String timestamp; 
		public String keyHead; 
		public String keyHeadIndex; 
		public NodeList fields; 	
	}
	
	class PrefixSurfix{
		public PrefixSurfix(String prefix1, String prefix2, String surfix) {
			this.prefix1 = prefix1;
			this.prefix2 = prefix2;
			this.surfix = surfix;
		}
		public String prefix1;
		public String prefix2;
		public String surfix;
	}
	
	private static final int WORD_BEGIN = 11;	
	private DocumentBuilderFactory dbFactory;
	private Element root;
	private Document doc;
	
	protected List<Email> emailInfos;
	protected PrefixSurfix pns;
	protected DocumentBuilder dBuilder;
	protected String source;
	protected String target;
	protected String language;
	protected EmailSender email;
	protected String ffmpeg;
	protected CSVArchive csv;
	protected CSVArchive history;
	protected SHA1CheckSum sha1;
	protected HHRPlacemark placemark;
	protected MediaConverter converter;
	
	protected List<String> audioList = new ArrayList<String>();
	protected List<List<String>> textList = new ArrayList<List<String>>();
	protected List<String> videoList = new ArrayList<String>();
	
	public FormHandler(HHREnvironment env)
	{
		dbFactory = DocumentBuilderFactory.newInstance();

		this.source = env.source;
		this.target = env.target;
		this.language = env.language;
			
		try {
			dBuilder  = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}

		sha1 = SHA1CheckSum.getInstance();
		converter = MediaConverter.getInstance();
		email = EmailSender.getInstance();
		emailInfos = new ArrayList<Email>();
	}
	
	public void init(File form) throws SAXException, IOException{
		doc = dBuilder.parse(form);
		root = doc.getDocumentElement();
		placemark.setRoot(root);
	}
		
	protected void makeCurrentCopy(){
		List<List<String>> entries = csv.getEntries();
		for(int i=0;i<entries.size();i++){
			String keyHead = entries.get(i).get(0);
			String keyHeadIndex = entries.get(i).get(1);
			String timestamp = entries.get(i).get(3);
			String parent = target + pns.prefix2 + language + pns.prefix1 + keyHead +  keyHeadIndex;
			String currentPath = parent + pns.surfix;
			String versionPath = parent + timestamp + pns.surfix;
			
			try {
				File f = new File(versionPath);
				if(f.exists()){
					HHRUtils.copyFile(versionPath, currentPath);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected abstract void action(Node word) throws IOException, InterruptedException;	
	
	protected void updateKML() {
		// TODO Auto-generated method stub
		placemark.build();
		KMLGenerator.getInstance().insert(placemark);				
	}
	
	protected List<String> extractEmails(){	
		List<String> res = new ArrayList<String>();
		for(int i=0;i<emailInfos.size();i++){
			Email emailInfo = emailInfos.get(i);
			Element e = (Element)root.getElementsByTagName(emailInfo.groupName).item(0);
			String email = e.getElementsByTagName(emailInfo.fieldName).item(0).getTextContent();
			res.add(email);
		}
		return res;
	}	
	
	private void sendEmail() throws AddressException, MessagingException{
		List<String> recipients = extractEmails();
		if(audioList.size() != 0 || textList.size() != 0 || videoList.size() != 0){
			String summary = HHRUtils.makeChangeSummary(source,audioList, textList, videoList);
			//System.out.println(summary);
			email.send(language,summary,new ArrayList<String>());
			//email.send(summary,recipients);
		}
	}
	
	public void handle() throws SAXException, IOException, AddressException, MessagingException, InterruptedException{
		// TODO Auto-generated method stub
		NodeList children = root.getChildNodes();
		for(int i = WORD_BEGIN; i < WORD_BEGIN + 1742;i++){
			//Statistic.getInstance().incWord();	
			action(children.item(i));
		}

		updateKML();
		sendEmail();
		//makeCurrentCopy();
	}
	
	protected void copyMediaFile(WordInfo info) throws IOException, InterruptedException{
		String name = info.fields.item(1).getTextContent();
		if(name.equals(""))
			return;
		
		String hash = sha1.hash(source + name);
		String oldHash = csv.getHash(info.keyHead + info.keyHeadIndex);
		
		if(!hash.equals(oldHash)){
			String convertedVideoName = language + pns.prefix1 + info.keyHead +  info.keyHeadIndex + pns.surfix;
			String convertedVersionedVideoName = language + pns.prefix1 + info.keyHead +  info.keyHeadIndex + info.timestamp + pns.surfix;
			converter.convert(source + name, target + pns.prefix2 + convertedVersionedVideoName,target + pns.prefix2 + convertedVideoName);
			videoList.add(convertedVersionedVideoName + "(" + name + ")");
			history.appendToCSV(info.keyHead + "," + info.keyHeadIndex + "," + name + "," + info.timestamp + ",=HYPERLINK(\"" + convertedVersionedVideoName + "\")");
			csv.setHash(info.keyHead + info.keyHeadIndex, hash);
			csv.setTimestamp(info.keyHead + info.keyHeadIndex, info.timestamp);
		}			
	}
	
	protected WordInfo getWordInfo(Node word){
		return new WordInfo(HHRUtils.makeTimeStamp(),
				HHRUtils.getKeyHead(word.getNodeName()),
				HHRUtils.getKeyIndex(word.getNodeName()),
				word.getChildNodes());
	}
	
	public abstract void cleanUp() throws IOException;
}
