package com.vegiecrud.vegie.dto;

import java.util.List;

public class KafkaHelperResponse <T>{
    private int status;
    private T info;

    public KafkaHelperResponse() {
        this.status = status;
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
