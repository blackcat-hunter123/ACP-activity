/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author muhammadusman
 */
public class LibraryManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        char ch;
        ArrayList<Book> books = new ArrayList<>();
        //Book b;
        Scanner input = new Scanner(System.in);
        int ID;
        String Name, Author, Category;
        Book bb = new Book();
        
        do{
            System.out.println("*** Library Manangement System ***");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. View Specific Book");
            System.out.println("4. Update Book");
            System.out.println("5. Sort Book Through ID");
            System.out.println("**********************************");
            System.out.print("Enter choice : ");
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    System.out.print("Enter Book ID : ");
                    ID = input.nextInt();
                    System.out.print("Enter Book Name : ");
                    Name = input.next();
                    System.out.print("Enter Book Author : ");
                    Author = input.next();
                    System.out.print("Enter Book Category : ");
                    Category = input.next();
                    
                    books.add(new Book(ID,Name,Author,Category));
                    break;
                case 2:
                    for(Book b : books){
                        b.display();
                    }
                    break;
                case 3:
                    System.out.print("Enter Book Category : ");
                    String cat = input.next();
                    
                    for(Book b : books){
                        if(b.get_Category().equals(cat))
                            b.display();
                        else
                            System.out.println("Book not exist");
                    }
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
            System.out.print("Do you want to run again (Y/N) : ");
            ch = input.next().charAt(0);
        }while(ch == 'Y' || ch == 'y');
    }
}