package com.cg.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ErrorResponseDto {
	private String errormsg;
	private LocalDateTime timeStamp;
	private String status;
	private Map<String, List<String>> errMap;

	public Map<String, List<String>> getErrMap() {
		return errMap;
	}

	public void setErrMap(Map<String, List<String>> errMap) {
		this.errMap = errMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
}
