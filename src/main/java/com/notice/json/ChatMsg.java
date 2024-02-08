package com.notice.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg implements Serializable {

    /**
     * 1. 登录 2. 下载消息推送 3. 信息通知 4. 在线会话
     */
    private String tokenId;

    private Integer action;

    private String msg;

}

