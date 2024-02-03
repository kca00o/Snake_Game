import javax.swing.*;


public class Main {
    public static void main(String[] args) {
     int bwidth = 600;
     int bheight = bwidth;

     JFrame frame = new JFrame("Snake");
     frame.setVisible(true);
     frame.setSize(bwidth,bheight);
     frame.setLocationRelativeTo(null);
     frame.setResizable(false);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


     SnakeGame snakeGame= new SnakeGame(bwidth,bheight);
     frame.add(snakeGame);
     frame.pack();
     snakeGame.requestFocus();
    }
}