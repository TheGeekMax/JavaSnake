import com.sun.tools.javac.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class GameScreen extends JPanel {
    private GameState state = GameState.START;

    //varibales du jeu
    public static final int GRID_WIDTH = 40;
    public static final int GRID_HEIGHT = 40;
    public static final int TILE_WIDTH = 20;
    public static final int FRAME_STEP = 10;

    //variablesp our le jeu
    int frame = 0;
    private Snake snakeGame = new Snake(GRID_WIDTH,GRID_HEIGHT);
    private KeyboardManager keyboard = new KeyboardManager(KeyEvent.VK_Z,KeyEvent.VK_Q,KeyEvent.VK_S,KeyEvent.VK_D);

    public GameScreen(){
        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(state == GameState.START && e.getKeyCode() == KeyEvent.VK_SPACE){
                    state = GameState.PLAYING;
                    System.out.println("stating !");
                    frame = 0;
                }

                if(state == GameState.GAME_OVER && e.getKeyCode() == KeyEvent.VK_SPACE){
                    state = GameState.PLAYING;
                    System.out.println("stating !");
                    frame = 0;
                    snakeGame = new Snake(GRID_WIDTH,GRID_HEIGHT);
                    keyboard = new KeyboardManager(KeyEvent.VK_Z,KeyEvent.VK_Q,KeyEvent.VK_S,KeyEvent.VK_D);
                }

                if(state == GameState.PLAYING) keyboard.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void loopItteration(){
        switch(state){
            case START:
            case GAME_OVER:
                if (frame++ == FRAME_STEP*16) {
                    frame = 0;
                }
                this.repaint();
                break;

            case PLAYING:
                if (frame++ == FRAME_STEP) {
                    snakeGame.changeOrientation(keyboard.getDirrection());
                    boolean gameover = snakeGame.iteration();
                    this.repaint();
                    frame = 0;

                    if(!gameover){
                        System.out.println("game over");
                        state = GameState.GAME_OVER;
                        frame = 0;
                    }
                }
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(GRID_WIDTH*TILE_WIDTH,GRID_HEIGHT*TILE_WIDTH);
    }

    private void paintGame(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,GRID_WIDTH*TILE_WIDTH,GRID_HEIGHT*TILE_WIDTH);

        //on print le tableau a l'ecran
        for(int i = 0 ; i < GRID_WIDTH; i++){
            for(int j = 0 ; j < GRID_HEIGHT; j++){
                if(snakeGame.getPlateau(i,j) != 0){
                    g.setColor(Color.GREEN);
                }else{
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i*TILE_WIDTH,j*TILE_WIDTH,TILE_WIDTH,TILE_WIDTH);
            }
        }
        //on print la pomme, la tete et la queue
        Vector2Int head = snakeGame.getHeadPos();
        Vector2Int tail = snakeGame.getTailPos();
        Vector2Int apple = snakeGame.getApplePos();

        g.setColor(Color.BLUE);
        g.fillRect(head.getX()*TILE_WIDTH,head.getY()*TILE_WIDTH,TILE_WIDTH,TILE_WIDTH);
        g.fillRect(tail.getX()*TILE_WIDTH,tail.getY()*TILE_WIDTH,TILE_WIDTH,TILE_WIDTH);

        g.setColor(Color.RED);
        g.fillRect(apple.getX()*TILE_WIDTH,apple.getY()*TILE_WIDTH,TILE_WIDTH,TILE_WIDTH);
    }

    private void paintPressStart(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,GRID_WIDTH*TILE_WIDTH,GRID_HEIGHT*TILE_WIDTH);

        if(frame < FRAME_STEP*8){
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(30f));

            Rectangle2D bounds = g.getFontMetrics().getStringBounds("Press space to start", g);
            int Ilength = (int)bounds.getWidth();
            int Iheight = (int)bounds.getHeight();

            g.drawString("Press space to start",GRID_WIDTH*TILE_WIDTH/2-Ilength/2,GRID_HEIGHT*TILE_WIDTH/2-Iheight/2);
        }
    }

    private void paintGameOver(Graphics g){
        paintGame(g);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(30f));

        Rectangle2D bounds = g.getFontMetrics().getStringBounds("Game Over", g);
        int Ilength = (int)bounds.getWidth();
        int Iheight = (int)bounds.getHeight();

        g.drawString("Game Over",GRID_WIDTH*TILE_WIDTH/2-Ilength/2,GRID_HEIGHT*TILE_WIDTH/2-Iheight);

        bounds = g.getFontMetrics().getStringBounds("Press space to restart", g);
        Ilength = (int)bounds.getWidth();
        Iheight = (int)bounds.getHeight();

        g.drawString("Press space to restart",GRID_WIDTH*TILE_WIDTH/2-Ilength/2,GRID_HEIGHT*TILE_WIDTH/2+Iheight);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(state){
            case START -> paintPressStart(g);
            case PLAYING -> paintGame(g);
            case GAME_OVER -> paintGameOver(g);
        }

    }

}
