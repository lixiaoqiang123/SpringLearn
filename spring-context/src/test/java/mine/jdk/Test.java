package mine.jdk;


public class Test {

    public static void main(String[] args){
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Caculator proxy = CaculatorProxy.getProxy(new MyCalculator());
        int add = proxy.add(1, 1);
        System.out.println(proxy.getClass());
    }
}
