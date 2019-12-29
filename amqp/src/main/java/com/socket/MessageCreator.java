package com.socket;

public class MessageCreator {
    private static final String SN_HEAD = "我是SN";
    private static final String PORT_HEAD = "我是PORT";

    public static int parsePort(String data){
        if(data.startsWith(PORT_HEAD)){
            return Integer.parseInt(data.substring(PORT_HEAD.length()));
        }

        return -1;
    }

    public static String parseSn(String data){
        if(data.startsWith(SN_HEAD)){
            return data.substring(SN_HEAD.length());
        }

        return null;
    }

    public static String buildWithSn(String sn){
        return SN_HEAD+sn;
    }
    public static String buildWithPort(int port){
        return PORT_HEAD+port;
    }
}

