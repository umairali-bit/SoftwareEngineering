package set;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class School {

    public static void main(String[] args) {

        Set<Student> set = new HashSet<>();

        set.add(new Student(1, "Walter White"));
        set.add(new Student(1, "Walter White"));
        set.add(new Student(1, "Walter White"));
        set.add(new Student(1, "Walter White"));

        System.out.println(set);

        Set<Student> set2 = new HashSet<>();
        set2.add(new Student(1, "Hank Scharader"));
        set2.add(new Student(1, "Jessie Pinkman"));

        System.out.println(set2);

    }
}


class Student {

    int rollNo;
    String name;

    public Student(int rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student [rollNo=" + rollNo + ", name=" + name + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return rollNo == student.rollNo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rollNo);
    }
}