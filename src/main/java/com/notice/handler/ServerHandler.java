package com.notice.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerHandler extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道以及配置对应管道的处理器
     * @param channel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new IdleStateHandler(0,0,12*60*60));
        pipeline.addLast(new HeartBeatHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
        //自定义的handler
        pipeline.addLast(new ServerListenerHandler());


    }
}

