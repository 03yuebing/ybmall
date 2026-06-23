package com.yuebing.ybmall.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductPingController {

    @GetMapping("/product/ping")
    public String Ping(){
        return "ybmall-product is running";
    }
}
