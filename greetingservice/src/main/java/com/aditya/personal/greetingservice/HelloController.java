package com.aditya.personal.greetingservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    private final RestTemplate restTemplate;

    public HelloController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/sayHello/{name}")
    public ResponseEntity<String> sayHello(@PathVariable("name") String name) {

        String goodbyeKeyword = getByeKeyword(name);

        return ResponseEntity.ok(String.format("Hello %s and %s", name, goodbyeKeyword));
    }

    private String getByeKeyword(String name) {

        ResponseEntity<String> response = restTemplate.getForEntity("http://bye-service/sayBye/" + name, String.class);

        if (response.getStatusCode() != HttpStatus.OK)
            return "Error for GoodBye";

        return response.getBody();
    }
}
