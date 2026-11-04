package learnPackage;

public class Person {


   private int age;

   private String name;

   boolean ageChange = false;

   public int getAge() {
       return age;
   }

   public String getName() {
       return name;
   }

   public void setAge(int age) {

       if (ageChange) {
           this.age = age;
       }
   }

   public void setName(String name) {
       this.name = name;
   }



}
