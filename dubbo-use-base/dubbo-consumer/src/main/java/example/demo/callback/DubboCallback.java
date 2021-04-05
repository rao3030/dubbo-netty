package example.demo.callback;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * todo 异步不等待结果的使用callback比较好一点，要不然什么也不知道。同步用这个意义就不是很大了
 */
@Component
public class DubboCallback {
    /**
     * @param result rpc调用成功的结果，
     * @param arg1   rpc调用接口的实参
     */
    public void onSuccess(String result, Date arg1, int arg2) {
        System.out.println("rpc调用的succeed--" + result + "方法的实参" + arg1 + " ; " + arg2);
    }

    /**
     * @param error rpc调用失败的异常，
     * @param arg1  rpc调用接口的实参
     */
    public void onError(Throwable error, Date arg1, int arg2) {
        System.out.println("rpc调用的fail的Exception--" + error.getMessage() + "方法的实参" + arg1);
    }
}
