package zovl.zhongguanhua.jni.demo.jni;

public class Person {

    private String name;

    private int age;

    private boolean isMan;

    public Person(String name, int age, boolean isMan) {
        this.name = name;
        this.age = age;
        this.isMan = isMan;
    }

    public static native String getResponse(String name, int age, boolean isMan);
}
