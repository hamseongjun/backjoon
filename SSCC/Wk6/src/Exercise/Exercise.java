package Exercise;

abstract class Animal {
    String name;

    void setName(String name) {
        this.name = name;
    }

    abstract String getFood();
}

class Tiger extends Animal {
    public String getFood() {
        return "apple";
    }
}

class  Lion extends Animal {
    public String getFood() {
        return "banana";
    }
}

class ZooKeeper {
    public void feed(Animal animal) {
        System.out.println(animal.getFood());
    }
}

public class Exercise {
    public static void main(String[] args) {
        ZooKeeper zooKeeper = new ZooKeeper();
        Tiger tiger = new Tiger();
        Lion lion = new Lion();
        zooKeeper.feed(tiger);
        zooKeeper.feed(lion);
    }
}
