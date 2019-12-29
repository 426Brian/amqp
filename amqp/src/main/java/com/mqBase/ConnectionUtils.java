package com.mqBase;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 设置服务地址    本次连接使用的是 vm 上的rabbitmq, IP 为vm 的IP
        connectionFactory.setHost("192.168.1.101");

        // AMQP 5672
        connectionFactory.setPort(5672);

        // vhost
        connectionFactory.setVirtualHost("/");

        //(admin 是自己添加的用户)
        connectionFactory.setUsername("guest");

        // 密码
        connectionFactory.setPassword("guest");

        return connectionFactory.newConnection();

    }
}
