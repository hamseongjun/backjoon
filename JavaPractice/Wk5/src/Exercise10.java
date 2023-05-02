public class Exercise10 {
    public static void main(String[] args) {
        int[][] arr = new int[4][4];

        int x = 0;
        int y = 0;

        for(int i=0; i<10; i++) {
            x = (int)(Math.random() * 4);
            y = (int)(Math.random() * 4);

            if (arr[y][x] == 0)
                arr[y][x] = (int) (Math.random() * 10 + 1);
            else
                i--;

        }
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++)
                System.out.printf("%-3d", arr[i][j]);
            System.out.print("\n");
        }
    }
}