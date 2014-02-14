package org.tests.jtail;

public class LogMonitorConfig {
	private String logDir;
	private Long pollingInterval;

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
