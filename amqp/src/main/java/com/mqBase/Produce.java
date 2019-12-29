package com.mqBase;

import com.maAdvance.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Produce {

    private static final String QUEUE_NAME = "test_base_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello simple from test_base_queue";

        // 发布消息
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("---send msg --- "+msg);

        channel.close();
        connection.close();
    }
}
