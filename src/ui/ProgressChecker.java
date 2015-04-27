package ui;

import hhr.Statistic;
import javax.swing.SwingUtilities;

public class ProgressChecker implements Runnable{

	private HHRUI ui;
	
	private Statistic stat;
	
	private boolean stop = false;
	
	ProgressChecker(HHRUI ui,Statistic stat){
		this.ui = ui;
		this.stat = stat;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		start();
		while(!stop)
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					if(stat.getLanguages() == 0)
						ui.getStat().setText("Scanning Existing Folders...\n");
					else {
						if(stat.getCompletePercentage() < 100){
						ui.getStat().setText("Totally " + stat.getLanguages() + " Folders\n");
					ui.getStat().append(stat.getCompleteCount() + " Folder Finished, " 
					+ stat.getCompleteWordCount() + " Words Finished, " + String.format("%.2f", stat.getCompletePercentage()) + "%\n");
						}else{
							ui.getStat().setText("Copying media files...\n");
						}
					}
				}
			});
			
			if(!ui.getScanner().getStat().isStart()){
				stop = true;
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						double second = (stat.getEndTime() - stat.getStartTime())/1000;
						if(second <= 60)
							ui.getStat().append("Scanning finished, using " + String.format("%.2f", second) + " sec.\n");
						else
							ui.getStat().append("Scanning finished, using " + String.format("%.2f", second/60) + " min.\n");
						ui.getConsole().append("Waiting for start...\n");	
						ui.setStopFlag(true);
					}
				});
				break;
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void start(){
		stop = false;
	}
	
	public void stop() {
		// TODO Auto-generated method stub
		stop = true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
