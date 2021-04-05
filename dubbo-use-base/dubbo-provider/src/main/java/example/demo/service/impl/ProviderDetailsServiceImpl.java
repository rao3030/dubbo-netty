package example.demo.service.impl;

import example.demo.entity.ConsumerDetails;
import example.demo.entity.ProviderDetails;
import example.demo.service.ProviderDetailsService;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//@Service
//@com.alibaba.dubbo.config.annotation.Service
public class ProviderDetailsServiceImpl implements ProviderDetailsService {
    @Override
    public ProviderDetails returnProviderDetails(ConsumerDetails consumerDetails) {
        ProviderDetails details = new ProviderDetails();
        details.setServiceName("producer---" + consumerDetails.toString());
        /*try {
            System.out.println("--------ProviderDetailsServiceImpl------------");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return details;
    }

    @Override
    public ProviderDetails returnASync(String name) {
        ProviderDetails details = new ProviderDetails();
        details.setServiceName(name);
        return details;
    }

    @Override
    public String callbackMethod(Date date, int number) {
        if (number < 5) {
            throw new RuntimeException("模拟业务的异常");
        }
        return date.toString();
    }
}
