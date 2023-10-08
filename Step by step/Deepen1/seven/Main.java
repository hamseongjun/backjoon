package seven;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int count = 0;
        String str;

        for (int i = 0; i < n; i++) {
            str = scanner.next();
            boolean isGroup = true;
            // 여태까지 나타난 알파벳 저장하는 리스트
            ArrayList<String> exists = new ArrayList<>();
            for (int j = 0; j < str.length(); j++) {
                // 첫 번째 알파벳 무조건 저장
                if (j == 0) {
                    exists.add(str.substring(j, j + 1));
                    continue;
                }
                // 연속된 알파벳 스킵
                if (str.charAt(j) == str.charAt(j-1))
                    continue;
                // 떨어진 알파벳이면 그룹 단어 아님 -> break
                if (exists.contains(str.substring(j,j+1))) {
                    isGroup = false;
                    break;
                }
                // 새로운 알파벳이면 리스트에 추가
                else {
                    exists.add(str.substring(j, j + 1));
                }
            }
            // break 안 돼서 그룹 단어로 통과되면 count 증가 
            if (isGroup)
                count++;
        }
        System.out.print(count);
    }
}
