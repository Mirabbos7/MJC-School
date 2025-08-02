package com.mjc.school.service.exceptions;


public enum ExceptionConstants {
    NEWS_ID_DOES_NOT_EXIST("001", "News Id does not exist. News Id is: %s"),
    AUTHOR_ID_DOES_NOT_EXIST("002", "Author Id does not exist. Author Id is: %s"),
    STRING_LENGTH_OUT_OF_RANGE("003", "%s can not be less than %s and more than %s symbols. %s is %s"),
    INT_VALUE_OUT_OF_RANGE("004", "%s should be number"),
    NULL_STRING_EXCEPTION("005", "%s can not be null");


    public final String code;
    public final String msg;


    ExceptionConstants(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getCodeMsg() {
        return "ERROR_CODE: " + code + ", " + "ERROR_MESSAGE: " + msg;
    }

}
