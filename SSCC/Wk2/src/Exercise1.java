public class Exercise1 {
    public static void main(String[] args) {
        int[] hong = {80, 75, 55};
        int average=0;
        for (int i=0; i<hong.length; i++)
            average += hong[i];
        average /= hong.length;
        System.out.print("평균은 "+average+"입니다.\n");
    }
}