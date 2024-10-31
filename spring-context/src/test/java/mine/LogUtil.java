package mine;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 1:before
 * 2:环绕通知 before
 * 3:程序代码
 * 4:after-returning
 * 5:环绕通知 after
 * 6:环绕通知 finally
 * 7:after
 *
 * Around->Before->businessFun->Around后续->After->AfterReturing/AfterThrowing
 */

//@Aspect
//@Component
public class LogUtil {

    @Pointcut("execution(* main.Phone.*())")
    public void myPointCut(){}

    //    @Pointcut("execution(* *(..))")
    public void myPointCut1(){}

    @Around("myPointCut()")
//    @Order(4)
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        Object[] args = pjp.getArgs();
        Object result = null;
        try {
            System.out.println("around---环绕通知start："+signature.getName()+"方法开始执行，参数为："+Arrays.asList(args));
            //通过反射的方式调用目标的方法，相当于执行method.invoke(),可以自己修改结果值
            result = pjp.proceed(args);
//            result=100;
            System.out.println("around---环绕通知stop"+signature.getName()+"方法执行结束");
        } catch (Throwable throwable) {
            System.out.println("around---环绕异常通知："+signature.getName()+"出现异常");
            throw throwable;
        }finally {
            System.out.println("around---环绕返回通知："+signature.getName()+"方法返回结果是："+result);
        }
        return result;
    }

    @Before(value = "myPointCut()")
//    @Order(5)
    private int before(JoinPoint joinPoint){
        //获取方法签名
        Signature signature = joinPoint.getSignature();
        //获取参数信息
        Object[] args = joinPoint.getArgs();
        System.out.println("before---"+signature.getName()+"方法开始执行：参数是"+Arrays.asList(args));
        return 100;
    }

    @After("myPointCut()")
//    @Order(3)
    public static void after(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        System.out.println("after---"+signature.getName()+"方法执行结束。。。。。over");
    }


    @AfterReturning(value = "myPointCut()",returning = "result")
//    @Order(2)
    public static void afterReturning(JoinPoint joinPoint,Object result){
        Signature signature = joinPoint.getSignature();
        System.out.println("afterReturning---"+signature.getName()+"方法执行结束，结果是："+result);
    }

    @AfterThrowing(value = "myPointCut()",throwing = "e")
//    @Order(1)
    public static void afterthrowing(JoinPoint joinPoint,Exception e){
        Signature signature = joinPoint.getSignature();
        System.out.println("afterthrowing---"+signature.getName()+"方法抛出异常："+e.getMessage());
    }
}
