import edu.macalester.graphics.CanvasWindow;
import java.awt.Color;

public class Game {
    private String WINDOW_TITLE = "Dots and Boxes by SCC";
    private int BOUNDARY_SPACE = 10;
    private CanvasWindow canvas;
    private BoardGUI b;

    public Game(int numRows, int numColumns, int boxSize, int dotDiameter, 
                Color edgeColorWhenNotSelected, Color edgeColorWhenHover,
                Color dotColor,
                double edgeThinkness) {
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
        b = new BoardGUI(numRows, numColumns, boxSize, dotDiameter,
                        edgeColorWhenNotSelected, edgeColorWhenHover,
                        dotColor,
                        edgeThinkness);
        this.canvas.add(b, BOUNDARY_SPACE, BOUNDARY_SPACE);

        this.canvas.onMouseMove(e -> {
            b.highlightIfHoveredEdge(e.getPosition());
        });
        
        this.canvas.onDrag(e -> {
            b.highlightIfHoveredEdge(e.getPosition());
        });

        this.canvas.onMouseUp(e -> {
            BoardGUI.Edge edge = b.highlightIfClickedEdge(e.getPosition(), Color.BLUE);
            if(edge != null) {
                // TODO: do something
            }
        });
    }


    public static void main(String[] args) {
        new Game(10, 10, 100, 20, Color.LIGHT_GRAY, Color.RED, Color.DARK_GRAY, 10);
    }
}
