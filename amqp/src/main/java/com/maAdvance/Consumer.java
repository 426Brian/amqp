package com.maAdvance;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;


public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();


























































































































































































































































































































        String queueName = "test001"
        channel.queueDeclare(queueName, true, false, false, null);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName, true, queueingConsumer);


        while(true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());

            System.out.println("consumer ---> "+msg);

            Envelope envelope = delivery.getEnvelope();
        }


    }
}
