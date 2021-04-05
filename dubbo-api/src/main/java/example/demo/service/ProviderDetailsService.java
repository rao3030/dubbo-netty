package example.demo.service;

import example.demo.entity.ConsumerDetails;
import example.demo.entity.ProviderDetails;

import java.util.Date;

public interface ProviderDetailsService {
    ProviderDetails returnProviderDetails(ConsumerDetails consumerDetails);

    /*异步调用，看成java的Future模式*/
    ProviderDetails returnASync(String name);

    String callbackMethod(Date date,int number);
}
