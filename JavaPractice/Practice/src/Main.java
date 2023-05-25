class Person {
    String name;

    void setName(String name) {
        this.name = name;
    }

    void introduce() {
        System.out.println("저는 " + name + "입니다.");
    }
}

class Programmer extends Person {
    void job() {
        System.out.println(this.name + "는 코딩하는 중");
    }

    @Override
    void introduce() {
        System.out.println("저는 " + "개발자 " + name + "입니다.");
    }
}

public class Main {
    public static void main(String[] args) {
        Programmer programmer = new Programmer();
        programmer.setName("자바천재");
        programmer.introduce();
        programmer.job();
    }
}