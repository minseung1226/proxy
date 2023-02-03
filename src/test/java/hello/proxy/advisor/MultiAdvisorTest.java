package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
@Slf4j
public class MultiAdvisorTest {

    @Test
    @DisplayName("여러 프록시")
    void multiAdvisorTest1(){
        // client -> proxy1 -> proxy2 -> target

        //프록시 1
        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory1 = new ProxyFactory(target);
        DefaultPointcutAdvisor defaultPointcutAdvisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        factory1.addAdvisor(defaultPointcutAdvisor1);
        ServiceInterface proxy1 =(ServiceInterface) factory1.getProxy();

        //프록시 2
        ProxyFactory factory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor defaultPointcutAdvisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        factory2.addAdvisor(defaultPointcutAdvisor2);
        ServiceInterface proxy2 =(ServiceInterface) factory2.getProxy();

        proxy2.save();
        proxy2.find();

    }
    @Test
    @DisplayName("하나의 프록시 여러 어드바이저")
    void multiAdvisorTest2(){
        // client -> proxy1 -> advisor2 ->advisor2 -> target

        //프록시 1

        DefaultPointcutAdvisor defaultPointcutAdvisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor defaultPointcutAdvisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());


        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.addAdvisor(defaultPointcutAdvisor2);
        factory.addAdvisor(defaultPointcutAdvisor1);

        ServiceInterface proxy =(ServiceInterface) factory.getProxy();


        proxy.save();
        proxy.find();

    }

    static class Advice1 implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice1 호출");
            return invocation.proceed();
        }
    }

    static class Advice2 implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice2 호출");
            return invocation.proceed();
        }
    }
}
