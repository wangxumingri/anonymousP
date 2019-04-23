package test;

public class Zi extends Fu{

    private int a =  1;

    static {
        System.out.println("子类第一个静态块");
    }

    private static int b = test("初始化子类的b");

    public static void main(String[] args) {
        test("子类调用test");
        Zi zi = new Zi();
    }
}
