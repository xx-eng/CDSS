package com.response;

import lombok.Data;

@Data
public class BaseResponse {
    private Object data;
    private String resultCode;
    private String resultDesc;
    private String cookie;

    public BaseResponse returnResponse(Object data, String resultCode, String resultDesc){
        this.data = data;
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        return this;
    }

    public BaseResponse returnResponse(Object data, String cookie, String resultCode, String resultDesc){
        this.data = data;
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        this.cookie = cookie;
        return this;
    }
}
