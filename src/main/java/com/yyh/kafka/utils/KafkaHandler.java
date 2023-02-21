package com.yyh.kafka.utils;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
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
        // 收到消息后发送到目的Kafka
        for (String msg : records) {
//            producerTemplate.send(tarTopic, msg);
            LzWsdData lzWsdData = new LzWsdData();
            lzWsdData.setId(String.valueOf(IdWorkerUtil.nextId()));
            lzWsdData.setMessage(msg);
            lzWsdData.setConsumTime(dateStr);
            JSONObject msgJsonObj = JSONObject.parseObject(msg);
            JSONArray values = (JSONArray) msgJsonObj.get("Values");
            for (Object obj : values) {
                JSONObject jsonObject = JSONObject.parseObject(obj.toString());
                String kpiId = jsonObject.getString("KpiId");
                String value = jsonObject.getString("Value");
                switch (kpiId) {
                    case "温度": lzWsdData.setTemperature(value);
                    case "湿度": lzWsdData.setHumidity(value);
                    case "电量": lzWsdData.setElectricity(value);
                }
            }
            lzWsdData.setDevId(msgJsonObj.getString("DevId"));
            lzWsdData.setSpecialty(msgJsonObj.getString("Specialty"));
            lzWsdData.setDataType(msgJsonObj.getString("DataType"));
            lzWsdData.setImplType(msgJsonObj.getString("ImplType"));
            lzWsdData.setPointId(msgJsonObj.getString("PointId"));
            lzWsdData.setDTime(msgJsonObj.getString("DTime"));
            lzWsdData.setSn(msgJsonObj.getString("SN"));
            lzWsdDataList.add(lzWsdData);
        }
        logger.info(dateStr+" 本次消费了"+records.size()+"条数据");
        logger.info("准备将数据保存到数据库中······");
        boolean saveRes = lzWsdDataService.saveOrUpdateBatch(lzWsdDataList);
        logger.info("Save result is " + saveRes);
        logger.info("=============保存完毕，消费结束=============\n");
    }

    public static void main(String[] args) {
        String msg = "{\n" +
                "  \"DevId\": \"066512E01\",\n" +
                "  \"Specialty\": \"G\",\n" +
                "  \"DataType\": \"DMA\",\n" +
                "  \"ImplType\": \"N\",\n" +
                "  \"PointId\": \"01\",\n" +
                "  \"DTime\": \"2023-02-01 13:05:00.046\",\n" +
                "  \"SN\": \"\",\n" +
                "  \"Values\": [\n" +
                "    {\n" +
                "      \"KpiId\": \"温度\",\n" +
                "      \"Value\": \"13.0\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"KpiId\": \"湿度\",\n" +
                "      \"Value\": \"41.6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"KpiId\": \"电量\",\n" +
                "      \"Value\": \"85\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject msgJsonObj = JSONObject.parseObject(msg);
        JSONArray values = (JSONArray) msgJsonObj.get("Values");
        for (Object obj : values) {
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            String kpiId = jsonObject.getString("KpiId");
            System.out.println(jsonObject.get("Value"));
        }
    }
}
/*
{
  "DevId": "066512E01",
  "Specialty": "G",
  "DataType": "DMA",
  "ImplType": "N",
  "PointId": "01",
  "DTime": "2023-02-01 13:05:00.046",
  "SN": "",
  "Values": [
    {
      "KpiId": "温度",
      "Value": "13.0"
    },
    {
      "KpiId": "湿度",
      "Value": "41.6"
    },
    {
      "KpiId": "电量",
      "Value": "85"
    }
  ]
}
 */


