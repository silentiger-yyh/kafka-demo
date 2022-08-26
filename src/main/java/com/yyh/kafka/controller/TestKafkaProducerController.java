package com.yyh.kafka.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("kafka")
@EnableScheduling
public class TestKafkaProducerController {

    @Autowired
    @Qualifier("kafkaConsumerTemplate")
    private KafkaTemplate kafkaTemplate;
    @GetMapping("producer")
    public void testDemo() throws InterruptedException {
        List<String> msgs = new ArrayList<>();
        for(int i=0;i<5000;i++) {
            kafkaTemplate.send("lz_wsd_data", (i + 1) + "");
        }
        System.out.println("本次发送了500条数据");
        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
        Thread.sleep(2000);
    }

    @Value("${producer.simulation}")
    private Boolean isProducer;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void producerToLzWsdData() {
        if (isProducer) {
            int batchSize = 5000;
            for (int i = 0; i < batchSize; i++) {
                kafkaTemplate.send("lz_wsd_data", (i + 1) + "");
            }
            System.out.println("本次发送了" + batchSize + "条数据");
        }
    }
//    @Bean
//    public TaskScheduler poolScheduler() {
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setThreadNamePrefix("poolScheduler");
//        scheduler.setPoolSize(10);
//        return scheduler;
//    }
}
