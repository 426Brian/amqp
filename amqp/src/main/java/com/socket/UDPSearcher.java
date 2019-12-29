package com.socket;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UDPSearcher {
    private static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws Exception {
        System.out.println("UDPSearcher started....");
        Listen listen = listen();
        sendBroadCast();

        System.in.read();
        List<Devices> devices = listen.getDevicesAndClose();
        for (Devices device: devices) {
            System.out.println(device.toString());
        }

        System.out.println("UDPSearcher finished....");
    }

    private static Listen listen() throws Exception {
        System.out.println("" +
                "UDPSearcher listen started....");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listen listen = new Listen(LISTEN_PORT, countDownLatch);
        listen.start();

        countDownLatch.await();
        return listen;
    }

    private static void sendBroadCast() throws Exception {
        System.out.println("UDPSearcher sendBroadCast started....");
        // 作为搜索方, 让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        // 构建回送数据
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] requestDataBytes = requestData.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(requestDataBytes, requestDataBytes.length);
        // 广播地址
        responsePacket.setAddress(InetAddress.getByName("255.255.255.255"));
        responsePacket.setPort(20000);

        ds.send(responsePacket);
        ds.close();

        System.out.println("UDPProvider sendBroadCast finished....");
    }

    private static class Devices {
        final String sn;
        final String ip;
        final int port;

        public Devices(String sn, String ip, int port) {
            this.sn = sn;
            this.ip = ip;
            this.port = port;
        }

        @Override
        public String toString() {
            return "Devices{" +
                    "sn='" + sn + '\'' +
                    ", ip='" + ip + '\'' +
                    ", port=" + port +
                    '}';
        }
    }

    private static class Listen extends Thread {
        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Devices> devicesList = new ArrayList<>();
        private boolean done = false;
        private DatagramSocket ds = null;

        public Listen(int listenPort, CountDownLatch countDownLatch) {
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();

            // 通知已启动
            countDownLatch.countDown();
            try {
                ds = new DatagramSocket(listenPort);
                while (!done) {
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
                    System.out.println("UDPProvider received from ip: " + ip + "\t port: " + port + "\t info " + info + "\t dataLength: " + dataLen);

                    String sn = MessageCreator.parseSn(info);
                    if (sn != null) {
                        Devices devices = new Devices(sn, ip, port);
                        devicesList.add(devices);
                    }


                }
            } catch (Exception ignored) {

            } finally {
                close();
            }
            System.out.println("UDPSearcher Listen finished....");
        }


        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Devices> getDevicesAndClose() {
            done = true;
            close();
            return devicesList;
        }
    }

}
