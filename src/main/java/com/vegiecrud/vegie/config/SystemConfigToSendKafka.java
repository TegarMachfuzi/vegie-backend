package com.vegiecrud.vegie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfigToSendKafka {


    private boolean onSendNotifVegie;

    @Value("${send.kafka.veggie}")
    public boolean isOnSendNotifVegie() {
        return onSendNotifVegie;
    }

    public void setOnSendNotifVegie(boolean onSendNotifVegie) {
        this.onSendNotifVegie = onSendNotifVegie;
    }
}
