package configure;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class HHRConfig {
	private String source;
	private String target;
	private String dependency;
	private String ffmpeg;
	private String threads;
	private String lastRunTime;
	
	public String getDependency() {
		return dependency;
	}

	public void setDependency(String dependency) {
		this.dependency = dependency;
	}

	public String getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(String lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public void readConfig() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);

			source = prop.getProperty("source");
			target = prop.getProperty("target");
			dependency = prop.getProperty("dependency");
			ffmpeg = prop.getProperty("ffmpeg");
			threads = prop.getProperty("threads");
			lastRunTime = prop.getProperty("lastruntime");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeConfig() {

		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream("config.properties");

			// set the properties value
			prop.setProperty("source", source);
			prop.setProperty("target", target);
			prop.setProperty("dependency", dependency);
			prop.setProperty("ffmpeg", ffmpeg);
			prop.setProperty("threads", threads);
			if(lastRunTime == null)
				prop.setProperty("lastruntime", "0");
			else
				prop.setProperty("lastruntime", lastRunTime);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getFfmpeg() {
		return ffmpeg;
	}

	public void setFfmpeg(String ffmpeg) {
		this.ffmpeg = ffmpeg;
	}

	public String getThreads() {
		return threads;
	}

	public void setThreads(String threads) {
		this.threads = threads;
	}

}
