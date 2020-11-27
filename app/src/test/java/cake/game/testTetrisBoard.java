package cake.game;
import static org.junit.Assert.*;
import org.junit.Test;

import cake.game.GamePiece.Tetromino;

public class testTetrisBoard {
    @Test
    public void testNewBoard(){
        TetrisBoard tb = new TetrisBoard();
        for (int xx = 0; xx < tb.getTETRIS_WIDTH(); xx++) {
            for (int yy = 0; yy < tb.getTETRIS_HEIGHT(); yy++) {
                assertEquals(Tetromino.NoShape, tb.shapeAt(xx, yy));
            }
        }
    }
}
