package com.api.shortenerUrl.exceptionHandlers;

public class ErrorInfo {
    public final StringBuffer url;
    public final String error;

    public ErrorInfo(StringBuffer stringBuffer, Exception ex) {
        this.url = stringBuffer;
        this.error = ex.getLocalizedMessage();
    }
    
}
