package com.dim.ui.model;

/**
 * Created by dim on 2017/3/31.
 * @author dim
 */

public class Info {
    /**  */
    private String info;
    /**  */
    private String content;
    public Info (String info, String content) {
        this.info = info;
        this.content = content;
    }

    public String getInfo() {
        return info;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
