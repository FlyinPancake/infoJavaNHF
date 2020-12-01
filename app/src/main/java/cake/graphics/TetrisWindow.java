package cake.graphics;

import cake.menu.MainMenu;
import cake.menu.PieceShower;
import cake.menu.TetrisStatusbar;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Az alkalmazás ablaka
 */
public class TetrisWindow extends JFrame {

    private GridBagLayout mylayout;
    private PieceShower hold;
    private PieceShower next;
    private TetrisStatusbar statusbar;

    public TetrisWindow() {
        setTitle("Tetris like game, but unlicensed by FlyinPancake");
        setSize(600, 600);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mylayout = new GridBagLayout();
        setLayout(mylayout);
        statusbar = new TetrisStatusbar();
        init();
        pack();
        setVisible(true);
    }

    /**
     * Előkészíti az ablakot
     */
    private void init() {
        setLocationRelativeTo(null);
        GridBagConstraints c = new GridBagConstraints();
        

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        // c.weighty = .1;
        c.gridheight = 1;
        c.fill = 0; 

        // c.fill = 1;
        // c.anchor = GridBagConstraints.PAGE_START;
        this.add(new JLabel("Next Piece"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 88;
        c.ipady = 44;
        c.fill = 1; 

        // c.weighty = .2;

        PieceShower next = new PieceShower();
        this.add(next, c);

        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 0;
        c.ipady = 0;
        c.fill = 0; 
        
        this.add(new JLabel("Held piece"), c);

        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 80;
        c.ipady = 44;
        c.fill = GridBagConstraints.BOTH;
        // c.weighty = .2;

        hold = new PieceShower();
        this.add(hold, c);

        c.gridx=0;
        c.gridy=0;
        c.gridheight = 5;
        c.ipadx = 210;
        c.ipady = 462;
        c.fill = 1;
        // c.weighty = 1;
        // c.weightx = 1;
        TetrisScreen scr = new TetrisScreen(next,hold,statusbar);
        this.add(scr, c);

        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 0;
        c.ipadx = 0;
        c.ipady = 0;

        MainMenu mainMenu = new MainMenu(scr);
        this.add(mainMenu,c);
        scr.setMenu(mainMenu);

        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 2;
//        c.fill = GridBagConstraints.BOTH;
//        c.anchor = GridBagConstraints.CENTER;

        this.add(statusbar, c);

        scr.requestFocus();
    }

    public PieceShower getHold() {
        return hold;
    }

    public PieceShower getNext() {
        return next;
    }

    public TetrisStatusbar getStatusbar() {
        return statusbar;
    }
}
