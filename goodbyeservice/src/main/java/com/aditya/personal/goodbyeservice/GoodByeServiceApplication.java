package com.aditya.personal.goodbyeservice;

import io.ap4k.kubernetes.annotation.ImagePullPolicy;
import io.ap4k.kubernetes.annotation.KubernetesApplication;
import io.ap4k.kubernetes.annotation.ServiceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@KubernetesApplication(imagePullPolicy = ImagePullPolicy.Always, serviceType = ServiceType.ClusterIP,
        name = "bye-service", group = "personalCarrot", version = "latest")
public class GoodByeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodByeServiceApplication.class, args);
    }

}
