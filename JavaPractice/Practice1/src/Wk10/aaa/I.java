package ex.Wk10.aaa;

public interface I extends S{   //interface 끼리 상속 가능
    public abstract void f();
}
/*
abstract class D implements I {

}

class E extends D {
    @Override
    public void f(){    }
}
 */

class C implements I, I2 {  //interface 여러개 구현 가능
    @Override
    public void f() {} //I와 I2 모두 f()가 존재한다. f는 전부 추상 클래스이므로
                        //이 코드가 I와 I2의 f()를 둘 다 구현한다.
    @Override
    public void f2() {}
    @Override
    public void fs() {}
}