package test;

public class Z extends F {
    private int k = printInit("子类的K");

    Z() {
        System.out.println("子类构造");
    }

    static {
        System.out.println("子类静态块");
    }


    private static int m = printInit("子类的m");

    {
        System.out.println("子类普通块");
    }

    public static void main(String[] args) {
        System.out.println("main方法");
        Z z = new Z();
    }
}
