package com.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();

        socket.connect(new InetSocketAddress("localhost", 8089));

        System.out.println("客户端已进行连接--> ");

        todo(socket);
    }

    private static void todo(Socket socket) throws IOException {
        InputStream in = System.in;

        // 从键盘读取
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));


        OutputStream outputStream = socket.getOutputStream();
        // 利用打印流输出到服务器端
        PrintStream printStream = new PrintStream(outputStream);


        InputStream inputStream = socket.getInputStream();
        BufferedReader echo = new BufferedReader(new InputStreamReader(inputStream));


        boolean flag = true;

        do {
            // 从键盘读取一行
            String str = bufferedReader.readLine();
            // 发送到服务器端
            printStream.println(str);

            // 从服务器端发送来的数据, 读取一行
            String echoStr = echo.readLine();
            if ("bye".equalsIgnoreCase(str)) {
                flag = false;
                System.out.println("客户端断开连接");
            } else {
                System.out.println(echoStr);
            }

        } while (flag);

        printStream.close();
        echo.close();
    }
}
