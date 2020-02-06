package com.example.posterservice.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
}
