package com.dim.ui.Model;

/**
 * Created by dim on 2017/3/31.
 */

public class Info {
    private String info, content;
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
