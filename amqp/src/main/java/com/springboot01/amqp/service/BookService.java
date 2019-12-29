package com.springboot01.amqp.service;

import com.springboot01.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @RabbitListener(queues = "brian.news")
    public void receive(Book book){
        System.out.println("收到消息 *** "+book);
    }

    @RabbitListener(queues = "brian")
    public void receive(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
