import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import java.awt.Color;

public class Game {
    private String WINDOW_TITLE = "Dots and Boxes by SCC";
    private int BOUNDARY_SPACE = 10;
    private CanvasWindow canvas;
    private Board b;

    public Game(int numRows, int numColumns, int boxSize, int dotDiameter) {
        // calculate canvas size based on 
        // - number of rows and number of columns,
        // - box size
        // - dot diameter
        int horizontalSpace = 2*BOUNDARY_SPACE;
        int canvasWidth = numColumns*boxSize + dotDiameter + horizontalSpace;
        int verticalSpace = 2*BOUNDARY_SPACE;
        int canvasHeight = numRows*boxSize + dotDiameter + verticalSpace;

        // create canvas with the calculated dimenstions
        this.canvas = new CanvasWindow(WINDOW_TITLE, canvasWidth, canvasHeight);
        
        // ceate a board & add to canvas
        b = new Board(numRows, numColumns, boxSize, dotDiameter);
        this.canvas.add(b, BOUNDARY_SPACE, BOUNDARY_SPACE);

        this.canvas.onMouseMove(e -> {
            GraphicsObject obj = b.getElementAt(e.getPosition());
            if(obj != null) {
                if(obj instanceof Board.Edge) {
                    Board.Edge edge = (Board.Edge) obj;
                    if(!edge.isClicked()) {
                        edge.hovered();
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
        new Game(10, 10, 50, 10);
    }
}
