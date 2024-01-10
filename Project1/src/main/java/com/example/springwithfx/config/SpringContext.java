package com.example.springwithfx.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SpringContext {

    @Autowired
    private ApplicationContext applicationContext;

}