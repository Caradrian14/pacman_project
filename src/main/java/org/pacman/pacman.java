package org.pacman;

import java.awt.*;
import java.util.HashSet;
import javax.swing.*;

public class pacman extends JPanel{

    class Block {
        int x;
        int y;
        int width;
        int heigth;
        Image image;

        int startX;
        int startY;
        Block(Image image, int x, int y, int width, int heigth) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.heigth = heigth;
            this.startX = x;
            this.startY = y;
        }
    }

    private int rowCount = 21;
    private int columnCount =  19;
    private int titleSize = 32;
    private int boardWeight = columnCount * titleSize;
    private int boardHeight = rowCount * titleSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostimage;
    private Image pinkGhostimage;
    private Image redGhostimage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRigthImage;

    HashSet<Block> walls;
    HashSet<Block> food;
    HashSet<Block> ghosts;
    Block pacman;
    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    pacman() {
        setPreferredSize(new Dimension(boardWeight, boardHeight));
        setBackground(Color.BLACK);

        // carga las imagenes
        wallImage = new ImageIcon(getClass().getResource("src/main/resources/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("src/main/resources/blueGhost.png")).getImage();
        orangeGhostimage = new ImageIcon(getClass().getResource("src/main/resources/orangeGhost.png")).getImage();
        pinkGhostimage = new ImageIcon(getClass().getResource("src/main/resources/pinkGhost.png")).getImage();
        redGhostimage = new ImageIcon(getClass().getResource("src/main/resources/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("src/main/resources/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("src/main/resources/pacmanDown.png")).getImage();
        pacmanRigthImage = new ImageIcon(getClass().getResource("src/main/resources/pacmanRight.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("src/main/resources/pacmanLeft.png.png")).getImage();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        food = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                String row = tileMap[r];
                char titleMapChar = row.charAt(c);

                int x = c*titleSize;
                int y = r*titleSize;

                if (titleMapChar == 'X') { // Muro
                    Block wall = new Block(wallImage, x, y, titleSize, titleSize);
                    walls.add(wall);
                }
                else if(titleMapChar == 'b') {//blue ghost
                    Block ghost = new Block(blueGhostImage, x, y, titleSize, titleSize);
                    ghosts.add(ghost);
                }
                else if(titleMapChar == 'b') {//orange ghost
                    Block ghost = new Block(blueGhostImage, x, y, titleSize, titleSize);
                    ghosts.add(ghost);
                }
                else if(titleMapChar == 'b') {//pink ghost
                    Block ghost = new Block(blueGhostImage, x, y, titleSize, titleSize);
                    ghosts.add(ghost);
                }
                else if(titleMapChar == 'b') {//blue ghost
                    Block ghost = new Block(blueGhostImage, x, y, titleSize, titleSize);
                    ghosts.add(ghost);
                }
            }
        }
    }
}
