package com.alibaba.otter.manager.biz.monitor;

import com.alibaba.otter.shared.common.model.config.alarm.MonitorName;

/**
 * 告警参数
 */
public class AlarmParameter {

    private MonitorName type;

    private long secondTimes;//延迟、超时告警使用的参数

    private String message;

    public AlarmParameter(MonitorName type,String message){
        this.type = type;
        this.message = message;
    }

    public AlarmParameter(MonitorName type,long time, String message){
        this.type = type;
        this.secondTimes = time;
        this.message = message;
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
}
