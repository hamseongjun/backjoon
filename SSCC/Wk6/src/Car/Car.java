package Car;

public class Car {
    protected int speed = 0;
    protected String name;
    protected int score = 0;

    public Car(String name, int speed) {
        this.speed = speed;
        this.name = name;
    }

    void go() {
        score += speed;
    }

    void printInfo() {
        System.out.println("스피드는" + speed + "이고, 이름은 " + name + "입니다.");
    }

    void sayScore() {
        System.out.println("Score: " + score);
    }
}