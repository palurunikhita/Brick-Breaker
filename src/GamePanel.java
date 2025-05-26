import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.awt.geom.Ellipse2D;

public class GamePanel extends JPanel implements KeyListener,ActionListener
{
    private static final long serialVersionUID = 1L;

    private boolean play=false;
    private boolean exit=false;
    private boolean restart=false;
    private int score=0;

    private Timer timer;
    private int delay=4;

    private int playerX=310;

    private int ballposX=120;
    private int ballposY=350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    private MapGenerator Map;
    BBMain b=  new BBMain();
    Color c;



    JFrame theFrame=new JFrame("Brick Breaker");

    public GamePanel()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer=new Timer(delay,this);
        timer.start();

        Map=new MapGenerator(3,3);
    }
    int level=1 ;
    public void paint(Graphics g)
    {
        System.out.println("Paint method called");
        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, BBMain.Width, BBMain.Height);

        //borders
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 792, 4);
        g.fillRect(0, 4, 3, 792);
        g.fillRect(783, 0, 3, 792);

        //paddle
        int i =b.pget();
        if(i==0)
        {
            c= Color.BLUE;
        }
        else if(i==1)
        {
            c= Color.GREEN;
        }
        else if(i==2)
        {
            c= Color.YELLOW;
        }
        else if(i==3)
        {
            c= Color.RED;
        }
        else if(i==4)
        {
            c= Color.MAGENTA;
        }
        else
        {
            c= Color.CYAN;
        }

        g.setColor(c);
        g.fillRect(playerX, 730, 100, 8);

        //bricks
        Map.draw((Graphics2D) g);

        g.setColor(Color.GREEN);
        g.drawString("LEVEL :"+ level, 100, 30);

        g.setColor( Color.GREEN);
        g.drawString("Bricks : "+Map.totalBricks, 300,30);
        g.drawString("Score  : "+score, 600,30);

        //ball
        i =b.bget();
        if(i==0)
        {
            c= Color.BLUE;
        }
        else if(i==1)
        {
            c= Color.GREEN;
        }
        else if(i==2)
        {
            c= Color.YELLOW;
        }
        else if(i==3)
        {
            c= Color.RED;
        }
        else if(i==4)
        {
            c= Color.MAGENTA;
        }
        else
        {
            c= Color.CYAN;
        }


        g.setColor(c);
        g.fillOval(ballposX, ballposY, 20,20);

        if(ballposY > 730)
        {
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Press ENTER to restart", 230, 300);
            g.drawString("Press Esc to exit", 300, 400);
        }

        if(Map.totalBricks<=0)
        {
            play=false;
            ballposX=120;
            ballposY=450;
            level++;
            g.setColor(Color.YELLOW);
            g.drawString("LEVEL :"+ level, 300, 330);
            Map=new MapGenerator(7,9);
        }

        if(exit)
        {
            g.clearRect(0, 0, BBMain.Width, BBMain.Height);
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("You've exited the game", 250, 200);
            g.setColor(Color.BLUE);
            g.drawString("Your score is  : "+score, 300, 300);
            g.drawString("Thank you !!!" , 330, 400);
        }
        if(restart)
        {
            Map=new MapGenerator(3,3);
            restart=false;
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play)
        {
            if(ballposX<0) {
                ballXdir=-ballXdir;
            }
            if(ballposX>770) {
                ballXdir=-ballXdir;
            }
            if(ballposY<0) {
                ballYdir=-ballYdir;
            }
            //if()

            //Shape circle = new Ellipse2D.Double(ballposX,ballposY,19,19);
            Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
            Rectangle paddleRect=new Rectangle(playerX, 730, 100, 8);

            if(ballRect.intersects(paddleRect)) {
                ballYdir=-ballYdir;
            }

            A:for(int i=0;i<Map.map.length;i++) {
                for(int j=0;j<Map.map[0].length;j++) {

                    if(Map.map[i][j]>0) {

                        int width=Map.brickWidth;
                        int height=Map.brickHeight;
                        int brickposX=j*width+Map.vertPad;
                        int brickposY=i*height+Map.horPad;

                        Rectangle brickRect=new Rectangle(brickposX,brickposY,width,height);

                        if(ballRect.intersects(brickRect)) {
                            Map.map[i][j]=0;
                            Map.totalBricks--;
                            score++;
                            //Map.setBrick(0, i, j);

                            if(ballposX+19<=brickRect.x || ballposX+1>=brickRect.x+brickRect.width) {
                                ballXdir=-ballXdir;
                            }
                            else{
                                ballYdir=-ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;
        }
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) {

            if(playerX>=690) {
                playerX=690;
            }else {
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT) {
            if(playerX<10) {
                playerX=10;
            }else {
                moveLeft();
            }
        }
        repaint();
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                play=true;
                restart=true;
                ballposX= 120;
                ballposY=300;
                ballXdir=-1;
                ballYdir=-2;
                playerX=100;
                level=1;
                score=0;
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
            play=false;

            exit=true;
        }

    }
    public void moveRight() {
        play=true;
        playerX+=20;
    }
    public void moveLeft() {
        play=true;
        playerX-=20;
    }
    @Override
    public void keyTyped(KeyEvent e) {	}
    @Override
    public void keyReleased(KeyEvent e) {	}
}
