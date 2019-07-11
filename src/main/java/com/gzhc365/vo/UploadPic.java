package com.gzhc365.vo;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 17:30 2019/7/1 0001
 * @Modified by:
 */
public class UploadPic {

    private int code;
    private String msg;
    private String url;//上传到fast-dfs上后、访问的url
    private String data;
    private String fileName;

    public UploadPic() {
    }

    public UploadPic(int code, String msg, String url, String data, String fileName) {
        this.code = code;
        this.msg = msg;
        this.url = url;
        this.data = data;
        this.fileName = fileName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {



    }
}
