package mine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Computer extends Product{

    @Autowired
    private Phone phone;

    public void test(){
        System.out.println("computer aop------");
    }

//    public Computer() {
//        System.out.println("生产的是电脑");
//    }
//
//    @PostConstruct
//    public void init(){
//        System.out.println("init......");
//    }
//
//    @PreDestroy
//    public void destory(){
//        System.out.println("destory....");
//    }
}
