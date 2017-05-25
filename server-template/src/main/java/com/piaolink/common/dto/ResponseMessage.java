package com.piaolink.common.dto;

/**
 * Created by bill on 2017-4-25.
 */
public class ResponseMessage {

    private String code;
    private String message;

    public ResponseMessage(String code) {
        this.code = code;
        this.message = null;
    }

    public ResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
