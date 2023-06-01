package ex.Interface.Interface1;

interface Interface {
    void method();
    default void newDefaultMethod() { System.out.println("newDefaultMethod() in Interface"); }
}
class SuperCls {
    public void newDefaultMethod() { System.out.println("newDefaultMethod() in SuperCls"); }
}
class Cls extends SuperCls implements Interface {
    @Override
    public void method() { System.out.println("method() in Cls"); }
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