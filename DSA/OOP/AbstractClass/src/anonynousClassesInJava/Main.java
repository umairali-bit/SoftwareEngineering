package anonynousClassesInJava;

public class Main {

//    class InnerClass extends OuterClass {
//
//
//    }


//    Anonymous class

    OuterClass obj = new OuterClass() {

        void sing() {

        }

        public void outerMethod() {

        }

    };

    SuperInterface obj2 = new SuperInterface() {

        public void interfaceMethod() {
        }
    };


}

class OuterClass {

    public void outerMethod() {


    }
}

interface SuperInterface{

    void interfaceMethod();
}
