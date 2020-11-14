package cake;

import javax.swing.JPanel;
import javax.swing.*;

import java.awt.Graphics;
import java.awt.Color;
import cake.game.GamePiece;

public class TetrisScreen extends JPanel {
    public TetrisScreen() {
        repaint();
    }

    public void paint(Graphics g) {

        GamePiece gp = new GamePiece();
        gp.setShape(GamePiece.Tetromino.LShape);

        for (int ii = -2; ii < 3; ii++) {
            for (int jj = -2; jj < 3; jj++) {
                for (int kk = 0; kk < 4; kk++) {
                    if (gp.GetY(kk) == jj && gp.GetX(kk) == -ii) {
                        g.setColor(Color.CYAN);
                        break;
                    }
                    else {
                        g.setColor(Color.DARK_GRAY);
                    }
                }
                g.fillRect(90*ii+200, 90*jj+200, 80, 80);
            }
        }
    }
}
