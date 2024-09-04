package ru.alishev.springcourse;

import java.util.Arrays;
import java.util.Scanner;

public class ex3 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int[] rowsSum=new int[n];
        int[] colsSum=new int[n];
        int[][] matrix=new int[n][n];
        int num;
        for(int row=0;row<n;++row){
            for(int col=0;col<n;++col){
                num=scanner.nextInt();
                rowsSum[row]+=num;
                colsSum[col]+=num;
                matrix[row][col]=num;
            }
        }
        int count=0;
        for(int row=0;row<n;++row){
            for(int col=0;col<n;++col){
                if(Math.abs(rowsSum[row]-colsSum[col])<=matrix[row][col])
                    ++count;
            }
        }
        System.out.println(count);
    }
}
