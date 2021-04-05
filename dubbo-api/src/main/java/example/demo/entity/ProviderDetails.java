package example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProviderDetails implements Serializable {
    private String serviceName;
    private Date date;
    private int age;
}
