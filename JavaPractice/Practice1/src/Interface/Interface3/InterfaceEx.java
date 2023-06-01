package ex.Interface.Interface3;

interface Interface {
    void method();
    default void newDefaultMethod() { System.out.println("newDefaultMethod() in Interface"); }
}
abstract class AbstractClass {
    abstract void newDefaultMethod();
}
class Cls extends AbstractClass implements Interface {
    @Override
    public void method() { System.out.println("method() in Cls"); }
    @Override
    public void newDefaultMethod() { System.out.println("newDefaultMethod() in Cls"); } //이 문장 주서처리 하면 컴파일 에러남.
}
public class InterfaceEx {
    public static void main(String[] args) {
        Cls obj = new Cls();
        obj.method();
        obj.newDefaultMethod();
        Interface obj2 = new Cls();
        obj2.method();
        obj2.newDefaultMethod();
    }
}