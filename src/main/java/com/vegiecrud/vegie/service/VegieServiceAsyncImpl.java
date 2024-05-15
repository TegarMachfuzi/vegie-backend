package com.vegiecrud.vegie.service;

import com.vegiecrud.vegie.config.SystemConfigToSendKafka;
import com.vegiecrud.vegie.constant.KafkaConstant;
import com.vegiecrud.vegie.dto.VegieDto;
import com.vegiecrud.vegie.model.Vegie;
import com.vegiecrud.vegie.repository.RedisRepository;
import com.vegiecrud.vegie.repository.VegieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
public class VegieServiceAsyncImpl{

    @Autowired
    private VegieServiceImpl vegieService;
    @Value("${spring.datasource.redis.timeout}")
    private Long redisTimeout;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private VegieRepository vegieRepository;

    @Autowired
    ISendOtherSystem sendOtherSystem;

    @Autowired
    private SystemConfigToSendKafka systemConfig;
    @Transactional
    @Async
    public CompletableFuture<Vegie> saveOrderDetailsFuture(VegieDto vegie) throws InterruptedException {
        System.out.println("Execute method with return type + " + Thread.currentThread().getName() + ", Thread ID " + Thread.currentThread().getId());
        Vegie result = vegieService.saveVegieToDb(vegie);
        Thread.sleep(5000);
        System.out.println("First async operation completed");
        sendToKafkaVegie(result);
        return CompletableFuture.completedFuture(result);
    }

    private void sendToKafkaVegie(Vegie result) {
            sendOtherSystem.sendToKafka(KafkaConstant.KafkaTopic.KAFKA_VEGGIE, result);
    }

    @Transactional
    @Async
    public CompletableFuture<Vegie> saveOrderDetilsRedisFuture(Vegie vegie) throws InterruptedException {
        System.out.println("Execute method with return type + " + Thread.currentThread().getName() + ", Thread ID " + Thread.currentThread().getId());
        Vegie result = vegieService.saveVegieToRedis(vegie);
        Thread.sleep(6000);
        System.out.println("Second async operation completed");
        return CompletableFuture.completedFuture(result);
    }

}
