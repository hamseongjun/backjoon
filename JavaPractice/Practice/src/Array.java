public class Array {
    static int [] ccc() {
        int bbb[] = {0, 3, 2};
        return bbb;
    }

    public static void main(String[] args) {
        int k[];
        k = ccc();
        k[1] = 5;
        int b[];
        b = ccc();
        System.out.println(b[1]);
    }
}
