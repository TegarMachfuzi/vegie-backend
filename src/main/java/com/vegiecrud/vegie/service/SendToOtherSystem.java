package com.vegiecrud.vegie.service;

import com.vegiecrud.vegie.constant.KafkaConstant;
import com.vegiecrud.vegie.utils.KafkaHelperMessagePublishInitialize;
import com.vegiecrud.vegie.dto.KafkaHelperResponse;
import com.vegiecrud.vegie.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendToOtherSystem implements ISendOtherSystem{

    @Autowired
    KafkaHelperMessagePublishInitialize kafkaPublish;


    @Override
    public <T> void sendToKafka(String strTopic, T data) {
        String strJsonData = JsonUtils.toJson(data);
        KafkaHelperResponse<String> publishResp = this.kafkaPublish.publish(strTopic, strJsonData);
        log.info(KafkaConstant.LOG_FORMAT_PUBLISH_TO_KAFKA, strTopic, strJsonData, JsonUtils.toJson(publishResp));

    }
}
