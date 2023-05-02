import java.util.Scanner;

class Dictionary {
    private static String[] kor = {"사랑", "아기", "돈", "미래", "희망"};
    private static String[] eng = {"love", "baby", "money", "future", "hope"};

    public static String kor2Eng(String word) {
        for (int i = 0; i < kor.length; i++) {
            if (word.equals(kor[i]))
                return eng[i];
        }
        return null;
    }
}

public class DicApp {
    Scanner scanner = new Scanner(System.in);
    String word;

    public void run() {
        System.out.println("한영 단어 검색 프로그램입니다.");
        while (true) {
            System.out.print("한글 단어?");
            word = scanner.next();

            if (word.equals("그만"))
                break;

            String searchResult = Dictionary.kor2Eng(word);

            if (searchResult != null)
                System.out.println(word + "는 " + searchResult);
            else
                System.out.println(word + "는 저의 사전에 없습니다.");
        }
    }

    public static void main(String[] args) {
        DicApp dicApp = new DicApp();
        dicApp.run();
    }
}
