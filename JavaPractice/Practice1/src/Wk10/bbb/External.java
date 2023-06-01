//package ex.Wk10.bbb;
//
//import ex.Wk10.aaa.I;
//import ex.Wk10.aaa.I2;
//import ex.Wk10.aaa.Is;
//import ex.Wk10.aaa.S;
//public class External {
//
//    void useI(I obj) {
//        obj.f();
//        obj.fs();
//        obj.f2();   //에러
//    }
//    void useI2(I2 obj) {
//        obj.fs();   //에러
//        obj.f();    //I2에 f()가 없었다면 에러
//        obj.f2();
//    }
//    void useS(S obj) {
//        obj.fs();
//        obj.f();    //에러
//        obj.f2();   //에러
//    }
//
//    void getI() {
//        I obj = Is.getInstance();
//        useI(obj);
//    }
//}
