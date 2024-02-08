package com.notice.common.enums;

public enum MessageActionEnum {

    //定义消息类型

    LOGIN_MSG(1,"登录"),
    DOWNLOAD_MSG(2,"下载消息推送"),
    INFORMATION_MSG(3,"信息通知"),
    ONLINE_MSG(4,"在线会话"),

    KEEPALIVE(5,"客户端保持心跳");

    public final Integer type;
    public final String content;
    MessageActionEnum(Integer type,String content) {
        this.type = type;
        this.content = content;
    }
}

