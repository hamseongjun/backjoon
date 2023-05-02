package ex;

class A {
    void ff() {
        System.out.println("김태준");
    }

    static void tt() {
        System.out.println("남광운");

    }

}

class B extends A {
    void ff() {
        System.out.println("신정은");
    }

    void ff(int a) {
        System.out.println("오세환");
    }

    static void tt() {
        System.out.println("이도현");

    }
}

public class C {
    public static void main(String[] args) {
        B b = new B();
        b.ff(5);
        A a = new B();
        a.ff();
        b.ff();
        a.tt();
        b.tt();
    }
}
