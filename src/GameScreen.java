import Picture.PictureManager;
import config.AiConfig;
import config.Configuration;
import config.PlayerConfig;
import player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GameScreen extends JPanel {
    private GameState state = GameState.START;

    //varibales du jeu
    public Configuration config = new PlayerConfig();
    private int currentFrame = 0;

    //variablesp our le jeu
    int frame = 0;

    int slowFrame = 0;
    private Snake snakeGame = new Snake(config.getGRID_WIDTH(),config.getGRID_HEIGHT());
    private Player player = new Human(snakeGame);
    private PictureManager pictures = new PictureManager(getClass().getResourceAsStream("/Picture/sprite.png"),16);

    private BufferedImage[][] background = new BufferedImage[config.getGRID_WIDTH()][config.getGRID_HEIGHT()];

    public GameScreen(){
        //les images
        loadTextures();
        setupBackground();
        //pour la fenetre
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
                    snakeGame = new Snake(config.getGRID_WIDTH(),config.getGRID_HEIGHT());
                    //todo reset la le joueur
                }

                if(state == GameState.PLAYING) player.keyPressed(e);
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
                if (frame++ == config.getFRAME_STEP()*16) {
                    frame = 0;
                }
                this.repaint();

                try {
                    Thread.sleep(config.getTIME_TEXT_BLINK_SPEED());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            case PLAYING:
                if (frame++ == config.getFRAME_STEP()) {
                    snakeGame.changeOrientation(player.play());
                    boolean gameover = snakeGame.iteration();
                    frame = 0;
                    if(currentFrame ++ == config.getFRAME_SKIP()) {
                        this.repaint();
                        currentFrame = 0;
                    }else{
                        frame = config.getFRAME_STEP();
                    }


                    if(!gameover){
                        System.out.println("game over");
                        state = GameState.GAME_OVER;
                        frame = 0;
                    }
                    if(slowFrame-- < 0) slowFrame = 0;
                    if(snakeGame.squaredDistanceToApple()<=16){
                        slowFrame = 5;
                    }
                }

                try {
                    Thread.sleep(slowFrame>=0?config.getTIME_SLOW_SPEED():config.getTIME_SPEED());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(config.getGRID_WIDTH()*config.getTILE_WIDTH(),config.getGRID_HEIGHT()*config.getTILE_WIDTH());
    }

    private void loadTextures(){
        pictures.addFromPicture("background",1,5);
        pictures.addFromPicture("apple",0,5);

        pictures.addFromPicture("head_1",0,0);
        pictures.addFromPicture("head_2",1,0);
        pictures.addFromPicture("head_3",2,0);
        pictures.addFromPicture("head_4",3,0);

        pictures.addFromPicture("tail_1",0,4);
        pictures.addFromPicture("tail_2",1,4);
        pictures.addFromPicture("tail_3",2,4);
        pictures.addFromPicture("tail_4",3,4);

        pictures.addFromPicture("corps_f1_t1",0,1);
        pictures.addFromPicture("corps_f2_t2",1,1);
        pictures.addFromPicture("corps_f3_t3",2,1);
        pictures.addFromPicture("corps_f4_t4",3,1);

        pictures.addFromPicture("corps_f1_t2",0,2);
        pictures.addFromPicture("corps_f2_t3",1,2);
        pictures.addFromPicture("corps_f3_t4",2,2);
        pictures.addFromPicture("corps_f4_t1",3,2);

        pictures.addFromPicture("corps_f1_t4",0,3);
        pictures.addFromPicture("corps_f2_t1",1,3);
        pictures.addFromPicture("corps_f3_t2",2,3);
        pictures.addFromPicture("corps_f4_t3",3,3);

        //background
        pictures.addFromPicture("bg_tl",4,0);
        pictures.addFromPicture("bg_ml",4,1);
        pictures.addFromPicture("bg_bl",4,2);

        pictures.addFromPicture("bg_tm",5,0);
        pictures.addFromPicture("bg_al",5,1);
        pictures.addFromPicture("bg_bm",5,2);

        pictures.addFromPicture("bg_tr",6,0);
        pictures.addFromPicture("bg_mr",6,1);
        pictures.addFromPicture("bg_br",6,2);
    }

    private void setupBackground(){
        for(int i = 0 ; i < config.getGRID_WIDTH(); i++){
            for(int j = 0 ; j < config.getGRID_HEIGHT(); j++){
                //on affiche le tableau
                if(i == 0 && j == 0) // corners
                    background[i][j] = pictures.getBufferedPictureFromName("bg_tl");
                else if(i == config.getGRID_WIDTH()-1 && j == 0)
                    background[i][j] = pictures.getBufferedPictureFromName("bg_tr");
                else if(i == 0 && j == config.getGRID_HEIGHT()-1)
                    background[i][j] = pictures.getBufferedPictureFromName("bg_bl");
                else if(i == config.getGRID_WIDTH()-1 && j == config.getGRID_HEIGHT()-1)
                    background[i][j] = pictures.getBufferedPictureFromName("bg_br");
                else if(i == 0)
                    background[i][j] = pictures.getBufferedPictureFromName("bg_ml");
                else if(i == config.getGRID_WIDTH()-1)
                    background[i][j] = pictures.getBufferedPictureFromName("bg_mr");
                else if(j == 0)
                    background[i][j] = pictures.getBufferedPictureFromName("bg_tm");
                else if(j == config.getGRID_HEIGHT()-1)
                    background[i][j] = pictures.getBufferedPictureFromName("bg_bm");
                else{
                    background[i][j] = Math.random()>0.5?pictures.getBufferedPictureFromName("background"):pictures.getBufferedPictureFromName("bg_al");
                }
            }
        }
    }

    private void paintGame(Graphics g){

        //on affiche le tableau

        Vector2Int head = snakeGame.getHeadPos();
        Vector2Int tail = snakeGame.getTailPos();
        Vector2Int apple = snakeGame.getApplePos();


        //on affiche le corps
        Vector2Int back1 = new Vector2Int(tail.getX(),tail.getY());
        Vector2Int back0 = back1;
        switch(snakeGame.getPlateau(back1.getX(),back1.getY())){
            case 1 -> back0 = new Vector2Int((back1.getX() + 1)%config.getGRID_WIDTH(), back1.getY());
            case 2 -> back0 = new Vector2Int(back1.getX() , (back1.getY() + 1 )%config.getGRID_HEIGHT());
            case 3 -> back0 = new Vector2Int((back1.getX() - 1 + config.getGRID_WIDTH())%config.getGRID_WIDTH(), back1.getY());
            case 4 -> back0 = new Vector2Int(back1.getX() , (back1.getY() -1 + config.getGRID_HEIGHT())%config.getGRID_HEIGHT());
        }

        while(back0.getX() != head.getX() || back1.getY() != head.getY()){
            String picName = "corps_f"+snakeGame.getPlateau(back1.getX(),back1.getY())+"_t"+ snakeGame.getPlateau(back0.getX(),back0.getY());
            g.drawImage(pictures.getBufferedPictureFromName(picName),back0.getX()*config.getTILE_WIDTH(),back0.getY()*config.getTILE_WIDTH(),config.getTILE_WIDTH(),config.getTILE_WIDTH(),null);
            back1 = new Vector2Int(back0.getX(),back0.getY());
            switch(snakeGame.getPlateau(back1.getX(),back1.getY())){
                case 1 -> back0 = new Vector2Int((back1.getX() + 1)%config.getGRID_WIDTH(), back1.getY());
                case 2 -> back0 = new Vector2Int(back1.getX() , (back1.getY() + 1)%config.getGRID_HEIGHT());
                case 3 -> back0 = new Vector2Int((back1.getX() - 1 + config.getGRID_WIDTH())%config.getGRID_WIDTH(), back1.getY());
                case 4 -> back0 = new Vector2Int(back1.getX() , (back1.getY() -1 + config.getGRID_HEIGHT())%config.getGRID_HEIGHT());
            }
        }

        //on print la pomme, la tete et la queue
        g.setColor(Color.BLUE);
        g.drawImage(pictures.getBufferedPictureFromName("head_"+snakeGame.getDirrection()),head.getX()*config.getTILE_WIDTH(),head.getY()*config.getTILE_WIDTH(),config.getTILE_WIDTH(),config.getTILE_WIDTH(),null);
        g.drawImage(pictures.getBufferedPictureFromName("tail_"+snakeGame.getPlateau(tail.getX(),tail.getY())),tail.getX()*config.getTILE_WIDTH(),tail.getY()*config.getTILE_WIDTH(),config.getTILE_WIDTH(),config.getTILE_WIDTH(),null);

        g.setColor(Color.RED);
        g.drawImage(pictures.getBufferedPictureFromName("apple"),apple.getX()*config.getTILE_WIDTH(),apple.getY()*config.getTILE_WIDTH(),config.getTILE_WIDTH(),config.getTILE_WIDTH(),null);
    }

    private void paintPressStart(Graphics g){

        if(frame < config.getFRAME_STEP()*8){
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(30f));

            Rectangle2D bounds = g.getFontMetrics().getStringBounds("Press space to start", g);
            int Ilength = (int)bounds.getWidth();
            int Iheight = (int)bounds.getHeight();

            g.drawString("Press space to start",config.getGRID_WIDTH()*config.getTILE_WIDTH()/2-Ilength/2,config.getGRID_HEIGHT()*config.getTILE_WIDTH()/2-Iheight/2);
        }
    }

    private void paintGameOver(Graphics g){
        paintGame(g);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(30f));

        Rectangle2D bounds = g.getFontMetrics().getStringBounds("Game Over", g);
        int Ilength = (int)bounds.getWidth();
        int Iheight = (int)bounds.getHeight();

        g.drawString("Game Over",config.getGRID_WIDTH()*config.getTILE_WIDTH()/2-Ilength/2,config.getGRID_HEIGHT()*config.getTILE_WIDTH()/2-Iheight);

        bounds = g.getFontMetrics().getStringBounds("Press space to restart", g);
        Ilength = (int)bounds.getWidth();
        Iheight = (int)bounds.getHeight();

        g.drawString("Press space to restart",config.getGRID_WIDTH()*config.getTILE_WIDTH()/2-Ilength/2,config.getGRID_HEIGHT()*config.getTILE_WIDTH()/2+Iheight);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0,0,config.getGRID_WIDTH()*config.getTILE_WIDTH(),config.getGRID_HEIGHT()*config.getTILE_WIDTH());
        for(int i = 0 ; i < config.getGRID_WIDTH(); i++){
            for(int j = 0 ; j < config.getGRID_HEIGHT(); j++){
                //on affiche le tableau
                g.drawImage(background[i][j],i*config.getTILE_WIDTH(),j*config.getTILE_WIDTH(),config.getTILE_WIDTH(),config.getTILE_WIDTH(),null);
            }
        }
        switch(state){
            case START -> paintPressStart(g);
            case PLAYING -> paintGame(g);
            case GAME_OVER -> paintGameOver(g);
        }

    }

}
