package com.notice.pool;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户链接池
 */
public class UserConnectPool {

    //广播组消息
    private static volatile ChannelGroup channelGroup = null;

    /**
     * 存放请求ID与channel的对应关系
     */
    private static volatile ConcurrentHashMap<String, Channel> channelMap = null;



    /**
     * 定义两把锁
     */
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();


    /**
     * 保证ChannelGroup只被创建一次
     * @return
     */
    public static ChannelGroup getChannelGroup() {
        if (null == channelGroup) {
            synchronized (lock1) {
                if (null == channelGroup) {
                    channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                }
            }
        }
        return channelGroup;
    }

    public static ConcurrentHashMap<String, Channel> getChannelMap() {
        if (null == channelMap) {
            synchronized (lock2) {
                if (null == channelMap) {
                    channelMap = new ConcurrentHashMap<>();
                }
            }
        }
        return channelMap;
    }

    public static Channel getChannel(String userId) {
        if (null == channelMap) {
            return getChannelMap().get(userId);
        }
        return channelMap.get(userId);
    }

}

