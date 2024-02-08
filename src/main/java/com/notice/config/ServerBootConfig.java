package com.notice.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 自动启动配置类
 */
@Component
@Slf4j
public class ServerBootConfig {

    @Autowired
    ServerBootstrap serverBootstrap;

    @Resource
    NioEventLoopGroup boosGroup;

    @Resource
    NioEventLoopGroup workerGroup;

    @Value("${netty.port}")
    private Integer nettyPort;

    @Value("${netty.portSalve}")
    private Integer nettyPortSalve;



    /**
     * 开机启动
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {
        // 绑定端口启动
        serverBootstrap.bind(nettyPort).sync();
        serverBootstrap.bind(nettyPortSalve).sync();
        log.info("启动Netty多端口服务器: {},{}",nettyPort,nettyPortSalve);
    }

    /**
     * 关闭线程池
     */
    @PreDestroy
    public void close() throws InterruptedException {
        log.info("关闭Netty服务器");
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

