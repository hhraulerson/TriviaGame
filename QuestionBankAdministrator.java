/**
Program that tests the QuestionBank class by displaying the 
menu and parsing the user's selection.

@author Hiram Raulerson
@version 1.1
 
E-mail Address: hhr3@students.uwf.edu.
 
Last Changed: October 22, 2015.
 
COP5007	Project #: 4
File Name: QuestionBankAdministrator.java
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.InputMismatchException;

public class QuestionBankAdministrator
{
   public static void main(String[] args)
   {
      QuestionBank q = new QuestionBank();
     
      Scanner keyboard = new Scanner(System.in);
      
      boolean quit = false;
      
      while (!quit)
      {
         q.displayMenu();
         
         int choice = 0;
      
         boolean correctInput = false;
               
         while (!correctInput)
         {
            try
            {
               choice = keyboard.nextInt();
                     
               correctInput = true;
            }
            catch (InputMismatchException e)
            {
               System.out.println("\nError: integer was not entered.  Try again.");
            }
            catch (Exception e)
            {
               System.out.println("\nError: problems user input.  Try again.");
            }
         }
      
         if (choice == 4)
         {
            quit = true;
         }
         else
         {
            q.parseUserSelection(choice);
         }
      }
      
      q.close();
   }
}