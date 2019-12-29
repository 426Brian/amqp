package com.springboot01.amqp;

import com.springboot01.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpAdmin amqpAdmin;


    @Test
    public void createExchange(){

        amqpAdmin.declareExchange( new DirectExchange("amqpadmin.exchange"));
    }

    /**
     * 点对点： 单播
     */
//    @Test
    public void contextLoads() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是第一个消息");
        map.put("data", Arrays.asList("helloworld", 123, true));
//        rabbitTemplate.convertAndSend("exchange.direct", "brian.news",map);
        rabbitTemplate.convertAndSend("exchange.direct", "brian.news", new Book("西游记", "吴承恩"));
    }

//    @Test
    public void receive(){
        Object obj = rabbitTemplate.receiveAndConvert("brian.news");
        System.out.println(obj.getClass());
        System.out.println(obj);

    }

    /**
     * 广播
     */
    @Test
    public void sendAll(){
//        rabbitTemplate.convertAndSend("exchange.fanout","", new Book("红楼梦","曹雪芹"));
        rabbitTemplate.convertAndSend("exchange.fanout","", new Book("三国演义","罗贯中"));
    }

}
