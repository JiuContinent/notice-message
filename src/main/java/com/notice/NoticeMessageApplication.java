package com.notice;

import com.notice.common.annotation.EnableNettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableNettyServer
@SpringBootApplication
public class NoticeMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticeMessageApplication.class, args);
    }

}
