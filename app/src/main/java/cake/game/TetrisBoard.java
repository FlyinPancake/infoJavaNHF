package cake.game;

import java.util.ArrayList;
import java.util.Random;

import cake.PieceShower;
import cake.game.GamePiece.Tetromino;

public class TetrisBoard {
    private final int TETRIS_WIDTH = 10;
    private final int TETRIS_HEIGHT = 22;

    private PieceShower hold;
    private PieceShower next;

    private GamePiece currentPiece;
    private int currentX;
    private int currentY;
    private GamePiece.Tetromino[][] gameBoard;
    private int score;
    private Random random;

    public TetrisBoard(PieceShower holdPiece, PieceShower nextPiece) {
        gameBoard = new GamePiece.Tetromino[TETRIS_WIDTH][TETRIS_HEIGHT];
        InitGameBoard();
        score =0;
        next = nextPiece;
        hold = holdPiece;
    }

    private void InitGameBoard() {
        for (int ii= 0; ii< gameBoard.length; ii++) {
            for (int jj = 0; jj < gameBoard[ii].length; jj++) {
                gameBoard[ii][jj] = Tetromino.NoShape;
            }
        }
        random = new Random();
        NewPiece();
    }

    public GamePiece.Tetromino shapeAt(int x, int y) {
        return gameBoard[x][y];
    }

    private boolean attemptMove(int mvX, int mvY) {
        return attemptMove(mvX, mvY, currentPiece);
    }

    private boolean attemptMove(int mvX, int mvY, GamePiece gp) {
        for (int i = 0; i < 4; i++) {
            int xx = currentX + gp.GetX(i) + mvX;
            int yy = currentY + gp.GetY(i) + mvY;

            if (xx < 0 || xx >= TETRIS_WIDTH || yy < 0 || yy >= TETRIS_HEIGHT) { 
                return false;
            } else if (shapeAt(xx, yy) != Tetromino.NoShape) {
                return false;
            }
        }

        currentX += mvX;
        currentY += mvY;

        return true;
    }

    private ArrayList<Integer> checkFullLines(){
        ArrayList<Integer> fulls = new ArrayList<>();
        for (int yy = 0; yy < TETRIS_HEIGHT; yy++) {
            for (int xx = 0; xx < TETRIS_WIDTH; xx++) {
                if (gameBoard[xx][yy] == Tetromino.NoShape) {
                    break;
                } else if (xx == gameBoard.length -1) {
                    fulls.add(yy);
                }
            }
        }
        return fulls;

    }

    private void removeLine(int index) {
        for (int yy = index+1; yy < TETRIS_HEIGHT; yy++) {
            for (int xx = 0; xx < TETRIS_WIDTH; xx++) {
                gameBoard[xx][yy-1] = gameBoard[xx][yy];
            }
        }
    }

    private int removeFullLines(ArrayList<Integer> fullLines) {
        int score = 0;
        //TODO Analize full lines
        for (int i = fullLines.size() - 1; i >= 0; i--) {
            removeLine(fullLines.get(i));
        }
        return score;
    }

    private void PieceDropped() {
        //piece masolas a boardra
        for (int i = 0; i < 4; i++) {
            int xx = currentX + currentPiece.GetX(i);
            int yy = currentY + currentPiece.GetY(i);
            gameBoard[xx][yy] = currentPiece.GetShape();
        }
        //teli sorok torlese, pontozas
        ArrayList<Integer> fullLines = checkFullLines();
        if (fullLines.size() != 0){
            score += removeFullLines(fullLines);
        }
        //uj elem inditasa
        NewPiece();
    }

    public void SoftDrop() { 
        if (!attemptMove(0, -1)){
            PieceDropped();
        }
    }

    public void HardDrop() {
        while(attemptMove(0, -1));
        PieceDropped();
    }

    public void MoveLeft() { 
        if (attemptMove(-1, 0)){
            
        }
    }

    public void MoveRight() { 
        if (attemptMove(1, 0)) {
            
        }
    }

    public boolean LeftRotatePiece() {
        GamePiece rotable = currentPiece.rotateLeft();
        if (attemptMove(0, 0, rotable)) {
            currentPiece = rotable;
            return true;
        }
        return false;
    }

    public boolean RightRotatePiece() {
        GamePiece rotable = currentPiece.rotateRight();
        if (attemptMove(0, 0, rotable)) {
            currentPiece = rotable;
            return true;
        }
        return false;
    }


    
    public void NewPiece(){
        currentPiece = new GamePiece();
        currentPiece.randomizeShape();
        currentY = TETRIS_HEIGHT - 1;
        currentX = TETRIS_WIDTH/2 - 1;

    }

    /**
     * @return the gameBoard
     */
    public GamePiece.Tetromino[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * @return the currentPiece
     */
    public GamePiece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * @return the currentX
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * @return the currentY
     */
    public int getCurrentY() {
        return currentY;
    }

    /**
     * @return the tETRIS_HEIGHT
     */
    public int getTETRIS_HEIGHT() {
        return TETRIS_HEIGHT;
    }

    /**
     * @return the tETRIS_WIDTH
     */
    public int getTETRIS_WIDTH() {
        return TETRIS_WIDTH;
    }
}
