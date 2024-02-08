package com.notice.config;

import com.notice.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * netty 配置
 */
@Configuration
@EnableConfigurationProperties
public class NettyConfig {

    @Value("${netty.boss}")
    private Integer nettyBoss;

    @Value("${netty.worker}")
    private Integer nettyWorker;

    @Value("${netty.timeout}")
    private Integer nettyTimeout;

    /**
     * boss 线程池
     * 负责客户端连接
     * @return
     */
    @Bean
    public NioEventLoopGroup boosGroup(){
        return new NioEventLoopGroup(nettyBoss);
    }

    /**
     * worker线程池
     * 负责业务处理
     * @return
     */
    @Bean
    public NioEventLoopGroup workerGroup(){
        return  new NioEventLoopGroup(nettyWorker);
    }
    /**
     * 服务器启动器
     * @return
     */
    @Bean
    public ServerBootstrap serverBootstrap(){
        ServerBootstrap serverBootstrap  = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup(),workerGroup())   // 指定使用的线程组
                .channel(NioServerSocketChannel.class) // 指定使用的通道
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,nettyTimeout) // 指定连接超时时间
                .childHandler(new ServerHandler()); // 指定worker处理器
        return serverBootstrap;
    }
}

