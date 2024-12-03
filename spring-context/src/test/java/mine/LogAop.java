package mine;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;

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


public class LogAop {


    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        Object[] args = pjp.getArgs();
        Object result = null;
        try {
            System.out.println("around---环绕通知start："+signature.getName()+"方法开始执行，参数为："+Arrays.asList(args));
            result = pjp.proceed(args);
            System.out.println("around---环绕通知stop"+signature.getName()+"方法执行结束");
        } catch (Throwable throwable) {
            System.out.println("around---环绕异常通知："+signature.getName()+"出现异常");
            throw throwable;
        }finally {
            System.out.println("around---环绕返回通知："+signature.getName()+"方法返回结果是："+result);
        }
        return result;
    }

    private int before(JoinPoint joinPoint){
        //获取方法签名
        Signature signature = joinPoint.getSignature();
        //获取参数信息
        Object[] args = joinPoint.getArgs();
        System.out.println("before---"+signature.getName()+"方法开始执行：参数是"+Arrays.asList(args));
        return 100;
    }

    public static void after(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        System.out.println("after---"+signature.getName()+"方法执行结束。。。。。over");
    }


    public static void afterReturning(JoinPoint joinPoint,Object result){
        Signature signature = joinPoint.getSignature();
        System.out.println("afterReturning---"+signature.getName()+"方法执行结束，结果是："+result);
    }

    public static void afterthrowing(JoinPoint joinPoint,Exception e){
        Signature signature = joinPoint.getSignature();
        System.out.println("afterthrowing---"+signature.getName()+"方法抛出异常："+e.getMessage());
    }
}
