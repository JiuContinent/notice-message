package com.notice.handler;

import com.alibaba.fastjson2.JSON;
import com.notice.common.enums.MessageActionEnum;
import com.notice.common.utils.JsonUtils;
import com.notice.json.ChatMsg;
import com.notice.json.DataContent;
import com.notice.pool.UserConnectPool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerListenerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 当建立链接时将Channel放置在Group当中
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("有新的客户端链接：[{}]", ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        UserConnectPool.getChannelGroup().add(ctx.channel());
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("收到消息"+msg.text());
        String content = msg.text();
        ChatMsg chatMsg = JSON.parseObject(content, ChatMsg.class);
        assert chatMsg != null;
        Integer action = chatMsg.getAction();
        if(Objects.equals(action, MessageActionEnum.LOGIN_MSG.type)){
            // 登录 进行关联注册
            this.websocketLogin(chatMsg,ctx);
        }else if(Objects.equals(action, MessageActionEnum.DOWNLOAD_MSG.type)){
            //发送消息
            Channel receiverChannel = UserConnectPool.getChannel(chatMsg.getTokenId());
            if(receiverChannel==null){
                //用户不在线
            }else {
                //为了保险起见你还可以在你的Group里面去查看有没有这样的Channel
                //毕竟不太能够保证原子性操作嘛，反正底层也是CurrentMap
                Channel findChannel = UserConnectPool.getChannelGroup().find(ctx.channel().id());
                if(findChannel!=null){
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(JsonUtils.objectToJson(chatMsg))
                    );
                }else {
                    //离线
                }
            }

        }else if (Objects.equals(action, MessageActionEnum.INFORMATION_MSG.type)){

        }else if (Objects.equals(action, MessageActionEnum.ONLINE_MSG.type)){

        }else if (Objects.equals(action, MessageActionEnum.KEEPALIVE)){
            System.out.println("收到来自channel 为["+ctx.channel()+"]的心跳包");
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("用户下线了:{}", ctx.channel().id().asLongText());
        // 删除通道
        UserConnectPool.getChannelGroup().remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常
        log.info("异常：{}", cause);
        // 删除通道
        UserConnectPool.getChannelGroup().remove(ctx.channel());
    }

    private void websocketLogin(ChatMsg chatMsg,ChannelHandlerContext ctx) {
        String senderId = chatMsg.getTokenId();

        UserConnectPool.getChannelMap().put(senderId, ctx.channel());
        // 将用户token或ID作为自定义属性加入到channel中，方便随时channel中获取
        AttributeKey<String> key = AttributeKey.valueOf("tokenId");
        ctx.channel().attr(key).setIfAbsent(senderId);
    }


}
