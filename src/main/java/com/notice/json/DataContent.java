package com.notice.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataContent implements Serializable {

    /**
     * 1. 登录 2. 下载消息推送 3. 信息通知 4. 在线会话
     */
    private Integer action;

    private ChatMsg chatMsg;

    private String extend;
}

