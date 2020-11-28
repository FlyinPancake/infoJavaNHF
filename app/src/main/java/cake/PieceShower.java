package cake;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;

import cake.game.GamePiece;
import cake.game.GamePiece.Tetromino;

public class PieceShower extends JPanel{
    private GamePiece currentPiece;
    private int squareSize;
    private HashMap<Tetromino, Color> colorDict;

    public PieceShower() {
        this.squareSize = 20;
        ReadColorDict();
        currentPiece = new GamePiece();
        currentPiece.setShape(Tetromino.NoShape);
    }

	@Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);

        drawPiece((Graphics2D) g);
    }
    
    private HashMap<Tetromino, Color> ReadColorDict() {
        colorDict = new HashMap<>();
        colorDict.put(Tetromino.NoShape, Color.WHITE);

        colorDict.put(Tetromino.SQShape, Color.getHSBColor(.1666f, 1.0f, 0.94f));

        colorDict.put(Tetromino.ZShape, Color.RED);
        colorDict.put(Tetromino.SShape, Color.getHSBColor(.3333f, .5f, .8f));

        colorDict.put(Tetromino.IShape, Color.getHSBColor(.5f, 1.0f, 0.94f));

        colorDict.put(Tetromino.LShape, Color.getHSBColor(.1111f, 1f, .94f));
        colorDict.put(Tetromino.JShape, Color.BLUE);

        colorDict.put(Tetromino.TShape, Color.getHSBColor(.7778f, 1f, .94f));
        return colorDict;
    }

    private Graphics2D TetrominoColorChanger(Graphics2D gtd, Tetromino t) {
        if (colorDict.containsKey(t)) {
            gtd.setColor(colorDict.get(t));
        } else {
            gtd.setColor(Color.magenta);
        }

        return gtd;
    }
    private void drawPiece(Graphics2D gtd) {
        Tetromino[][] smallboard = new Tetromino[2][4];
        for (int yy= 0; yy< 2; yy++) {
            for (int xx = 0; xx < 4; xx++) {
                smallboard[yy][xx] = Tetromino.NoShape;
            }
        }

        for (int i = 0; i < 4; i++) {
            smallboard[currentPiece.GetY(i)+1][currentPiece.GetX(i)+1] = currentPiece.GetShape();
        }

        for (int yy= 0; yy< 2; yy++) {
            for (int xx = 0; xx < 4; xx++) {
                int startx = xx * (squareSize + 1);
                int starty = yy * (squareSize + 1);
                TetrominoColorChanger(gtd, smallboard[yy][xx]);
                if (smallboard[yy][xx] != Tetromino.NoShape) {
                    gtd.fillRect(startx, starty, squareSize, squareSize);
                }
            }
        }
    }

    public void setPiece(GamePiece gp) {
        currentPiece = gp;
        repaint();
    }
}
