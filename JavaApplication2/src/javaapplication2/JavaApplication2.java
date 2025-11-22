/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication2;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author raja usam
 */
public class JavaApplication2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
   
     int val1, val2, res;
//        Scanner input = new Scanner(System.in);
//        
//        System.out.print("Please enter first value : ");
//        val1 = input.nextInt();
//        
//        System.out.print("Please enter second value : ");
//        val2 = input.nextInt();
//        
//        res = val1 + val2;
//        
//        System.out.println("The sum of "+val1+" & "+val2+" is equal to "+res);
        String value1 = JOptionPane.showInputDialog("Please enter first value");
        String value2 = JOptionPane.showInputDialog("Please enter second value");
        
        val1 = Integer.parseInt(value1);
        val2 = Integer.parseInt(value2);
        
        res = val1 + val2;
        
        JOptionPane.showMessageDialog(null, "The sum of "+val1+" & "+val2+" is equal to "+res);
    }
    
}
    
