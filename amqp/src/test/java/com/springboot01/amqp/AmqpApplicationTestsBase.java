package com.springboot01.amqp;

import com.springboot01.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqpApplicationTestsBase {


    @Autowired
    RabbitAdmin rabbitAdmin;


    @Test
    public void createExchange(){

        rabbitAdmin.declareExchange( new DirectExchange("amqpadmin.exchange"));
    }



}
