package mine;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class LogAop {

    @Before("execution(* mine.Phone.*(..))")
    public void before(JoinPoint jp) {
        String name = jp.getSignature().getName();
        System.out.println(name+" 切面Before方法开始执行了...");
    }

    @After("execution(* mine.Phone.*(..))")
    public void after(JoinPoint jp) {
        String name = jp.getSignature().getName();
        System.out.println(name + " 切面After方法开始执行了...");
    }
}
