import javax.swing.JFrame;

public class BBMain {
    public static final int Width = 800;
    public static final int Height = 800;

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GamePanel gamePlay = new GamePanel();

        obj.setBounds(10, 10, Width, Height);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
        obj.setVisible(true);
    }

    public int pget() {
        return 0;
    }

    public int bget() {
        return 3;
    }
}
