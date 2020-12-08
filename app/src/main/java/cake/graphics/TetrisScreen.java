package cake.graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;

import java.util.HashMap;
import java.awt.Color;
import cake.game.GamePiece;
import cake.game.TetrisBoard;
import cake.game.GamePiece.Tetromino;
import cake.menu.MainMenu;
import cake.menu.PieceShower;
import cake.menu.TetrisStatusbar;

/**
 * A Tetris játék kirajzolása és vezérlése
 */
public class TetrisScreen extends JPanel {

    /**
     * Ha egy Tetromino nem szerepel a colorDictben akkor ezt a színt használja a program
     */
    private final Color UNKNOWNSHAPECOLOR = Color.MAGENTA;
    /**
     * Tetromino kulcsokkal tárolja a hozzájuk tartozó színeket.
     */
    private HashMap<GamePiece.Tetromino, Color> colorDict;
    /**
     * A TetrisScreen belső TetrisBoardja
     */
    protected TetrisBoard myTb;

    /**
     * A jéték időzítője.
     */
    private Timer gameTimer;
    /**
     * A félretett elem PieceShowerje.
     */
    private final PieceShower hold;
    /**
     * A következő elem PieceShowerje.
     */
    private final PieceShower next;
    /**
     * A játék állapotsávja.
     */
    private final TetrisStatusbar statusbar;
    /**
     * A játék főmenüje.
     */
    private MainMenu menu;

    public TetrisScreen(PieceShower next, PieceShower hold, TetrisStatusbar statusbar) {
        initBoard();
        addKeyListener(new TetrisKeyListener());
        this.hold = hold;
        this.next = next;
        this.statusbar = statusbar;
    }

    /**
     * Előkészíti a játékterületet.
     */
    private void initBoard() {
        myTb = new TetrisBoard(next, hold, this);
        colorDict = ReadColorDict();
    }

    /**
     * A beolvassa a színkönyvtárat
     * @return színkönyvtár
     */
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        PaintTetrisBoard((Graphics2D) g, myTb);
    }

    /**
     * Elindítja a játékot és kezel mindent ami ezzel kapcsolatos
     */
    public void startGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        initBoard();
        gameTimer = new Timer(500, new GameCycle());
        myTb.NewPiece();
        statusbar.setPlaying();
        gameTimer.start();
    }

    /**
     * Szünetelteti a játékot
     */
    public void pauseGame() {
        gameTimer.stop();
        statusbar.setPaused();
    }

    /**
     * Folytatja a szüneteltetett játékot.
     */
    public void resumeGame() {
        gameTimer.restart();
        statusbar.setPlaying();
    }

    /**
     * Befejezi a játékot és elindítja a játék vége utáni feladatokat.
     */
    public void endGame() {
        menu.gameEnded();
        statusbar.setEnded();
        gameTimer.stop();
        setHiScore(myTb.getScore());
    }

    /**
     * Megszakítja a játékot és indítja a játék utáni feladatokat.
     */
    public void abortGame() {
        gameTimer.stop();
        statusbar.setEnded();
        setHiScore(myTb.getScore());
    }

    /**
     * Elküldi a pontszámot a toplistának
     * @param score pontszám
     */
    private void setHiScore(int score) {
        menu.getScoreBoard().addScore(score);
    }

    /**
     * Megváltoztatja a Graphics2D tollszínét
     * @param gtd Graphics2D
     * @param t Tetromino
     */
    private void TetrominoColorChanger(Graphics2D gtd, Tetromino t) {
        gtd.setColor(colorDict.getOrDefault(t, UNKNOWNSHAPECOLOR));

    }

    /**
     * Kirajzolja a játékteret
     * @param gtd Graphics2D
     * @param tb tetrisBoard, amit kirajzol
     */
    private void PaintTetrisBoard(Graphics2D gtd, TetrisBoard tb) {
        // GFX options
        final int PADDING = 1; // In Pixels
        final int SQUARE_SIZE = 20; // In Pixels

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

    /**
     * A gameTimer actionListenerje
     */
    private class GameCycle implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent e) {
            myTb.softDrop();
            repaint();
        }
    }

    /**
     * @return the gameTimer
     */
    public Timer getGameTimer() {
        return gameTimer;
    }

    /**
     * A játék bemeneteit olvassa be
     */
    private class TetrisKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_UP:
                    myTb.HardDrop();
                    break;

                case KeyEvent.VK_DOWN:
                    myTb.softDrop();
                    break;

                case KeyEvent.VK_LEFT:
                    myTb.MoveLeft();
                    break;

                case KeyEvent.VK_RIGHT:
                    myTb.MoveRight();
                    break;

                case KeyEvent.VK_N:
                    myTb.NewPiece();
                    break;

                case KeyEvent.VK_Q:
                    myTb.LeftRotatePiece();
                    break;
                
                case KeyEvent.VK_E:
                    myTb.RightRotatePiece();
                    break;

                case KeyEvent.VK_S:
                    startGame();
                    break;

                case KeyEvent.VK_SPACE:
                    myTb.holdPiece();
                    break;

                default:
                    break;
            }
            repaint();
        }

    }

    /**
     * @return the statusbar
     */
    public TetrisStatusbar getStatusbar() {
        return statusbar;
    }

    /**
     * @param menu A beállítja a menüt ami a screenhez tartozik
     */
    public void setMenu(MainMenu menu) {
        this.menu = menu;
    }
}
