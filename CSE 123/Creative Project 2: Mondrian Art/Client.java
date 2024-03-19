import java.awt.*;
import java.util.*;

public class Client { 
    public static void main(String[] args) throws Exception {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the CSE 123 Mondrian Art Generator!");

        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("Enter 1 for a basic Mondrian or 2 for a complex Mondrian: ");
            choice = console.nextInt();
        }
        int width = 0;
        while(width < 300) {
            System.out.print("Enter image width (>= 300px): ");
            width = console.nextInt();    
            if(width < 300) {
                System.out.println("Width must be >= 300 Pixels! Try again :( \n");
            }      
        }
        int height = 0;
        while(height < 300) {
            System.out.print("Enter image height (>= 300px): ");
            height = console.nextInt();    
            if(height < 300) {
                System.out.println("Height must be >= 300 Pixels! Try again :( \n");
            }      
        }
        Mondrian mond = new Mondrian();
        Picture pic = new Picture(width, height);
        Color[][] pixels = pic.getPixels();

        if (choice == 1) {
            mond.paintBasicMondrian(pixels);
        } else {    // choice == 2
            mond.paintComplexMondrian(pixels);
        }
        
        pic.setPixels(pixels);
        pic.save(choice == 1 ? "basic.png" : "extension.png");
        pic.show();
        System.out.println("Enjoy your artwork! " + height + " X "  + width + " pixels");
        console.close();
    }     
         
}

