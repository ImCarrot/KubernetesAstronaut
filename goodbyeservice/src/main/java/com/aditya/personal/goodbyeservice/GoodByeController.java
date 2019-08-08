package com.aditya.personal.goodbyeservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodByeController {

    @GetMapping("/sayBye/{name}")
    public ResponseEntity<String> sayBye(@PathVariable("name") String name) {
        return ResponseEntity.ok(String.format("Bye %s", name));
    }

}
