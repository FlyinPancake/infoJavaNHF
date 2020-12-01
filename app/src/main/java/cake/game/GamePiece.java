package cake.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Tetris játékelemek
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
     * Alosztály a setShape metódus segítségére.
     */
    private class Shape {
        private Tetromino type;
        private int[][] blox;

        private Shape() {
        };

        public Shape(Tetromino t, int[][] b) {
            type = t;
            blox = b;
        }

        /**
         * @return the type
         */
        public Tetromino getType() {
            return type;
        }

        /**
         * @return the blox
         */
        public int[][] getBlox() {
            return blox;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            String cords = "";
            for (int i = 0; i < GamePiece.this.blox.length; i++) {
                cords += "[" + blox[i][0] + "," + blox[i][1] + "] ";
            }
            return "Shape: " + type.toString() + " " + cords;
        }
    }

    
    /** 
     * @param f Bemeneti file, ahova irja az eredeti shape definiciokat
     */
    private void CreateShapesFile(File f) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File shapesFile = f;
        try {
            int[][][] shapeBlox = { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, // NoShape
                    { { -1, 0 }, { 0, 0 }, { 0, -1 }, { 1, -1 } }, // ZShape
                    { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 1, 0 } }, // SShape
                    { { -1, 0 }, { 0, 0 }, { 1, 0 }, { -1, -1 } }, // LShape
                    { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, -1 } }, // JShape
                    { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 2, 0 } }, // IShape
                    { { -1, -1 }, { 0, -1 }, { 1, -1 }, { 0, 0 } }, // TShape
                    { { 1, -1 }, { 0, 0 }, { 1, 0 }, { 0, -1 } } // SQShape
            };
            Shape[] shapes = new Shape[shapeBlox.length];
            for (int ii = 0; ii < shapeBlox.length; ii++) {
                shapes[ii] = new Shape(Tetromino.values()[ii], shapeBlox[ii]);
            }
            FileWriter fw = new FileWriter(shapesFile);
            fw.write(gson.toJson(shapes));
            fw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Beállítja az elem alakját a választott Tetromino-ra.
     * 
     * @param t : Tetromino
     */
    public void setShape(Tetromino t) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File shapesFile = new File("shapes.json");

        if (!shapesFile.exists()) {
            CreateShapesFile(shapesFile);
        }
        try {
            BufferedReader fr = new BufferedReader(new FileReader(shapesFile));

            String jsonText = "";
            while (fr.ready()) {
                jsonText += fr.readLine();
            }
            fr.close();

            Shape[] shapeArr = gson.fromJson(jsonText, Shape[].class);

            for (int i = 0; i < blox.length; i++) {
                blox[i] = Arrays.copyOf(shapeArr[t.ordinal()].blox[i], 2);
            }

            pieceShape = t;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException jse) {
            // shape definitions file incorrect, replacing with stamdard
            shapesFile.delete();
            CreateShapesFile(shapesFile);
            setShape(t);
        }

    }

    /**
     * Véletlenszerűen beállítja a GamePiece alakját valamelyik Tetromino-ra. Kivéve
     * a NoShape-et
     */
    public void randomizeShape() {
        Random ran = new Random();
        Tetromino shape = Tetromino.values()[ran.nextInt(7) + 1];
        setShape(shape);
    }

    /**
     * Jobbra fordítja a játékelemet, és ezt egy új elemeben adja vissza. Ha az elem
     * négyzet, azt egyből visszadja, ahogy az eredeti játék is teszi
     * 
     * @return GamePiece - magát adja vissza
     */
    public GamePiece rotateRight() {
        if (this.pieceShape == Tetromino.SQShape) {
            return this;
        }

        GamePiece result = new GamePiece();
        result.setShape(pieceShape);
        for (int i = 0; i < this.blox.length; i++) {
            result.SetX(i, GetY(i));
            result.SetY(i, -1 * GetX(i));
        }
        return result;
    }

    /**
     * Balra fordítja a játékelemet balra, és ezt egy új elemeben adja vissza. Ha az elem
     * négyzet, azt egyből visszadja, ahogy az eredeti játék is teszi
     *
     * @return GamePiece - magát adja vissza
     */
    public GamePiece rotateLeft() {
        if (this.pieceShape == Tetromino.SQShape) {
            return this;
        }

        GamePiece result = new GamePiece();
        result.setShape(pieceShape);
        for (int i = 0; i < this.blox.length; i++) {
            result.SetX(i, -1 * GetY(i));
            result.SetY(i, GetX(i));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePiece gamePiece = (GamePiece) o;
        return pieceShape == gamePiece.pieceShape;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(pieceShape);
        result = 31 * result + Arrays.hashCode(blox);
        return result;
    }

    /**
     * @param at
     * @param val
     */
    private void SetX(int at, int val) {
        blox[at][0] = val;
    }

    /**
     * @param at
     * @param val
     */
    private void SetY(int at, int val) {
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
