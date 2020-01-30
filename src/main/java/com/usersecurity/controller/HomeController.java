package com.usersecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/")
    public String index(){
        return "Hello world";
    }

    @GetMapping(value = "/private")
    public String privateArea(){
        return "Private area";
    }

    @GetMapping(value = "/cia")
    public String ciaArea(){
        return "CIA Private area";
    }

    @GetMapping(value = "/fbi")
    public String fbiArea(){
        return "FBI Private area";
    }
}

