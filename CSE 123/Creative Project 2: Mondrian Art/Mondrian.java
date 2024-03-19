// Ananya Soni 
// 02/14/2024
// CSE 123
// C2: Mondrian Art
// TA: Lainey
// This class represents a Mondrian style art piece.
// It allows the user to create an image of a Basic Modrian art 
// piece or a Complex Mondrian art piece. 
import java.util.*;
import java.awt.*;

public class Mondrian {

    private Random rand;
    private ArrayList<Color> colors;
    
    public Mondrian() {
        rand = new Random();
        colors = new ArrayList<Color>();
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.WHITE);
        // colors --> green, magenta, orange, pink, red, yellow, cyan, white
    }

    // Behavior: 
    //   - This method paints a random artwork in the style of 
    //     Mondrian. It starts with a blank canvas and repeately breaks
    //     the canvas into random smaller and smaller regions until the 
    //     region is below a certain threshold, painting each region a
    //     random color with an uncolored border around it. 
    // Parameters:
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A   
    public void paintBasicMondrian(Color[][] pixels) {
        paintMondrian(pixels, 0, 0, pixels.length, pixels[0].length, true);   
    }

    // Behavior: 
    //   - This method paints a random artwork in the style of 
    //     Mondrian. It starts with a blank canvas and repeately breaks
    //     the canvas into random smaller and smaller regions until the 
    //     region is below a certain threshold, painting each region a
    //     random color with an uncolored border around it. However the 
    //     color is determined partly by the region's distance from the center of 
    //     the canvas. The closer the region is to the center of the canvas the darker
    //     its color is and the farther a region is from the center of the canvas the 
    //     lighter its color is.
    // Parameters:
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    // Returns: 
    //   - N/A
    // Exceptions:
    //   - N/A   
    public void paintComplexMondrian(Color[][] pixels) {
        paintMondrian(pixels, 0, 0, pixels.length, pixels[0].length, false);   
    }

    // Behavior: 
    //   - This method paints a random artwork in the style of 
    //     Mondrian. It starts with a blank canvas and repeately breaks
    //     the canvas into random smaller and smaller regions until the 
    //     region is below a certain threshold, painting each region a
    //     random color with an uncolored border around it. The color scheme
    //     is determined by isBasic color. If isBasic color is true  
    //     then this method colors each region completely randomly otherwise the 
    //     color is determined partly by the region's distance from the center of 
    //     the canvas. The closer the region is to the center of the canvas the darker
    //     its color is and the farther a region is from the center of the canvas the 
    //     lighter its color is. Note: each region is a rectangle.
    // Parameters:
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    //   - int rowInt: Starting row of the region currently under consideration (top-most row)
    //   - int colInt: Starting col. of the region currently under consideration (left-most column)
    //   - int rowFinal: Final row of the region currently under consideration (bottom-most row)
    //   - int colFinal: Final col. of the region currently under consideration (right-most column) 
    //   - boolean isBasicColor: determines which color scheme this method uses to color the 
    //     current region under consideration 
    // Returns:
    //   - N/A
    // Exceptions:
    //   - N/A   
    private void paintMondrian(Color[][] pixels, int rowInt, int colInt, int rowFinal,  
                                int colFinal, boolean isBasicColor) {
        //Recursive Cases:
        //If the region being considered is at least one-fourth the 
        //height of the full canvas and at least one-fourth the width
        //of the full canvas, split it into four smaller regions by 
        //choosing one vertical and one horizontal dividing line at
        //random.
        if((rowFinal - rowInt) >= (pixels.length / 4) && 
            ((colFinal - colInt) >= (pixels[0].length / 4)) && 
                rowFinal > rowInt + 1 && colFinal > colInt + 1) {
            int horizontalSplit = rand.nextInt(rowInt + 1, rowFinal);
            int verticalSplit = rand.nextInt(colInt + 1, colFinal);
            //split into 4 regions
            //       Pixel Grid  Initial Row  Initial Column  Final Row  Final Column, Color Scheme
            paintMondrian(pixels, rowInt, colInt, horizontalSplit, verticalSplit, isBasicColor);
            paintMondrian(pixels, rowInt, verticalSplit, horizontalSplit, colFinal,isBasicColor);
            paintMondrian(pixels, horizontalSplit, colInt, rowFinal, verticalSplit, isBasicColor);
            paintMondrian(pixels, horizontalSplit, verticalSplit, rowFinal, colFinal, isBasicColor);

        //If the region being considered is at least one-fourth the height
        //of the full canvas, split it into two smaller regions by choosing 
        //a horizontal dividing line at random.
        } else if((rowFinal - rowInt) >= (pixels.length / 4) && rowFinal > rowInt + 1) {
            int horizontalSplit = rand.nextInt(rowInt + 1, rowFinal);
            //split into 2 regions
            paintMondrian(pixels, rowInt, colInt, horizontalSplit, colFinal, isBasicColor);
            paintMondrian(pixels, horizontalSplit, colInt, rowFinal, colFinal, isBasicColor);

        //If the region being considered is at least one-fourth the width of 
        //the full canvas, split it into two smaller regions by choosing a 
        //vertical dividing line at random.   
        } else if((colFinal - colInt) >= (pixels[0].length / 4)  && colFinal > colInt + 1) {
             //split into 2 regions
             int verticalSplit = rand.nextInt(colInt + 1, colFinal);
             paintMondrian(pixels, rowInt, colInt, rowFinal, verticalSplit, isBasicColor);
             paintMondrian(pixels, rowInt, verticalSplit, rowFinal, colFinal, isBasicColor);
        //Base Case: If the region being considered is smaller than one-fourth the height 
        //of the full canvas and smaller than one-fourth the width of the full 
        //canvas, do not divide the region 
        } else {
            //Fill the region with a random color, leaving a one-pixel border around the 
            //edges uncolored.
            Color randomColor = pickColor(isBasicColor, pixels, rowInt, colInt);
            for(int i = rowInt + 1; i < rowFinal - 1; i++ ) {
                for(int j = colInt + 1; j < colFinal - 1; j++) {
                    pixels[i][j] = randomColor;
                }
            }
        }    
    }


    // Behavior: 
    //   - This method picks a Random color for a region in an image 
    //     depending on isBasicColor. If isBasic color is true  
    //     then this method chooses a color completely randomly otherwise the 
    //     color is determined partly by the region's distance from the center of 
    //     the canvas. The closer the region is to the center of the canvas the darker
    //     the chosen color is and the farther a region is from the center of the canvas the 
    //     lighter the chosen color is. 
    // Parameters:
    //   - boolean isBasicColor: determines which color scheme this method uses to choose a color
    //   - Color[][] pixels: Takes the picture to edit as a parameter.
    //   - int rowInt: Starting row of the region currently under consideration (top-most row)
    //   - int colInt: Starting col. of the region currently under consideration (left-most column)
    // Returns: 
    //   - Color: the chosen color for the region 
    // Exceptions:
    //   - N/A   
    private Color pickColor(boolean isBasicColor, Color[][] pixels, 
                            int rowInt, int colInt) {
             //colors --> red, yellow, cyan, white
            int randomColorIndex = rand.nextInt(4, colors.size());
            if(isBasicColor) {
                return colors.get(randomColorIndex);
            }
            //choose a new Random color without the option of the color white 
            //colors --> green, magenta, orange, pink, red, yellow, cyan
            randomColorIndex = rand.nextInt(0, colors.size() - 1); 
            //makes regions closer to the center a relatively darker color
            //and regions farther from the center a  relatively lighter color 
            //center of the canvas:
            int centerX = pixels[0].length / 2;
            int centerY = pixels.length / 2;
            //calculate distance from top left corner of current region to the center of the canvas
            int distance = (int) Math.sqrt(Math.pow((rowInt - centerX), 2) +
                            Math.pow((colInt - centerY), 2));
            while(distance >= 255) {
                distance = distance - 5;
            }
            int red = Math.min(colors.get(randomColorIndex).getRed() + distance, 255);
            int blue = Math.min(colors.get(randomColorIndex).getBlue() + distance, 255);
            int green = Math.min(colors.get(randomColorIndex).getGreen() + distance, 255);
            return new Color(red, green, blue);           
    }
}
