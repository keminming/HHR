package ui;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CustomOutputStream extends PrintStream{

	private JTextArea textArea;
	
	public CustomOutputStream(OutputStream out, JTextArea textArea){
		super(out);
		this.textArea = textArea;
	}

	public void write(byte[] buf,int off, int len){
		final String message = new String(buf, off, len);
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				textArea.append(message);
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		});
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}
