package com.alibaba.otter.shared.common.model.config.channel;

import java.util.List;

/**
 * 快速创建channel
 */
public class QuickChannel extends Channel {
    /**
     * 数据源1
     */
    private Long dataMedia1;
    /**
     * 数据源2
     */
    private Long dataMedia2;
    private String canal1Name;
    private String canal2Name;
    private Long zk1Id;
    private Long zk2Id;
    private String pipeline1Name;
    private String pipeline2Name;
    private List<Long> select1Nodes;
    private List<Long> select2Nodes;
    /**
     * 是否双向同步
     */
    private boolean twoWay;

    public Long getDataMedia1() {
        return dataMedia1;
    }

    public void setDataMedia1(Long dataMedia1) {
        this.dataMedia1 = dataMedia1;
    }

    public Long getDataMedia2() {
        return dataMedia2;
    }

    public void setDataMedia2(Long dataMedia2) {
        this.dataMedia2 = dataMedia2;
    }

    public boolean isTwoWay() {
        return twoWay;
    }

    public void setTwoWay(boolean twoWay) {
        this.twoWay = twoWay;
    }

    public String getCanal1Name() {
        return canal1Name;
    }

    public void setCanal1Name(String canal1Name) {
        this.canal1Name = canal1Name;
    }

    public String getCanal2Name() {
        return canal2Name;
    }

    public void setCanal2Name(String canal2Name) {
        this.canal2Name = canal2Name;
    }

    public String getPipeline1Name() {
        return pipeline1Name;
    }

    public void setPipeline1Name(String pipeline1Name) {
        this.pipeline1Name = pipeline1Name;
    }

    public String getPipeline2Name() {
        return pipeline2Name;
    }

    public void setPipeline2Name(String pipeline2Name) {
        this.pipeline2Name = pipeline2Name;
    }

    public Long getZk1Id() {
        return zk1Id;
    }

    public void setZk1Id(Long zk1Id) {
        this.zk1Id = zk1Id;
    }

    public Long getZk2Id() {
        return zk2Id;
    }

    public void setZk2Id(Long zk2Id) {
        this.zk2Id = zk2Id;
    }

    public List<Long> getSelect1Nodes() {
        return select1Nodes;
    }

    public void setSelect1Nodes(List<Long> select1Nodes) {
        this.select1Nodes = select1Nodes;
    }

    public List<Long> getSelect2Nodes() {
        return select2Nodes;
    }

    public void setSelect2Nodes(List<Long> select2Nodes) {
        this.select2Nodes = select2Nodes;
    }
}
