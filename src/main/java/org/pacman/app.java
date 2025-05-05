package org.pacman;
import javax.swing.JFrame;

public class app {
    public static void main(String[] args) throws Exception{
        // Definicion de variables
        int rowCount = 21;
        int columnCount =  19;
        int titleSize = 32;
        int boardWeight = columnCount * titleSize;
        int boardHeight = rowCount * titleSize;

        JFrame frame  = new JFrame("Pac Man Game");
        frame.setVisible(true);
        frame.setSize(boardWeight, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
