package example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ConsumerDetails implements Serializable {
    private String name;
    private Date date;
    private int age;
}
