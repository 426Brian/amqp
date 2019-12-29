package com.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

public class UDPProvider {
    public static void main(String[] args) throws IOException {
        // 生成唯一标识
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        provider.start();

        System.in.read();
        provider.exit();
    }

    private static class Provider extends Thread{
        private final String sn;
        private boolean done = false;
        private  DatagramSocket ds = null;

        public Provider(String sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            System.out.println("UDPProvider started....");
            try {
                // 创建接收者， 指定一个端口用于接收信息
                ds = new DatagramSocket(20000);

                while (!done){

                    // 构建实体
                    byte[] bytes = new byte[512];
                    DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);
                    // 接收
                    ds.receive(receivePacket);

                    String ip = receivePacket.getAddress().getHostAddress();
                    int port = receivePacket.getPort();
                    int dataLen = receivePacket.getLength();
                    String info = new String(receivePacket.getData(), 0, dataLen);
                    // 输出接收到信息
                    System.out.println("UDPProvider received from ip: " + ip + "\t port: " + port+"\t info "+ info+ "\t dataLength: " + dataLen);

                    // 端口
                    int responsePort = MessageCreator.parsePort(info);
                    if(responsePort != -1){
                        // 发送信息
                        String respoonse = MessageCreator.buildWithSn(sn);
                        byte[] respoonseBytes = respoonse.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(
                                respoonseBytes,
                                respoonseBytes.length,
                                receivePacket.getAddress(),
                                responsePort);
                        ds.send(responsePacket);

                    }

                }
            } catch (Exception ignored) {

            }finally {
                close();
            }

            System.out.println("UDPProvider finished....");
        }

        private void close(){
            if(ds !=null){
                ds.close();
                ds = null;
            }
        }
        void exit(){
            done = true;
            close();
        }
    }
}

