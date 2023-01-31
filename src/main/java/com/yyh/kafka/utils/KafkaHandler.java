package com.yyh.kafka.utils;



import com.yyh.kafka.entity.LzWsdData;
import com.yyh.kafka.mapper.LzWsdDataMapper;
import com.yyh.kafka.service.ILzWsdDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@EnableKafka
public class KafkaHandler {

    @Autowired
    @Qualifier(value = "kafkaProducerTemplate")
    private KafkaTemplate<String, Object> producerTemplate;
    @Autowired
    private LzWsdDataMapper lzWsdDataMapper;
    @Autowired
    private ILzWsdDataService lzWsdDataService;

    @Value("${spring.kafka.topics.tar-topic}")
    private String tarTopic;
    @Value("${spring.kafka.topics.org-topic}")
    private String orgTopic;

    private static final Logger logger = Logger.getLogger("kafkaLog");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @KafkaListener(topics = "lz_wsd_data", containerFactory = "consumerContainerFactory") //定义此消费者接收topic为“test_topic”的消息
    public void listen(List<String> records) {
        logger.info("=============收到消息，开始消费=============");
        String dateStr = dateFormat.format(new Date());
        List<LzWsdData> lzWsdDataList = new ArrayList<>();
        // 收到消息后发送到上海宝信
        for (String msg : records) {
//            producerTemplate.send(tarTopic, msg);
            LzWsdData lzWsdData = new LzWsdData();
            lzWsdData.setMessage(msg);
            lzWsdData.setConsumTime(dateStr);
            lzWsdDataList.add(lzWsdData);
        }
        logger.info(dateStr+" 本次消费了"+records.size()+"条数据");
        logger.info("准备将数据保存到数据库中······");
        boolean saveRes = lzWsdDataService.saveOrUpdateBatch(lzWsdDataList);
        logger.info("Save result is " + saveRes);
        logger.info("=============保存完毕，消费结束=============\n");
    }
}



