package hhr;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistic {
	
	private static final int WORDS = 1742;
	
	private double startTime;
	
	private int folderCnt;
	
	private AtomicInteger folderCompleteCnt = new AtomicInteger(0);
	
	private AtomicInteger mediaCnt = new AtomicInteger(0);
	
	private AtomicInteger mdiaCompleteCnt = new AtomicInteger(0);
	
	private static Statistic instance = new Statistic();
	
	private Statistic(){};
	
	public static void setInstance(Statistic instance) {
		Statistic.instance = instance;
	}
	
	public static Statistic getInstance(){
		return instance;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public static int getWords() {
		return WORDS;
	}
	
	public int getFolderCnt() {
		return folderCnt;
	}

	public void setFolderCnt(int languages) {
		this.folderCnt = languages;
	}

	public void reset(){
		folderCompleteCnt.set(0);
		mediaCnt.set(0);
		mdiaCompleteCnt.set(0);
		startTime = System.currentTimeMillis();
	}
	
	public void incFolder(){
		folderCompleteCnt.incrementAndGet();
	}
	
	public void incMedia()
	{
		mediaCnt.incrementAndGet();
	}
	
	public void incCompleteMedia()
	{
		mdiaCompleteCnt.incrementAndGet();
	}
	
	public int getCompleteFolderCnt(){
		return folderCompleteCnt.get();
	}
	
	public int getleMediaCnt(){
		return mediaCnt.get();
	}
	
	public int getCompleteMediaCnt(){
		return mdiaCompleteCnt.get();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
