package com.notice.controller;

import com.notice.json.ChatMsg;
import com.notice.json.DataContent;
import com.notice.pool.UserConnectPool;
import com.notice.server.PushMsgService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/push")
public class NettyMsgController {

    @Autowired
    PushMsgService pushMsgService;

    @PostMapping("/pushOne")
    public void pushOne(@RequestBody ChatMsg chatMsg){
        pushMsgService.pushMsgToOne(chatMsg);
    }

    /**
     * 群发
     * @param msg
     */
    @GetMapping("/pushAll")
    public void pushAll(@RequestParam String msg){
        pushMsgService.pushMsgToAll(msg);
    }

    /**
     * 获取当前在线的客户端列表
     *
     * @return
     */
    @GetMapping("/clientList")
    public Map<String, Channel> clientList() {
        return UserConnectPool.getChannelMap();
    }

    /**
     * 获取当前在线组的客户端
     *
     * @return
     */
    @GetMapping("/clientGrpupList")
    public ChannelGroup clientGrpupList() {
        return UserConnectPool.getChannelGroup();
    }
}

