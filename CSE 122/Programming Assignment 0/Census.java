// Ananya Soni
// 10/12/2023
// CSE 122
// This class asks the user to enter the age of people in a certain population via the 
// console until the user enters a negative number. It then prints the average age of the
// population (total age of population/# of people in population).
import java.util.*;

public class Census {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the Census!");
        System.out.println("Input the ages of the population and we will compute the average age");
        int nextAge = 0;
        double sum = 0;
        int populationSize = 0;
        while(nextAge >= 0) {
            System.out.print("Next person's age (negative to quit)? ");
            nextAge = console.nextInt();
            if(nextAge >= 0) {
                sum += nextAge;
                populationSize++;
            }
        }
        System.out.println("The average age is " + (double)(sum/(populationSize)));
    }   
}