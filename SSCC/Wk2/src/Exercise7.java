import java.util.ArrayList;
import java.util.Arrays;
public class Exercise7 {
    public static void main(String[] args) {
        ArrayList<String> myList = new ArrayList<>(Arrays.asList("Life", "is", "too", "short"));
        String myNewList = String.join(" ", myList);
        System.out.print(myNewList);
    }
}
