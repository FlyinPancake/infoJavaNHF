package cake.game;

import java.util.ArrayList;

import cake.menu.PieceShower;
import cake.graphics.TetrisScreen;
import cake.game.GamePiece.Tetromino;

/**
 * A játékteret és az aktuális elem mozgását kezeli
 */
public class TetrisBoard {
    private final int TETRIS_WIDTH = 10;
    private final int TETRIS_HEIGHT = 22;

    private GamePiece holdGamePiece;
    private final PieceShower hold;

    private GamePiece nextGamePiece; 
    private final PieceShower next;

    private GamePiece currentPiece;
    private int currentX;
    private int currentY;

    private final GamePiece.Tetromino[][] gameBoard;

    private boolean running;

    private final TetrisScreen myScreen;

    private int score;


    public TetrisBoard(PieceShower nextPiece, PieceShower holdPiece, TetrisScreen myScreen) {
        gameBoard = new GamePiece.Tetromino[TETRIS_WIDTH][TETRIS_HEIGHT];
        initGameBoard();
        score =0;
        next = nextPiece;
        hold = holdPiece;
        this.myScreen = myScreen;
        this.running = true;
    }

    /**
     * Előkészíti a játékterületet. Minden blokk eredetileg 'NoShape'
     */
    private void initGameBoard() {
        for (int ii= 0; ii< gameBoard.length; ii++) {
            for (int jj = 0; jj < gameBoard[ii].length; jj++) {
                gameBoard[ii][jj] = Tetromino.NoShape;
            }
        }
        // NewPiece();
    }

    /**
     * Megadja, hogy x,y koordinátán milyen elem blokkja található.
     * Színezéshez használtam.
     * @param x x koordináta
     * @param y y koordináta
     * @return Az adott X,Y-on lévő Tetromino
     */
    public GamePiece.Tetromino shapeAt(int x, int y) {
        return gameBoard[x][y];
    }

    /**
     * Az aktuális elemet próbálja mozgatni a megadott koordinátákkal.
     * Másodlagos funkciója 0,0 megadásával megállapítható, hogy az a hely ahol az elem van,
     * szabályos-e
     * @param mvX mozgatás X irányba
     * @param mvY mozgatás Y irányba
     * @return sikerült-e a mozgás
     */
    private boolean attemptMove(int mvX, int mvY) {
        return attemptMove(mvX, mvY, currentPiece);
    }

    /**
     * Tetszőleges elemet próbálja mozgatni a megadott koordinátákkal.
     * Másodlagos funkciója 0,0 megadásával megállapítható, hogy az a hely,
     * ahol az elem van szabályos-e
     * @param mvX mozgatás X irányba
     * @param mvY mozgatás Y irányba
     * @return sikerült-e a mozgás
     */
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

    /**
     * Végignézi a sorokat, hogy van -e köztük teli.
     * Ha igen egy ArrayListben visszaadja a sorindexük
     * @return ArrayList a teli sorok indexével
     */
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

    /**
     * index indexű sor eltávolítása, a fentebbi sorokat egyel lejjebb hozza
     * @param index Törlendő sor száma
     */
    private void removeLine(int index) {
        for (int yy = index+1; yy < TETRIS_HEIGHT; yy++) {
            for (int xx = 0; xx < TETRIS_WIDTH; xx++) {
                gameBoard[xx][yy-1] = gameBoard[xx][yy];
            }
        }
    }

    /**
     * Vezényli a sorok törlését, és pontokat ad.
     * @param fullLines a checkFullLines() által adott arrayList
     * @return Kapott pontok
     */
    private int removeFullLines(ArrayList<Integer> fullLines) {
        int score = 0;
        if (fullLines.size() == 4) {
            score += 800;
        } else {
            score += 100 * fullLines.size();
        }
        for (int i = fullLines.size() - 1; i >= 0; i--) {
            removeLine(fullLines.get(i));
        }
        return score;
    }

    /**
     * Amikor leérkezik egy elem ez a metódus vezényli a sortörlést, pontozást
     * és az új elem indítását is.
     */
    private void pieceDropped() {
        myScreen.getGameTimer().stop();
        //piece masolas a boardra
        for (int i = 0; i < 4; i++) {
            int xx = currentX + currentPiece.GetX(i);
            int yy = currentY + currentPiece.GetY(i);
            gameBoard[xx][yy] = currentPiece.GetShape();
        }
        //teli sorok torlese, pontozas
        ArrayList<Integer> fullLines = checkFullLines();
        if (!fullLines.isEmpty()) {
            score += removeFullLines(fullLines);
        }
        //uj elem inditasa
        myScreen.getGameTimer().restart();
        myScreen.getStatusbar().setScore(score);
        if (running) {
            NewPiece();
        }
    }

    /**
     * Egyel lejjebb dobja az aktuális játékelemet.
     */
    public void softDrop() {
        if (!attemptMove(0, -1)) {
            pieceDropped();
        }
    }

    /**
     * Addig dobja lefelé a játékelemet, amíg nem ér "földet".
     */
    public void HardDrop() {
        while(attemptMove(0, -1));
        pieceDropped();
    }

    /**
     * Egyel balra mozgatja a játékelemet.
     */
    public void MoveLeft() { 
        if (attemptMove(-1, 0)){
            
        }
    }

    /**
     * Egyel jobbta mozgatja a játékelemet.
     */
    public void MoveRight() { 
        if (attemptMove(1, 0)) {
            
        }
    }

    /**
     * Balra forgatja a játékelemet.
     */
    public void LeftRotatePiece() {
        GamePiece rotable = currentPiece.rotateLeft();
        if (attemptMove(0, 0, rotable)) {
            currentPiece = rotable;
        }
    }

    /**
     * Jobbra fordítja a játékelemet.
     */
    public void RightRotatePiece() {
        GamePiece rotable = currentPiece.rotateRight();
        if (attemptMove(0, 0, rotable)) {
            currentPiece = rotable;
        }
    }

    /**
     * Véletlenszerű új játékelem indítását vezérli, ha a játék folyamatban van.
     * Ezen kívül a következő elem ablakot is ez vezérli.
     */
    public void NewPiece() {
        if (!running){
            return;
        }
        if (nextGamePiece == null) {
            nextGamePiece = new GamePiece();
            nextGamePiece.randomizeShape();
        }
        GamePiece gPiece = nextGamePiece;
        StartPiece(gPiece);
        nextGamePiece = new GamePiece();
        nextGamePiece.randomizeShape();
        next.setPiece(nextGamePiece);
    }

    /**
     * Elindítja a kapott elemet a pályára.
     * @param gPiece indítandó elem
     */
    public void StartPiece(GamePiece gPiece){
        currentY = TETRIS_HEIGHT - 1;
        currentX = TETRIS_WIDTH/2 - 1;
        if (!attemptMove(0,0,gPiece)) {
            myScreen.endGame();
            running = false;
            return;
        }
        currentPiece = gPiece;
    }

    /**
     * A félrerakott elemet kezeli, ha van akkor azt indítja el a pályára,
     * ha nincs akkor egy véletlenszíerűt a startPiece-el.
     * minden esetben elteszi az aktuális elemet.
     */
    public void holdPiece(){
        if (holdGamePiece == null) {
            holdGamePiece = currentPiece;
            hold.setPiece(holdGamePiece);
            NewPiece();
        } else {
            GamePiece tempPiece = new GamePiece();
            tempPiece.setShape(currentPiece.GetShape());
            currentPiece = new GamePiece();
            currentPiece.setShape(holdGamePiece.GetShape());
            holdGamePiece.setShape(tempPiece.GetShape());
            hold.setPiece(holdGamePiece);
            StartPiece(currentPiece);
        }
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

    /**
     * A játékos által elért pontszám
     * @return pontszám
     */
    public int getScore() {
        return score;
    }
}


