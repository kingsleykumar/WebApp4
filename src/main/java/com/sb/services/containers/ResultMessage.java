package com.sb.services.containers;

/**
 * Created by Kingsley Kumar on 12/11/2016.
 */
public class ResultMessage {
    private boolean result;
    private String message;

    public ResultMessage(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
