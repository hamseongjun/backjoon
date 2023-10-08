package four;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.next();

        StringBuffer sb = new StringBuffer(str);
        sb = sb.reverse();

        String reverseStr = sb.toString();

        if (str.equals(reverseStr))
            System.out.print(1);
        else
            System.out.print(0);
    }
}
