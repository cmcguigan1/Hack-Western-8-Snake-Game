import javax.swing.*;

public class Frame extends JFrame {
    public Frame(){
        //adds the background colour, start button...ect to the board
        this.add(new DemoClass());
        //closes the game when the game is lost
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Snakes");
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
}

