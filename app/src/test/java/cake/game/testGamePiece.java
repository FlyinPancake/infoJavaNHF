package cake.game;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class testGamePiece {
    GamePiece gp;
    @Before
    public void initPieceTest(){
        gp = new GamePiece();
    }

    @Test
    public void testNewGamePiece(){
        assertEquals(GamePiece.Tetromino.NoShape, gp.GetShape());
    }

    @Test
    public void testIShapeGamePiece(){
        gp.setShape(GamePiece.Tetromino.IShape);
        int[][] norota = new int[][] {{-1,0}, {0,0}, {1,0}, {2,0}};
        for (int i = 0; i < norota.length; i++) {
            assertEquals(gp.GetX(i), norota[i][0]); 
            assertEquals(gp.GetY(i), norota[i][1]);
        }
    }

    @Test
    public void testRotateIShape(){
        gp.setShape(GamePiece.Tetromino.IShape);

        int[][] norota =new int[][] {{-1,0}, {0,0}, {1,0}, {2,0}};
        for (int i = 0; i < norota.length; i++) {
            assertEquals(gp.GetX(i), norota[i][0]); 
            assertEquals(gp.GetY(i), norota[i][1]);
        }

        gp = gp.rotateRight();
        int[][] one = new int[][] {{0,1}, {0,0}, {0,-1}, {0,-2}};
        for (int i = 0; i < norota.length; i++) {
            assertEquals(one[i][0], gp.GetX(i));
            assertEquals(one[i][1], gp.GetY(i));
        }
    }

    @Test
    public void testTShape(){
        gp.setShape(GamePiece.Tetromino.TShape);
        int[][] norota = { { -1, -1 }, { 0, -1 }, { 1, -1 }, { 0, 0 } };
        for (int i = 0; i < norota.length; i++) {
            assertEquals(gp.GetX(i), norota[i][0]); 
            assertEquals(gp.GetY(i), norota[i][1]);
        }
    }

    @Test
    public void testQuadRotateTShape() {
        gp.setShape(GamePiece.Tetromino.TShape);
        int[][] norota = { { -1, -1 }, { 0, -1 }, { 1, -1 }, { 0, 0 } };
        for (int i = 0; i < norota.length; i++) {
            assertEquals(gp.GetX(i), norota[i][0]); 
            assertEquals(gp.GetY(i), norota[i][1]);
        }

        for (int i = 0; i < 4; i++) {
            gp = gp.rotateRight();
        }
        
        for (int i = 0; i < norota.length; i++) {
            assertEquals(gp.GetX(i), norota[i][0]); 
            assertEquals(gp.GetY(i), norota[i][1]);
        }
    }

    @Test
    public void testGamePieceEquals() {
        GamePiece gamePiece2 = new GamePiece();
        for (GamePiece.Tetromino t: GamePiece.Tetromino.values()) {
            gp.setShape(t);
            gamePiece2.setShape(t);
            Assert.assertEquals(gp,gamePiece2);
        }
    }

}
