package hhr;

public class HHREnvironment {
	
	public String language;
	public String source;
	public String target;
	public String ffmpeg;
	
	public HHREnvironment(String language, String source, String target,
			String ffmpeg) {
		this.language = language;
		this.source = source;
		this.target = target;
		this.ffmpeg = ffmpeg;
	}
}
