package jvm;

import java.util.Objects;

public class HashCodeTest {
    public static void main(String[] args) {
        Person xiaoming1 = new Person("xiaoming", 20);
        Person xiaoming2 = new Person("xiaoming", 20);

        System.out.println(xiaoming1.hashCode());
        System.out.println(xiaoming2.hashCode());
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}