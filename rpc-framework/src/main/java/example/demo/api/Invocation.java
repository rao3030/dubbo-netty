package example.demo.api;

import lombok.Data;

import java.io.Serializable;

@Data
@SuppressWarnings("all")
public class Invocation implements Serializable {
    private String interfaceName;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;
}
