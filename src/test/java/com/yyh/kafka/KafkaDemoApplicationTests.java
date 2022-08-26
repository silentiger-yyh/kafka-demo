package com.yyh.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class KafkaDemoApplicationTests {


    @Test
    void contextLoads() {

        Log log = LogFactory.getLog("kafkaLog");
        log.info("哈哈哈哈哈");
    }
    @Autowired
    @Qualifier("kafkaConsumerTemplate")
    private KafkaTemplate kafkaTemplate;
    @Test
    public void testDemo() throws InterruptedException {
        List<String> msgs = new ArrayList<>();
        for(int i=0;i<500;i++) {
            kafkaTemplate.send("lz_wsd_data", (i + 1) + "");
        }
        System.out.println("本次发送了500条数据");
        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
        Thread.sleep(2000);
    }
    private Logger logger = LoggerFactory.getLogger(getClass());


}
