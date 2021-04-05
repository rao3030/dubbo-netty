package example.demo.controller;

import example.demo.entity.ConsumerDetails;
import example.demo.service.CallProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class CallProviderController {
    @Autowired
    private CallProviderService callProviderService;

    //  http://localhost:9999/getDetails?name=wa&age=18
    @RequestMapping("/getDetails")
    public Object getDetails(ConsumerDetails details) {
        return callProviderService.getProviderDetails(details);
    }

    //  http://localhost:9999/async?name=wahaha
    @RequestMapping("/async")
    public Object async(@RequestParam String name) {
        return callProviderService.async(name);
    }

    //  http://localhost:9999/callback/3?name=wahaha      <5 fail ; >5 succeed
    @RequestMapping("/callback/{number}")
    public Object callback(@RequestParam String name, @PathVariable("number") int number) {
        return callProviderService.callbackMethodTest(new Date(), number) + name;
    }

    //  http://localhost:9999/unknow/wahaha
    @RequestMapping("/unknow/{name}")
    public Object callback(@PathVariable String name) {
        return callProviderService.unKnow(name);
    }
}
