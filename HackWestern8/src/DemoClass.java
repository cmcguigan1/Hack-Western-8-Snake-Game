import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DemoClass extends JPanel implements ActionListener {

    //data fields hold the x and y coordinates of the snake
    public int[] xCoordinate = new int[1000 / 25];
    public int[] yCoordinate = new int[750 / 25];
    //holds length of snake
    public int snakeLength = 5;
    //holds size of individual squares on the board
    public final int squaresSize = 25;
    //determines whether the game is ongoing
    public boolean going = false;
    //hold direction of the snake, either up, down, right or left
    char direction = 'R';
    //hold width and height of the game board
    public final int width = 1000;
    public final int height = 750;
    //start game button
    public JButton button;
    //holds location of the apple
    public int appleLocationX = 50;
    public int appleLocationY =50;
    //speed of the snake
    public int speed = 100;
    //delays the repaint of the snake, so it moves at a reasonable pace
    public Timer timer = new Timer(100,this);

    public static void main(String[] args) {
        //created a new game board
        new Frame();
    }

    public DemoClass() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.ORANGE);
        this.setFocusable(true);
        this.addKeyListener(new GUIKeyboard());
        //creates a start game button
        button = new JButton("Start Game");
        button.addActionListener(this);
        this.add(button);
    }

    //generates a new random location for the apple
    public void apple() {
        appleLocationX =  (int) (Math.random() * (width / squaresSize)) * squaresSize;
        appleLocationY =  (int) (Math.random() * (height / squaresSize)) * squaresSize;
    }

    //paints the apple into the game board
    public void addApple(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(appleLocationX, appleLocationY, squaresSize, squaresSize);
    }

    //determine if the first block (the head) of the snake collides with the location the apple is in
    public boolean ifEaten() {
        if (xCoordinate[0] == appleLocationX && yCoordinate[0] == appleLocationY) {
            return true;
        } else {
            return false;
        }
    }

    //starts the timer and begins the game
    public void start() {
        timer.start();
        going = true;
    }

    //method is responsible for the snake movement
    public void move() {
        //for loop shifts the blocks over by one space
        for (int i = snakeLength; i > 0; i--) {
            xCoordinate[i] = xCoordinate[i - 1];
            yCoordinate[i] = yCoordinate[i - 1];
        }
        //switch statement changes the direction of the snakes head (element at index 0)
        switch (direction) {
            case ('R'):
                xCoordinate[0] = xCoordinate[0] + squaresSize;
                break;
            case ('L'):
                xCoordinate[0] = xCoordinate[0] - squaresSize;
                break;
            case ('U'):
                yCoordinate[0] = yCoordinate[0] - squaresSize;
                break;
            case ('D'):
                yCoordinate[0] = yCoordinate[0] + squaresSize;
                break;
        }
    }

    //Action Listener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (going) {
            //calls the move method to move the snake
            move();
            //executes while the apple is eaten
            while (ifEaten() == true) {
                //calls method to generate a new apple
                apple();
                //increments snake length
                snakeLength++;
                //increments the snake's speed and updates the timer
                speed+=150;
                timer = new Timer(speed, this);
            }
            //method call checks for a collision
            checkCollisions();
        }
        //if statement determines that if the game is not going, then begin
        if (!going) {
            //begins game
            start();
            //makes the start button disappear
            button.setEnabled(false);
            button.setVisible(false);
        }
        //repaints the snake at its updated location on the game board
        repaint();
    }

    //ends the game
    public void endGame() {
        going = false;
        timer.stop();
    }

    //method paints the individual squares of the snakes body
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //if-statement paints the head of the snake a different colour than its body
        for (int i = 0; i < snakeLength; i++) {
            if (i == 0) {
                g.setColor(new Color(81, 0, 180));
                g.fillRect(xCoordinate[i], yCoordinate[i], squaresSize, squaresSize);
            } else {
                g.setColor(new Color(162, 0, 180));
                g.fillRect(xCoordinate[i], yCoordinate[i], squaresSize, squaresSize);
            }
        }
        //methods call paints the apple into the board
        addApple(g);

    }
    //method checks if the snake collides with itself or the walls of the frame
    public void checkCollisions() {
        //checks if head collides with body
        for (int i = snakeLength; i > 0; i--) {
            if ((xCoordinate[0] == xCoordinate[i]) && (yCoordinate[0] == yCoordinate[i])) {
                System.exit((0));
                going = false;
            }
        }
        //check if head touches left border
        if (xCoordinate[0] < 0) {
            System.exit((0));
            going = false;
        }
        //check if head touches right border
        if (xCoordinate[0] > width) {
            System.exit((0));
            going = false;
        }
        //check if head touches top border
        if (yCoordinate[0] < 0) {
            System.exit((0));
            going = false;
        }
        //check if head touches bottom border
        if (yCoordinate[0] > height) {
            System.exit((0));
            going = false;
        }

        if (!going) {
            timer.stop();
        }
    }

    //method is responsible for the keyboard action listeners
    public class GUIKeyboard extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //gets the key code of the target (key pressed)
            int keyCode = e.getKeyCode();
            //switch statement switches direction of snake
            /*if statement ensures the snake cannot do a 180 degree change of directions in order
            to prevent accidental collisions with the snakes body*/
            switch (keyCode) {
                case (KeyEvent.VK_RIGHT):
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case (KeyEvent.VK_LEFT):
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case (KeyEvent.VK_UP):
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case (KeyEvent.VK_DOWN):
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}

