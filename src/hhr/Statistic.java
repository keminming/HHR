package hhr;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistic {
	
	private boolean start = false;
	
	private long startTime;
	
	private long endTime;
	
	private static final int WORDS = 1742;
	
	private AtomicInteger ai = new AtomicInteger(0);
	
	private AtomicInteger wai = new AtomicInteger(0);
	
	private static Statistic instance = new Statistic();
	
	private Statistic(){};
	
	public static void setInstance(Statistic instance) {
		Statistic.instance = instance;
	}
	
	public static Statistic getInstance(){
		return instance;
	}

	public boolean isStart() {
		return start;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public AtomicInteger getAi() {
		return ai;
	}

	public void setAi(AtomicInteger ai) {
		this.ai = ai;
	}

	public AtomicInteger getWai() {
		return wai;
	}

	public void setWai(AtomicInteger wai) {
		this.wai = wai;
	}

	public static int getWords() {
		return WORDS;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	private int languages;
	
	public int getLanguages() {
		return languages;
	}

	public void setLanguages(int languages) {
		this.languages = languages;
	}

	public void reset(){
		ai.set(0);
		wai.set(0);
		startTime = System.currentTimeMillis();
		endTime = 0;
		start = true;
	}
	
	public void stop(){
		endTime = System.currentTimeMillis();
		start = false;
	}
	
	public void incFolder(){
		ai.incrementAndGet();
	}
	
	public void incWord(){
		wai.incrementAndGet();
	}
	
	public double getCompletePercentage(){
		return 100 * wai.doubleValue()/WORDS/languages;
	}
	
	public int getCompleteCount(){
		return ai.get();
	}
	
	public int getCompleteWordCount(){
		return wai.get();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
