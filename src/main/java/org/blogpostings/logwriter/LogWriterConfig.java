package org.blogpostings.logwriter;
import java.util.ArrayList;
import java.util.List;


public class LogWriterConfig {
	
	private List<String> logfiles = new ArrayList<String>();
	private Long writeInterval;

	public List<String> getLogfiles() {
		return logfiles;
	}
	public void setLogfiles(List<String> logfiles) {
		this.logfiles = logfiles;
	}
	public Long getWriteInterval() {
		return writeInterval;
	}
	public void setWriteInterval(Long writeInterval) {
		this.writeInterval = writeInterval;
	}
	

}
