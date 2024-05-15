package com.vegiecrud.vegie.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Value("${async.queue.capacity}")
    private Integer queueCapacity;

    @Value("${async.max.pool.size}")
    private Integer maxPoolSize;

    @Value("${async.core.pool.size}")
    private Integer corePoolSize;

    @Value("${async.thread.name}")
    private String threadName;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setQueueCapacity(queueCapacity);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setThreadNamePrefix(threadName);
        executor.initialize();
        return executor;
    }
}

//        - setQueueCapacity(100): Ini menentukan kapasitas maksimum antrian tugas yang akan ditangani oleh executor. Jika antrian penuh, tugas-tugas tambahan akan ditolak.
//        - setMaxPoolSize(2): Ini menentukan jumlah maksimum thread dalam pool. Dalam kasus ini, executor akan memiliki maksimal 2 thread yang aktif pada satu waktu.
//        - setCorePoolSize(2): Ini menentukan jumlah minimum thread yang akan dijalankan secara terus-menerus, meskipun tidak ada tugas yang berjalan. Dalam kasus ini, ada 2 thread inti yang selalu aktif.
//        - setThreadNamePrefix("poolThread-"): Ini menentukan awalan yang digunakan untuk nama thread yang dibuat oleh executor.
//        - initialize(): Metode ini menginisialisasi task executor berdasarkan konfigurasi yang telah diatur sebelumnya.