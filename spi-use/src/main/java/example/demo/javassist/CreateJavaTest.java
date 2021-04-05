package example.demo.javassist;

import com.alibaba.dubbo.common.compiler.support.JavassistCompiler;
import javassist.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * todo javassist在内存生成一个类去运行。dubbo通过javassist生成静态代理，jdk动态代理性能比较不好
 */
public class CreateJavaTest {
    public static void main(String[] args) throws Exception {
//        javassist_1();
        javassist_2();
    }

    private static void javassist_2() throws Exception {
        JavassistCompiler compiler = new JavassistCompiler();

        //(1)第一个参数为一个类组成的字符串
        //(2)第二个参数为一个加载器
        //直接通过compiler方法就可以获取字符串对应的类的class类型
        Class<?> clazz = compiler.compile(
                "public class DemoImpl implements DemoService {     " +
                        "public String sayHello(String name) {" +
                        "System.out.println(\"hello \" + name);     " +
                        "return \"Hello, \" + name ;" +
                        "}}",
                Thread.currentThread().getContextClassLoader());

        //通过class类型创建实例对象
        Object obj = clazz.newInstance();
        //反射 执行方法sayHello
        obj.getClass().getMethod("sayHello", new Class[]{String.class}).invoke(obj, "yoyo");
    }

    private static void javassist_1() throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        //class
        CtClass ctClass = classPool.makeClass("example.demo.JavassistDemo");
        CtField nameField = new CtField(classPool.getCtClass("java.lang.String"), "name", ctClass);
        nameField.setModifiers(Modifier.PRIVATE);
        //filed-name
        ctClass.addField(nameField);
        //filed-age
        CtField ageField = new CtField(classPool.getCtClass("int"), "age", ctClass);
        ageField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ageField);
        //setter getter
        ctClass.addMethod(CtNewMethod.setter("setAge", ageField));
        ctClass.addMethod(CtNewMethod.getter("getAge", ageField));
        ctClass.addMethod(CtNewMethod.setter("setName", nameField));
        ctClass.addMethod(CtNewMethod.getter("getName", nameField));

        //sayHello方法
        CtMethod sayHello = new CtMethod(CtClass.voidType, "sayHello", new CtClass[]{}, ctClass);
        sayHello.setModifiers(Modifier.PUBLIC);
        //方法体
        sayHello.setBody("{\nSystem.out.println(\"javassist生产的class文件\");\n}");
        ctClass.addMethod(sayHello);
        //将javassist动态在内存生成一个类，后面就是反射调用了
        Class aClass = ctClass.toClass();
        Object instance = aClass.newInstance();
        System.out.println(instance);
        aClass.getMethod("sayHello").invoke(instance);
    }
}

