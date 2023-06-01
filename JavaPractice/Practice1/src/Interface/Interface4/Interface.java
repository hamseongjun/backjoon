package ex.Interface.Interface4;

interface Interface {
    void method();
    //private void privateAbstractMethod();
    //private default void privateDefaultMethod() {}
    default void defaultMethod1() {
        privateMethod1();
    }
    default void defaultMethod2() {
        System.out.println("Default Method in Interface");
    }
    private void privateMethod1() {
        System.out.println("Private Method1 in Interface");
        privateMethod2();
        defaultMethod2();
        method();   // 추상메소드지만 컴파일 에러 안 남. 
    }
    private void privateMethod2() {
        System.out.println("Private Method2 in Interface");
    }
}