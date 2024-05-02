import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTest {
    // >>>>> SETUP <<<<<
    private final int numRows = 2;
    private final int numCols = 3;
    private Board board = new Board(numRows, numCols);

    @Test
    public void addVerticalLineOutsideBoard() {
        // to right of board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(numCols+1, 0, "A"));
        
        // to left of board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(-1, 0, "A"));

        // above board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(-1, 2, "A"));

        // below board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(0, numRows+1, "A"));
    }

    @Test
    public void addHorizontalLineOutsideBoard() {
        // to right of board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(numCols, 0, "A"));
        
        // to left of board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(-1, 0, "A"));

        // above board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(-1, 2, "A"));

        // below board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(0, numRows+1, "A"));
    }

    @Test
    public void addVerticalLineTwice() {
        board.addVerticalLine(0, 0, "A");

        assertThrows(IllegalStateException.class, () -> board.addVerticalLine(0, 0, "A"));
    }

    @Test
    public void addHorizontalLineTwice() {
        board.addHorizontalLine(0, 0, "A");

        assertThrows(IllegalStateException.class, () -> board.addHorizontalLine(0, 0, "A"));
    }

    @Test
    public void makeBoxTopLeft() {
        List<BoxPos> labelledBoxes = new LinkedList<>();
        
        labelledBoxes = board.addHorizontalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(0, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 0, "A");
        assertEquals(1, labelledBoxes.size());
    }

    @Test
    public void makeBoxBottomLeft() {
        List<BoxPos> labelledBoxes = new LinkedList<>();
        
        labelledBoxes = board.addHorizontalLine(0, numRows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(0, numRows, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, numRows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, numRows-1, "A");
        assertEquals(1, labelledBoxes.size());
    }

    @Test
    public void makeBoxTopRight() {
        List<BoxPos> labelledBoxes = new LinkedList<>();
        
        labelledBoxes = board.addHorizontalLine(numCols-1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(numCols-1, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(numCols-1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(numCols, 0, "A");
        assertEquals(1, labelledBoxes.size());
    }


    @Test
    public void makeBoxBottomRight() {
        List<BoxPos> labelledBoxes = new LinkedList<>();
        
        labelledBoxes = board.addHorizontalLine(numCols-1, numRows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(numCols-1, numRows, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(numCols-1, numRows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(numCols, numRows-1, "A");
        assertEquals(1, labelledBoxes.size());
    }

    @Test
    public void makeTwoBoxesHorizontalTop() {
        List<BoxPos> labelledBoxes = new LinkedList<>();

        labelledBoxes = board.addHorizontalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(0, 1, "A");
        assertEquals(labelledBoxes.size(), 0);
        
        labelledBoxes = board.addHorizontalLine(1, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(2, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 0, "A");
        assertEquals(labelledBoxes.size(), 2);
    }

    @Test
    public void makeTwoBoxesHorizontalBottom() {
        List<BoxPos> labelledBoxes = new LinkedList<>();

        labelledBoxes = board.addHorizontalLine(0, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(1, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(0, 2, "A");
        assertEquals(labelledBoxes.size(), 0);
        
        labelledBoxes = board.addHorizontalLine(1, 2, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(2, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 1, "A");
        assertEquals(labelledBoxes.size(), 2);
    }


    @Test
    public void makeTwoBoxesVerticalLeft() {
        List<BoxPos> labelledBoxes = new LinkedList<>();
        
        labelledBoxes = board.addHorizontalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(0, 2, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 1, "A");
        assertEquals(labelledBoxes.size(), 0);
        
        labelledBoxes = board.addHorizontalLine(0, 1, "A");
        assertEquals(labelledBoxes.size(), 2);
    }

    @Test
    public void makeTwoBoxesVerticalMiddle() {
        List<BoxPos> labelledBoxes = new LinkedList<>();
        
        labelledBoxes = board.addHorizontalLine(1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(1, 2, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(2, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(2, 1, "A");
        assertEquals(labelledBoxes.size(), 0);
        
        labelledBoxes = board.addHorizontalLine(1, 1, "A");
        assertEquals(labelledBoxes.size(), 2);
    }

    @Test
    public void makeTwoBoxesVerticalRight() {
        List<BoxPos> labelledBoxes = new LinkedList<>();
        
        labelledBoxes = board.addHorizontalLine(2, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(2, 2, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(2, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(2, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(3, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(3, 1, "A");
        assertEquals(labelledBoxes.size(), 0);
        
        labelledBoxes = board.addHorizontalLine(2, 1, "A");
        assertEquals(labelledBoxes.size(), 2);
    }
}
