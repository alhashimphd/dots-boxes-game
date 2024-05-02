import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class BoardTest {
    // >>>>> SETUP <<<<<
    private final int rows = 2;
    private final int cols = 3;
    private Board board = new Board(rows, cols);

    @Test
    public void addVerticalLineOutsideBoard() {
        // to right of board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(cols+1, 0, "A"));
        
        // to left of board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(-1, 0, "A"));

        // above board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(-1, 2, "A"));

        // below board
        assertThrows(IllegalArgumentException.class, () -> board.addVerticalLine(0, rows+1, "A"));
    }

    @Test
    public void addHorizontalLineOutsideBoard() {
        // to right of board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(cols, 0, "A"));
        
        // to left of board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(-1, 0, "A"));

        // above board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(-1, 2, "A"));

        // below board
        assertThrows(IllegalArgumentException.class, () -> board.addHorizontalLine(0, rows+1, "A"));
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
        Set<BoxPos> labelledBoxes = new HashSet<>();
        
        labelledBoxes = board.addHorizontalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(0, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, 0, "A");
        assertEquals(Set.of(new BoxPos(0, 0)), labelledBoxes);
    }

    @Test
    public void makeBoxBottomLeft() {
        Set<BoxPos> labelledBoxes = new HashSet<>();
        
        labelledBoxes = board.addHorizontalLine(0, rows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(0, rows, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(0, rows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(1, rows-1, "A");
        assertEquals(Set.of(new BoxPos(0, rows-1)), labelledBoxes);
    }

    @Test
    public void makeBoxTopRight() {
        Set<BoxPos> labelledBoxes = new HashSet<>();
        
        labelledBoxes = board.addHorizontalLine(cols-1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(cols-1, 1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(cols-1, 0, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(cols, 0, "A");
        assertEquals(Set.of(new BoxPos(cols-1, 0)), labelledBoxes);
    }


    @Test
    public void makeBoxBottomRight() {
        Set<BoxPos> labelledBoxes = new HashSet<>();
        
        labelledBoxes = board.addHorizontalLine(cols-1, rows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addHorizontalLine(cols-1, rows, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(cols-1, rows-1, "A");
        assertEquals(labelledBoxes.size(), 0);

        labelledBoxes = board.addVerticalLine(cols, rows-1, "A");
        assertEquals(Set.of(new BoxPos(cols-1, rows-1)), labelledBoxes);
    }

    @Test
    public void makeTwoBoxesHorizontalTop() {
        Set<BoxPos> labelledBoxes = new HashSet<>();

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
        assertEquals(Set.of(new BoxPos(0, 0), new BoxPos(1, 0)), labelledBoxes);
        
    }

    @Test
    public void makeTwoBoxesHorizontalBottom() {
        Set<BoxPos> labelledBoxes = new HashSet<>();

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
        assertEquals(Set.of(new BoxPos(0, 1), new BoxPos(1, 1)), labelledBoxes);
    }


    @Test
    public void makeTwoBoxesVerticalLeft() {
        Set<BoxPos> labelledBoxes = new HashSet<>();
        
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
        assertEquals(Set.of(new BoxPos(0, 0), new BoxPos(0, 1)), labelledBoxes);
    }

    @Test
    public void makeTwoBoxesVerticalMiddle() {
        Set<BoxPos> labelledBoxes = new HashSet<>();
        
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
        assertEquals(Set.of(new BoxPos(1, 0), new BoxPos(1, 1)), labelledBoxes);
    }

    @Test
    public void makeTwoBoxesVerticalRight() {
        Set<BoxPos> labelledBoxes = new HashSet<>();
        
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
        assertEquals(Set.of(new BoxPos(2, 0), new BoxPos(2, 1)), labelledBoxes);

    }
}
