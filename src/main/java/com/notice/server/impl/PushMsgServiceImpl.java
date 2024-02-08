package com.notice.server.impl;

import com.alibaba.fastjson2.JSON;
import com.notice.json.ChatMsg;
import com.notice.json.DataContent;
import com.notice.pool.UserConnectPool;
import com.notice.server.PushMsgService;
import com.notice.common.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PushMsgServiceImpl implements PushMsgService {

    @Override
    public void pushMsgToOne(ChatMsg chatMsg) {
        Channel channel = UserConnectPool.getChannel(chatMsg.getTokenId());
        if (Objects.isNull(channel)) {
            throw new RuntimeException("未连接socket服务器");
        }

        channel.writeAndFlush(
                new TextWebSocketFrame(
                        JSON.toJSONString(chatMsg)
                )
        );
    }

    @Override
    public void pushMsgToAll(String msg) {
        UserConnectPool.getChannelGroup().writeAndFlush(
                new TextWebSocketFrame(
                        JsonUtils.objectToJson(msg)
                )
        );
    }
}
