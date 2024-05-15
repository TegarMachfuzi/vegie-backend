package com.vegiecrud.vegie.service;

public interface ISendOtherSystem {

    <T> void sendToKafka(String strTopic, T data);
}
