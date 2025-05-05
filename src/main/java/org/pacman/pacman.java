package org.pacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.*;

public class pacman extends JPanel implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

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
    HashSet<Block> foods;
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
        wallImage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
        orangeGhostimage = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
        pinkGhostimage = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
        redGhostimage = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
        pacmanRigthImage = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();

        loadMap();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
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
                else if(titleMapChar == 'o') {//orange ghost
                    Block ghost = new Block(orangeGhostimage, x, y, titleSize, titleSize);
                    ghosts.add(ghost);
                }
                else if(titleMapChar == 'p') {//pink ghost
                    Block ghost = new Block(pinkGhostimage, x, y, titleSize, titleSize);
                    ghosts.add(ghost);
                }
                else if(titleMapChar == 'r') {//red ghost
                    Block ghost = new Block(redGhostimage, x, y, titleSize, titleSize);
                    ghosts.add(ghost);
                }
                else if(titleMapChar == 'P') {//pacman
                    pacman = new Block(pacmanRigthImage, x, y, titleSize, titleSize);

                }
                else if(titleMapChar == ' ') {//food
                    Block food = new Block(null, x+14, y+14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.heigth, null);

        for (Block ghost: ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.heigth, null);
        }

        for (Block wall: walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.heigth, null);
        }

        g.setColor(Color.WHITE);
        for (Block food: foods){
            g.drawImage(food.image, food.x, food.y, food.width, food.heigth, null);
        }
    }
}
