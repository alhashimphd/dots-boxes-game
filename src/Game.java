import edu.macalester.graphics.CanvasWindow;
import java.awt.Color;

public class Game {
    private final String WINDOW_TITLE = "Dots and Boxes by SCC";
    private final int BOUNDARY_SPACE = 100;
    private final CanvasWindow canvas;
    private final BoardGUI b;

    public Game(int rows, int cols, int boxSize, int dotDiameter, 
                Color linkColorWhenNotSelected, Color linkColorWhenHover,
                Color dotColor,
                double linkThinkness) {
        // calculate canvas size based on 
        // - number of rows and number of columns,
        // - box size
        // - dot diameter
        int horizontalSpace = 2*BOUNDARY_SPACE;
        int canvasWidth = cols*boxSize + dotDiameter + horizontalSpace;
        int verticalSpace = 2*BOUNDARY_SPACE;
        int canvasHeight = rows*boxSize + dotDiameter + verticalSpace;

        // create canvas with the calculated dimenstions
        canvas = new CanvasWindow(WINDOW_TITLE, canvasWidth, canvasHeight);
        
        // ceate a board & add to canvas
        b = new BoardGUI(rows, cols, boxSize, dotDiameter,
                        linkColorWhenNotSelected, linkColorWhenHover,
                        dotColor,
                        linkThinkness);
        canvas.add(b, BOUNDARY_SPACE, BOUNDARY_SPACE);

        canvas.onMouseMove(e -> b.highlightIfHoveredEdge(e.getPosition()));
        canvas.onDrag(e -> b.highlightIfHoveredEdge(e.getPosition()));
        canvas.onMouseUp(e -> {
            BoardGUI.Edge edge = b.highlightIfClickedEdge(e.getPosition(), Color.BLUE);
            if(edge != null) {
                // TODO: do something
            }
        });
    }


    public static void main(String[] args) {
        new Game(10, 10, 100, 20, Color.LIGHT_GRAY, Color.RED, Color.DARK_GRAY, .3);
    }
}
