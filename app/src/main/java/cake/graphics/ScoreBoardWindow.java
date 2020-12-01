package cake.graphics;

import cake.menu.ScoreBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A toplista abalaka és kezelése
 */
public class ScoreBoardWindow extends JFrame {
    private JButton resetButton;
    private JButton hideButton;
    private ScoreBoard scoreBoard;
    private String playername;
    public ScoreBoardWindow() {
        scoreBoard = new ScoreBoard();
        setLayout(new BorderLayout());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        JTable hiTable = new JTable(scoreBoard);
        hiTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(hiTable);
        this.add(scrollPane,BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        hideButton = new JButton("Hide");
        hideButton.addActionListener(new HideButtonActionListener());
        southPanel.add(hideButton);
        resetButton = new JButton("Reset");
        southPanel.add(resetButton);
        resetButton.addActionListener(new ResetButtonActionListener());
        this.add(southPanel,BorderLayout.SOUTH);

        setVisible(false);
        this.setSize(100,200);
        this.setResizable(false);
    }

    /**
     * Bekéri a játékostól a monogramját és ha elég magas a pontszáma, beveszi a
     * toplistába, majd a toplistát el is menti.
     * @param score elért pontszám
     */
    public void addScore(int score) {
        String name;
        try{
            name = askPlayername();
            scoreBoard.addHiScore(name.toUpperCase(), score);

        } catch (ScoreNameExceedsCharLimitException e) {
            JFrame f=new JFrame();
            JOptionPane.showMessageDialog(f,"Use at most 3 letters in your initials","Bad name",JOptionPane.WARNING_MESSAGE);
            addScore(score);
        }

    }

    /**
     * Bekéri a játékos monogramját, a kalsszikus játék szellemében 3 karaktert
     * @return 1-3 karakter hosszú név.
     * @throws ScoreNameExceedsCharLimitException ha a név hosszabb mint 3 karakter kivételt dob
     */
    private String askPlayername() throws ScoreNameExceedsCharLimitException {
        JFrame f=new JFrame();
        String name=JOptionPane.showInputDialog(f,"Enter Initials (3)");
        if (name == null || name.length() == 0){
            name = askPlayername();
            return name;
        } else if (name.length() >3){
            throw new ScoreNameExceedsCharLimitException();
        } else {
            return name;
        }
    }

    /**
     * A toplista eltüntetőgombjának az actionListener megvalósítása
     */
    private class HideButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }

    /**
     * A toplista visszaállító gombjának az actionListener megvalósítása
     */
    private class ResetButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            scoreBoard.resetHiScores();
            scoreBoard.fireTableDataChanged();
        }
    }
}
