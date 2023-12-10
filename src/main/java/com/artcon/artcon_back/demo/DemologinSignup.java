package com.artcon.artcon_back.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemologinSignup {
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("This is me trying");
    }
}
