package ru.alishev.springcourse;

import java.util.Scanner;

public class ex2 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int result=4*(n-1);
        System.out.println(result==0?1:result);
    }
}
