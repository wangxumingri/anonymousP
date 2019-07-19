package test;

public class Fu {

    private int a;

    private static int b = 2;

    static {
        System.out.println("父类第一个静态块");
    }

    private static int s;

    {
        System.out.println("父类第一个普通块");
        s = test("初始化父类的s");
    }

    private int aa = 33;

    public Fu() {
        System.out.println("父类构造方法");
        System.out.println("a=" + a + ";b=" + b + ";s=" + s);
    }

    public static int test(String str) {
        System.out.println(str);

        return 22;
    }
}
