package com.kodzotech.fileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FileUploadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadServiceApplication.class, args);
    }

}
