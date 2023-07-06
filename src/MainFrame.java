import com.sun.tools.javac.Main;

import javax.swing.*;

public class MainFrame extends JFrame {

    private GameScreen game = new GameScreen();
    public MainFrame(){
        this.setTitle("SnakeGame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(game);
        this.pack();

        this.setVisible(true);
        this.setResizable(false);

        mainLoop();
    }

    private void mainLoop(){
        while(true) {
            game.loopItteration();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new MainFrame();
    }
}