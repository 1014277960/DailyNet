package com.wiipu.dailynet.base;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午9:29
 * @description:
 */
public class Response {

    private boolean isSuccess = false;
    private String errorMsg = "";
    private String result = "";

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
