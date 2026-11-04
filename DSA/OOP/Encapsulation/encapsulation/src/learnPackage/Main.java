package learnPackage;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();

        Teacher obj = new  Teacher();
        obj.teachingClass = 12;
//        obj.id = 123;

        Person person = new Person();
        System.out.println(person.getAge());
        person.setAge(10);
        System.out.println(person.getAge());
        System.out.println(person.getName());
        person.setName("Jessie Pinkman");
        System.out.println(person.getName());

    }
}
