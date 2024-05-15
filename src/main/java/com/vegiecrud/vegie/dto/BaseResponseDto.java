package com.vegiecrud.vegie.dto;

import java.io.Serializable;

public class BaseResponseDto <T> implements Serializable {
    private static final long serialVersionUID = -5565183286487777163L;

    private int status = 200;
    private String info = "Success";
    private T content;

    public BaseResponseDto() {
    }

    public BaseResponseDto(T content) {
        this.content = content;
    }

    public BaseResponseDto(String info, T content) {
        this.info = info;
        this.content = content;
    }

    public BaseResponseDto(int status, String info, T content) {
        this.status = status;
        this.info = info;
        this.content = content;
    }


    //encapsulation
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
