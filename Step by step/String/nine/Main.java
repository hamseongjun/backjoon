package nine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String a = scanner.next();
        String b = scanner.next();

        StringBuffer sb = new StringBuffer(a);
        sb = sb.reverse();
        a = sb.toString();

        sb = new StringBuffer(b);
        sb = sb.reverse();
        b = sb.toString();

        if (Integer.parseInt(a) > Integer.parseInt(b)) {
            System.out.print(a);
        } else
            System.out.print(b);
    }
}
