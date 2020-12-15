package resources;

import java.util.List;

public class Person {
    private final int id;
    private static int counter = 1;
    private final String firstName;
    private final String secondName;
    private final int height;
    private final int weight;
    private final List<Person> children;

    public Person(String firstName, String secondName, int height, int weight, List<Person> children) {
        this.id = counter++;
        this.firstName = firstName;
        this.secondName = secondName;
        this.height = height;
        this.weight = weight;
        this.children = children;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", children=" + children +
                '}';
    }
}
