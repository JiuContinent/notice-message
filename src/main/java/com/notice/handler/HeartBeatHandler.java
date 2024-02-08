package com.notice.handler;

import com.notice.pool.UserConnectPool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;

            if (event.state() == IdleState.ALL_IDLE) {
                // 12 小时清除一次会话
                System.out.println("客户端长时间未发送消息，关闭通道：" + ctx.channel());
                UserConnectPool.getChannelGroup().remove(ctx.channel());
            }
        }
    }


}

