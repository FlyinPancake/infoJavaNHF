package cake;

import javax.swing.JPanel;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.HashMap;
import java.awt.Color;
import cake.game.GamePiece;
import cake.game.TetrisBoard;
import cake.game.GamePiece.Tetromino;

public class TetrisScreen extends JPanel {

    private final int BLOCK_SIZE = 20;
    private final Color UNKNOWNSHAPECOLOR = Color.MAGENTA;
    private HashMap<GamePiece.Tetromino, Color> colorDict;
    protected TetrisBoard myTb;
    protected GamePiece piece;
    // private PieceShower hold;
    // private PieceShower next;

    public TetrisScreen(PieceShower next, PieceShower hold) {
        initBoard();
        myTb = new TetrisBoard(next, hold);
    }

    private void initBoard() {
        addKeyListener(new TetrisKeyListener());
        piece = new GamePiece();
        colorDict = ReadColorDict();
    }

    private HashMap<Tetromino, Color> ReadColorDict() {
        colorDict = new HashMap<>();
        colorDict.put(Tetromino.NoShape, Color.GRAY);

        colorDict.put(Tetromino.SQShape, Color.getHSBColor(.1666f, 1.0f, 0.94f));

        colorDict.put(Tetromino.ZShape, Color.RED);
        colorDict.put(Tetromino.SShape, Color.getHSBColor(.3333f, .5f, .8f));

        colorDict.put(Tetromino.IShape, Color.getHSBColor(.5f, 1.0f, 0.94f));

        colorDict.put(Tetromino.LShape, Color.getHSBColor(.1111f, 1f, .94f));
        colorDict.put(Tetromino.JShape, Color.BLUE);

        colorDict.put(Tetromino.TShape, Color.getHSBColor(.7778f, 1f, .94f));
        return colorDict;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        PaintTetrisBoard((Graphics2D) g, myTb);
    }

    

    private Graphics2D TetrominoColorChanger(Graphics2D gtd, Tetromino t) {
        if (colorDict.containsKey(t)) {
            gtd.setColor(colorDict.get(t));
        } else {
            gtd.setColor(UNKNOWNSHAPECOLOR);
        }

        return gtd;
    }

    private void PaintTetrisBoard(Graphics2D gtd, TetrisBoard tb) {
        // GFX options
        final int PADDING = 1; // In Pixels
        final int SQUARE_SIZE = BLOCK_SIZE; // In Pixels

        // gtd.setBackground(Color.BLACK);

        for (int xx = 0; xx < tb.getGameBoard().length; xx++) {
            for (int yy = 0; yy < tb.getGameBoard()[xx].length; yy++) {
                TetrominoColorChanger(gtd, tb.shapeAt(xx, yy));

                gtd.fillRect(xx * (PADDING + SQUARE_SIZE),
                        (tb.getGameBoard()[xx].length - yy - 1) * (PADDING + SQUARE_SIZE), SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        if (tb.getCurrentPiece() != null) {
            TetrominoColorChanger(gtd, tb.getCurrentPiece().GetShape());
            for (int ii = 0; ii < 4; ii++) {
                int offset = (PADDING + SQUARE_SIZE);
                int startX = (tb.getCurrentX() + tb.getCurrentPiece().GetX(ii)) * offset;
                int startY = (tb.getGameBoard()[0].length - 1 - (tb.getCurrentY() + tb.getCurrentPiece().GetY(ii)))
                        * offset;
                gtd.fillRect(startX, startY, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private class TetrisKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int ketcode = e.getKeyCode();

            switch (ketcode) {
                case KeyEvent.VK_UP:
                    // piece = piece.rotate();
                    myTb.HardDrop();
                    break;

                case KeyEvent.VK_DOWN:
                    myTb.SoftDrop();
                    break;

                case KeyEvent.VK_LEFT:
                    myTb.MoveLeft();
                    break;

                case KeyEvent.VK_RIGHT:
                    myTb.MoveRight();
                    break;

                case KeyEvent.VK_N:
                    myTb.NewPiece();
                    // System.out.println("New shape: " + myTb.getCurrentPiece().GetShape());
                    break;

                case KeyEvent.VK_Q:
                    myTb.LeftRotatePiece();
                    break;
                
                case KeyEvent.VK_E:
                    myTb.RightRotatePiece();
                    break;

                default:
                    break;
            }
            repaint();
        }
    }
}
