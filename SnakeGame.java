import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x =x;
            this.y = y;
        }
    }
    int bwidth;
    int bheight;
    int tileSize = 25;
    //snake
    Tile snakehead;
    ArrayList<Tile> snakebody;

    //Food
    Tile food;
    Random random;

    /// game logic
    Timer gameLoop;
    int velocityy;
    int velocityx;
    boolean gameOver = false;
    JButton resbutton;

    SnakeGame(int bwidth, int bheight){
        this.bheight=bheight;
        this.bwidth= bwidth;

        setPreferredSize(new Dimension(this.bwidth,this.bheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakehead = new Tile(5,5);
        snakebody = new ArrayList<Tile>();


        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityy=0;
        velocityx=0;

        gameLoop = new Timer(100,this);
        gameLoop.start();


    }

    public boolean collision(Tile t1, Tile t2){
        return  t1.x == t2.x && t1.y == t2.y;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // Grid
//        for (int i=0; i<bwidth/tileSize; i++){
//            g.drawLine(i*tileSize, 0,i*tileSize,bheight);
//            g.drawLine(0, i*tileSize, bwidth, i*tileSize);
//        }

        //food
        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize,food.y *tileSize, tileSize,tileSize, true);


        //Snakehead
        g.setColor((Color.cyan));
        g.fill3DRect(snakehead.x*25, snakehead.y*25, tileSize, tileSize,true);

        //Snakebody
        for (int i = 0; i<snakebody.size(); i++){
            Tile snakePart = snakebody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize , tileSize,true);
        }
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over!!! - Your score is:  " + String.valueOf(snakebody.size()), tileSize-16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakebody.size()), tileSize-16, tileSize);
        }


    }


    public void placeFood(){
        food.x= random.nextInt(bwidth/tileSize);
        food.y= random.nextInt(bheight/tileSize);

    }

    public void move() {
        //when eat
        if (collision(snakehead, food)) {
            snakebody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snakebody
        for (int i = snakebody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakebody.get(i);
            if (i == 0) {
                snakePart.x = snakehead.x;
                snakePart.y = snakehead.y;
            } else {
                Tile prevsnakePart = snakebody.get(i - 1);
                snakePart.x = prevsnakePart.x;
                snakePart.y = prevsnakePart.y;

            }
        }

        //snakeHead
        snakehead.x += velocityx;
        snakehead.y += velocityy;

        // game over con
        for (int i = 0; i < snakebody.size(); i++) {
            Tile snakePart = snakebody.get(i);
            // collide with the head
            if (collision(snakehead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakehead.x * tileSize < 0 || snakehead.x * tileSize > bwidth
                || snakehead.y * tileSize < 0 || snakehead.y > bheight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityy != 1){
            velocityx= 0;
            velocityy =-1;
        } else if (e.getKeyCode()== KeyEvent.VK_DOWN && velocityy != -1 ) {
            velocityy = 1;
            velocityx = 0;
        } else if (e.getKeyCode()== KeyEvent.VK_LEFT && velocityx != 1) {
            velocityx = -1;
            velocityy = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityx != -1) {
            velocityy=0;
            velocityx=1;

        }

    }


    /// do not need
    @Override
    public void keyTyped(KeyEvent e) {}



    @Override
    public void keyReleased(KeyEvent e) {}




}