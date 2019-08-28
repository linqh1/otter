package com.alibaba.otter.manager.biz.monitor;

import com.alibaba.otter.shared.common.model.config.alarm.MonitorName;

import java.util.Map;

/**
 * 告警参数
 */
public class AlarmParameter {

    private MonitorName type;

    private long secondTimes;//延迟、超时告警使用的参数

    private String message;

    private Map<String,String> tags;

    public AlarmParameter(MonitorName type, String message, Map<String,String> tags){
        this.type = type;
        this.message = message;
        this.tags = tags;
    }

    public AlarmParameter(MonitorName type,long time, String message, Map<String,String> tags){
        this.type = type;
        this.secondTimes = time;
        this.message = message;
        this.tags = tags;
    }

    public MonitorName getType() {
        return type;
    }

    public long getSecondTimes() {
        return secondTimes;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getTags() {
        return tags;
    }
}
