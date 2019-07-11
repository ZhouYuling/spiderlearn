package com.gzhc365.vo;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 15:35 2019/7/10 0010
 * @Modified by:
 */
public class MyException extends Exception {

    private String url;//记录发生错误的url
    private String message;//自定义错误信息

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyException(String url,String message){
        this.url = url;
        this.message = message;
    }

    public String toString() {
        return "MyException{" +
                "url='" + url + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    //================================继承自父类===================================
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }
    //==============================================================================
}
