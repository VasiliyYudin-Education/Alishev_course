package ru.alishev.springcourse;

import java.util.Scanner;

public class ex1 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        System.out.println((100L+n)*(n-100+1)/2);
    }
}
