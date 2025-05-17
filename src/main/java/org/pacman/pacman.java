package org.pacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class pacman extends JPanel implements ActionListener, KeyListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameover){
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameover) {
            loadMap();
            resetPosition();
            lives= 3;
            score=0;
            gameover = false;
            gameLoop.start();
        }
        // System.out.println("KeyEvent: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        }

    }

    class Block {
        int x;
        int y;
        int width;
        int heigth;
        Image image;

        int startX;
        int startY;
        char direction = 'U';
        int velocityX = 0;
        int velocityY = 0;

        Block(Image image, int x, int y, int width, int heigth) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.heigth = heigth;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction) {
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -titleSize/4;
            }
            else if (this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = titleSize/4;
            }
            else if (this.direction == 'L') {
                this.velocityX = -titleSize/4;
                this.velocityY = 0;
            }
            else if (this.direction == 'R') {
                this.velocityX = titleSize/4;
                this.velocityY = 0;
            }

            if (pacman.direction == 'U') {
                pacman.image = pacmanUpImage;
            } else if (pacman.direction == 'D') {
                pacman.image = pacmanDownImage;
            } else if (pacman.direction == 'L') {
                pacman.image = pacmanLeftImage;
            } else if (pacman.direction == 'R') {
                pacman.image = pacmanRigthImage;
            }
        }
        void reset() {
            this.x = this.startX;
            this.y = this.startY;
        }
    }

    private int rowCount = 21;
    private int columnCount =  19;
    private int titleSize = 32;
    private int scoreSuperPower = 400;
    private int boardWeight = columnCount * titleSize;
    private int boardHeight = rowCount * titleSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostimage;
    private Image pinkGhostimage;
    private Image redGhostimage;

    private Image scaredGhostimage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRigthImage;

    private Image foodImage;

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

    Timer gameLoop;
    char[] directions = {'U', 'D', 'L', 'R'};
    Random random = new Random();
    int score = 0;
    boolean superPowerActivated = false;
    int lives = 3;
    boolean gameover = false;

    pacman() {
        setPreferredSize(new Dimension(boardWeight, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // carga las imagenes
        wallImage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
        orangeGhostimage = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
        pinkGhostimage = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
        redGhostimage = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();

        scaredGhostimage = new ImageIcon(getClass().getResource("/scaredGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
        pacmanRigthImage = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();

        foodImage = new ImageIcon(getClass().getResource("/powerFood.png")).getImage();
        loadMap();
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        gameLoop = new Timer(50, this); //20 fps
        gameLoop.start();
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
                    Block food = new Block(foodImage, x+14, y+14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void move(){
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        if (foods.isEmpty()) {
            loadMap();
            resetPosition();
        }

        // comprueba colision muros
        for (Block wall:walls) {
            if (collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }

        // colision fanstamita
        Iterator<Block> iterator = ghosts.iterator();
        while (iterator.hasNext()) {
            Block ghost = iterator.next();

            if (collision(ghost, pacman)) {
                if (superPowerActivated) {
                    // Eliminar el fantasma de la lista usando el iterador
                    iterator.remove();
                    continue; // Saltar al siguiente elemento en el iterador
                } else {
                    lives -= 1;
                    if (lives == 0) {
                        gameover = true;
                        return;
                    }
                    resetPosition();
                }
            }
            if (ghost.y == titleSize * 9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall:walls) {
                if (collision(ghost, wall) || ghost.x + ghost.width >= boardWeight || ghost.x <= 0) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }

        //Salimos del mapa y volvemos  aentrar al mapa por el otro lado
        //detectados que el pacman se ha salido del mapa
        if (pacman.x > boardWeight) {
            pacman.x = 0;
        }
        else if (pacman.x < 0) {
            pacman.x = pacman.startX *2;
        }

        // colision comida
        Block foodEaten = null;
        for (Block food : foods) {
            if (collision(pacman, food)) {
                foodEaten = food;
                score += 10;
                if (score >= scoreSuperPower) {
                    // activate super power en funcion
                    activationSuperPower();
                }
            }
        }
        foods.remove(foodEaten);
    }

    public void resetPosition() {
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for (Block ghost: ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    public boolean collision(Block a, Block b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.heigth &&
                a.y + a.heigth > b.y;
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

        //score
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if(gameover) {
            g.drawString("Game Over:" +  String.valueOf(score), titleSize/2, titleSize/2);
        } else {
            g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), titleSize/2, titleSize/2);
        }
    }

    public void activationSuperPower () {
        superPowerActivated = true;
        // ghost blues
        for (Block ghost : ghosts) {
            ghost.image = scaredGhostimage;
        }
        // ghost can be eaten
        // temporizador
    }
}
