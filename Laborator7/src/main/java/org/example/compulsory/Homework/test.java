package org.example.compulsory.Homework;

public class test {

    public static void main(String args[]) {
        int foo = 13;
        System.out.println(foo); // this will print "1"

        setFoo(foo);
        System.out.println(foo); // this will still print "1"
    }

    public static void setFoo(int bar) {
        bar = 2;
    }
}
