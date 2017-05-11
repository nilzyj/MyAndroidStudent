package com.dim.ui.model;

/**
 * Created by dim on 2017/4/12.
 * @author dim
 */

public class Account {
    /**  */
    private String name;
    /**  */
    private boolean isFill;
    /**
     * @param name    登录用户名
     * @param fill    是否填写报考信息标志变量
     */
    public Account(String name, boolean fill) {
        this.name = name;
        isFill = fill;
    }

    /**
     * @return get
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return true：已填写，false：未填写
     */
    public boolean isFill() {
        return isFill;
    }

    /**
     * @param fill is
     */
    public void setFill(boolean fill) {
        isFill = fill;
    }
}
