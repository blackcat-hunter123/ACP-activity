/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1;
import java.util.Scanner;
/**
 *
 * @author raja usam
 */
public class JavaApplication1 {

    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        
        System.out.println("Welcome to Student Grades Analysis System\n");
        
        System.out.print("Enter number of students: ");
        int stdNo = input.nextInt();
        
        System.out.print("Enter number of subjects: ");
        int subNo = input.nextInt();
        
        int[] stdID = new int[stdNo];
        String[] stdName = new String[stdNo];
        int[][] subGrade = new int[stdNo][subNo];
        double[] stdAverage = new double[stdNo];
        double[] subAverage = new double[subNo];
        int marksSum = 0;
        
        for(int i = 0; i < stdNo; i++){
            marksSum = 0;
            System.out.println("\nEnter details for Student "+(i+1)+" :");
            
            System.out.print("ID : ");
            stdID[i] = input.nextInt();
            
            System.out.print("Name : ");
            stdName[i] = input.next();
            
            System.out.println("Enter subject Marks below : ");
            for(int j = 0; j<subNo; j++){
                System.out.print("Subject # "+(j+1)+" Marks : ");
                subGrade[i][j] = input.nextInt();
                marksSum = marksSum + subGrade[i][j];
            }
            stdAverage[i] = (double)marksSum/subNo;
        }
        
        System.out.println("--- Results ---");
        for(int i = 0; i < stdNo; i++){
            System.out.println(stdName[i]+ " -> Average : "+ stdAverage[i]);
        }
        
        System.out.println("Class Average (per subject):");
        
        for(int i = 0; i < subNo; i++){
            marksSum = 0;
            for(int j = 0; j < stdNo; j++){
                marksSum = marksSum + subGrade[j][i];
            }
            subAverage[i] = (double)marksSum/stdNo;
            System.out.println("Subject " + (i+1) + " : " + subAverage[i]);
        }
        
    }
    
}