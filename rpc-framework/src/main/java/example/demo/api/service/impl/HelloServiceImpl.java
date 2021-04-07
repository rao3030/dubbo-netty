package example.demo.api.service.impl;

import example.demo.api.service.HelloService;

import java.util.Date;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name, Date date) {
        return name + " talk " + date;
    }
}
