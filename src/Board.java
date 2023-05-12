import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup; 

public class Board {
    private int numRows;
    private int numCols;
    private double boxSize;
    private double dotSize;
    private GraphicsGroup board;

    public Board(int numRows, int numCols, double boxSize, double dotSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.boxSize = boxSize;
        this.dotSize = dotSize;
        this.buildBoard();
    }

    private void buildBoard() {
        board = new GraphicsGroup();

        for(int row=0; row<this.numRows; row++) {
            for(int col=0; col<this.numCols; col++) {
                // create circle
                Ellipse dot = new Ellipse(0, 0, dotSize, dotSize);

                // place it at position (row*boxSize, col*boxSize)
                board.add(dot, row*boxSize, col*boxSize);
            }
        }
    }

    public GraphicsGroup getBoard() {
        return this.board;
    }
}
