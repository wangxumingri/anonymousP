package test;

public class F {

    F(){
        System.out.println("父类构造");
    }
    private static int x1 = printInit("父类调用打印");

     static int printInit(String str) {
        System.out.println(str);
        return 2;
    }
}
