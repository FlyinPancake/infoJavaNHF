package cake.menu;

import cake.graphics.ScoreBoardWindow;
import cake.graphics.TetrisScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játék főmenüje
 */
public class MainMenu extends JPanel {
    private TetrisScreen screen;
    private StartStopButton startStopButton;
    private PauseResumeButton pauseResumeButton;
    private ScoreBoardWindow scoreBoard;


    /**
     * @param s TetrisScreen
     */
    public MainMenu(TetrisScreen s) {
        Dimension buttonDimensions = new Dimension(100,20 );
        this.scoreBoard = new ScoreBoardWindow();

        this.screen = s;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipady = 10;

        this.add(new JLabel("Main Menu"), gbc);
        gbc.gridy = 1;
        gbc.ipady = 0;
        startStopButton = new StartStopButton(buttonDimensions);

        this.add(startStopButton, gbc);
        gbc.gridy = 2;
        pauseResumeButton = new PauseResumeButton(buttonDimensions);
        this.add(pauseResumeButton,gbc);
        gbc.gridy = 3;
        this.add(new ScoreBoardButton(buttonDimensions),gbc);
        JButton closeButton = new JButton("Exit");
        closeButton.addActionListener(new CloseListener());
        closeButton.setPreferredSize(buttonDimensions);
        gbc.gridy = 4;
        this.add(closeButton, gbc);


    }

    /**
     * Az ablak bezárását kezeli
     */
    private class CloseListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //DO SOMETHING
            System.exit(0);
        }
    }

    /**
     * A játékot indító és megállító gomb
     */
    private class StartStopButton extends JButton {
        boolean started;

        public StartStopButton(Dimension buttonDimensions) {
            setPreferredSize(buttonDimensions);
            this.started = false;
            setText("Start");
            addActionListener(new StartStopActionListener());
        }

        private void switchState() {
            if (!started) {
                screen.requestFocus();
                MainMenu.this.pauseResumeButton.setEnabled(true);
                setText("Stop");
            } else {
                setText("Start");
                MainMenu.this.pauseResumeButton.setEnabled(false);
            }
            started = !started;
        }

        public class StartStopActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!started) {
                    screen.requestFocus();
                    screen.startGame();
                    MainMenu.this.pauseResumeButton.setEnabled(true);
                    setText("Stop");
                } else {
                    setText("Start");
                    screen.abortGame();
                    MainMenu.this.pauseResumeButton.resetButton();
                }
                started = !started;
            }
        }
    }

    /**
     * A játékot szüneteltető és folyatató gomb
     */
    private class PauseResumeButton extends JButton {
        boolean started;
        public PauseResumeButton(Dimension buttonDimension) {
            this.started = false;
            setPreferredSize(buttonDimension);
            setText("Pause");
            addActionListener(new PauseResumeActionListener());
            setEnabled(false);
        }

        private void resetButton(){
            this.started = false;
            setText("Pause");
            setEnabled(false);
        }

        private class PauseResumeActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!started) {
                    screen.pauseGame();
                    setText("Resume");
                } else {
                    setText("Pause");
                    screen.requestFocus();
                    screen.resumeGame();
                }
                started = !started;
            }
        }
    }

    /**
     * A toplista gombja
     */
    private class ScoreBoardButton extends JButton {
        private boolean scoreBoardShown;
        ScoreBoardButton(Dimension d) {
            setPreferredSize(d);
            setText("Highscores");
            addActionListener(new ScoreBoardActionListener());

        }

        private class ScoreBoardActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreBoard.setVisible(true);
            }
        }
    }

    public void gameEnded() {
        startStopButton.switchState();
        pauseResumeButton.resetButton();
    }

    public ScoreBoardWindow getScoreBoard() {
        return scoreBoard;
    }
}

