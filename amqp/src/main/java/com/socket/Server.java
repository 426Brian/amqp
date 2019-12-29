package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8089);

        System.out.println("服务器端等待连接");

        for (; ; ) {
            Socket socket = serverSocket.accept();

            // 异步线程
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }


    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("新客户端连接："+socket.getInetAddress()+" port: "+socket.getPort());
            
            try{
                // 向客户端发送数据
                PrintStream socketOut = new PrintStream(socket.getOutputStream());

                // 从客户端读取数据
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                do{
                    String str = socketIn.readLine();

                    if ("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        socketOut.println("bye");
                    } else {
                        System.out.println(str);
                        socketOut.println("回送 "+str.length());
                    }
                }while (flag);

                socketIn.close();
                socketOut.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("客户端已经退出 "+socket.getInetAddress()+" port: "+socket.getPort());
            }
        }
    }
}
