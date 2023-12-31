// Ananya Soni 
// 10/26/2023
// CSE 122 
// C1: General Image Manipulation
// This class applies filters to an image and produces 4 pictures 
// with those filters that make the original image appear more purple, make the 
// image appear grainy, make specified rectangular portions of the image appear grainy
// and lastly mirror the right half of the image onto the left half of the image.
import java.util.*;
import java.awt.*;
import java.util.Random;

public class ImageManipulation {
    public static void main(String[] args) {
        Picture pic = new Picture("suzzallo.jpg");
        Color[][] pixels = pic.getPixels();

        // Apply filter from Task 1
        increasePurple(pixels);
        pic.setPixels(pixels);
        pic.save("creative1.jpg");

        // Apply filter from Task 2
        grainy(pixels);
        pic.setPixels(pixels);
        pic.save("creative2.jpg");

        // Apply filter from Task 3
        //grainyRectangle(Color[][] pixels, int row1, int col1, int row2, int col2);
        grainyRectangle(pixels, 100, 1100, 700, 1200);
        grainyRectangle(pixels, 100, 900, 700, 1000);
        pic.setPixels(pixels);
        pic.save("creative3.jpg");

        // Apply filter from Task 4
        // save completed image with all filters applied
        mirrorRight(pixels);
        pic.setPixels(pixels);
        pic.save("creative4.jpg");
    }

    // Behavior: 
    //   -  This method makes each pixel more purple by increasing the RGB values of each color by a ratio similar 
    //      to that of purple. (roughly 63% red, 13% green, and 94% blue)
    // Parameters:
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    // Returns:
    //   - N/A 
    // Exceptions:
    //   - N/A
    public static void increasePurple(Color[][] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                Color originalColor = pixels[i][j];
                int red = originalColor.getRed();
                int green = originalColor.getGreen();
                int blue = originalColor.getBlue();
                Color newColor = new Color(Math.min(red + 63, 255), Math.min(green + 13, 255), Math.min(blue + 94, 255));
                pixels[i][j] = newColor;
            }
        }
    }

    // Behavior: 
    //   -  This method makes the image more grainy by randomly choosing whether to increase red, 
    //      green, or blue by 100. 
    // Parameters:
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    // Returns:
    //   - N/A 
    // Exceptions:
    //   - N/A
    public static void grainy(Color[][] pixels) {
        grainyRectangle(pixels, 0, 0, pixels.length, pixels[0].length);
    }

    // Behavior: 
    //   -  This method makes the specified rectange portion of a image more grainy by randomly 
    //      choosing whether to increase red, green, or blue by 100 at the specified portion of the image. 
    // Parameters:
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    //   - int row1: The top left y-coordinate of the rectangle on the image you want to make grainy
    //   - int col1: The top left x-coordinate of the rectangle on the image you want to make grainy
    //   - int row1: The bottom right y-coordinate of the rectangle on the image you want to make grainy
    //   - int col1: The bottom right x-coordinate of the rectangle on the image you want to make grainy
    // Returns:
    //   - N/A 
    // Exceptions:
    //   - N/A
    public static void grainyRectangle(Color[][] pixels, int row1, int col1, int row2, int col2) {
        Random rand = new Random();
        for (int i = row1; i < row2; i++) {
            for (int j = col1; j < col2; j++) {
                Color originalColor = pixels[i][j];
                int red = originalColor.getRed();
                int green = originalColor.getGreen();
                int blue = originalColor.getBlue();
                int randomNum = rand.nextInt(3);
                Color newColor;
                if(randomNum == 0) {
                    newColor = new Color(Math.min(red + 100, 255), green, blue);
                    pixels[i][j] = newColor;
                } else if(randomNum == 1) {
                    newColor = new Color(red, Math.min(green + 100, 255), blue);
                    pixels[i][j] = newColor;
                } else {
                    newColor = new Color(red, green, Math.min(blue + 100, 255));  
                    pixels[i][j] = newColor;
                }          
            }
        }
    }

    // Behavior: 
    //   - This method mirrors the right half on the image onto the left half of the image.
    // Parameters:
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A
    public static void mirrorRight(Color[][] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < (pixels[i].length) / 2; j++) {
                Color nextColor = pixels[i][pixels[i].length - 1 - j];
                int red = nextColor.getRed();
                int green = nextColor.getGreen();
                int blue = nextColor.getBlue();
                pixels[i][j] = nextColor;
            }
        }

    }   
}