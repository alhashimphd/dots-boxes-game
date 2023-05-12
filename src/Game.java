import edu.macalester.graphics.CanvasWindow;

public class Game {
    private String WINDOW_TITLE = "Dots and Boxes by SCC";
    private int BOUNDARY_SPACE = 10;
    private CanvasWindow canvas;

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
        Board b = new Board(numRows, numColumns, boxSize, dotDiameter);
        this.canvas.add(b, BOUNDARY_SPACE, BOUNDARY_SPACE);
    }


    public static void main(String[] args) {
        new Game(20, 20, 30, 10);
    }
}
