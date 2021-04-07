package com.alibaba.otter.shared.common.model.config.channel;

/**
 * 快速创建channel
 */
public class QuickChannel extends Channel {
    /**
     * 数据源1
     */
    private String dataMedia1;
    /**
     * 数据源2
     */
    private String dataMedia2;
    /**
     * 是否双向同步
     */
    private boolean twoWay;

    public String getDataMedia1() {
        return dataMedia1;
    }

    public void setDataMedia1(String dataMedia1) {
        this.dataMedia1 = dataMedia1;
    }

    public String getDataMedia2() {
        return dataMedia2;
    }

    public void setDataMedia2(String dataMedia2) {
        this.dataMedia2 = dataMedia2;
    }

    public boolean isTwoWay() {
        return twoWay;
    }

    public void setTwoWay(boolean twoWay) {
        this.twoWay = twoWay;
    }
}
