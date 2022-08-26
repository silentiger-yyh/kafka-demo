package com.yyh.kafka.config;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @author: along
 */
@Slf4j
@Component
public class KafkaSendResultHandler implements ProducerListener {

    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        log.info("Message send success : " + producerRecord.toString());
    }

    @SneakyThrows
    @Override
    public void onError(ProducerRecord producerRecord, RecordMetadata recordMetadata,Exception exception){
        log.info("Message send error : " + producerRecord.toString());
        throw new RuntimeException("kafka消息发送失败");
    }

}
