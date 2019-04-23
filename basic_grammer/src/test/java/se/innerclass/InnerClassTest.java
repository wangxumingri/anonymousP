package se.innerclass;

import org.junit.Test;

public class InnerClassTest {

    @Test
    public void testMemberInner(){
        // 创建外部类实例
        MemberInnerClass memberInnerClass = new MemberInnerClass("大黄蜂",100);
        memberInnerClass.printInfo("ss");

        // 创建内部类实例
        MemberInnerClass.Wheel wheel = new MemberInnerClass().new Wheel();
        MemberInnerClass.Wheel wheel2 = memberInnerClass.new Wheel();

        wheel.printInfo("防滑轮胎",80);

        wheel2.printInfo("大黄蜂的防滑轮胎",100);

    }
}
