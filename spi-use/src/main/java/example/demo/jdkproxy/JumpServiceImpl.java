package example.demo.jdkproxy;

import java.util.Date;

public class JumpServiceImpl implements JumpService {
    @Override
    public String jumpName(String name, int age) {
        return "name:[" + name + "]\tage:[" + age + "]";
    }

    @Override
    public void print(Date date) {
        System.out.println(date);
    }
}
