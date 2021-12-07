package com.rui.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

//使用mq的意义在于，可以直接使用listener去监听消息，一旦有消息输入就可以得到提醒
//redis不具备这样的功能
@Service
@RocketMQMessageListener(consumerGroup = "message",topic = "newOrder")
@Slf4j   //输出日志
public class MessageService implements RocketMQListener<String> {
    @Override
    public void onMessage(String string) {
        log.info("收到消息:{}", string);
    }
}