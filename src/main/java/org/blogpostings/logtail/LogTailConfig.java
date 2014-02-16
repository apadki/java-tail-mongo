package org.blogpostings.logtail;

import org.blogpostings.logprocess.ProcessLogLine;

public class LogTailConfig {
	private String logDir;
	private Long pollingInterval;
	private ProcessLogLine processLogLine;
	
	public ProcessLogLine getProcessLogLine() {
		return processLogLine;
	}

	public void setProcessLogLine(ProcessLogLine processLogLine) {
		this.processLogLine = processLogLine;
	}

	public Long getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(Long pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

}
