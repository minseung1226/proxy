package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        log.info("start");
        String result1 = target.callA();
        log.info("result={}",result1);

        log.info("start");
        String result2 = target.callB();
        log.info("result={}",result2);
    }

    @Test
    void reflect1() throws  Exception {
        //클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target= new Hello();

        // 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);

        log.info("result1={}",result1);

        // 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallA.invoke(target);

        log.info("result2={}",result2);

    }

    @Test
    void reflect2() throws  Exception {
        //클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target= new Hello();

        // 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA,target);

        // 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB,target);

    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");

        Object result = method.invoke(target);
        log.info("result={}",result);
    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("call A");
            return "A";
        }
        public String callB(){
            log.info("call B");
            return "B";
        }
    }
}
