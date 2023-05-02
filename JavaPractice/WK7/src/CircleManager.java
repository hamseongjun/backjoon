import java.util.Scanner;

class Circle {
    private double x, y;
    private int radius;

    public Circle(double x, double y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void show() {
        System.out.println("(" + x + "," + y + ")" + radius);
    }

    public int getRadius() {
        return radius;
    }
}

public class CircleManager {
    static Circle largest(Circle c[]) {
        Circle max = null;
        for (int i = 0; i < c.length - 1; i++) {
            if (c[i].getRadius() > c[i + 1].getRadius())
                max = c[i];
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Circle c[] = new Circle[3];
        for (int i = 0; i < c.length; i++) {
            System.out.print("x, y, radius >>");
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            int radius = scanner.nextInt();
            c[i] = new Circle(x, y, radius);
        }
        System.out.print("가장 면적이 큰 원은 ");
        CircleManager.largest(c).show();
    }
    /* 5번
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Circle c[] = new Circle[3];
        for (int i = 0; i < c.length; i++) {
            System.out.print("x, y, radius >>");
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            int radius = scanner.nextInt();
            c[i] = new Circle(x, y, radius);
        }
        for (int i = 0; i < c.length; i++) c[i].show();
        scanner.close();
    }
     */
}
