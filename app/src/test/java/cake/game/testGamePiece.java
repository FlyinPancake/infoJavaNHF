package cake.game;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import cake.game.GamePiece;


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

        gp.rotate();
        int[][] one = new int[][] {{0,1}, {0,0}, {0,-1}, {0,-2}};
        for (int i = 0; i < norota.length; i++) {
            assertEquals(gp.GetX(i), one[i][0]); 
            assertEquals(gp.GetY(i), one[i][1]);
        }
    }

}
