package com.team.youarelikemetoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class YouAreLikeMeTooApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouAreLikeMeTooApplication.class, args);
    }

}
