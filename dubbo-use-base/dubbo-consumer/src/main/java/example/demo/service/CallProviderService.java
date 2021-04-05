package example.demo.service;

import example.demo.entity.ConsumerDetails;
import example.demo.entity.ProviderDetails;

import java.util.Date;

public interface CallProviderService {
    ProviderDetails getProviderDetails(ConsumerDetails consumerDetails);

    ProviderDetails async(String name);

    String callbackMethodTest(Date date,int number);

    String unKnow(String name);
}
