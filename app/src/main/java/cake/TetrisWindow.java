package cake;

import javax.swing.JFrame;
import java.awt.GridLayout;

public class TetrisWindow extends JFrame{
    public TetrisWindow() {
        setTitle("Tetris like game, but unlicensed by FlyinPancake");
        setSize(800,600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        init();
    }

    private void init() {
        setLocationRelativeTo(null);

        setLayout(new GridLayout(1,1,0,0));

        TetrisScreen scr = new TetrisScreen();
        add(scr);

        setVisible(true);
    }
}
