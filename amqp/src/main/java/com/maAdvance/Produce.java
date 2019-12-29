package com.maAdvance;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class Produce {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();


        String msg = "hello rabbitmq";
        String routingKey = "test001";

        for (int i = 0; i < 5; i++) {
            channel.basicPublish("", routingKey, null, msg.getBytes());
        }

        channel.close();
        connection.close();

    }
}
