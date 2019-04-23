package se.innerclass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 成员内部类：
 *      外部类：汽车
 *      内部类：轮胎
 */

public class MemberInnerClass {
    private String name;

    private int price;

    private String str1 = "外部类的私有成员";

    public  String str2 = "外部类的公共成员";

    public MemberInnerClass() {
    }

    public MemberInnerClass(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void printInfo(String name){
        System.out.println(name+"汽车");
        new Wheel("擎天柱",100).printInfo("s",1);
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    /**
     * 成员内部类：轮胎类
     */
    class Wheel{
        private String name;

        private int size;

        public Wheel() {
        }

        public Wheel(String name, int size) {
            this.name = name;
            this.size = size;
        }

        void printInfo(String name,int size){
            System.out.println(name+"直径为"+size+"cm的"+name); // 方法局部变量
            System.out.println(this.name); // wheel 的成员变量
            System.out.println(name);
            System.out.println(MemberInnerClass.this.name); // 外部类的成员变量
        }

        public String getname() {
            return name;
        }

        public void setname(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

}
