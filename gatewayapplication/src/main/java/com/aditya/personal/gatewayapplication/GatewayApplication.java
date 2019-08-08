package com.aditya.personal.gatewayapplication;

import io.ap4k.kubernetes.annotation.ImagePullPolicy;
import io.ap4k.kubernetes.annotation.KubernetesApplication;
import io.ap4k.kubernetes.annotation.Probe;
import io.ap4k.kubernetes.annotation.ServiceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableDiscoveryClient
@KubernetesApplication(
        serviceType = ServiceType.NodePort, imagePullPolicy = ImagePullPolicy.IfNotPresent,
        name = "gateway-service", group = "personal", version = "latest",
        livenessProbe = @Probe(httpAction = "/actuator/health"),
        readinessProbe = @Probe(httpAction = "/actuator/health"))
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/sayHello/**").filters(f ->
                        f.hystrix(c -> c.setName("greeting-service")
                                .setFallbackUri("forward:/fallback"))).uri("lb://greeting-service"))

                .route(p -> p.path("/sayBye/**").filters(f ->
                        f.hystrix(c -> c.setName("bye-service")
                                .setFallbackUri("forward:/fallback"))).uri("lb://bye-service"))

                .build();
    }

    @GetMapping("/fallback")
    public ResponseEntity<String> fallback() {
        System.out.println("fallback enabled");
        HttpHeaders headers = new HttpHeaders();
        headers.add("fallback", "true");
        return ResponseEntity.ok().headers(headers).body("Fallback Triggered!");
    }

}
