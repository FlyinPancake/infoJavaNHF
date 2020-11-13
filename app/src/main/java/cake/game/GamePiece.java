package cake.game;

import java.util.Arrays;
import java.util.Random;

/**
 * Tetris  játékelemek
 */
public class GamePiece {
    /**
     * Az alakok, amiket a Tetrominok felvehetnek.
     */
    public static enum Tetromino {
        NoShape, ZShape, SShape, LShape, JShape, IShape, TShape, SQShape
    }

    /**
     * Tetromino alakja.
     */
    private Tetromino pieceShape;
    /**
     * Tetromino blokkjainak koordinátái.
     */
    private int[][] blox;

    /**
     * Készít egy üres GamePiece objektumot.
     */
    public GamePiece() {

        blox = new int[4][2];
        setShape(Tetromino.NoShape);
    }

    
    /** 
     * Beállítja az elem alakját a választott Tetromino-ra.
     * @param t : Tetromino
     */
    public void setShape(Tetromino t) {
        // Tetromino Definíciók, json-ből is lehetne
        //TODO json-ből importált definíciók.
        int[][][] shapes = {
            {{0,0}, {0,0}, {0,0}, {0,0}},  //NoShape
            {{-1,0}, {0,0}, {0,-1}, {1,-1}},  // ZShape
            {{-1,-1}, {0,-1}, {0,0}, {1,0}}, //SShape
            {{-1,0}, {0,0}, {1,0}, {1,1}}, //LShape
            {{-1,0}, {0,0}, {1,0}, {1,-1}}, //JShape
            {{-1,0}, {0,0}, {1,0}, {2,0}}, //IShape
            {{-1,0}, {0,0}, {1,0}, {0,1}}, //TShape
            {{1,1}, {0,0}, {1,0}, {0,1}} //SQShape
        };

        for (int i = 0; i < blox.length; i++) {
            blox[i] = Arrays.copyOf(shapes[t.ordinal()][i],2);
        }

        pieceShape = t;
    }

    /**
     * Véletlenszerűen beállítja a GamePiece alakját valamelyik Tetromino-ra. Kivéve a NoShape-et
     */
    public void randomizeShape(){
        Random ran = new Random();
        Tetromino shape = Tetromino.values()[ran.nextInt(7) + 1];
        setShape(shape);
    }

    
    /** 
     * Balra fordítja a játékelemet, és ezt egy új elemeben adja vissza.
     * Ha az elem négyzet, azt nem kell forgatni, így annak a 
     * @return GamePiece - magát adja vissza
     */
    public GamePiece rotate(){
        //TODO rotate
        if (this.pieceShape == Tetromino.SQShape) {
            return this;
        }

        GamePiece result = new GamePiece();
        for (int i = 0; i < blox.length; i++) {
            result.SetX(i, GetY(i));
            result.SetY(i, -1 * GetX(i));
        }
        return result;
    } 
    
    //TODO javadoc Getterek setterek
    /** 
     * @param at 
     * @param val
     */
    public void SetX(int at, int val) {
        blox[at][0] = val;
    }
    
    
    /** 
     * @param at
     * @param val
     */
    public void SetY(int at, int val) {
        blox[at][1] = val;
    }

    
    /** 
     * @param at
     * @return int
     */
    public int GetX(int at) {
        return blox[at][0];
    }
    
    
    /** 
     * @param at
     * @return int
     */
    public int GetY(int at) {
        return blox[at][1];
    }

    
    /** 
     * @return Tetromino
     */
    public Tetromino GetShape() {
        return pieceShape;
    }
}
