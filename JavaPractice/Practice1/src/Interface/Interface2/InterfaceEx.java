package ex.Interface.Interface2;

interface Interface {
    void method();
    default void newDefaultMethod() { System.out.println("newDefaultMethod() in Interface"); }
}
interface AnotherInterface {
    default void newDefaultMethod() { System.out.println("newDefaultMethod() in AnotherInterface"); }
}
//class Cls implements Interface, AnotherInterface {
//    @Override
//    public void method() { System.out.println("method() in Cls"); }
//}
//public class InterfaceEx {
//    public static void main(String[] args) {
//        Cls obj = new Cls();
//        obj.method();
//        obj.newDefaultMethod();
//        Interface obj2 = new Cls();
//        obj2.method();
//        obj2.newDefaultMethod();
//        AnotherInterface obj3 = new Cls();
////obj3.method();
//        obj3.newDefaultMethod();
//    }
//}