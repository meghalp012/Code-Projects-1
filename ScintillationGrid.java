import java.awt.Graphics;
import java.awt.Color;
import java.io.IOException;

/**
 * CS312 Assignment 3.
 *
 * Replace <NAME> with your name, stating on your honor you completed this
 * assignment on your own and that you didn't share your code with other
 * students.
 * 
 * On my honor, <Meghal Patel>, this programming assignment is my own work and I
 * have
 * not shared my solution with any other student in the class.
 *
 * A program to print out various scintillation grids and a student designed
 * drawing.
 *
 * email address: meghalp012@gmail.com
 * UTEID:mrp3937
 * Number of slip days I am using on this assignment:0
 */

public class ScintillationGrid {

    // Main method that creates the DrawingPanel with scintillation grids.
    public static void main(String[] args) {
        final int WIDTH = 950;
        final int HEIGHT = 650;
        DrawingPanel dp = new DrawingPanel(WIDTH, HEIGHT);
        Graphics gr = dp.getGraphics();
        gr.setColor(Color.CYAN);
        gr.fillRect(0, 0, 950, 650);
        Drawgrid(gr, 0, 0, 348, 75, 3, 16); // top left rect
        Drawgrid(gr, 400, 50, 422, 50, 6, 12); // top right rect
        Drawgrid(gr, 50, 400, 220, 100, 1, 20); // bottom left rect
        Drawgrid(gr, 500, 500, 148, 15, 7, 4); // bottom right rect
        saveDrawingPanel(dp, "grid.png");
    }

    public static void Drawgrid(Graphics gr, int x, int y, int large, int small, int line, 
    int thick) { // gets the other methods to help draw the grid
        square(gr, x, y, large); // draws black squares
        lines(gr, x, y, large, small, line, thick); // draws horixontal and verital lines
        circle(gr, x, y, small, line, thick); // draws all circles
    }

    public static void square(Graphics gr, int x, int y, int large) { // creates the 
        // black squares 
        gr.setColor(Color.BLACK);
        gr.fillRect(x, y, large, large); // determines location and size of rectangles
    }

    public static void lines(Graphics gr, int x, int y, int large, int small, int line, int thick) {
        // draws the lines on each grid
        int j = 0;
        for (int i = 1; i <= line; i++) { // determines the amount of vertical lines
            gr.setColor(Color.GRAY); // vertical lines
            gr.fillRect(x + (small * i + j * thick), y, thick, large); // size and locations
            gr.setColor(Color.GRAY); // horizontal lines
            gr.fillRect(x, y + (small * i + j * thick), large, thick); // size and locations 
            // of horizontal lines
            j++;
        }
    }

    public static void circle(Graphics gr, int x, int y, int small, int line, int thick) {
        // creates the size and location of each circle
        int extra = Math.max(4, (int) (0.4 * thick)); // math used for extra bit
        int bit = thick + extra; // equation for total extra bit
        for (int i = 0; i < line; i++) { // amount of circles
            for (int p = 1; p <= line; p++) { // location of circles 
                gr.setColor(Color.WHITE);
                gr.fillOval(small * p + thick * (p - 1) + x - (extra / 2), // positioning of circles
                        small * (i + 1) + thick * i + y - (extra / 2), bit, bit);

            }
        }

    }

    public static void saveDrawingPanel(DrawingPanel dp, String fileName) {
        try {
            dp.save(fileName);
        } catch (IOException e) {
            System.out.println("Unable to save DrawingPanel");
        }
    }
}