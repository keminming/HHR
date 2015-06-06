package ui;

import hhr.Statistic;
import javax.swing.SwingUtilities;

public class ProgressChecker implements Runnable{

	private HHRUI ui;
	
	private Statistic stat;
	
	private boolean stop = false;
	
	ProgressChecker(HHRUI ui){
		this.ui = ui;
	}

	public Statistic getStat() {
		return stat;
	}

	public void setStat(Statistic stat) {
		this.stat = stat;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					ui.getStat().setText("Folders Found: " + stat.getFolderCnt() + "\n");
					ui.getStat().append("Folders Processed: " + stat.getCompleteFolderCnt() + "\n");
					ui.getStat().append("Media Files Found: " + stat.getleMediaCnt() + "\n");
					ui.getStat().append("Media Files Converted: " + stat.getCompleteMediaCnt() + "\n"); 
					ui.getStat().append("\n");
					ui.getStat().append("\n");
					double second = (System.currentTimeMillis() - stat.getStartTime())/1000;
					if(second <= 60)
						ui.getStat().append("Time Elapsed: " + String.format("%.2f", second) + " sec.\n");
					else
						ui.getStat().append("Time Elapsed: " + String.format("%.2f", second/60) + " min.\n");
				}
			});
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
