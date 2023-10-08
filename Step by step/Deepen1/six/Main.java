package six;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();

        int len = str.length();
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) == 'c') {
                if (str.charAt(i + 1) == '=') {
                    len--;
                    i++;
                    continue;
                }
                if (str.charAt(i + 1) == '-') {
                    len--;
                    i++;
                    continue;
                }
            }
            if (str.charAt(i) == 'd') {
                if (str.charAt(i + 1) == 'z') {
                    if (i + 2 < str.length() && str.charAt(i + 2) == '=') {
                        len -= 2;
                        i += 2;
                        continue;
                    } else {
                        i++;
                        continue;
                    }
                }
                if (str.charAt(i + 1) == '-') {
                    len--;
                    i++;
                    continue;
                }
            }
            if (str.charAt(i) == 'l') {
                if (str.charAt(i + 1) == 'j') {
                    len--;
                    i++;
                    continue;
                }
            }
            if (str.charAt(i) == 'n') {
                if (str.charAt(i + 1) == 'j') {
                    len--;
                    i++;
                    continue;
                }
            }
            if (str.charAt(i) == 's') {
                if (str.charAt(i + 1) == '=') {
                    len--;
                    i++;
                    continue;
                }
            }
            if (str.charAt(i) == 'z') {
                if (str.charAt(i + 1) == '=') {
                    len--;
                    i++;
                }
            }
        }
        System.out.print(len);
    }
}
