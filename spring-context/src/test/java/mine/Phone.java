package mine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Phone extends Product{

    @Autowired
    private Computer computer;

    public void log(){
        System.out.println("Phone 切面加载");
    }

//    public Phone(Computer computer) {
//        this.computer = computer;
//    }

//    @Autowired
//    private Core core;
//
//    @Value("123")
//    private String name;
//
//    public Phone() {
//        System.out.println("生产的是手机2");
//    }
//
//    public Phone(String name) {
//        System.out.println("生产的是手机");
//    }
//
//    public String getName() {
//        System.out.println("123123123123");
//        return name;
//    }
//
//    @PostConstruct
//    public void testPostConstruct(){
//        System.out.println("testPostConstruct");
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
