package cake.menu;

import javax.swing.*;
import java.awt.*;

/**
 * A játék alatt a játékosnak hasznos információkat nyújt.
 */
public class TetrisStatusbar extends JPanel {
    private int score;
    private GameState state;
    private JLabel stateLabel;
    private JLabel scoreLabel;
    private enum GameState {
        Playing,Paused,Ended
    }
    public TetrisStatusbar() {
        this.setLayout(new GridLayout(1,2));
        score = 0;
        state = GameState.Ended;
        init();
    }

    private void init(){
        stateLabel = new JLabel();
        this.add(stateLabel);
        updateState();

        scoreLabel = new JLabel();
        setScore(0);
        this.add(scoreLabel);
    }

    /**
     * Frissíti a kiírt állapotot a statusbar belső értéke szerint.
     */
    private void updateState() {
        String currstate = switch (state) {
            case Playing -> "In-game";
            case Ended -> "Ready to start";
            case Paused -> "Paused";
        };
        stateLabel.setText("Game: " + currstate);
        repaint();
    }

    public void setPaused(){
        state = GameState.Paused;
        updateState();
    }

    public void setPlaying(){
        state = GameState.Playing;
        updateState();
    }

    public void setEnded() {
        state = GameState.Ended;
        updateState();
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + this.score);
    }
}
