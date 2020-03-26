package com.cst.springcloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MainAppConfigCenter3345 {

    public static void main(String[] args) {
        SpringApplication.run(MainAppConfigCenter3345.class,args);
    }
}
