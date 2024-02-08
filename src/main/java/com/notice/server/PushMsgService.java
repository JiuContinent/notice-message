package com.notice.server;

import com.notice.json.ChatMsg;
import com.notice.json.DataContent;

/**
 * @Author JiuContinent
 * @create 2024/2/5 15:39
 */
public interface PushMsgService {

    void pushMsgToOne(ChatMsg chatMsg);

    void pushMsgToAll(String msg);
}
