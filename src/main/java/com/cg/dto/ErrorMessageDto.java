package com.cg.dto;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

public class ErrorMessageDto {

    private String errorMsg;
    private String status;
    private LocalDateTime timeStamp;
    private Map<String, List<String>> errMap;

    public ErrorMessageDto() {
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, List<String>> getErrMap() {
        return errMap;
    }

    public void setErrMap(Map<String, List<String>> errMap) {
        this.errMap = errMap;
    }
}
