package example.demo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("classpath:dubbo-provider.xml")
//@PropertySource("classpath:dubbo.properties")
//@EnableDubbo(scanBasePackages = "example.demo.service.impl")
public class DubboProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApp.class);
    }
}
